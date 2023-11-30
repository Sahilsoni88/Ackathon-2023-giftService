package com.giftservice.springboot.repositories;

import com.giftservice.springboot.core.values.CoinType;
import com.giftservice.springboot.core.values.UserCoins;
import com.giftservice.springboot.models.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WalletRepo  extends JpaRepository<Wallet, Integer> {
    //select user_id, coin_type, sum(balance) from wallet where user_id = '1042570' group by coin_type, user_id
    @Query(value = "SELECT w.user_id, w.coin_type, SUM(w.balance) FROM wallet w WHERE w.user_id = :userId GROUP BY w.coin_type, w.user_id", nativeQuery = true)
    List<Object[]> getByUserId(final String userId);
    Optional<Wallet> findByUserIdAndCoinType(final String userId, final CoinType coinType);
}
