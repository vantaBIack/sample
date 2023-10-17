package com.adpcoinchange.adpcoinchange;

import com.adpcoinchange.adpcoinchange.exception.InvalidBillValueException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum BillType {

    ONE(1),
    TWO(2),
    FIVE(5),
    TEN(10),
    TWENTY(20),
    FIFTY(50),
    ONE_HUNDRED(100);

    final int billNumericValue;

    public static int value(final String billType) {
        return Arrays.stream(BillType.values())
                .filter(bill -> bill.name().equals(billType))
                .findFirst().map(BillType::getBillNumericValue)
                .orElseThrow(() ->
                        new InvalidBillValueException("Invalid bill value provided. Accepted values are ".concat(Arrays.toString(BillType.values()))));
    }

}
