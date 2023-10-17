package com.adpcoinchange.adpcoinchange.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class ChangeCoinsResponse {

    private List<String> amountOfEachCoinsValue;

    static ChangeCoinsResponse toResponse(final Map<BigDecimal, Integer> amountOfCoinsForBill) {

        final var amountOfCoins =
                amountOfCoinsForBill.entrySet()
                        .stream()
                        .map(bigDecimalIntegerEntry ->
                                "The number of $"
                                        .concat(bigDecimalIntegerEntry.getKey().toString())
                                        .concat(" coins for change is ")
                                        .concat(String.valueOf(bigDecimalIntegerEntry.getValue())))
                        .collect(Collectors.toList());

        return ChangeCoinsResponse.builder()
                .amountOfEachCoinsValue(amountOfCoins)
                .build();

    }
}
