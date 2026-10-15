package com.vitoriaferreira.trip_aggregator.service.serviceCore;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.vitoriaferreira.trip_aggregator.dto.request.TripRequest;
import com.vitoriaferreira.trip_aggregator.dto.response.TripResponse;
import com.vitoriaferreira.trip_aggregator.entity.TripSearch;
import com.vitoriaferreira.trip_aggregator.integration.DeOnibusScrapingClient;
import com.vitoriaferreira.trip_aggregator.repository.TripSearchRepository;
import com.vitoriaferreira.trip_aggregator.service.serviceStorage.PriceSnapshotService;
import com.vitoriaferreira.trip_aggregator.service.serviceStrategy.PricingComparisonService;

@Service
public class TripService {

    // injecao de dependencia
    private final CityService cityService;
    private final DeOnibusScrapingClient deOnibusScrapingClient;
    private final PriceSnapshotService priceSnapshotService;
    private final PricingComparisonService pricingComparisonService;
    private final TripSearchRepository tripSearchRepository;

    // construtor - entra apenas dependencias que o service usa para trabalhar, nao
    // dados de entrada
    public TripService(CityService cityService, DeOnibusScrapingClient deOnibusScrapingClient,
            PriceSnapshotService priceSnapshotService, PricingComparisonService pricingComparisonService,
            TripSearchRepository tripSearchRepository) {
        this.cityService = cityService;
        this.deOnibusScrapingClient = deOnibusScrapingClient;
        this.priceSnapshotService = priceSnapshotService;
        this.pricingComparisonService = pricingComparisonService;
        this.tripSearchRepository = tripSearchRepository;
    }

    // metodo
    /**
     * Dominio: objetivo principal do metodo = fazer busca de viagem
     * Regra interna do sistema: se a busca nao exisitr, persistir
     */
    public List<TripResponse> searchTrips(TripRequest request) {

        String originSlug = cityService.toSlug(request.getOrigin());
        String destinationSlug = cityService.toSlug(request.getDestination());
        LocalDate date = request.getDate();

        // startOfToday pega o momento exato em que o dia de começou 00h.
        Instant startOfToday = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant();

        /**
         * Verifica se a busca foi feita no dia
         * Optional - vazio ou contem exatamente um unico objeto(Pai)
         */
        Optional<TripSearch> searchExistente = tripSearchRepository
                .findFirstByOriginAndDestinationAndTravelDateAndInstantSearchAfterOrderByInstantSearchDesc(
                        originSlug, destinationSlug, date, startOfToday);

        List<TripResponse> scrapedTrip;
        TripResponse cheaper;

        // Se o Optional estiver vazio, fazemos o scraping
        if (searchExistente.isEmpty()) {
            System.out.println("Busca não salva, fazendo novo scraping");
            scrapedTrip = deOnibusScrapingClient.searchTrips(originSlug, destinationSlug, date);

            TripSearch search = new TripSearch(originSlug, destinationSlug, date, Instant.now());

            // salva nova busca e gera ID dela
            TripSearch searchSalva = tripSearchRepository.save(search);

            // response do scraping e a busca atual salva e seu id(fk de pricesnapshot)
            priceSnapshotService.save(scrapedTrip, searchSalva);

            // comparacao do mais barato local, da busca atual
            cheaper = pricingComparisonService.findCheapestTrip(scrapedTrip);
            System.out.println("Passagens mais barata da busca: " + cheaper.getPrice());

        } else {
            System.out.println("Busca já feita hoje!Recuperando do banco!");

            // id busca que optional trouxe PAI
            Long buscaId = searchExistente.get().getId();

            // chamamos o service para busca os FILHOS
            scrapedTrip = priceSnapshotService.buscaPorId(buscaId);
            System.out.println("Recuperados " + scrapedTrip.size()); // numero de objs
            cheaper = pricingComparisonService.findCheapestTrip(scrapedTrip);
            System.out.println("Passagens mais barata da busca: " + cheaper.getPrice());
        }

        return scrapedTrip;
    }

}