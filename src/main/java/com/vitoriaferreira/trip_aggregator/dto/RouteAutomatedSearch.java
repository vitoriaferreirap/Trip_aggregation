package com.vitoriaferreira.trip_aggregator.dto;

public class RouteAutomatedSearch {

    private String origin;
    private String destination;

    public RouteAutomatedSearch(String origin, String destination) {
        this.origin = origin;
        this.destination = destination;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

}
