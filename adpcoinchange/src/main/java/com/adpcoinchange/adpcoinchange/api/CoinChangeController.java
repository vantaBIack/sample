package com.adpcoinchange.adpcoinchange.api;

import com.adpcoinchange.adpcoinchange.BillType;
import com.adpcoinchange.adpcoinchange.service.ChangeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/change-bill")
@RequiredArgsConstructor
@Tag(name = "change-bill")
public class CoinChangeController {

    private final ChangeService changeService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Returns the amount of coins for change a bill. Accepted bills are:  ONE, TWO, FIVE, TEN, TWENTY, FIFTY, ONE_HUNDRED")
    public ResponseEntity<ChangeCoinsResponse> changeBill(@Validated @RequestBody final ChangeCoinsRequest request) {
        final var billValue = BigDecimal.valueOf(BillType.value(request.getBillType()));
        final var amountOfCoinsForBill = changeService.changeBill(billValue, request.getDesiredAmount());
        return ResponseEntity.ok(ChangeCoinsResponse.toResponse(amountOfCoinsForBill));
    }

}
