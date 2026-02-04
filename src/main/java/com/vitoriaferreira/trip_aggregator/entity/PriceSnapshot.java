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
    private String origin_city;
    private String destination_city;
    private LocalDate travel_date;
    private String departure_time;
    private String arrival_time;
    private String company;
    private String seat_type;
    private double price;

    // mesmo que alguém tente update, o Hibernate não inclui o campo no SQL
    @Column(nullable = false, updatable = false)
    private Instant collectedAt;// data e hora do momento da coleta

    public PriceSnapshot(String origin_city, String destination_city, LocalDate travel_date, String departure_time,
            String arrival_time, String company, String seat_type, double price) {
        this.origin_city = origin_city;
        this.destination_city = destination_city;
        this.travel_date = travel_date;
        this.departure_time = departure_time;
        this.arrival_time = arrival_time;
        this.company = company;
        this.seat_type = seat_type;
        this.price = price;
    }

    public String getOrigin_city() {
        return origin_city;
    }

    public String getDestination_city() {
        return destination_city;
    }

    public LocalDate getTravel_date() {
        return travel_date;
    }

    public String getDeparture_time() {
        return departure_time;
    }

    public String getArrival_time() {
        return arrival_time;
    }

    public String getCompany() {
        return company;
    }

    public String getSeat_type() {
        return seat_type;
    }

    public double getPrice() {
        return price;
    }

}