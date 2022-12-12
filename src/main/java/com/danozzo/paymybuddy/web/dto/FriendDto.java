package com.danozzo.paymybuddy.web.dto;

import java.util.Date;
/**
 * user data transform object
 */
public class FriendDto {

    private String connection;
    private String description;
    private Date date;
    private Double amount;

    public FriendDto() {

    }

    public FriendDto(String connection, String description, Date date, Double amount) {
        this.connection = connection;
        this.description = description;
        this.date = date;
        this.amount = amount;
    }



    public String getConnection() {
        return connection;
    }

    public void setConnection(String connection) {
        this.connection = connection;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
