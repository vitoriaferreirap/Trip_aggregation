package com.vitoriaferreira.trip_aggregator.dto.analysis;

import java.math.BigDecimal;

//DTO ANALISE DE PRECO DO HISTORICO
public class PriceAnalysis {

    private String cheapestCompany;
    private BigDecimal cheapestAvgPrice;
    private BigDecimal differencePercent;

    public PriceAnalysis(String cheapestCompany, BigDecimal cheapestAvgPrice, BigDecimal differencePercent) {
        this.cheapestCompany = cheapestCompany;
        this.cheapestAvgPrice = cheapestAvgPrice;
        this.differencePercent = differencePercent;
    }

    public String getCheapestCompany() {
        return cheapestCompany;
    }

    public BigDecimal getCheapestAvgPrice() {
        return cheapestAvgPrice;
    }

    public BigDecimal getDifferencePercent() {
        return differencePercent;
    }

}