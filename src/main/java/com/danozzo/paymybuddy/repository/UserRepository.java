package com.danozzo.paymybuddy.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.danozzo.paymybuddy.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

}
