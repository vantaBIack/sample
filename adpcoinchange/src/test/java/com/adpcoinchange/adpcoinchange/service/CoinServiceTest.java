package com.adpcoinchange.adpcoinchange.service;

import com.adpcoinchange.adpcoinchange.repository.CoinEntity;
import com.adpcoinchange.adpcoinchange.repository.CoinsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CoinServiceTest {

    @Mock
    private CoinsRepository coinsRepository;

    @InjectMocks
    private CoinService coinService;

    private static final CoinEntity coinEntityOne = new CoinEntity(UUID.randomUUID(), BigDecimal.ONE, 20);
    private static final CoinEntity coinEntityTwo = new CoinEntity(UUID.randomUUID(), BigDecimal.TEN, 5);

    @Test
    void shouldRetrieveCoinsAvailableAmount() {

        when(coinsRepository.findAll(any(Sort.class))).thenReturn(Arrays.asList(coinEntityOne, coinEntityTwo));

        final List<CoinEntity> coinEntities = coinService.retrieveCoinsAvailableAmount(Sort.by(Sort.Direction.DESC, "value"));

        assertNotNull(coinEntities);
        assertEquals(2, coinEntities.size());
        assertEquals(coinEntityOne.getValue(), coinEntities.get(0).getValue());
        assertEquals(coinEntityOne.getId(), coinEntities.get(0).getId());
        assertEquals(coinEntityOne.getAvailableAmount(), coinEntities.get(0).getAvailableAmount());

        assertEquals(coinEntityTwo.getValue(), coinEntities.get(1).getValue());
        assertEquals(coinEntityTwo.getId(), coinEntities.get(1).getId());
        assertEquals(coinEntityTwo.getAvailableAmount(), coinEntities.get(1).getAvailableAmount());

        verify(coinsRepository).findAll(any(Sort.class));
        verify(coinsRepository, never()).saveAll(any());
    }

    @Test
    void shouldUpdateCoinsAmount() {
        when(coinsRepository.saveAll(any())).thenReturn(Arrays.asList(coinEntityOne, coinEntityTwo));

        final Integer updatedCoinsAmount = coinService.updateCoinsAmount(Arrays.asList(coinEntityOne, coinEntityTwo));

        assertEquals(2, updatedCoinsAmount);

        verify(coinsRepository).saveAll(any());
        verify(coinsRepository, never()).findAll(any(Sort.class));

    }

    @Test
    void shouldGetCurrentMaxAvailableChange() {
        final var currentMaxAvailableChange = coinService.getCurrentMaxAvailableChange(Arrays.asList(coinEntityOne, coinEntityTwo));

        assertEquals(BigDecimal.valueOf(70), currentMaxAvailableChange);
    }
}