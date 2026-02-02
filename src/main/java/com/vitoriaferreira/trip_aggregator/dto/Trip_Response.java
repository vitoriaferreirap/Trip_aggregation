package com.vitoriaferreira.trip_aggregator.dto;

import java.time.LocalDate;

public class Trip_Response {
    // DTO Saida - minha API DEVOLVE depois de processar a busca.
    // encapsulamento
    // modelo dados da API
    private String origin;
    private String destination;
    private LocalDate date;
    private double price;
    private String platform;

    // construtor
    public Trip_Response(String origin, String destination, LocalDate date, double price, String platform) {
        this.origin = origin;
        this.destination = destination;
        this.date = date;
        this.price = price;
        this.platform = platform;
    }

    // necessario para a resposta do servidor
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

}
