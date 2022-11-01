package com.danozzo.paymybuddy.model;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name="transfer")
@DynamicUpdate
public class Transfer {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="transfer_id")
    private int transferId;

    @Column(name="amount")
    private double amount;

    @Column(name= "description")
    private String description;

    @Column(name= "transaction_date")
    private Date transactionDate;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Transfer() {
    }

    public Transfer(double amount, String description, Date transactionDate) {
        this.amount = amount;
        this.description = description;
        this.transactionDate = transactionDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getTransferId() {
        return transferId;
    }

    public void setTransferId(int transferId) {
        this.transferId = transferId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }


}