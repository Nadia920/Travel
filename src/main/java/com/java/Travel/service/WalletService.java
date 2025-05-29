package com.java.Travel.service;


import com.java.Travel.controller.dto.BankCardDTO;
import com.java.Travel.controller.dto.WalletDTO;
import com.java.Travel.security.CustomUserDetail;

public interface WalletService {
    WalletDTO findByUserId(Long id);

    void replenishBalance(BankCardDTO bankCard, CustomUserDetail currUser);
}
