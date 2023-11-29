package com.giftservice.springboot.usecases.createCoupon;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.giftservice.springboot.common.CommonRequest;
import com.giftservice.springboot.core.values.CouponType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CreateCouponRequest extends CommonRequest {
    private double amount;
    private String phoneNumber;
    private String name;
    private String email;
    private CouponType type;
}
