package com.giftservice.springboot.core.values;

public enum CoinType {
    TRAVEL("TRAVEL"),
    AUTO("AUTO"),
    HEALTH("HEALTH"),
    GENERAL("GENERAL"),
    INVALID("INVALID");

    private final String value;

    CoinType(final String value) {
        this.value = value;
    }

    public static CoinType from(final String value) {
        for (final CoinType CoinType : CoinType.values()) {
            if (CoinType.getValue().equals(value)) {
                return CoinType;
            }
        }
        return CoinType.INVALID;
    }

    public String getValue() {
        return this.value;
    }
}
