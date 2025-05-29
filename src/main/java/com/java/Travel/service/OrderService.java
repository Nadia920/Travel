package com.java.Travel.service;

import com.java.Travel.controller.dto.OrderCreateUpdateDTO;
import com.java.Travel.controller.dto.OrderDTO;
import com.java.Travel.model.OrderStatus;


import java.util.List;

public interface OrderService {

    OrderDTO add(OrderCreateUpdateDTO orderDTO);

    List<OrderDTO> getOrdersByUserId(Long id);

    OrderDTO findById(Long id);

    void deleteTicketsOnTripByUSer(OrderCreateUpdateDTO order);

    OrderDTO takeMoreTickets(OrderCreateUpdateDTO order);

    List<OrderDTO> findAllByTripId(Long idTrip);

    List<OrderDTO> getOrdersByUserIdAndStatus(Long id, OrderStatus status);
}
