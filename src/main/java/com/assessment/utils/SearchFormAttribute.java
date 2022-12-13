package com.assessment.utils;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public class SearchFormAttribute {
    @Min(value = 9780000000000L, message = "Barcode start with 978... Length must be 13.")
    @Max(value = 9789999999999L, message = "Barcode max value 9789999999999.")
    private long barcode;

    public SearchFormAttribute() {
    }

    public long getBarcode() {
        return barcode;
    }

    public void setBarcode(long barcode) {
        this.barcode = barcode;
    }

    @Override
    public String toString() {
        return "SearchFormAttribute{" +
                "barcode=" + barcode +
                '}';
    }
}
