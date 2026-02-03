package com.vitoriaferreira.trip_aggregator.integration;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

/**
 * Classe responsável por scraping (engenharia reversa de frontend).
 * Integração com um site externo via HTTP + parse de HTML usando Jsoup
 * Função:
 * - Acessar um site externo via HTTP
 * - Baixar o HTML da página de resultados
 * - Navegar na árvore DOM
 * - Extrair dados de viagens
 */

@Component // bens
public class DeOnibusScrapingClient {

    // Executa a busca de viagens no site externo.

    // SLOGS NO FORMATO QUE O SITE ESPERA
    public List<String> searchTrips(String originSlug,
            String destinationSlug,
            LocalDate date) {

        // Lista que irá armazenar os resultados extraídos
        List<String> results = new ArrayList<>();

        // Montar URL de busca
        String url = buildSearchUrl(originSlug, destinationSlug, date);

        // Baixar e parsear o HTML
        Document document;
        try {
            document = Jsoup
                    .connect(url) // abre conexão HTTP com o site.
                    .userAgent("Mozilla/5.0") // simula navegador real para evitar bloqueio do site
                    .get(); // faz o GET e retorna o HTML como objeto Document
        } catch (Exception e) {
            throw new RuntimeException("Erro ao acessar o site externo.", e);
        }

        // Selecionar os elementos HTML que representam viagens
        Elements trips = document.select(
                "li[itemtype='https://schema.org/BusTrip']");

        // Extração dos dados de cada viagem
        for (Element trip : trips) {

            String departureTime = trip.select("span[itemprop='departureTime']").text();

            String price = trip.select("span[itemprop='price']").text();

            String originCity = trip.select("div[itemprop='departureBusStop'] span").first().text();

            String destinationCity = trip.select("div[itemprop='arrivalBusStop'] span").first().text();

            // Temporário: juntar tudo em uma string
            String summary = originCity + " -> " +
                    destinationCity + " | " +
                    departureTime + " | R$ " +
                    price;

            results.add(summary);
        }

        return results;
    }

    /**
     * Monta a URL real utilizada pelo site.
     * replica a regra de roteamento do site
     */
    private String buildSearchUrl(String originSlug,
            String destinationSlug,
            LocalDate date) {

        String baseUrl = "https://rodoviariacuritiba.com.br/passagens-de-onibus/";

        String path = originSlug + "-para-" + destinationSlug + "-todos";

        String formattedDate = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        return baseUrl + path + "?departureDate=" + formattedDate;
    }
}
