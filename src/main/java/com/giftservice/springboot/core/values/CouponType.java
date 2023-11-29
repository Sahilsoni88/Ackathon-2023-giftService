package com.giftservice.springboot.core.values;

public enum CouponType {
    DISCOUNT("DISCOUNT"),
    GIFT("GIFT"),
    INVALID("INVALID");

    private final String value;

    CouponType(final String value) {
        this.value = value;
    }

    public static CouponType from(final String value) {
        for (final CouponType couponType : CouponType.values()) {
            if (couponType.getValue().equals(value)) {
                return couponType;
            }
        }
        return CouponType.INVALID;
    }

    public String getValue() {
        return this.value;
    }
}
