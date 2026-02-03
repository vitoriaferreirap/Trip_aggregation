package com.vitoriaferreira.trip_aggregator.service;

import java.text.Normalizer;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.vitoriaferreira.trip_aggregator.dto.CityResponse;
import com.vitoriaferreira.trip_aggregator.integration.IBGECityClient;

@Service
public class CityService {
    // Converter cidade para slug não é scraping É normalização de dados, isso fica
    // no service - AUTOMPLETE

    // injecao de dependencia
    private final IBGECityClient ibgeCityClient;

    public CityService(IBGECityClient ibgeCityClient) {
        this.ibgeCityClient = ibgeCityClient;
    }

    public List<String> autocomplete(String query) {

        String normalizedQuery = normalize(query);

        return ibgeCityClient.fetchAllCities().stream() // Filtra apenas as que começam com a query normalizada.
                .filter(city -> normalize(city.getNome()).startsWith(normalizedQuery))
                .map(CityResponse::getNome) // só o nome da cidade
                .limit(10) // Lista é filtrada, normalizada e limitada a 10 resultados
                .collect(Collectors.toList());
    }

    // SLUG - troca espacos por hifens
    public String toSlug(String city) {
        return normalize(city)
                .replace(" ", "-");
    }

    // Normalizacao - remove acentos e caracteres especiais
    private String normalize(String value) {
        return Normalizer.normalize(value, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "")
                .toLowerCase()
                .trim();
    }

}
