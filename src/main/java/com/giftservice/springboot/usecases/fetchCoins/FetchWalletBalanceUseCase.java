package com.giftservice.springboot.usecases.fetchCoins;

import com.giftservice.springboot.common.CommonRequest;
import com.giftservice.springboot.common.CommonResponse;
import com.giftservice.springboot.core.values.CoinType;
import com.giftservice.springboot.core.values.UserCoins;
import com.giftservice.springboot.infrastructure.user.CentralUserRepo;
import com.giftservice.springboot.models.User;
import com.giftservice.springboot.repositories.WalletRepo;
import lombok.RequiredArgsConstructor;
import org.apache.http.auth.AuthenticationException;
import org.springframework.stereotype.Service;

import javax.naming.ServiceUnavailableException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FetchWalletBalanceUseCase {
    private final WalletRepo walletRepo;
    private final CentralUserRepo userRepo;
    public CommonResponse execute(final String userCookie) throws AuthenticationException, IOException, ServiceUnavailableException {
        final User user = userRepo.fetch(userCookie);
        final List<Object[]> rawCoins = walletRepo.getByUserId(user.getId());
        final List<UserCoins> userCoinList = rawCoins.stream().map(
                x -> {
                    return UserCoins.builder()
                            .coinType(CoinType.valueOf((String) x[1]))
                            .totalBalance((double) x[2])
                            .build();
                }
        ).toList();
        double totalBalance = 0d;
        final FetchWalletBalanceResponse response = FetchWalletBalanceResponse.builder().build();
        for (UserCoins coin: userCoinList) {
            totalBalance += coin.getTotalBalance();
            switch (coin.getCoinType()){
                case AUTO -> response.setAutoCoins(coin.getTotalBalance());
                case HEALTH -> response.setHealthCoins(coin.getTotalBalance());
                case TRAVEL -> response.setTravelCoins(coin.getTotalBalance());
                case GENERAL -> response.setGeneralCoins(coin.getTotalBalance());
            }
        }
        response.setTotalBalance(totalBalance);
        return CommonResponse.builder()
                .status("success")
                .message("success")
                .data(response)
                .build();
    }
}
