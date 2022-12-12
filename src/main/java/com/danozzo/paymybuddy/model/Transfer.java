package com.danozzo.paymybuddy.model;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * Transfer model
 */
@Entity
@Table(name = "transfer")
@DynamicUpdate
public class Transfer {
    /**
     * pay may buddy transfers id
     **/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transfer_id")
    private Long transferId;
    /**
     * pay may buddy description
     **/
    @Column(name = "description")
    private String description;
    /**
     * pay may buddy amount
     **/
    @Column(name = "amount")
    private double amount;

    @ManyToOne
    @JoinColumn(name = "debit_account")
    private User debitAccount;

    @ManyToOne
    @JoinColumn(name = "credit_account")
    private User creditAccount;


    public Transfer() {
    }

    /**
     * @param description
     * @param amount
     * @param debitAccount
     * @param creditAccount
     */
    public Transfer(String description, double amount, User debitAccount, User creditAccount) {
        this.description = description;
        this.amount = amount;
        this.debitAccount = debitAccount;
        this.creditAccount = creditAccount;

    }

    /**
     * @param description
     * @param amount
     */
    public Transfer(String description, double amount) {
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
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


}