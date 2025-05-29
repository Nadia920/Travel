package com.java.Travel.service.ServiceImpl;


import com.java.Travel.controller.dto.OrderCreateUpdateDTO;
import com.java.Travel.exception.WalletIncorrectBalanceException;
import com.java.Travel.model.OrderEntity;
import com.java.Travel.model.TripEntity;
import com.java.Travel.model.UserEntity;

import com.java.Travel.model.WalletEntity;

import com.java.Travel.repository.WalletEntityRepository;
import com.java.Travel.service.PaymentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;

@Service
public class PaymentServiceImpl implements PaymentService {
    private final static Logger LOGGER = LogManager.getLogger();

    @Autowired
    private WalletEntityRepository walletRepository;


    private DecimalFormat decimalFormat = new DecimalFormat("#.##");

    @Transactional
    @Override
    public boolean payOrder(OrderCreateUpdateDTO orderDTO, TripEntity tripEntity, UserEntity userEntity) {
        LOGGER.info("Pay order: " + orderDTO + ", trip: " + tripEntity + ",user: " + userEntity);
        WalletEntity walletEntity = walletRepository.findByOwnerId(userEntity.getId());
        Double mustPay = orderDTO.getCountSeats() * tripEntity.getPrice();
        Double currBalance = walletEntity.getSum();
        if (mustPay < currBalance) {
            currBalance -= mustPay;
            walletEntity.setSum(Double.parseDouble(decimalFormat.format(currBalance).replace(",", ".")));
            walletRepository.save(walletEntity);
        } else {
            LOGGER.info("Wallet balance incorrect.\nBalance: " + walletEntity.getSum());
            throw new WalletIncorrectBalanceException("You need to replenish wallet balance.\nYour balance: " + walletEntity.getSum());
        }
        return true;
    }

    @Transactional
    @Override
    public boolean returnMoney(OrderCreateUpdateDTO orderDTO, TripEntity tripEntity, UserEntity userEntity) {
        LOGGER.info("Return money for order: " + orderDTO + ", user: " + userEntity);
        WalletEntity walletEntity = walletRepository.findByOwnerId(userEntity.getId());
        Double mustReturn = orderDTO.getReturnTickets() * tripEntity.getPrice();
        Double currBalance = walletEntity.getSum() + mustReturn;

        walletEntity.setSum(Double.parseDouble(decimalFormat.format(currBalance).replace(",", ".")));
        walletRepository.save(walletEntity);

        return true;
    }

    @Transactional
    @Override
    public boolean returnMoneyForTripCancellation(TripEntity tripEntity) {
        LOGGER.info("Return money for trip cancellation. Trip: " + tripEntity);
        WalletEntity walletEntity;
        Double currBalance;
        for (OrderEntity orderEntity : tripEntity.getOrders()) {
            walletEntity = orderEntity.getUser().getWallet();
            currBalance = walletEntity.getSum();
            walletEntity.setSum(currBalance + orderEntity.getFinalCost());
            walletRepository.save(walletEntity);
        }

        return true;
    }

}
