package com.danozzo.paymybuddy.model;

import javax.persistence.*;

@Entity
public class Profit {
    @Id
    @Column(name = "id", nullable = false)
    private long id;

    private double fees;

    @ManyToOne
    private Transfer transfer;

    public void setFees(double fees) {
        this.fees = fees;
    }

    public Transfer getTransfer() {
        return transfer;
    }

    public void setTransfer(Transfer transfer) {
        this.transfer = transfer;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getFees() {
        return fees;
    }

    public void setFees(Double fees) {
        this.fees = fees;
    }
}
