package com.giftservice.springboot.repositories;

import com.giftservice.springboot.models.AckoCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AckoCouponRepo extends JpaRepository<AckoCoupon, Integer> {
}
