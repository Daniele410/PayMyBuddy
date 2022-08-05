package com.danozzo.paymybuddy.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.danozzo.paymybuddy.model.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {

}
