package com.java.Travel.controller.dto;


import com.java.Travel.model.OrderStatus;

import java.sql.Timestamp;
import java.util.List;

public class OrderDTO {

    private Long id;
    private Double finalCost;
    private OrderStatus status;
    private Timestamp orderDate;
    private UserDTO userDTO;
    private TripDTO tripDTO;
    private List<TicketDTO> ticketDTOList;

    public OrderDTO() {
    }

    public OrderDTO(Long id, Double finalCost, OrderStatus status, Timestamp orderDate, UserDTO userDTO, TripDTO tripDTO, List<TicketDTO> ticketDTOList) {
        this.id = id;
        this.finalCost = finalCost;
        this.status = status;
        this.orderDate = orderDate;
        this.userDTO = userDTO;
        this.tripDTO = tripDTO;
        this.ticketDTOList = ticketDTOList;
    }

    public OrderDTO(Long id) {
        this.id = id;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getFinalCost() {
        return finalCost;
    }

    public void setFinalCost(Double finalCost) {
        this.finalCost = finalCost;
    }

    public Timestamp getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public TripDTO getTripDTO() {
        return tripDTO;
    }

    public void setTripDTO(TripDTO tripDTO) {
        this.tripDTO = tripDTO;
    }

    public List<TicketDTO> getTicketDTOList() {
        return ticketDTOList;
    }

    public void setTicketDTOList(List<TicketDTO> ticketDTOList) {
        this.ticketDTOList = ticketDTOList;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "id=" + id +
                ", finalCost=" + finalCost +
                ", status=" + status +
                ", orderDate=" + orderDate +
                ", userDTO=" + userDTO +
                ", TripDTO=" + tripDTO +
                ", ticketDTOList=" + ticketDTOList +
                '}';
    }
}
