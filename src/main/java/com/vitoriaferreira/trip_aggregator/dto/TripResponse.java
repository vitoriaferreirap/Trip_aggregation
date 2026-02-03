package com.vitoriaferreira.trip_aggregator.dto;

import java.time.LocalDate;

public class TripResponse {
    // DTO Saida - minha API DEVOLVE depois de processar a busca.
    // encapsulamento
    // modelo dados da API
    private String origin;
    private String destination;
    private LocalDate date;
    private String departureTime;
    private String arrivalTime;
    private String seatType;
    private double price;
    private String platform; // da qual esta fazendo o scraping

    // construtor
    public TripResponse(String origin, String destination, LocalDate date,
            String departureTime, String arrivalTime, String seatType,
            double price, String platform) {
        this.origin = origin;
        this.destination = destination;
        this.date = date;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.seatType = seatType;
        this.price = price;
        this.platform = platform;
    }

    // necessario para a resposta do servidor
    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getSeatType() {
        return seatType;
    }

    public void setSeatType(String seatType) {
        this.seatType = seatType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    /**
     * Converte uma string formatada em um objeto TripResponse.
     * Formato esperado: "Cidade - UF para Cidade - UF | partida HH:MM | chegada
     * HH:MM | tipoPoltrona | R$ valor"
     * 
     * Exemplo: "Curitiba - PR para Florianópolis - SC | partida 18:30 | chegada
     * 00:10 | leito | R$ 119,99"
     */
    public static TripResponse fromString(String tripString, LocalDate date, String platform) {
        try {
            // Divide por pipes (|)
            String[] parts = tripString.split("\\|");

            if (parts.length < 5) {
                throw new IllegalArgumentException("Formato de string inválido");
            }

            // Extrai origem e destino
            String[] cities = parts[0].trim().split("para");
            String origin = cities[0].trim();
            String destination = cities.length > 1 ? cities[1].trim() : "Desconhecido";

            // Extrai horário de partida (ex: "partida 18:30")
            String departureStr = parts[1].trim();
            String departureTime = departureStr.replaceAll("partida\\s*", "").trim();

            // Extrai horário de chegada (ex: "chegada 00:10")
            String arrivalStr = parts[2].trim();
            String arrivalTime = arrivalStr.replaceAll("chegada\\s*", "").trim();

            // Extrai tipo de poltrona
            String seatType = parts[3].trim();

            // Extrai preço (ex: "R$ 119,99")
            String priceStr = parts[4].trim();
            double price = Double.parseDouble(priceStr.replaceAll("[R$\\s]", "").replace(",", "."));

            return new TripResponse(origin, destination, date, departureTime, arrivalTime, seatType, price, platform);

        } catch (Exception e) {
            throw new IllegalArgumentException("Erro ao fazer parsing da string de viagem: " + tripString, e);
        }
    }

}
