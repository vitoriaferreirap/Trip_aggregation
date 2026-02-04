package com.vitoriaferreira.trip_aggregator.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.vitoriaferreira.trip_aggregator.dto.TripRequest;
import com.vitoriaferreira.trip_aggregator.dto.TripResponse;
import com.vitoriaferreira.trip_aggregator.integration.DeOnibusScrapingClient;

@Service
public class TripService {

    // injecao de dependencia
    private final CityService cityService;
    private final DeOnibusScrapingClient deOnibusScrapingClient;

    // construtor - entra apenas dependencias que o service usa para trabalhar, nao
    // dados de entrada
    public TripService(CityService cityService, DeOnibusScrapingClient deOnibusScrapingClient) {
        this.cityService = cityService;
        this.deOnibusScrapingClient = deOnibusScrapingClient;
    }

    // metodo
    public List<TripResponse> searchTrips(TripRequest request) {

        // request.getOrigin() - apenas transporta dados de entrada/ dto para leitura
        // dado devolvido - retorno ja resolvido - slug e normalizacao
        // cityService.toSlug(originSlug) - string normalizada retorn
        String originSlug = cityService.toSlug(request.getOrigin());
        String destinationSlug = cityService.toSlug(request.getDestination());
        LocalDate date = request.getDate();

        // nao executa nada novo, na volta ira retornar o que o scraping ja produziu
        return deOnibusScrapingClient.searchTrips(originSlug, destinationSlug, date);
    }
}
