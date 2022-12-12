package com.danozzo.paymybuddy.service;

import com.danozzo.paymybuddy.model.BankAccount;
import com.danozzo.paymybuddy.web.dto.BankRegistrationDto;
import com.danozzo.paymybuddy.web.dto.TransferDto;

import java.util.List;

/**
 * contain all business service methods for BankAccount
 */
public interface IBankAccountService {

    /**
     * @param bankRegistrationDto
     * @param emailConnectedUser
     * @return save bank in dataBase
     */
    BankAccount saveBank(BankRegistrationDto bankRegistrationDto, String emailConnectedUser);


    /**
     * @param iban
     * @return true if is done
     * @return check if the iban exists
     */
    public boolean existsByIban(String iban);


    /**
     * @param emailConnectedUser
     * get bankAccount of current user
     * @return
     */
    List<BankAccount> getUsersBanks(String emailConnectedUser);


    /**
     * @param id
     * delete bankAccount by id
     */
    public void deleteBankById(Long id);

    /**
     * @param bankAccount
     * @param amount
     * save transfer user to bank
     * @throws Exception
     */
    void saveBankTransfert(BankRegistrationDto bankAccount, double amount) throws Exception;
}
