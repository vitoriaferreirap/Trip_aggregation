package com.vitoriaferreira.trip_aggregator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling // automatizacao
public class TripAggregatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(TripAggregatorApplication.class, args);
    }
}
