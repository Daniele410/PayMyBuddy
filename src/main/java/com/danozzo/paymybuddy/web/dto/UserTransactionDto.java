package com.danozzo.paymybuddy.web.dto;

import com.sun.istack.NotNull;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.Currency;

public class UserTransactionDto {

//    @NotNull
//    //A cross-record validation is also done in UserTransferController
//    private Long userDestinationId;

    @NotNull
    private String email;

    @NotNull
    private BigDecimal amount;

    @NotNull
    private String description;


    public UserTransactionDto() {
    }

    public UserTransactionDto(String email, BigDecimal amount, String description) {
        this.email = email;
        this.amount = amount;
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
