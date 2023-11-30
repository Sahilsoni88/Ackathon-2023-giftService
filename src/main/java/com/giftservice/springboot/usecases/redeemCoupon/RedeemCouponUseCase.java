package com.giftservice.springboot.usecases.redeemCoupon;

import com.giftservice.springboot.common.CommonRequest;
import com.giftservice.springboot.common.CommonResponse;
import com.giftservice.springboot.core.values.CouponStatus;
import com.giftservice.springboot.infrastructure.user.CentralUserRepo;
import com.giftservice.springboot.models.AckoCoupon;
import com.giftservice.springboot.models.User;
import com.giftservice.springboot.repositories.AckoCouponRepo;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.http.auth.AuthenticationException;
import org.springframework.stereotype.Service;

import javax.naming.ServiceUnavailableException;
import java.io.IOException;
import java.time.Instant;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RedeemCouponUseCase {
    private final AckoCouponRepo couponRepo;
    private final CentralUserRepo userRepo;
    public CommonResponse execute (final RedeemCouponRequest request)
            throws AuthenticationException, IOException, ServiceUnavailableException {
        Optional<AckoCoupon> optionalCoupon = couponRepo.findByCode(request.getCode());
        final User user = userRepo.fetch(request.getUserCookie());
        if (optionalCoupon.isPresent()) {
            double couponDiscount = 0d;
            AckoCoupon coupon = optionalCoupon.get();
            if (!user.getId().equals(coupon.getUserId())) {
                throw new RuntimeException("You are not authorized to redeem this coupon");
            }
            if(coupon.getExpiry() != null || Instant.now().isAfter(coupon.getExpiry())){
                throw new RuntimeException("Coupon has expired");
            }
            if (CouponStatus.ACTIVE.equals(coupon.getStatus())) {
                if (coupon.getUsableAmount() >= request.getAmount()) {
                    double usableAmount = coupon.getUsableAmount() - request.getAmount();
                    if(usableAmount < 1d){
                        coupon.setUsableAmount(0d);
                        coupon.setStatus(CouponStatus.USED);
                    }else {
                        coupon.setUsableAmount(usableAmount);
                    }
                    couponDiscount = request.getAmount();
                } else {
                    couponDiscount = request.getAmount() - coupon.getUsableAmount();
                    coupon.setUsableAmount(0d);
                    coupon.setStatus(CouponStatus.USED);
                }
                couponRepo.save(coupon);
                final RedeemCouponResponse response = RedeemCouponResponse.builder()
                        .code(coupon.getCode())
                        .couponDiscount(couponDiscount)
                        .remainingCouponAmount(coupon.getUsableAmount())
                        .build();
                return CommonResponse.builder()
                        .status("success")
                        .message("success")
                        .data(response)
                        .build();
            } else {
                throw new RuntimeException("Coupon is not active");
            }
        } else {
            throw new RuntimeException("Coupon not found");
        }
    }
}
