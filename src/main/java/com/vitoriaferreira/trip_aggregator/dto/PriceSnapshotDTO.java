package com.vitoriaferreira.trip_aggregator.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

import com.vitoriaferreira.trip_aggregator.entity.PriceSnapshot;

public class PriceSnapshotDTO {

    private String originCity;
    private String destinationCity;
    private LocalDate travelDate;
    private String departureTime;
    private String arrivalTime;
    private String seatType;
    private String company;
    private BigDecimal price;
    private Instant collectedAt;

    /**
     * DTO DE EXIBICAO PARA O FRONT
     * Existe apenas para transporte de dados, O construtor do DTO que recebe
     * PriceSnapshot copia os valores da entidade para o DTO, usando os getters da
     * entidade.
     * So organiza os dados depois que a entidade já foi salva.
     * Construtor que converte entidade em DTO
     * 
     */
    public PriceSnapshotDTO(PriceSnapshot snapshot) {
        this.originCity = snapshot.getOriginCity();
        this.destinationCity = snapshot.getDestinationCity();
        this.travelDate = snapshot.getTravelDate();
        this.departureTime = snapshot.getDepartureTime();
        this.arrivalTime = snapshot.getArrivalTime();
        this.seatType = snapshot.getSeatType();
        this.company = snapshot.getCompany();
        this.price = snapshot.getPrice();
        this.collectedAt = snapshot.getCollectedAt();
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
