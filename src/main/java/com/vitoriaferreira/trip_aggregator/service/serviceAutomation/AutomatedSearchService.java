package com.vitoriaferreira.trip_aggregator.service.serviceAutomation;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.vitoriaferreira.trip_aggregator.dto.automation.RouteAutomatedSearch;
import com.vitoriaferreira.trip_aggregator.dto.request.TripRequest;
import com.vitoriaferreira.trip_aggregator.service.serviceCore.TripService;
import com.vitoriaferreira.trip_aggregator.service.serviceStrategy.PriceAlertService;

@Service
public class AutomatedSearchService {
    // Orquestra o tempo e as rotas da automacao

    private final TripService tripService;
    private final PriceAlertService priceAlertService;

    public AutomatedSearchService(TripService tripService, PriceAlertService priceAlertService) {
        this.tripService = tripService;
        this.priceAlertService = priceAlertService;
    }

    public void execute() {
        List<RouteAutomatedSearch> routes = List.of(
                new RouteAutomatedSearch("Curitiba-PR", "Florianópolis-SC"),
                new RouteAutomatedSearch("Curitiba-PR", "Porto Alegre-RS"),
                new RouteAutomatedSearch("Florianópolis-SC", "Porto Alegre-RS"),
                new RouteAutomatedSearch("Florianópolis-SC", "Curitiba-PR"),
                new RouteAutomatedSearch("Porto Alegre-RS", "Curitiba-PR"),
                new RouteAutomatedSearch("Porto Alegre-RS", "Florianópolis-SC"));

        for (RouteAutomatedSearch route : routes) {
            // envio de dados de entrada
            try {
                System.out.println(
                        "Iniciando busca automatizada: " + route.getOrigin() + " para " + route.getDestination());

                // alvo da busca dia atual + 1
                LocalDate dataAlvo = LocalDate.now().plusDays(1);
                TripRequest request = new TripRequest(route.getOrigin(), route.getDestination(), dataAlvo);

                // TripService aplica a logica necessaria para a busca
                tripService.searchTrips(request);

                // busca que foi feita, pede analise do banco
                priceAlertService.analyzePriceDrop(route.getOrigin(), route.getDestination());

                // JITTER
                // Espera entre 15 e 30 segundos antes de ir para a próxima cidade
                long delay = 15000 + (long) (Math.random() * 15000);
                Thread.sleep(delay);

            } catch (Exception e) {
                System.err.println("❌ Falha na automação da rota " + route.getOrigin() + ": " + e.getMessage());
                // Se der erro de Timeout (bloqueio)
                break;
            }
        }
    }
}