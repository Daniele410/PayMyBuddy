package com.danozzo.paymybuddy.model;

import javax.persistence.*;

/**
 * Profit model
 */
@Entity
public class Profit {

    /**
     * pay may buddy profit id
     **/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * pay may buddy fees
     **/
    private double fees;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFees(double fees) {
        this.fees = fees;
    }

    public Double getFees() {
        return fees;
    }

    public void setFees(Double fees) {
        this.fees = fees;
    }
}
