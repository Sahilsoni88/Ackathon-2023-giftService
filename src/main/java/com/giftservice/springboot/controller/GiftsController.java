package com.giftservice.springboot.controller;

import com.giftservice.springboot.common.CommonResponse;
import com.giftservice.springboot.usecases.createCoupon.CreateCouponRequest;
import com.giftservice.springboot.usecases.createCoupon.CreateCouponUseCase;
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
}
