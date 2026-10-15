package com.vitoriaferreira.trip_aggregator.entity;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class TripSearch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String origin;
    private String destination;
    private LocalDate travelDate;
    private Instant instantSearch;

    // UM para MUITOS
    @OneToMany(mappedBy = "tripSearchId", cascade = CascadeType.ALL)
    private List<PriceSnapshot> priceSnapshots;

    public TripSearch() {
    }

    public TripSearch(String origin, String destination, LocalDate travelDate, Instant instantSearch) {
        this.origin = origin;
        this.destination = destination;
        this.travelDate = travelDate;
        this.instantSearch = instantSearch;
    }

    public Long getId() {
        return id;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public LocalDate getTravelDate() {
        return travelDate;
    }

    public Instant getInstantSearch() {
        return instantSearch;
    }

}
