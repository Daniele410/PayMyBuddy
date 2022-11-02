package com.danozzo.paymybuddy.model;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name="transfer")
@DynamicUpdate
public class Transfer {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="transfer_id")
    private Long transferId;


    @Column(name= "description")
    private String description;

    @Column(name="amount")
    private BigDecimal amount;


    @Column(name= "transaction_date")
    private Date transactionDate;

    private Double fees;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)//child entity, owner of the relationship
    @JoinColumn(name = "user_id")
    private User userSource;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)//child entity, owner of the relationship
    @JoinColumn(name = "user_friend_id")
    private User userFriend;

    public User getUserFriend() {
        return userFriend;
    }

    public void setUserFriend(User userFriend) {
        this.userFriend = userFriend;
    }

    public Transfer() {
    }

    public Transfer(String description, BigDecimal amount, Date transactionDate, Double fees, User userSource, User userFriend) {
        this.description = description;
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.fees = fees;
        this.userSource = userSource;
        this.userFriend = userFriend;
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

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Double getFees() {
        return fees;
    }

    public void setFees(Double fees) {
        this.fees = fees;
    }

    public User getUserSource() {
        return userSource;
    }

    public void setUserSource(User userSource) {
        this.userSource = userSource;
    }
}