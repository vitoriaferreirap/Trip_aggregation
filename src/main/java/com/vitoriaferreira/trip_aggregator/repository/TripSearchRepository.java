package com.vitoriaferreira.trip_aggregator.repository;

import java.time.Instant;
import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vitoriaferreira.trip_aggregator.entity.TripSearch;

//repository reposnavel por persistir dados de busca
@Repository
public interface TripSearchRepository extends JpaRepository<TripSearch, Long> {

    // after olha no momento que o dia comecou para frente > 00h
    boolean existsByOriginAndDestinationAndTravelDateAndInstantSearchAfter(
            String origin,
            String destination,
            LocalDate travelDate,
            Instant InstantTime);
}