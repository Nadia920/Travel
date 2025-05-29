package com.java.Travel.service.ServiceImpl;


import com.java.Travel.controller.dto.BankCardDTO;
import com.java.Travel.controller.dto.UserDTO;
import com.java.Travel.controller.dto.WalletDTO;


import com.java.Travel.exception.WalletBalanceException;
import com.java.Travel.model.WalletEntity;
import com.java.Travel.repository.WalletEntityRepository;
import com.java.Travel.security.CustomUserDetail;
import com.java.Travel.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;

@Service
public class WalletServiceImpl implements WalletService {

    private final Double MAX_BALANCE = 1_000_000D;

    @Autowired
    private WalletEntityRepository walletRepository;


    @Override
    public WalletDTO findByUserId(Long id) {
        return mapWalletDTO(walletRepository.findByOwnerId(id));
    }

    @Override
    public void replenishBalance(BankCardDTO bankCard, CustomUserDetail currUser) {

        WalletEntity walletEntity = walletRepository.findByOwnerId(currUser.getId());
        Double currBalance = walletEntity.getSum();

        if (currBalance + bankCard.getBalance() < MAX_BALANCE) {
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            currBalance += bankCard.getBalance();
            walletEntity.setSum(Double.parseDouble(decimalFormat.format(currBalance).replace(",", ".")));
            walletRepository.save(walletEntity);
        } else {
            throw new WalletBalanceException("Your_wallet_balance_should_not_exceed_1_000_000", bankCard);
        }
    }

    private WalletDTO mapWalletDTO(WalletEntity walletEntity) {
        return new WalletDTO(walletEntity.getId(), walletEntity.getSum(), new UserDTO(walletEntity.getOwner().getId()));
    }
}
