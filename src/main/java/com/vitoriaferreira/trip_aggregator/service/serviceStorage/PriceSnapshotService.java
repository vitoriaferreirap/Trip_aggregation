package com.vitoriaferreira.trip_aggregator.service.serviceStorage;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vitoriaferreira.trip_aggregator.dto.response.TripResponse;
import com.vitoriaferreira.trip_aggregator.entity.PriceSnapshot;
import com.vitoriaferreira.trip_aggregator.entity.TripSearch;
import com.vitoriaferreira.trip_aggregator.repository.PriceSnapshotRepository;

@Service
public class PriceSnapshotService {

    @Autowired
    private final PriceSnapshotRepository repository;

    public PriceSnapshotService(PriceSnapshotRepository repository) {
        this.repository = repository;

    }

    // parametro que define quantos obj seram salvos por vez
    public void save(List<TripResponse> trips, TripSearch tripSearchId) {
        // lista para salvar em lote e acessar uma vez o banco
        List<PriceSnapshot> listSave = new ArrayList<>();

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
                    Instant.now(),
                    tripSearchId); // vinculo id de busca
            listSave.add(snapshot);
        }
        // salva em lote, 1 conexao com o banco apenas
        repository.saveAll(listSave);
    }

    // BUSCAR (Chamado quando a busca já existia no banco hoje)
    // tranforma entidade do banco em DTO
    public List<TripResponse> buscaPorId(Long tripSearchId) {
        // recupera option - viagens com a fk de busca
        // acessa banco 1 vez
        List<PriceSnapshot> snapshots = repository.findByTripSearchId_Id(tripSearchId);
        // recebe retorno do repository
        List<TripResponse> response = new ArrayList<>();

        for (PriceSnapshot objSnapshot : snapshots) {
            TripResponse dto = new TripResponse(
                    objSnapshot.getOriginCity(),
                    objSnapshot.getDestinationCity(),
                    objSnapshot.getTravelDate(),
                    objSnapshot.getDepartureTime(),
                    objSnapshot.getArrivalTime(),
                    objSnapshot.getSeatType(),
                    objSnapshot.getPrice(),
                    objSnapshot.getCompany(),
                    "RodoviariaCuritiba");
            response.add(dto);
        }
        return response; // retorna dto ao front
    }
}
