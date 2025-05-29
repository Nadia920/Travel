package com.java.Travel.service;


import com.java.Travel.controller.dto.OrderCreateUpdateDTO;
import com.java.Travel.model.TripEntity;
import com.java.Travel.model.UserEntity;

public interface PaymentService {


    boolean payOrder(OrderCreateUpdateDTO orderDTO, TripEntity tripEntity, UserEntity userEntity);

    boolean returnMoney(OrderCreateUpdateDTO order, TripEntity tripEntity, UserEntity userEntity);

    boolean returnMoneyForTripCancellation(TripEntity tripEntity);

}
