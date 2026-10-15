package com.vitoriaferreira.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.vitoriaferreira.trip_aggregator.dto.request.TripRequest;
import com.vitoriaferreira.trip_aggregator.dto.response.TripResponse;
import com.vitoriaferreira.trip_aggregator.entity.TripSearch;
import com.vitoriaferreira.trip_aggregator.integration.DeOnibusScrapingClient;
import com.vitoriaferreira.trip_aggregator.repository.TripSearchRepository;
import com.vitoriaferreira.trip_aggregator.service.serviceCore.CityService;
import com.vitoriaferreira.trip_aggregator.service.serviceCore.TripService;
import com.vitoriaferreira.trip_aggregator.service.serviceStorage.PriceSnapshotService;
import com.vitoriaferreira.trip_aggregator.service.serviceStrategy.PricingComparisonService;

@ExtendWith(MockitoExtension.class)
class TripServiceTest {

    @Mock
    private CityService cityService;

    @Mock
    private DeOnibusScrapingClient deOnibusScrapingClient;

    @Mock
    private PriceSnapshotService priceSnapshotService;

    @Mock
    private PricingComparisonService pricingComparisonService;

    @Mock
    private TripSearchRepository tripSearchRepository;

    @InjectMocks
    private TripService tripService;

    @Test
    void deveFazerScrapingESalvarUmaNovaBusca() {
        // Arrange
        LocalDate date = LocalDate.of(2026, 12, 20);
        TripRequest request = new TripRequest("Curitiba", "Florianópolis", date);
        TripResponse trip = new TripResponse(
                "curitiba", "florianopolis", date, "08:00", "14:00",
                "Convencional", BigDecimal.valueOf(120.00), "Empresa", "DeÔnibus");
        List<TripResponse> trips = List.of(trip);
        TripSearch savedSearch = new TripSearch(
                "curitiba", "florianopolis", date, Instant.now());

        when(cityService.toSlug("Curitiba")).thenReturn("curitiba");
        when(cityService.toSlug("Florianópolis")).thenReturn("florianopolis");
        when(tripSearchRepository
                .findFirstByOriginAndDestinationAndTravelDateAndInstantSearchAfterOrderByInstantSearchDesc(
                        eq("curitiba"), eq("florianopolis"), eq(date), any(Instant.class)))
                .thenReturn(Optional.empty());
        when(deOnibusScrapingClient.searchTrips("curitiba", "florianopolis", date))
                .thenReturn(trips);
        when(tripSearchRepository.save(any(TripSearch.class))).thenReturn(savedSearch);
        when(pricingComparisonService.findCheapestTrip(trips)).thenReturn(trip);

        // Act
        List<TripResponse> result = tripService.searchTrips(request);

        // Assert
        assertEquals(trips, result);
        verify(deOnibusScrapingClient).searchTrips("curitiba", "florianopolis", date);
        verify(tripSearchRepository).save(any(TripSearch.class));
        verify(priceSnapshotService).save(trips, savedSearch);
        verify(pricingComparisonService).findCheapestTrip(trips);
    }

    @Test
    void deveRecuperarBuscaExistenteSemFazerNovoScraping() {
        // Arrange
        LocalDate date = LocalDate.of(2026, 12, 20);
        TripRequest request = new TripRequest("Curitiba", "Florianópolis", date);
        TripResponse trip = new TripResponse(
                "curitiba", "florianopolis", date, "08:00", "14:00",
                "Convencional", BigDecimal.valueOf(120.00), "Empresa", "DeÔnibus");
        List<TripResponse> trips = List.of(trip);
        TripSearch existingSearch = org.mockito.Mockito.mock(TripSearch.class);

        when(cityService.toSlug("Curitiba")).thenReturn("curitiba");
        when(cityService.toSlug("Florianópolis")).thenReturn("florianopolis");
        when(tripSearchRepository
                .findFirstByOriginAndDestinationAndTravelDateAndInstantSearchAfterOrderByInstantSearchDesc(
                        eq("curitiba"), eq("florianopolis"), eq(date), any(Instant.class)))
                .thenReturn(Optional.of(existingSearch));
        when(existingSearch.getId()).thenReturn(10L);
        when(priceSnapshotService.buscaPorId(10L)).thenReturn(trips);
        when(pricingComparisonService.findCheapestTrip(trips)).thenReturn(trip);

        // Act
        List<TripResponse> result = tripService.searchTrips(request);

        // Assert
        assertEquals(trips, result);
        verify(priceSnapshotService).buscaPorId(10L);
        verify(pricingComparisonService).findCheapestTrip(trips);
        verify(deOnibusScrapingClient, org.mockito.Mockito.never())
                .searchTrips(any(String.class), any(String.class), any(LocalDate.class));
        verify(tripSearchRepository, org.mockito.Mockito.never()).save(any(TripSearch.class));
    }
}