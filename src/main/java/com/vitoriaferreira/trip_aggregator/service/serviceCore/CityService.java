package com.vitoriaferreira.trip_aggregator.service.serviceCore;

import java.text.Normalizer;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.vitoriaferreira.trip_aggregator.dto.response.CityResponse;
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

        // normalisa palavra do user
        String normalizedQuery = normalize(query);

        // vai lá no IBGE e traz a lista de todas as cidades do Brasil
        // Com a lista completa na memória do seu computador, inicia o
        // .stream().filter(...) para cada palavra verifica se inicia com o que veio do
        // ususaio
        return ibgeCityClient.fetchAllCities().stream() // Filtra apenas as que começam com a query normalizada.
                .filter(city -> normalize(city.getNome()).startsWith(normalizedQuery))
                .map(city -> city.getNome() + "-" + city.getState())
                .limit(10) // Lista é filtrada, normalizada e limitada a 10 resultados
                .collect(Collectors.toList());
    }

    // FORMATACAO URL - PEGA JA NORMALIZADO E TROCA ESPACO POR -
    // SLUG - cidade - troca espacos por hifens
    public String toSlug(String city) {
        return normalize(city)
                .replace(" ", "-");
    }

    // SLUG - estado
    public String toSlugWithState(CityResponse city) {
        return toSlug(city.getNome() + " " + city.getState()); // ex: "Curitiba PR" -> "curitiba-pr"
    }

    // Normalizacao - ACENTOS, MINUSCULAS
    private String normalize(String value) {
        return Normalizer.normalize(value, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "")
                .toLowerCase()
                .trim();
    }

}
