package com.vitoriaferreira.trip_aggregator.integration;

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

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.vitoriaferreira.trip_aggregator.dto.response.TripResponse;

@Component
public class DeOnibusScrapingClient {

    private static final String PLATFORM_NAME = "RodoviariaCuritiba";
    private static final List<String> SEAT_TYPES = List.of(
            "semileito",
            "leito cama",
            "leito",
            "convencional",
            "executivo");

    public List<TripResponse> searchTrips(String originSlug, String destinationSlug, LocalDate date) {

        List<TripResponse> results = new ArrayList<>();
        String formattedDate = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String baseUrl = "https://rodoviariacuritiba.com.br/passagens-de-onibus/";

        String[] urls = {
                baseUrl + originSlug + "-para-" + destinationSlug + "-todos?departureDate=" + formattedDate,
                baseUrl + originSlug + "-todos-para-" + destinationSlug + "?departureDate=" + formattedDate,
                baseUrl + originSlug + "-para-" + destinationSlug + "?departureDate=" + formattedDate,
                baseUrl + originSlug + "-todos-para-" + destinationSlug + "-todos?departureDate=" + formattedDate
        };

        // PLAYWRIGHT

        // Cria o navegador (Chromium) de forma automática
        try (Playwright playwright = Playwright.create()) {
            // launch(new BrowserType.LaunchOptions().setHeadless(true)) - roda o navegador
            // "escondido"
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
            BrowserContext context = browser.newContext(new Browser.NewContextOptions()
                    .setUserAgent(
                            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36"));

            Page page = context.newPage();
            Elements trips = new Elements();

            for (String url : urls) {
                try {
                    System.out.println("Tentando acessar via Playwright: " + url);
                    page.navigate(url);

                    // Peça para o Playwright esperar até que as tags 'li' de viagem apareçam na
                    // tela
                    // Timeout de 30 segundos para dar tempo do JS renderizar
                    page.waitForSelector("li[itemtype='https://schema.org/BusTrip']",
                            new Page.WaitForSelectorOptions().setTimeout(30000));

                    // Pega o HTML da página já renderizada
                    String renderedHtml = page.content();
                    Document document = Jsoup.parse(renderedHtml);
                    trips = document.select("li[itemtype='https://schema.org/BusTrip']");

                    if (!trips.isEmpty()) {
                        break; // Achou as viagens, pode parar de tentar as URLs
                    }

                } catch (Exception e) {
                    System.err.println("Erro ou Timeout na URL: " + url + " | " + e.getMessage());
                }
            }

            browser.close(); // Fecha o navegador

            // Se após todas as URLs ainda estiver vazio, lança a exceção
            if (trips.isEmpty()) {
                throw new IllegalStateException(
                        "Página carregada, mas as viagens não apareceram (Bloqueio ou seletor mudou).");
            }

            for (Element trip : trips) {
                String departureTime = trip.select("span[itemprop='departureTime']").text();
                String arrivalTime = trip.selectFirst("span[itemprop='arrivalTime']").text();
                String price = trip.select("span[itemprop='price']").text();

                String originCity = trip.select("div[itemprop='departureBusStop'] span").first().text();
                String destinationCity = trip.select("div[itemprop='arrivalBusStop'] span").first().text();

                Element providerEl = trip.selectFirst("[itemprop=Provider], [itemprop=provider]");
                String company = (providerEl != null) ? providerEl.text() : "Viagem com conexão.";

                String rawText = trip.text();
                String seatType = extractSeatType(rawText);
                BigDecimal priceValue = new BigDecimal(price.replace(".", "").replace(",", "."));

                TripResponse objTrip = new TripResponse(
                        originCity, destinationCity, date, departureTime,
                        arrivalTime, seatType, priceValue, company, PLATFORM_NAME);

                results.add(objTrip);
            }
        }

        return results;
    }

    private String extractSeatType(String text) {
        String lowerText = text.toLowerCase();
        for (String seat : SEAT_TYPES) {
            if (lowerText.contains(seat))
                return seat;
        }
        return "desconhecido";
    }
}