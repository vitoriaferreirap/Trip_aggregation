package com.vitoriaferreira.trip_aggregator.service.serviceStorage;

import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Service;

import com.vitoriaferreira.trip_aggregator.dto.response.TripResponse;
import com.vitoriaferreira.trip_aggregator.entity.PriceSnapshot;
import com.vitoriaferreira.trip_aggregator.repository.PriceSnapshotRepository;

@Service
public class PriceSnapshotService {

    // injecao dependencia reposiptory
    private final PriceSnapshotRepository repository;

    public PriceSnapshotService(PriceSnapshotRepository repository) {
        this.repository = repository;

    }

    // parametro que define quantos obj seram salvos por vez
    public void save(List<TripResponse> trips) {
        for (TripResponse trip : trips) {

            PriceSnapshot snapshot = new PriceSnapshot(
                    trip.getOrigin(),
                    trip.getDestination(),
                    trip.getDate(),
                    trip.getDepartureTime(),
                    trip.getArrivalTime(),
                    trip.getSeatType(),
                    trip.getCompany(),
                    trip.getPrice(),
                    Instant.now());
            repository.save(snapshot);
        }
    }

}
