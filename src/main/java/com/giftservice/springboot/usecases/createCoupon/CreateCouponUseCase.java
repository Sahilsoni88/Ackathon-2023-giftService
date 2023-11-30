package com.giftservice.springboot.usecases.createCoupon;

import com.giftservice.springboot.common.CommonResponse;
import com.giftservice.springboot.core.values.CouponStatus;
import com.giftservice.springboot.core.values.CouponType;
import com.giftservice.springboot.infrastructure.user.CentralUserRepo;
import com.giftservice.springboot.models.AckoCoupon;
import com.giftservice.springboot.models.User;
import com.giftservice.springboot.repositories.AckoCouponRepo;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.http.auth.AuthenticationException;
import org.springframework.stereotype.Service;

import javax.naming.ServiceUnavailableException;
import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
@Log4j2
@RequiredArgsConstructor
public class CreateCouponUseCase {
    private final AckoCouponRepo couponRepo;
    private final CentralUserRepo userRepo;
    public final CommonResponse execute(final CreateCouponRequest request) throws AuthenticationException, IOException, ServiceUnavailableException {
        final User issuer = this.userRepo.fetch(request.getUserCookie());
        final User user = userRepo.create(User.builder()
                .name(request.getName())
                .phone(request.getPhoneNumber())
                .email(request.getEmail())
                .build()
        );
        final Instant expiry = Instant.now().plus(365, ChronoUnit.DAYS);
        final CouponStatus status = request.getType().equals(CouponType.GIFT) ?
                CouponStatus.PAYMENT_PENDING
                : CouponStatus.ACTIVE;
        AckoCoupon coupon = AckoCoupon.builder()
                .code(generateCouponCode())
                .couponId(UUID.randomUUID())
                .userId(user.getId())
                .type(request.getType())
                .expiry(expiry)
                .issuer(issuer.getId())
                .amount(request.getAmount())
                .usableAmount(request.getAmount())
                .status(status)
                .message(StringUtils.isEmpty(request.getMessage()) ? "Best wishes" : request.getMessage())
                .imageUrl(StringUtils.isEmpty(request.getImageUrl()) ? "default_url" : request.getImageUrl())
                .build();
        couponRepo.save(coupon);
        final CreateCouponResponse response = CreateCouponResponse.builder()
                .couponCode(coupon.getCode())
                .amount(coupon.getAmount())
                .build();
        return CommonResponse.builder()
                .status("success")
                .message("coupon created successfully")
                .data(response)
                .build();
    }

    private static String generateCouponCode() {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
        return uuid.substring(0, 10);
    }
}
