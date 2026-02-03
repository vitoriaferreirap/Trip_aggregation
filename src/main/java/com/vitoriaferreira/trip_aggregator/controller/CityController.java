package com.vitoriaferreira.trip_aggregator.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vitoriaferreira.trip_aggregator.service.CityService;

@RestController
public class CityController {

    // injecao dependencia
    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping("/cities/autocomplete") // Chama o CityService para retornar os resultados já filtrados.
    public List<String> autocomplete(@RequestParam String palavra) {
        return cityService.autocomplete(palavra);
    }
}
