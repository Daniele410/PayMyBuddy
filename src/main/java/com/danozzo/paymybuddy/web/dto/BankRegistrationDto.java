package com.danozzo.paymybuddy.web.dto;

import com.danozzo.paymybuddy.model.User;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class BankRegistrationDto {


    private String bankName;


    private String iban;


    private String location;

    private long userId;



    public BankRegistrationDto() {
    }

    public BankRegistrationDto(String bankName, String iban, String location, long userId) {
        this.bankName = bankName;
        this.iban = iban;
        this.location = location;
        this.userId = userId;
    }

    public BankRegistrationDto(String bankName, String iban, String location) {
        this.bankName = bankName;
        this.iban = iban;
        this.location = location;

    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


}
