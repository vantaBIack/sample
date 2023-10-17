package com.adpcoinchange.adpcoinchange.api;

import com.adpcoinchange.adpcoinchange.CoinsDesiredAmount;
import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class ChangeCoinsRequest {

    @Nonnull
    private String billType;
    @Nonnull
    private CoinsDesiredAmount desiredAmount;

}
