package com.java.Travel.exception;

import com.java.Travel.controller.dto.OrderCreateUpdateDTO;

public class OrderOnThisBusStationAlreadyExistException extends RuntimeException {
    OrderCreateUpdateDTO orderDTO;

    public OrderOnThisBusStationAlreadyExistException(String msg, OrderCreateUpdateDTO orderDTO) {
        super(msg);
        this.orderDTO = orderDTO;
    }
}
