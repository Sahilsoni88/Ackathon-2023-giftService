package com.giftservice.springboot.usecases.fetchCoupons;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.giftservice.springboot.core.values.CouponStatus;
import com.giftservice.springboot.core.values.CouponType;
import com.giftservice.springboot.models.AckoCoupon;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class FetchCouponsResponse {
    private List<UserCoupon> giftedCoupons;
    private List<UserCoupon> availableCoupons;
    public  static  FetchCouponsResponse fromAckoCouponList(final List<AckoCoupon> giftedCouponList,
                                                            final List<AckoCoupon> availableCouponList){
        final List<UserCoupon> giftedAckoCouponList = giftedCouponList.stream().map(UserCoupon::fromAckoCoupon).toList();
        final List<UserCoupon> availableAckoCouponList = availableCouponList.stream().map(UserCoupon::fromAckoCoupon).toList();

        return FetchCouponsResponse.builder()
                .giftedCoupons(giftedAckoCouponList)
                .availableCoupons(availableAckoCouponList)
                .build();
    }
    @Data
    @Builder
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class UserCoupon{
        private String couponCode;
        private CouponStatus status;
        private CouponType type;
        private String expiry;
        private String message;
        private String imageUrl;
        public static final UserCoupon fromAckoCoupon(final AckoCoupon coupon){
            LocalDateTime localDateTime = LocalDateTime.ofInstant(coupon.getExpiry(), ZoneId.systemDefault());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
            String expiryDate = localDateTime.format(formatter);
            return UserCoupon
                    .builder()
                    .couponCode(coupon.getCode())
                    .status(coupon.getStatus())
                    .type(coupon.getType())
                    .expiry(expiryDate)
                    .message(coupon.getMessage())
                    .imageUrl(coupon.getImageUrl())
                    .build();
        }
    }
}
