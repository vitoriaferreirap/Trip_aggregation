package com.vitoriaferreira.trip_aggregator.dto;

import java.time.LocalDate;

public class TripRequest {
    // DTO ENTRADA - minha API solicita o que quer buscar
    // encapsulamento
    private String origin;
    private String destination;
    private LocalDate date; // apenas data sem fuso

    // construtor
    public TripRequest() {
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

}
