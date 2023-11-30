package com.giftservice.springboot.usecases.redeemCoins;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.giftservice.springboot.common.CommonRequest;
import com.giftservice.springboot.core.values.CoinType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RedeemCoinsRequest extends CommonRequest {
    private CoinType coinType;
    private double amount;
}
