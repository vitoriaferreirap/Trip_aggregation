package com.vitoriaferreira.trip_aggregator.entity;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

// modela como os dados são armazenados no banco. Usa JPA/HIBERNAT
// específico.
@Entity
public class PriceSnapshot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String originCity;
    private String destinationCity;
    private LocalDate travelDate;
    private String departureTime;
    private String arrivalTime;
    private String seatType;
    private String company;
    private BigDecimal price; // guarda zeros apos .

    // mesmo que alguém tente update, o Hibernate não inclui o campo no SQL
    @Column(nullable = false, updatable = false)
    private Instant collectedAt;// data e hora do momento da coleta

    public PriceSnapshot(String originCity, String destinationCity, LocalDate travelDate, String departureTime,
            String arrivalTime, String seatType, String company, BigDecimal price, Instant collectedAt) {
        this.originCity = originCity;
        this.destinationCity = destinationCity;
        this.travelDate = travelDate;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.seatType = seatType;
        this.company = company;
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

    public String getCompany() {
        return company;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Instant getCollectedAt() {
        return collectedAt;
    }

}