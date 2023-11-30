package com.giftservice.springboot.usecases.fetchCoins;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class FetchWalletBalanceResponse {
    private double totalBalance;
    private double travelCoins;
    private double healthCoins;
    private double autoCoins;
    private double generalCoins;
}
