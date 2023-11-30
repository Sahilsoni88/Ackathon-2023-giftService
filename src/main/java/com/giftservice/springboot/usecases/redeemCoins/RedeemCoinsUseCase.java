package com.giftservice.springboot.usecases.redeemCoins;

import com.giftservice.springboot.common.CommonResponse;
import com.giftservice.springboot.core.values.CoinType;
import com.giftservice.springboot.exceptions.InsufficientBalanceException;
import com.giftservice.springboot.infrastructure.user.CentralUserRepo;
import com.giftservice.springboot.models.User;
import com.giftservice.springboot.models.Wallet;
import com.giftservice.springboot.repositories.WalletRepo;
import lombok.RequiredArgsConstructor;
import org.apache.http.auth.AuthenticationException;
import org.springframework.stereotype.Service;

import javax.naming.ServiceUnavailableException;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class RedeemCoinsUseCase {
    private final CentralUserRepo userRepo;
    private final WalletRepo walletRepo;

    public CommonResponse execute(final RedeemCoinsRequest request)
            throws AuthenticationException, IOException, ServiceUnavailableException, InsufficientBalanceException {
        final User user = userRepo.fetch(request.getUserCookie());
        final CoinType coinType = request.getCoinType() == null || request.getCoinType().equals(CoinType.INVALID)
                ?   CoinType.GENERAL
                :   request.getCoinType();
        Wallet wallet = walletRepo.findByUserIdAndCoinType(user.getId(), coinType)
                .orElseThrow(() -> new IllegalArgumentException("User does not have coins of type: " + coinType));

        if (wallet.getBalance() < request.getAmount()) {
            throw new InsufficientBalanceException("Insufficient balance to redeem coins");
        }

        wallet.setBalance(wallet.getBalance() - request.getAmount());
        walletRepo.save(wallet);
        return CommonResponse.builder()
                .status("success")
                .message("success")
                .build();
    }
}
