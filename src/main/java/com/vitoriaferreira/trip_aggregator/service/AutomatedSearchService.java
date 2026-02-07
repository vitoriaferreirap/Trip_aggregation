package com.vitoriaferreira.trip_aggregator.service;

import java.time.LocalDate;
import java.util.List;

import com.vitoriaferreira.trip_aggregator.dto.RouteAutomatedSearch;
import com.vitoriaferreira.trip_aggregator.dto.TripRequest;
import com.vitoriaferreira.trip_aggregator.dto.TripResponse;

public class AutomatedSearchService {

    private final TripService tripService;
    private final PriceSnapshotService priceSnapshotService;

    public AutomatedSearchService(TripService tripService, PriceSnapshotService priceSnapshotService) {
        this.tripService = tripService;
        this.priceSnapshotService = priceSnapshotService;
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
            TripRequest request = new TripRequest(route.getOrigin(), route.getDestination(), LocalDate.now());
            // chama service e metodo de busca retornando dados do site TripResponse
            List<TripResponse> trips = tripService.searchTrips(request);

            priceSnapshotService.save(trips);
        }
    }
}
