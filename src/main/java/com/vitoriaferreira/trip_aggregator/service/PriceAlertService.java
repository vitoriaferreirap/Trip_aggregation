package com.vitoriaferreira.trip_aggregator.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Service;

import com.vitoriaferreira.trip_aggregator.repository.PriceSnapshotRepository;

@Service
public class PriceAlertService {

    private final PriceSnapshotRepository repository;

    public PriceAlertService(PriceSnapshotRepository repository) {
        this.repository = repository;
    }

    /**
     * Regra de negócio:
     * Compara o preço médio da rota entre dois períodos consecutivos
     * e gera alerta caso a queda ultrapasse o limite definido.
     *
     * Exemplo:
     * - Semana passada vs semana atual
     */
    public void analyzePriceDrop(String origin, String destination) {

        // Instante atual- fim do periodo de analise
        Instant now = Instant.now();

        // Definição do intervalo entre dois instates - periodo
        // inicio do periodo atual = now - 3 (tres ultimos dias ate agora)
        // como se fosse essa semana
        Instant startCurrentPeriod = now.minus(3, ChronoUnit.DAYS);

        // como se fosse semana passada
        // inicio do periodo anterior = now - 6 (tres ultimos dias ANTES dos 3 do
        // periodo atual)
        Instant startPreviousPeriod = now.minus(6, ChronoUnit.DAYS);

        // Média da semana passada com a atual
        Double avgPreviousPeriod = repository.findAveragePriceByRouteAndPeriod(
                origin,
                destination,
                startPreviousPeriod,
                startCurrentPeriod);

        // Média da semana atual com o instante atual
        Double avgCurrentPeriod = repository.findAveragePriceByRouteAndPeriod(
                origin,
                destination,
                startCurrentPeriod,
                now);

        // Se não houver histórico suficiente, não há análise
        if (avgPreviousPeriod == null || avgCurrentPeriod == null) {
            return;
        }

        // Cálculo da variação percentual
        // (media semana atual - media semana passada) / media semana passada
        double variation = (avgCurrentPeriod - avgPreviousPeriod) / avgPreviousPeriod;

        // Regra de alerta:
        // queda maior ou igual a 15%
        if (variation <= -0.15) {
            generateAlert(
                    origin,
                    destination,
                    variation);
        }
    }

    /**
     * Simula a geração de alerta.
     * - persistir alerta
     * - notificar usuário
     */

    private void generateAlert(
            String origin,
            String destination,
            double variation) {
        System.out.println(
                "ALERTA DE PREÇO ↓ | Rota: " + origin + " → " + destination +
                        " | Queda: " + (variation * 100) + "%");
    }
}

/**
 * 100% baseado em banco
 * Histórico real (snapshots)
 * Comparação temporal
 * Independente de busca do usuário
 * 
 * Repository: responde “quanto custou”
 * Service: responde “mudou o suficiente para alertar?”
 * Scheduler: responde “quando rodar”
 */