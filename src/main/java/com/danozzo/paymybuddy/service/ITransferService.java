package com.danozzo.paymybuddy.service;

import com.danozzo.paymybuddy.model.Transfer;
import com.danozzo.paymybuddy.web.dto.TransferDto;

import java.math.BigDecimal;

public interface ITransferService {

    public Transfer saveTransfert(TransferDto transferDto, BigDecimal amount);
}
