package com.vitoriaferreira.trip_aggregator.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.vitoriaferreira.trip_aggregator.service.serviceAutomation.AutomatedSearchService;

@Component
public class AutomatedSearchScheduler {

    private final AutomatedSearchService automatedSearchService;

    public AutomatedSearchScheduler(AutomatedSearchService automatedSearchService) {
        this.automatedSearchService = automatedSearchService;
    }

    /**
     * SCHADULER
     * componente que executa uma tarefa automaticamente
     * em horários ou intervalos definidos.
     * Configuraçào de TEMPO
     */

    // Executa uma vez por dia, mas com um "sorteio" inicial
    @Scheduled(initialDelay = 1000, fixedDelay = 86400000)
    public void runAutomatedScheduler() {
        // Gera um atraso aleatório entre 1 e 30 minutos antes da busca do dia anterior
        long randomDelay = (long) (Math.random() * 30 * 1000);
        try {
            System.out.println("Aguardando " + (randomDelay / 60000) + " minutos para iniciar busca");
            Thread.sleep(randomDelay);
            automatedSearchService.execute();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
