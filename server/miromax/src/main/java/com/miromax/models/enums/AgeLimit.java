package com.miromax.models.enums;

public enum AgeLimit {
    ZERO_PLUS("0+"),
    TWO_PLUS("2+"),
    FOUR_PLUS("4+"),
    SIX_PLUS("6+"),
    EIGHT_PLUS("8+"),
    TEN_PLUS("10+"),
    TWELVE_PLUS("12+"),
    FOURTEEN_PLUS("14+"),
    SIXTEEN_PLUS("16+"),
    EIGHTEEN_PLUS("18+");

    private String label;

    AgeLimit(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}