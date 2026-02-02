package com.vitoriaferreira.trip_aggregator.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vitoriaferreira.trip_aggregator.dto.Trip_Response;
import com.vitoriaferreira.trip_aggregator.service.TripService;

@RestController // revebe req http API REST - retorna json automatico
@RequestMapping("/trips")
public class TripController {

    // injecao dependencia - tripService no contrutor do TripController
    private final TripService tripService;

    // spring permite controller usar dependencia tripService ja que precisa dele,
    // sem instanciar um novo obj
    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    // endpoints ( parametros entrada cliente para minha API)
    @GetMapping("/search")
    public List<Trip_Response> searchTrips( // busca cliente
            @RequestParam String origin,
            @RequestParam String destination,
            @RequestParam LocalDate date // apenas data sem fuso
    ) {
        // controller chama service
        return tripService.searchTrips(origin, destination, date);
    }

}
