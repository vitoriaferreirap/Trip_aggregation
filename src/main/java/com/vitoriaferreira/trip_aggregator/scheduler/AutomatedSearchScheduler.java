package com.vitoriaferreira.trip_aggregator.scheduler;

import org.springframework.scheduling.annotation.Scheduled;

import com.vitoriaferreira.trip_aggregator.service.AutomatedSearchService;
import com.vitoriaferreira.trip_aggregator.service.PriceSnapshotService;
import com.vitoriaferreira.trip_aggregator.service.TripService;

public class AutomatedSearchScheduler {

    private final TripService tripService;
    private final PriceSnapshotService priceSnapshotService;

    public AutomatedSearchScheduler(TripService tripService, PriceSnapshotService priceSnapshotService) {
        this.tripService = tripService;
        this.priceSnapshotService = priceSnapshotService;
    }

    /**
     * SCHADULER
     * componente que executa uma tarefa automaticamente
     * em horários ou intervalos definidos.
     */
    @Scheduled(cron = "0 0 8/20 * * *") // min seg h diames mes diasemana
    public void runAutomatedScheduler() {
        AutomatedSearchService service = new AutomatedSearchService(tripService, priceSnapshotService);
        service.execute();
    }
}
