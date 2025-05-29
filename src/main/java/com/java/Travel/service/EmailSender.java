package com.java.Travel.service;


import com.java.Travel.controller.dto.OrderDTO;

public interface EmailSender {
    void sendСonfirmPurchaseToEmail(OrderDTO orderDTO);

    void sendСancellationСonfirmToEmail(OrderDTO orderDTO);
}
