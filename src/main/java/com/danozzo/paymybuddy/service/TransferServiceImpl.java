package com.danozzo.paymybuddy.service;

import com.danozzo.paymybuddy.repository.TransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransferServiceImpl implements ITransferService{

    @Autowired
    TransferRepository transferRepository;

}
