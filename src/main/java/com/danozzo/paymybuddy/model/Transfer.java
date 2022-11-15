package com.danozzo.paymybuddy.model;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "transfer")
@DynamicUpdate
public class Transfer {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transfer_id")
    private Long transferId;

    @Column(name = "description")
    private String description;

    @Column(name = "amount")
    private BigDecimal amount;


    @ManyToOne
    @JoinColumn(name = "debit_account")
    private User debitAccount;

    @ManyToOne
    @JoinColumn(name = "credit_account")
    private User creditAccount;


    public Transfer() {
    }



    public Transfer(String description, BigDecimal amount, User debitAccount, User creditAccount) {
        this.description = description;
        this.amount = amount;
        this.debitAccount = debitAccount;
        this.creditAccount = creditAccount;

    }

    public Transfer(String description, BigDecimal amount) {
        this.description = description;
        this.amount = amount;

    }



    public Long getTransferId() {
        return transferId;
    }

    public void setTransferId(Long transferId) {
        this.transferId = transferId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public User getDebitAccount() {
        return debitAccount;
    }

    public void setDebitAccount(User debitAccount) {
        this.debitAccount = debitAccount;
    }

    public User getCreditAccount() {
        return creditAccount;
    }

    public void setCreditAccount(User creditAccount) {
        this.creditAccount = creditAccount;
    }



//        public void transfer(double amount,double balance, User user){
//        if (balance < amount){
//            throw new RuntimeException("Transfer fails!! you do not have enough money!");
//        }else{
//            user.getBalance() += amount;
//
//        }
//    }


}