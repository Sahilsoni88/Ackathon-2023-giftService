package com.giftservice.springboot.repositories;

import com.giftservice.springboot.models.AckoCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AckoCouponRepo extends JpaRepository<AckoCoupon, Integer> {
    List<AckoCoupon> findByUserId(String userId);
    List<AckoCoupon> findByIssuer(String issuer);
}
