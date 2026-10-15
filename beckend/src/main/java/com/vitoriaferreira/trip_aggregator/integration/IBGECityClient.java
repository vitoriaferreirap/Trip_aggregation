package com.vitoriaferreira.trip_aggregator.integration;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.vitoriaferreira.trip_aggregator.dto.response.CityResponse;

@Component
public class IBGECityClient {

    private static final String IBGE_URL = "https://servicodados.ibge.gov.br/api/v1/localidades/municipios";
    // leva pedido ate a API
    private final RestTemplate restTemplate = new RestTemplate();

    @SuppressWarnings("unchecked") // retira avisos
    public List<CityResponse> fetchAllCities() {
        // busca todas as cidades
        List<Map<String, Object>> response = restTemplate.getForObject(IBGE_URL, List.class);

        // verifica se a API retornou algo
        if (response == null) {
            return List.of(); // retorna lista vazia
        }

        // stream()
        return response.stream().map(cityMap -> {
            CityResponse city = new CityResponse();
            city.setId(Long.valueOf(cityMap.get("id").toString()));
            city.setNome((String) cityMap.get("nome"));

            // pegar UF de forma segura
            Map<String, Object> microrregiao = (Map<String, Object>) cityMap.get("microrregiao");
            String uf = "";
            if (microrregiao != null) {
                Map<String, Object> mesorregiao = (Map<String, Object>) microrregiao.get("mesorregiao");
                if (mesorregiao != null) {
                    Map<String, Object> ufMap = (Map<String, Object>) mesorregiao.get("UF");
                    if (ufMap != null) {
                        uf = (String) ufMap.get("sigla"); // pega a sigla da UF
                    }
                }
            }
            city.setState(uf);

            return city;
        }).toList();
    }
}
