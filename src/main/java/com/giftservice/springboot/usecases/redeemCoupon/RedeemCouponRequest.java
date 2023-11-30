package com.giftservice.springboot.usecases.redeemCoupon;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.giftservice.springboot.common.CommonRequest;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RedeemCouponRequest extends CommonRequest {
    private String code;
    private double amount;
}
