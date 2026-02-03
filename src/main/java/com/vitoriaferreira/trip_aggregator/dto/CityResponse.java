package com.vitoriaferreira.trip_aggregator.dto;

public class CityResponse {
    // mapea dados retornados do ibge em JSON para obj JAVA
    private Long id;
    private String nome;

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
