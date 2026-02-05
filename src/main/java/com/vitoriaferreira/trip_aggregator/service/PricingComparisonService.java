package com.vitoriaferreira.trip_aggregator.service;

import java.util.List;

import com.vitoriaferreira.trip_aggregator.dto.TripResponse;

public class PricingComparisonService {
    // AGREGANDO VALOR - COMPARACAO DE PRECO MAIS BAIXO DA BUSCA
    public TripResponse findCheapestTrip(List<TripResponse> trips) {

        // armazena mais barata = nao sei o que e mais barato ainda
        TripResponse cheapest = null;

        for (TripResponse trip : trips) {
            // primeiro laco ainda nao tem comparacoes
            // ao ter a primeira comparacao, excecuta o fluxo todo
            if (cheapest == null) {
                cheapest = trip;
                continue; // ja entende que precisa ir pro proximo loop direto
            }

            /**
             * comparacao preco - segunda comparacao em diant
             * a.compareTo(b)
             * < 0 = a menor
             * 0 = a e b igual
             * > 0 = a maior
             */
            if (trip.getPrice().compareTo(cheapest.getPrice()) < 0) {
                // trip atual menor
                cheapest = trip;
            }
        }
        return cheapest;
    }
}
