package com.giftservice.springboot.usecases.redeemCoupon;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RedeemCouponResponse {
    private String code;
    private double remainingCouponAmount;
    private double couponDiscount;
}
