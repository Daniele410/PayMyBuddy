package com.danozzo.paymybuddy.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.*;

@Entity
@Table(name = "bank_account")
public class BankAccount {
    private static final Logger logger = LogManager.getLogger("BankAccount");
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_count_bank")
    private int idCountBank;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "iban")
    private String iban;

    @Column(name = "bank_location")
    private String location;

    private double balance;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public BankAccount() {
    }

    public BankAccount(String bankName, String iban, String location) {
        this.bankName = bankName;
        this.iban = iban;
        this.location = location;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public double getBalance() {
        return this.balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

//        public void deposit(double amount){
//        balance += amount;
//    }
//
//    public void withdraw(double amount){
//        if (amount <= balance){
//            balance -= amount;
//        }else {
//
//            throw new RuntimeException("error!! you do not have enough money!");
//        }
//    }
//
//    public void transfer(double amount, BankAccount bankAccount){
//        if (this.balance < amount){
//            throw new RuntimeException("Transfer fails!! you do not have enough money!");
//        }else{
//            this.balance-=amount;
//            bankAccount.balance += amount;
//            logger.info("User of " + this.bankName + " become €"+this.balance);
//        }
//    }

}
