package com.adpcoinchange.adpcoinchange.service;

import com.adpcoinchange.adpcoinchange.repository.CoinEntity;
import com.adpcoinchange.adpcoinchange.repository.CoinsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CoinService {

    private final CoinsRepository coinsRepository;

    List<CoinEntity> retrieveCoinsAvailableAmount(final Sort sort) {
        return coinsRepository
                .findAll(sort)
                .stream()
                .map(coinEntity -> new CoinEntity(coinEntity.getId(), coinEntity.getValue(), coinEntity.getAvailableAmount()))
                .collect(Collectors.toList());
    }

    Integer updateCoinsAmount(final List<CoinEntity> remainingCoins) {
        return coinsRepository.saveAll(remainingCoins).size();
    }

    BigDecimal getCurrentMaxAvailableChange(final List<CoinEntity> coinsAvailable) {
        return coinsAvailable
                .stream()
                .map(coinEntity -> coinEntity.getValue().multiply(BigDecimal.valueOf(coinEntity.getAvailableAmount())))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

}
