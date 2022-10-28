package com.danozzo.paymybuddy.repository;

import com.danozzo.paymybuddy.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {


    User findByEmail(String email);


    Boolean existsByEmail(String email);


    Optional<User> findById(String email);
}
