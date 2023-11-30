package com.giftservice.springboot.controller;

import com.giftservice.springboot.common.CommonRequest;
import com.giftservice.springboot.common.CommonResponse;
import com.giftservice.springboot.usecases.addCoins.AddCoinRequest;
import com.giftservice.springboot.usecases.addCoins.AddCoinsUseCase;
import com.giftservice.springboot.usecases.createCoupon.CreateCouponRequest;
import com.giftservice.springboot.usecases.createCoupon.CreateCouponUseCase;
import com.giftservice.springboot.usecases.fetchCoins.FetchWalletBalanceUseCase;
import com.giftservice.springboot.usecases.fetchCoupons.FetchCouponsRequest;
import com.giftservice.springboot.usecases.fetchCoupons.FetchCouponsUseCase;
import com.giftservice.springboot.usecases.redeemCoupon.RedeemCouponRequest;
import com.giftservice.springboot.usecases.redeemCoupon.RedeemCouponUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.http.auth.AuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.ServiceUnavailableException;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Log4j2
public class GiftsController {
    private final CreateCouponUseCase createCouponUseCase;
    private final FetchCouponsUseCase fetchCouponsUseCase;
    private final RedeemCouponUseCase redeemCouponUseCase;
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck(){
        return new ResponseEntity<>("health-check", HttpStatus.OK);
    }
    @PostMapping("/create")
    public ResponseEntity<CommonResponse> createCoupon(@CookieValue(value = "user_id") final String userCookie,
                                               @RequestBody final CreateCouponRequest request) throws AuthenticationException, IOException, ServiceUnavailableException {
        request.setUserCookie(userCookie);
        final CommonResponse response = createCouponUseCase.execute(request);
        return new ResponseEntity<>( response, HttpStatus.OK);
    }
    @GetMapping("/coupons")
    public ResponseEntity<CommonResponse> fetchCoupons(@CookieValue(value = "user_id") final String userCookie) throws AuthenticationException, IOException, ServiceUnavailableException {
        final FetchCouponsRequest request = FetchCouponsRequest.builder().build();
        request.setUserCookie(userCookie);
        final CommonResponse response = fetchCouponsUseCase.execute(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping("/redeem")
    public ResponseEntity<CommonResponse> redeemCoupon(@CookieValue(value = "user_id") final String userCookie,
                                                       @RequestBody final RedeemCouponRequest request)
            throws AuthenticationException, IOException, ServiceUnavailableException {
        request.setUserCookie(userCookie);
        final CommonResponse response = redeemCouponUseCase.execute(request);
        return  new ResponseEntity<>(response, HttpStatus.OK);
    }
}
