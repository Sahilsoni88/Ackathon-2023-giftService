package com.giftservice.springboot.core.values;

public enum CouponStatus {
    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE"),
    EXPIRED("EXPIRED"),
    USED("USED"),
    PAYMENT_PENDING("PAYMENT_PENDING"),
    INVALID("INVALID");

    private final String value;

    CouponStatus(final String value) {
        this.value = value;
    }

    public static CouponStatus from(final String value) {
        for (final CouponStatus couponStatus : CouponStatus.values()) {
            if (couponStatus.getValue().equals(value)) {
                return couponStatus;
            }
        }
        return CouponStatus.INVALID;
    }

    public String getValue() {
        return this.value;
    }
}
