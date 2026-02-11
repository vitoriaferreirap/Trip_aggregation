package com.vitoriaferreira.trip_aggregator.entity;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

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
    // MUITOS para UM
    @ManyToOne
    @JoinColumn(name = "trip_search_id")
    private TripSearch tripSearchId;

    /**
     * Necessario para segunda busca: código chamou o Repository para "ler" do
     * banco. Para transformar a linha do SQL em um objeto Java, o Hibernate precisa
     * desse construtor vazio para "nascer" o objeto.
     */
    public PriceSnapshot() {
    }

    public PriceSnapshot(String originCity, String destinationCity, LocalDate travelDate, String departureTime,
            String arrivalTime, String seatType, String company, BigDecimal price, Instant collectedAt,
            TripSearch tripSearchId) {
        this.originCity = originCity;
        this.destinationCity = destinationCity;
        this.travelDate = travelDate;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.seatType = seatType;
        this.company = company;
        this.price = price;
        this.collectedAt = collectedAt;
        this.tripSearchId = tripSearchId;
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

    public TripSearch getTripSearchId() {
        return tripSearchId;
    }

}