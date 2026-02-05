package com.vitoriaferreira.trip_aggregator.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vitoriaferreira.trip_aggregator.entity.PriceSnapshot;
import com.vitoriaferreira.trip_aggregator.service.PriceSnapshotService;

@RestController
@RequestMapping("/price")
public class PriceSnapshotController {

    // injecao de dependencia do que preciso usar
    private final PriceSnapshotService service;

    public PriceSnapshotController(PriceSnapshotService service) {
        this.service = service;
    }

    // http post
    // @RequestBody - pega corpo da requisicao (json) e mapeai para o obj java
    @PostMapping("/save")
    public PriceSnapshot save(@RequestBody PriceSnapshot snapshot) {
        return service.savePrice(
                snapshot.getOriginCity(),
                snapshot.getDestinationCity(),
                snapshot.getTravelDate(),
                snapshot.getDepartureTime(),
                snapshot.getArrivalTime(),
                snapshot.getSeatType(),
                snapshot.getCompany(),
                snapshot.getPrice());
    }
}
