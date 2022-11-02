package com.danozzo.paymybuddy.web.dto;

import com.sun.istack.NotNull;

import java.math.BigDecimal;
import java.util.Currency;

public class UserTransactionDTO {

    @NotNull
    //A cross-record validation is also done in UserTransferController
    private Long userDestinationId;

    @NotNull
    private BigDecimal amount;

    @NotNull
    //A cross-record validation is also done in UserTransferController
    private Currency currency;


    public Long getUserDestinationId() {
        return userDestinationId;
    }

    public void setUserDestinationId(Long userDestinationId) {
        this.userDestinationId = userDestinationId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
