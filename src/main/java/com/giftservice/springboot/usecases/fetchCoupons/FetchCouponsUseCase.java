package com.giftservice.springboot.usecases.fetchCoupons;

import com.giftservice.springboot.common.CommonResponse;
import com.giftservice.springboot.infrastructure.user.CentralUserRepo;
import com.giftservice.springboot.models.AckoCoupon;
import com.giftservice.springboot.models.User;
import com.giftservice.springboot.repositories.AckoCouponRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.http.auth.AuthenticationException;
import org.springframework.stereotype.Service;

import javax.naming.ServiceUnavailableException;
import java.io.IOException;
import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class FetchCouponsUseCase {
    private final AckoCouponRepo couponRepo;
    private final CentralUserRepo userRepo;
    public CommonResponse execute(final FetchCouponsRequest request)
            throws AuthenticationException, IOException, ServiceUnavailableException {
        final User user = this.userRepo.fetch(request.getUserCookie());
        final List<AckoCoupon> availableAckoCouponList = couponRepo.findByUserId(user.getId());
        final List<AckoCoupon> issuedAckoCouponList = couponRepo.findByIssuer(user.getId());
        final FetchCouponsResponse response = FetchCouponsResponse.fromAckoCouponList(issuedAckoCouponList,
                availableAckoCouponList);
        return CommonResponse.builder()
                .status("success")
                .message("fetched successfully")
                .data(response)
                .build();
    }
}
