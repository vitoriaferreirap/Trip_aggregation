package com.vitoriaferreira.trip_aggregator.service;

import java.time.Instant;
import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.vitoriaferreira.trip_aggregator.entity.PriceSnapshot;
import com.vitoriaferreira.trip_aggregator.repository.PriceSnapshotRepository;

@Service
public class PriceSnapshotService {

    // injecao dependencia reposiptory
    private final PriceSnapshotRepository repository;

    public PriceSnapshotService(PriceSnapshotRepository repository) {
        this.repository = repository;
    }

    // metodo para salvar busca
    public PriceSnapshot savePrice(String originCity, String destinationCity,
            String company, LocalDate travelDate,
            String departureTime, String arrivalTime,
            String seatType, double price) {

        // intancia novo obj
        PriceSnapshot snapshot = new PriceSnapshot(
                originCity,
                destinationCity,
                company,
                travelDate,
                departureTime,
                arrivalTime,
                seatType,
                price,
                Instant.now() // momento da coleta
        );

        return repository.save(snapshot);
    }
}

/**
 * recebe uma lista de viagens
 * identifica:
 * menor preço atual
 * variação em relação ao histórico
 * se houve queda relevante
 * 
 * 
 * preço atual < menor preço registrado anteriormente
 * ou queda ≥ X%
 * 
 * 
 * job agendado (ex.: a cada 1h ou 1x por dia)
 * executa:
 * scraping
 * persistência
 * análise
 * disparo de alerta
 */
