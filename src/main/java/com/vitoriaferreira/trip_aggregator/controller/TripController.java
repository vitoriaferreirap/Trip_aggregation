package com.vitoriaferreira.trip_aggregator.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vitoriaferreira.trip_aggregator.dto.TripRequest;
import com.vitoriaferreira.trip_aggregator.dto.TripResponse;
import com.vitoriaferreira.trip_aggregator.service.TripService;

@RestController
@RequestMapping("/trips")
public class TripController {

    private final TripService tripService;

    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    /**
     * Esse GET recebe a busca (TripRequest)
     * Chama o TripService.searchTrips(request)
     * Retorna a lista de viagens (TripResponse) para o front-end
     * 
     * ATO DE PESQUISAR JA SALVARA NO BANCO OS DADOS DA PESQUISA
     */

    @GetMapping("/search")
    public List<TripResponse> searchTrips(TripRequest request) {
        // controller(TripRequest) -> TripService(originSlug) -> CityService(slug) - >
        // scrapgClient(searchTrips) (ida)
        return tripService.searchTrips(request);
    }
}
