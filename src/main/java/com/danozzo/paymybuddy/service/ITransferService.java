package com.danozzo.paymybuddy.service;

import com.danozzo.paymybuddy.model.Transfer;
import com.danozzo.paymybuddy.web.dto.TransferDto;

public interface ITransferService {

    public Transfer saveTransfert(TransferDto transferDto);
}
