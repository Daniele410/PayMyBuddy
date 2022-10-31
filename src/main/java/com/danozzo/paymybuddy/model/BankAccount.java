package com.danozzo.paymybuddy.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "bank_account")
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_count_bank")
    private int idCountBank;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "iban")
    private String iban;

    @Column(name = "bank_location")
    private String location;

    @Column(name = "user_id")
    private long userId;

    public BankAccount() {
    }

    public BankAccount(String bankName, String iban, String location, long userId) {
        this.bankName = bankName;
        this.iban = iban;
        this.location = location;
        this.userId = userId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getIdCountBank() {
        return idCountBank;
    }

    public void setIdCountBank(int idCountBank) {
        this.idCountBank = idCountBank;
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
