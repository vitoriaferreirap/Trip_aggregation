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
     * SCHEDULER(agendador)
     * - Componente que executa uma tarefa automaticamente
     * - GitHub Action (cron): Garante que o processo aconteça uma vez por dia.
     * - initialDelay(automacao inicia 1 segundo apos spring estar rodando )
     * - fixedDelay(define intervalo entre excecucoes caso sitema permaneca ligado
     * 24hx24h)
     * - System.exit: Garante que o GitHub Actions saiba que o trabalho acabou
     * e desligue a vm, ignora fixedDelay
     * - Script de Inicialização com Jitter(gera atraso entre requisicoes diarias)
     */

    @Scheduled(initialDelay = 1000, fixedDelay = 86400000)
    public void runAutomatedScheduler() {
        long randomDelay = (long) (Math.random() * 30 * 1000);
        try {
            System.out.println("Aguardando " + (randomDelay / 60000) + " minutos para iniciar busca");
            Thread.sleep(randomDelay);
            automatedSearchService.execute();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.exit(0);
    }
}
