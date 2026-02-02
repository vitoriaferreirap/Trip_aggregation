package com.vitoriaferreira.trip_aggregator.controller;

import java.time.LocalDate;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController // revebe req http API REST - retorna json automatico
@RequestMapping("/trips")
public class TripController {

    // endpoints ( parametros entrada cliente para minha API)
    @GetMapping("/search")
    public String searchTrips( // busca cliente
            @RequestParam String origin,
            @RequestParam String destination,
            @RequestParam LocalDate date // apenas data sem fuso
    ) {
        return "Dados recebidos: " + origin + ", " + destination + ", " + date;
    }

}
