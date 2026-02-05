package com.vitoriaferreira.trip_aggregator.entity;

import java.time.Instant;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

//Representar um registro histórico imutável de preço coletado em um instante específico.
@Entity
public class PriceSnapshot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String originCity;
    private String destinationCity;
    private String company;
    private LocalDate travelDate;
    private String departureTime;
    private String arrivalTime;
    private String seatType;
    private double price;

    // mesmo que alguém tente update, o Hibernate não inclui o campo no SQL
    @Column(nullable = false, updatable = false)
    private Instant collectedAt;// data e hora do momento da coleta

    public PriceSnapshot(String originCity, String destinationCity, String company, LocalDate travelDate,
            String departureTime, String arrivalTime, String seatType, double price, Instant collectedAt) {

        this.originCity = originCity;
        this.destinationCity = destinationCity;
        this.company = company;
        this.travelDate = travelDate;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.seatType = seatType;
        this.price = price;
        this.collectedAt = collectedAt;
    }

    public Long getId() {
        return id;
    }

    public String getOriginCity() {
        return originCity;
    }

    public String getDestinationCity() {
        return destinationCity;
    }

    public String getCompany() {
        return company;
    }

    public LocalDate getTravelDate() {
        return travelDate;
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

    public double getPrice() {
        return price;
    }

    public Instant getCollectedAt() {
        return collectedAt;
    }

}