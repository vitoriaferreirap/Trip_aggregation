package com.vitoriaferreira.trip_aggregator.service;

public class PriceAnlizerService {
    /**
     * recebe uma lista de viagens
     * identifica:
     * menor preço atual
     * variação em relação ao histórico
     * se houve queda relevante
     * 
     * 
     * preço atual < menor preço registrado anteriormente
     * ou queda ≥ X%
     * 
     * 
     * job agendado (ex.: a cada 1h ou 1x por dia)
     * executa:
     * scraping
     * persistência
     * análise
     * disparo de alerta
     */
}
