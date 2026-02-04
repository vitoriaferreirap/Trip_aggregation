package com.vitoriaferreira.trip_aggregator.dto;

import java.time.LocalDate;

public class TripResponse {
    // DTO Saida - minha API DEVOLVE depois de processar a busca.
    // encapsulamento
    // modelo dados da API
    private String origin;
    private String destination;
    private LocalDate date;
    private String departureTime;
    private String arrivalTime;
    private String seatType;
    private double price;
    private String provider;
    private String platform; // da qual esta fazendo o scraping

    // construtor
    public TripResponse(String origin, String destination, LocalDate date,
            String departureTime, String arrivalTime, String seatType,
            double price, String provider, String platform) {
        this.origin = origin;
        this.destination = destination;
        this.date = date;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.seatType = seatType;
        this.price = price;
        this.platform = platform;
        this.provider = provider;
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

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getSeatType() {
        return seatType;
    }

    public void setSeatType(String seatType) {
        this.seatType = seatType;
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

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

}
