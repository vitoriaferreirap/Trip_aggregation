package com.vitoriaferreira.trip_aggregator.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.vitoriaferreira.trip_aggregator.dto.TripRequest;
import com.vitoriaferreira.trip_aggregator.dto.TripResponse;
import com.vitoriaferreira.trip_aggregator.integration.DeOnibusScrapingClient;

@Service
public class TripService {

    // injecao de dependencia
    private final CityService cityService;
    private final DeOnibusScrapingClient deOnibusScrapingClient;
    private final PriceSnapshotService priceSnapshotService;

    // construtor - entra apenas dependencias que o service usa para trabalhar, nao
    // dados de entrada
    public TripService(CityService cityService, DeOnibusScrapingClient deOnibusScrapingClient,
            PriceSnapshotService priceSnapshotService) {
        this.cityService = cityService;
        this.deOnibusScrapingClient = deOnibusScrapingClient;
        this.priceSnapshotService = priceSnapshotService;
    }

    // metodo
    public List<TripResponse> searchTrips(TripRequest request) {

        // request.getOrigin() - apenas transporta dados de entrada/ dto para leitura
        // dado devolvido - retorno ja resolvido - slug e normalizacao
        // cityService.toSlug(originSlug) - string normalizada retorn
        String originSlug = cityService.toSlug(request.getOrigin());
        String destinationSlug = cityService.toSlug(request.getDestination());
        LocalDate date = request.getDate();

        // Recebe o retorno que o scraping produziu
        List<TripResponse> scrapedTrip = deOnibusScrapingClient.searchTrips(originSlug, destinationSlug, date);

        // salvar resultado no banco
        // passan apenas os dados do DTO (TripResponse) para o service, que constrói a
        // entidade e salva.
        for (TripResponse trip : scrapedTrip) {
            priceSnapshotService.savePrice(
                    trip.getOrigin(),
                    trip.getDestination(),
                    trip.getDate(),
                    trip.getDepartureTime(),
                    trip.getArrivalTime(),
                    trip.getSeatType(),
                    trip.getcompany(),
                    BigDecimal.valueOf(trip.getPrice())// converter double para BidDecimal
            );
        }
        // nao executa nada novo, na volta ira retornar o que o scraping ja produziu
        return scrapedTrip;
    }
}
