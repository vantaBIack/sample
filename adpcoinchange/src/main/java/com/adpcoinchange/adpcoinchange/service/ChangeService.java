package com.adpcoinchange.adpcoinchange.service;

import com.adpcoinchange.adpcoinchange.CoinsDesiredAmount;
import com.adpcoinchange.adpcoinchange.exception.DatabaseTransactionException;
import com.adpcoinchange.adpcoinchange.exception.InsufficientAmountOfCoinsException;
import com.adpcoinchange.adpcoinchange.repository.CoinEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChangeService {

    private final CoinService coinService;

    private static final String COIN_SORT_FIELD = "value";

    @Transactional
    public Map<BigDecimal, Integer> changeBill(final BigDecimal billValue,
                                               final CoinsDesiredAmount coinsDesiredAmount) {

        final var desiredSort = CoinsDesiredAmount.LEAST_COINS.equals(coinsDesiredAmount)
                ? Sort.by(Sort.Direction.DESC, COIN_SORT_FIELD)
                : Sort.by(Sort.Direction.ASC, COIN_SORT_FIELD);

        var billAsCents = billValue.multiply(BigDecimal.valueOf(100));
        final var disposalCoinsQuantities = coinService.retrieveCoinsAvailableAmount(desiredSort);

        if (billAsCents.compareTo(coinService.getCurrentMaxAvailableChange(disposalCoinsQuantities)) > 0) {
            log.warn(String.format("Insufficient amount of change for requested billValue %s", billValue));
            throw new InsufficientAmountOfCoinsException("Insufficient amount of change for requested billValue");
        }

        final var coinsAmountEachValue = calculateCoinsChange(billAsCents, disposalCoinsQuantities);
        final var updatedCoinsCount = coinService.updateCoinsAmount(disposalCoinsQuantities);
        if (updatedCoinsCount != disposalCoinsQuantities.size()) {
            log.warn("Error updating coins amounts after change calculation");
            throw new DatabaseTransactionException("Error processing change for bill. Try again");
        }

        return coinsAmountEachValue;
    }

    private Map<BigDecimal, Integer> calculateCoinsChange(BigDecimal bill, final List<CoinEntity> coinEntityList) {

        final var result = new HashMap<BigDecimal, Integer>();

        for (int i = 0; i < coinEntityList.size(); i++) {
            int amountOfCoinsUsed = 0;
            final var currentCoinValue = coinEntityList.get(i).getValue();
            while (bill.compareTo(currentCoinValue) >= 0) {
                var currentCoinAvailableAmount = coinEntityList.get(i).getAvailableAmount();
                if (currentCoinAvailableAmount <= 0) {
                    break;
                }
                bill = bill.subtract(currentCoinValue);
                coinEntityList.set(i,
                        CoinEntity.builder()
                                .id(coinEntityList.get(i).getId())
                                .value(currentCoinValue)
                                .availableAmount(currentCoinAvailableAmount - 1)
                                .build());
                amountOfCoinsUsed++;
            }
            result.put(currentCoinValue, amountOfCoinsUsed);
        }

        return result;

    }

}
