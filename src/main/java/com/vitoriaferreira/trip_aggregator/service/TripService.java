package com.vitoriaferreira.trip_aggregator.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.vitoriaferreira.trip_aggregator.dto.Trip_Response;

/*
Regra de negocio: 
receber dados de busca
devolver viagens encontradas
retornar obj bem definidos
*/
@Service
public class TripService {

    public List<Trip_Response> searchTrips(String origin, String destination, LocalDate date) {
        // simulacao de dados - mock
        // Receber critérios de busca e produzir uma lista de viagens possíveis.
        Trip_Response trip1 = new Trip_Response(
                origin,
                destination,
                date,
                170.00,
                "ClickBus");
        return List.of(trip1);
    }

}
