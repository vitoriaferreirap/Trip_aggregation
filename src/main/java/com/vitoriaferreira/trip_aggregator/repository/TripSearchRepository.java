package com.vitoriaferreira.trip_aggregator.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vitoriaferreira.trip_aggregator.entity.TripSearch;

//repository reposnavel por persistir dados de busca
@Repository
public interface TripSearchRepository extends JpaRepository<TripSearch, Long> {

    boolean existsByOriginAndDestinationAndTravelDate(
            String origin,
            String destination,
            LocalDate travelDate);
}