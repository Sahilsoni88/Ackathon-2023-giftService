package com.giftservice.springboot.controller;

import com.giftservice.springboot.common.CommonResponse;
import com.giftservice.springboot.exceptions.InsufficientBalanceException;
import com.giftservice.springboot.usecases.addCoins.AddCoinRequest;
import com.giftservice.springboot.usecases.addCoins.AddCoinsUseCase;
import com.giftservice.springboot.usecases.fetchCoins.FetchWalletBalanceUseCase;
import com.giftservice.springboot.usecases.redeemCoins.RedeemCoinsRequest;
import com.giftservice.springboot.usecases.redeemCoins.RedeemCoinsUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.http.auth.AuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.ServiceUnavailableException;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("wallet")
public class WalletController {
    private final AddCoinsUseCase addCoinsUseCase;
    private final FetchWalletBalanceUseCase fetchWalletBalanceUseCase;
    private final RedeemCoinsUseCase redeemCoinsUseCase;
    @GetMapping("")
    public ResponseEntity<CommonResponse> getCoins(@CookieValue(value = "user_id") final String userCookie) throws AuthenticationException, IOException, ServiceUnavailableException {
        final CommonResponse response = fetchWalletBalanceUseCase.execute(userCookie);
        return new ResponseEntity(response, HttpStatus.OK);
    }
    @PostMapping("/coin")
    public ResponseEntity<CommonResponse> addCoins(@CookieValue(value = "user_id") final String userCookie,
                                           @RequestBody final AddCoinRequest request) throws AuthenticationException, IOException, ServiceUnavailableException {
        request.setUserCookie(userCookie);
        addCoinsUseCase.execute(request);
        return new ResponseEntity(CommonResponse.builder()
                .status("success")
                .message("coins credited to user's account successfully")
                .build(), HttpStatus.OK);
    }
    @PutMapping("/coin/redeem")
    public ResponseEntity<CommonResponse> redeemCoins(@CookieValue(value = "user_id") final String userCookie,
                                                      @RequestBody final RedeemCoinsRequest request)
            throws AuthenticationException, InsufficientBalanceException, IOException, ServiceUnavailableException {
        request.setUserCookie(userCookie);
        final CommonResponse response = redeemCoinsUseCase.execute(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
