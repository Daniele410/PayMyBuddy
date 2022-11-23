package com.danozzo.paymybuddy.model;

import javax.persistence.*;

@Entity
public class Profit {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "profit_gen")
    @SequenceGenerator(name = "profit_gen", sequenceName = "profit_seq")
    @Column(name = "id", nullable = false)
    private Long id;

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
