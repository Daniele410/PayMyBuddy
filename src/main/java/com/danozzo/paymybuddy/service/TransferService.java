package com.danozzo.paymybuddy.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.danozzo.paymybuddy.model.Transfer;
import com.danozzo.paymybuddy.repository.TransferRepository;

@Service
public class TransferService {
	
	@Autowired
	TransferRepository transferRepository;
	
	
	public Iterable<Transfer> getTransfers(){
		return transferRepository.findAll();
	}
	
	public Optional<Transfer> getTransferById(Integer id){
		return transferRepository.findById(id);
	}

}
