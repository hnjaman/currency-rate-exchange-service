package com.hnj.code.repository;

import com.hnj.code.model.ExchangeRateToIDR;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RateExchangeRepository extends JpaRepository<ExchangeRateToIDR, Integer> {
}
