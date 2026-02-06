package com.vitoriaferreira.trip_aggregator.repository;

import java.time.Instant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vitoriaferreira.trip_aggregator.entity.PriceSnapshot;

@Repository
public interface PriceSnapshotRepository
        extends JpaRepository<PriceSnapshot, Long> {

    /**
     * Retorna o PREÇO MÉDIO de uma rota (origem + destino) APENAS CAPITAIS DO SUL
     * DO BRASIL
     * dentro de um intervalo de tempo específico.
     *
     * Essa query é a base da análise temporal.
     * Nenhuma regra de negócio vive aqui.
     * 
     * Quando o Service chama o Repository, ele pede:
     * “No banco, calcule a média dos preços das viagens dessa rota específica,
     * dentro do intervalo de tempo que eu estou te passando.”
     */
    @Query("""
            SELECT AVG(p.price)
            FROM PriceSnapshot p
            WHERE p.originCity IN ('Curitiba', 'Florianópolis', 'Porto Alegre')
            AND p.destinationCity IN ('Curitiba', 'Florianópolis', 'Porto Alegre')
                AND p.destinationCity = :destination
                AND p.collectedAt BETWEEN :start AND :end
            """)

    Double findAveragePriceByRouteAndPeriod(
            String origin,
            String destination,
            Instant start,
            Instant end);
}
