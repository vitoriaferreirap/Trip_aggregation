package com.vitoriaferreira.trip_aggregator.repository;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vitoriaferreira.trip_aggregator.entity.TripSearch;

//repository reposnavel por persistir dados de busca
@Repository
public interface TripSearchRepository extends JpaRepository<TripSearch, Long> {

    // after olha no momento que o dia comecou para frente > 00h
    Optional<TripSearch> findFirstByOriginAndDestinationAndTravelDateAndInstantSearchAfterOrderByInstantSearchDesc(
            String origin,
            String destination,
            LocalDate travelDate,
            Instant startOfDay);
}