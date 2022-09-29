package com.danozzo.paymybuddy.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.danozzo.paymybuddy.model.Transfer;

@Repository
public interface TransferRepository extends CrudRepository<Transfer, Integer>{

}
