package com.adpcoinchange.adpcoinchange.service;

import com.adpcoinchange.adpcoinchange.CoinsDesiredAmount;
import com.adpcoinchange.adpcoinchange.exception.DatabaseTransactionException;
import com.adpcoinchange.adpcoinchange.exception.InsufficientAmountOfCoinsException;
import com.adpcoinchange.adpcoinchange.repository.CoinEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChangeServiceTest {

    @Mock
    private CoinService coinService;

    @InjectMocks
    private ChangeService changeService;

    private static final CoinEntity coinEntityOne = new CoinEntity(UUID.randomUUID(), BigDecimal.ONE, 20);
    private static final CoinEntity coinEntityTwo = new CoinEntity(UUID.randomUUID(), BigDecimal.TEN, 5);


    @Test
    void shouldFailToChangeBillWhenBillIsHigherThanTheTotalAmountOfCoins() {
        when(coinService.retrieveCoinsAvailableAmount(any(Sort.class))).thenReturn(Arrays.asList(coinEntityOne, coinEntityTwo));
        when(coinService.getCurrentMaxAvailableChange(any())).thenReturn(BigDecimal.ONE);

        InsufficientAmountOfCoinsException thrown = assertThrows(
                InsufficientAmountOfCoinsException.class,
                () -> changeService.changeBill(BigDecimal.valueOf(100L), CoinsDesiredAmount.MOST_COINS));

        assertTrue(thrown.getMessage().contains("Insufficient amount of change for requested billValue"));

        verify(coinService, never()).updateCoinsAmount(any());
    }

    @Test
    void shouldFailToChangeBillWhenFailsToUpdateMachineRemainingCoins() {
        when(coinService.retrieveCoinsAvailableAmount(any(Sort.class))).thenReturn(Arrays.asList(coinEntityOne, coinEntityTwo));
        when(coinService.getCurrentMaxAvailableChange(any())).thenReturn(BigDecimal.valueOf(50000));
        when(coinService.updateCoinsAmount(any())).thenReturn(0);

        DatabaseTransactionException thrown = assertThrows(
                DatabaseTransactionException.class,
                () -> changeService.changeBill(BigDecimal.valueOf(100L), CoinsDesiredAmount.MOST_COINS));

        assertTrue(thrown.getMessage().contains("Error processing change for bill. Try again"));
    }

    @Test
    void shouldReturnAmountOfCoinsForChangeSuccessfully() {
        when(coinService.retrieveCoinsAvailableAmount(any(Sort.class))).thenReturn(Arrays.asList(coinEntityOne, coinEntityTwo));
        when(coinService.getCurrentMaxAvailableChange(any())).thenReturn(BigDecimal.valueOf(50000));
        when(coinService.updateCoinsAmount(any())).thenReturn(Arrays.asList(coinEntityOne, coinEntityTwo).size());

        final var coinsAmountEachValue = changeService.changeBill(BigDecimal.valueOf(100L), CoinsDesiredAmount.MOST_COINS);

        assertEquals(2, coinsAmountEachValue.size());

    }

}