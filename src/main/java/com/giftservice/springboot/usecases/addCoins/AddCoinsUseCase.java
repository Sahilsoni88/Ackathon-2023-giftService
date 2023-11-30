package com.giftservice.springboot.usecases.addCoins;

import com.giftservice.springboot.common.CommonResponse;
import com.giftservice.springboot.core.values.CoinType;
import com.giftservice.springboot.infrastructure.user.CentralUserRepo;
import com.giftservice.springboot.models.CentralUser;
import com.giftservice.springboot.models.User;
import com.giftservice.springboot.models.Wallet;
import com.giftservice.springboot.repositories.WalletRepo;
import lombok.RequiredArgsConstructor;
import org.apache.http.auth.AuthenticationException;
import org.springframework.stereotype.Service;

import javax.naming.ServiceUnavailableException;
import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AddCoinsUseCase {
    private final WalletRepo walletRepo;
    private final CentralUserRepo userRepo;
    public CommonResponse execute(final AddCoinRequest request)
            throws AuthenticationException, IOException, ServiceUnavailableException {
        final User user = userRepo.fetch(request.getUserCookie());
        final CoinType coinType =
                request.getType() == null || request.getType().equals(CoinType.INVALID)
                        ? CoinType.GENERAL
                        : request.getType();
        final Optional<Wallet> userWalletOptional = walletRepo.findByUserIdAndCoinType(user.getId(), coinType);
        final Wallet userWallet;
        if(userWalletOptional.isPresent()){
            userWallet = userWalletOptional.get();
            userWallet.setBalance(userWallet.getBalance() + request.getAmount());
        } else {
            userWallet = Wallet.builder()
                    .userId(user.getId())
                    .balance(request.getAmount())
                    .coinType(coinType)
                    .build();
        }
        walletRepo.save(userWallet);
        return null;
    }
}
