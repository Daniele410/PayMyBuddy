package com.danozzo.paymybuddy.repository;

import com.danozzo.paymybuddy.model.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {

    Boolean existsByIban(String iban);

    Optional<BankAccount> findById(Long userId);
}
