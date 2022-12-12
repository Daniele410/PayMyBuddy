package com.danozzo.paymybuddy.service;

import com.danozzo.paymybuddy.model.Transfer;
import com.danozzo.paymybuddy.web.dto.TransferDto;
import exception.UserNotFoundException;

import java.math.BigDecimal;
/**
 * contain all business service methods for Transfer
 */
public interface ITransferService {

    /**
     * @param transferDto String description, double amount, String email
     * @param amount double amount
     * @return save transfer from user to friend
     * @throws UserNotFoundException
     */
    public Transfer saveTransfert(TransferDto transferDto, double amount) throws UserNotFoundException;


}
