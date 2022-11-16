package com.danozzo.paymybuddy.web.dto;

import com.danozzo.paymybuddy.model.User;
import com.sun.istack.NotNull;

import java.util.List;

public class TransferDto {

//    @NotNull
//    //A cross-record validation is also done in UserTransferController
//    private Long userDestinationId;

    @NotNull
    private String email;

    @NotNull
    private double amount;

    @NotNull
    private String description;

    @NotNull
    private List<User> listFriend;

    public TransferDto() {
    }

    public TransferDto(String description, double amount) {

        this.amount = amount;
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
}
