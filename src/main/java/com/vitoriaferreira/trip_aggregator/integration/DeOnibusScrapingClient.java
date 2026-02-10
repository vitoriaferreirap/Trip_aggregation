package com.vitoriaferreira.trip_aggregator.integration;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import com.vitoriaferreira.trip_aggregator.dto.response.TripResponse;

/**
 * Esta classe é responsável por fazer SCRAPING.
 *
 * Em termos simples:
 * - Ela acessa um site externo
 * - Baixa o HTML da página
 * - Lê esse HTML como se fosse uma árvore
 * - Extrai informações específicas de cada viagem
 */
@Component
public class DeOnibusScrapingClient {

    private static final String PLATFORM_NAME = "RodoviariaCuritiba";

    /**
     * TIPOS DE POLTRONA
     * Lista fechada (whitelist).
     * Se aparecer algo fora disso, ignoramos.
     * CONSTANTE ESTATICA GLOBAL
     * Filtro semantico
     */
    private static final List<String> SEAT_TYPES = List.of(
            "semileito",
            "leito cama",
            "leito",
            "convencional",
            "executivo");

    /**
     * Método principal de scraping.
     * Ele recebe:
     * - origem (slug: cidade-uf)
     * - destino (slug: cidade-uf)
     * - data
     * E devolve uma lista de TripResponse.
     */
    public List<TripResponse> searchTrips(
            String originSlug,
            String destinationSlug,
            LocalDate date) {

        // guardar o resultado final
        List<TripResponse> results = new ArrayList<>();

        // O site espera a data nesse formato
        String formattedDate = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        // Montagem da URL exatamente como o site usa
        String baseUrl = "https://rodoviariacuritiba.com.br/passagens-de-onibus/";

        String[] urls = {
                baseUrl + originSlug + "-para-" + destinationSlug + "-todos?departureDate=" + formattedDate,
                baseUrl + originSlug + "-todos-para-" + destinationSlug + "?departureDate=" + formattedDate,
                baseUrl + originSlug + "-para-" + destinationSlug + "?departureDate=" + formattedDate,
                baseUrl + originSlug + "-todos-para-" + destinationSlug + "-todos?departureDate=" + formattedDate
        };

        Elements trips = new Elements();

        /**
         * Jsoup faz uma requisição HTTP real, recebe o HTML bruto da página e
         * transforma esse HTML em um objeto Document. Esse Document é uma árvore DOM
         */

        Document document = null;

        for (String url : urls) {
            try {
                document = Jsoup.connect(url)
                        .userAgent("Mozilla/5.0")
                        .timeout(15_000)
                        .get();

                trips = document.select("li[itemtype='https://schema.org/BusTrip']");

                if (!trips.isEmpty()) {
                    break; // achou uma URL válida
                }

            } catch (IOException e) {
                // 404 ou erro de rota → tenta a próxima URL
                continue;
            }
        }
        if (trips.isEmpty()) {
            throw new IllegalStateException(
                    "Página carregada, mas viagens são renderizadas via JavaScript. Jsoup não suporta esse fluxo.");
        }

        // Para cada viagem, extraímos os dados
        for (Element trip : trips) {
            // dentro dessa árvore HTML, me devolva todos os nós que representam uma viagem
            // aqui o site usou itemprop, que é uma marcação semântica estavel

            // Horário de partida
            String departureTime = trip.select("span[itemprop='departureTime']")
                    .text(); // EXRAR TEXTO DENTRO DAS

            // Horario de chegada
            String arrivalTime = trip.selectFirst("span[itemprop='arrivalTime']")
                    .text();

            // Preço
            String price = trip.select("span[itemprop='price']")
                    .text();

            // Cidade de origem
            String originCity = trip.select("div[itemprop='departureBusStop'] span")
                    .first()
                    .text();

            // Cidade de destino
            String destinationCity = trip.select("div[itemprop='arrivalBusStop'] span")
                    .first()
                    .text();

            Element providerEl = trip.selectFirst("[itemprop=Provider], [itemprop=provider]");
            String company;
            if (providerEl != null) {
                company = providerEl.text();
            } else {
                company = "Viagem com conexão.";
            }
            ;

            /**
             * Texto bruto do card inteiro.
             * Aqui vem TUDO misturado:
             * horário, duração, poltrona, embarque fácil
             * ex: me de todo o texto dessa card, mesmo que baguncado
             */
            String rawText = trip.text();

            // CONVERSOES:
            // Extrações usando lista para filtros
            String seatType = extractSeatType(rawText);

            // Conversão de preço - string para BigDecima
            // remove separacao de milhar e converte o separador decimap para padrao java
            BigDecimal priceValue = new BigDecimal(price.replace(".", "").replace(",", "."));

            // Cria o objeto TripResponse
            TripResponse objTrip = new TripResponse(
                    originCity,
                    destinationCity,
                    date,
                    departureTime,
                    arrivalTime,
                    seatType,
                    priceValue,
                    company,
                    PLATFORM_NAME);

            results.add(objTrip);
        }

        return results;
    }

    // metodos auxiliares que ajudam na filtragem
    // Procura apenas tipos de poltrona válidos
    private String extractSeatType(String text) {
        String lowerText = text.toLowerCase();

        for (String seat : SEAT_TYPES) {
            if (lowerText.contains(seat)) {
                return seat;
            }
        }

        return "desconhecido";
    }

}
