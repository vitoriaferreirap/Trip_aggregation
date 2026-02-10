package com.vitoriaferreira.trip_aggregator.dto.response;

import java.math.BigDecimal;
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
    private BigDecimal price;
    private String company;
    private String platform; // da qual esta fazendo o scraping

    // construtor
    public TripResponse(String origin, String destination, LocalDate date,
            String departureTime, String arrivalTime, String seatType,
            BigDecimal price, String company, String platform) {
        this.origin = origin;
        this.destination = destination;
        this.date = date;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.seatType = seatType;
        this.price = price;
        this.platform = platform;
        this.company = company;
    }

    // necessario para a resposta do servidor
    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public String getSeatType() {
        return seatType;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getPlatform() {
        return platform;
    }

    public String getCompany() {
        return company;
    }

}
