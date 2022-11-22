package com.danozzo.paymybuddy.repository;

import com.danozzo.paymybuddy.model.Profit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfitRepository extends JpaRepository<Profit, Long> {
}
