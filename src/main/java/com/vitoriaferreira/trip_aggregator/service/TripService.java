package com.vitoriaferreira.trip_aggregator.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.vitoriaferreira.trip_aggregator.dto.CityResponse;
import com.vitoriaferreira.trip_aggregator.integration.DeOnibusScrapingClient;

@Service
public class TripService {

    private final CityService cityService;
    private final DeOnibusScrapingClient deOnibusScrapingClient;

    public TripService(CityService cityService, DeOnibusScrapingClient deOnibusScrapingClient) {
        this.cityService = cityService;
        this.deOnibusScrapingClient = deOnibusScrapingClient;
    }

    public List<String> searchTrips(CityResponse originCity, CityResponse destinationCity, LocalDate date) {

        // cria slugs normalizados
        String originSlug = cityService.toSlug(originCity.getNome() + "-" + originCity.getState());
        String destinationSlug = cityService.toSlug(destinationCity.getNome() + "-" + destinationCity.getState());

        // chama scraping
        return deOnibusScrapingClient.searchTrips(originSlug, destinationSlug, date);
    }
}