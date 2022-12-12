package com.danozzo.paymybuddy.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.*;

/**
 * BankAccount model
 */
@Entity
@Table(name = "bank_account")
public class BankAccount {
    private static final Logger logger = LogManager.getLogger("BankAccount");

    /**
     * pay may buddy profit id
     **/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_count_bank")
    private Long idCountBank;

    /**
     * pay may buddy bankName
     **/
    @Column(name = "bank_name")
    private String bankName;

    /**
     * pay may buddy iban
     **/
    @Column(name = "iban")
    private String iban;

    /**
     * pay may buddy location
     **/
    @Column(name = "bank_location")
    private String location;

    /**
     * pay may buddy balance
     **/
    private double balance;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public BankAccount() {
    }

    /**
     * @param bankName
     * @param iban
     * @param location
     */
    public BankAccount(String bankName, String iban, String location) {
        this.bankName = bankName;
        this.iban = iban;
        this.location = location;
    }

    /**
     * @param idCountBank
     * @param bankName
     * @param iban
     * @param location
     * @param balance
     */
    public BankAccount(Long idCountBank, String bankName, String iban, String location, double balance) {
        this.idCountBank = idCountBank;
        this.bankName = bankName;
        this.iban = iban;
        this.location = location;
        this.balance = balance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getIdCountBank() {
        return idCountBank;
    }

    public void setIdCountBank(Long idCountBank) {
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

    public double getBalance() {
        return this.balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }



}
