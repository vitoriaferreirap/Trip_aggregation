package com.vitoriaferreira.trip_aggregator.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vitoriaferreira.trip_aggregator.dto.CityResponse;
import com.vitoriaferreira.trip_aggregator.dto.TripResponse;
import com.vitoriaferreira.trip_aggregator.service.TripService;

@RestController
@RequestMapping("/trips")
public class TripController {

    private final TripService tripService;

    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @GetMapping("/search")
    public List<TripResponse> searchTrips(
            @RequestParam String origin, // formato: cidade-uf
            @RequestParam String destination, // formato: cidade-uf
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        // divide "cidade-uf" em nome + estado
        String[] originParts = origin.split("-");
        String[] destinationParts = destination.split("-");

        CityResponse originCity = new CityResponse();
        originCity.setNome(originParts[0]);
        originCity.setState(originParts[1]);

        CityResponse destinationCity = new CityResponse();
        destinationCity.setNome(destinationParts[0]);
        destinationCity.setState(destinationParts[1]);

        return tripService.searchTrips(originCity, destinationCity, date);
    }
}
