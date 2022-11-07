package com.danozzo.paymybuddy.service;

import com.danozzo.paymybuddy.model.User;
import com.danozzo.paymybuddy.repository.TransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransferServiceImpl implements ITransferService {

    @Autowired
    TransferRepository transferRepository;



    @Autowired
    UserServiceImpl userService;







}
