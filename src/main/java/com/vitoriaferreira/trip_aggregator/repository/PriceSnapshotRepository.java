package com.vitoriaferreira.trip_aggregator.repository;

import java.time.Instant;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vitoriaferreira.trip_aggregator.entity.PriceSnapshot;

// repository reposnavel por persistir dados de retorno
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
    @Query("SELECT AVG(p.price) " +
            "FROM PriceSnapshot p " +
            "WHERE p.originCity = :origin " +
            "AND p.originCity IN ('curitiba','florianopolis','porto alegre') " +
            "AND p.destinationCity = :destination " +
            "AND p.collectedAt BETWEEN :start AND :end")

    // Automacao
    Double findAveragePriceByRouteAndPeriod(
            @Param("origin") String origin,
            @Param("destination") String destination,
            @Param("start") Instant start,
            @Param("end") Instant end);

    // Busca entidade banco por id de busca
    // O Spring entende que deve olhar para a entidade 'tripSearch'
    // e filtrar pelo atributo 'id' dela.
    List<PriceSnapshot> findByTripSearchId_Id(Long id);
}
