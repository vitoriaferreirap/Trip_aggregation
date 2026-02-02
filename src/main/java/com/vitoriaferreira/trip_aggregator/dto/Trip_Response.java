package com.vitoriaferreira.trip_aggregator.dto;

import java.sql.Time;
import java.time.LocalDate;

public class Trip_Response {
    // DTO Saida - minha API DEVOLVE depois de processar a busca.
    // encapsulamento
    private int id;
    private String origin;
    private String destination;
    private LocalDate date;
    private Time hours;
    private double price;
    private String platform;

    // construtor
    public Trip_Response() {
    }
}
