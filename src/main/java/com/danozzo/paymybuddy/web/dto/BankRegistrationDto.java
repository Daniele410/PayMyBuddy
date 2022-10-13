package com.danozzo.paymybuddy.web.dto;

import javax.persistence.Column;

public class BankRegistrationDto {


    private String bankName;


    private String iban;


    private String location;

    public BankRegistrationDto() {
    }

    public BankRegistrationDto(String bankName, String iban, String location) {
        this.bankName = bankName;
        this.iban = iban;
        this.location = location;
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
