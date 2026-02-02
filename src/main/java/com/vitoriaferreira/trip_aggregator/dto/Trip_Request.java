package com.vitoriaferreira.trip_aggregator.dto;

import java.time.LocalDate;

public class Trip_Request {
    // DTO ENTRADA - minha API solicita o que quer buscar
    // encapsulamento
    private int id;
    private String origin;
    private String destination;
    private LocalDate date; // apenas data sem fuso

    // construtor
    public Trip_Request() {
    }
}
