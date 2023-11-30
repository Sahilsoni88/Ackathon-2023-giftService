package com.giftservice.springboot.models;

import com.giftservice.springboot.core.values.CouponStatus;
import com.giftservice.springboot.core.values.CouponType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class AckoCoupon {
    public static final String TABLE_NAME = "acko_coupon";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "coupon_id")
    private UUID couponId;
    @Column(name = "code")
    private String code;
    @Column(name = "amount")
    private double amount;
    @Column(name = "usable_amount")
    private double usableAmount;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "issuer")
    private String issuer;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private CouponStatus status;
    @Column(name = "expiry")
    private Instant expiry;
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private CouponType type;
    @Column(name = "image_url")
    private String imageUrl;
    @Column(name = "message")
    private String message;
    @CreationTimestamp
    @Column(name = "created_on", updatable = false)
    protected Instant createdOn;

    @UpdateTimestamp
    @Column(name = "updated_on")
    protected Instant updatedOn;
}
