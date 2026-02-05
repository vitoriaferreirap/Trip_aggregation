package com.vitoriaferreira.trip_aggregator.service;

import java.math.BigDecimal;
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
            LocalDate travelDate,
            String departureTime, String arrivalTime,
            String seatType, String company, BigDecimal price) {

        // intancia novo obj - recupera dados passado como paramentro TripResponse e
        // tranforma em um novo obj a entidade
        PriceSnapshot snapshot = new PriceSnapshot(
                originCity,
                destinationCity,
                travelDate,
                departureTime,
                arrivalTime,
                seatType,
                company,
                price,
                Instant.now() // momento da coleta
        );

        /**
         * ANALISE HISTORICO E PADROES
         * Criar um método para buscar o menor preço registrado para determinada rota,
         * data e companhia.
         * Criar lógica para detectar variações significativas e gerar alerta.
         */

        /**
         * AUTOMATIZACAO PRECOS E EMPRESAS
         */

        return repository.save(snapshot);
    }
}
