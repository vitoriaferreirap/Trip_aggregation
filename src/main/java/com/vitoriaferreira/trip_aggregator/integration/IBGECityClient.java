package com.vitoriaferreira.trip_aggregator.integration;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.vitoriaferreira.trip_aggregator.dto.CityResponse;

@Component
public class IBGECityClient {
    // faz requisicao HTPP para IBGE e tranforma a res em obj Java - CityResponse
    private static final String IBGE_URL = "https://servicodados.ibge.gov.br/api/v1/localidades/municipios";

    private final RestTemplate restTemplate = new RestTemplate();

    public List<CityResponse> fetchAllCities() {
        // RestTemplate.getForObject: envia uma requisição GET para a URL e espera
        // receber um JSON que será convertido para um array de CityResponse
        CityResponse[] response = restTemplate.getForObject(IBGE_URL, CityResponse[].class);

        return Arrays.asList(response);// converte array em lista
    }
}
