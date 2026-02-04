package com.vitoriaferreira.trip_aggregator.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vitoriaferreira.trip_aggregator.entity.PriceSnapshot;

// responsavel por manipular a persistencia dos dados
public interface PriceSnapshotRepository extends JpaRepository<PriceSnapshot, Long> {

}
