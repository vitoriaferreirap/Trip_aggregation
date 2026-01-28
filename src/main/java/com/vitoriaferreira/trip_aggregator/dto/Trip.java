package com.vitoriaferreira.trip_aggregator.dto;

import java.time.LocalDate;

public class Trip {

    // encapsulamento
    private int id;
    private String origin;
    private String destination;
    private LocalDate date; // apenas data sem fuso
    private double price;
    private String platform;

    // construtor
    public Trip() {
    }
}
