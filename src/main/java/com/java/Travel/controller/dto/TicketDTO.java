package com.java.Travel.controller.dto;


public class TicketDTO {

    private Long id;
    private OrderDTO orderDTO;
    private Double price;

    public TicketDTO() {
    }

    public TicketDTO(Long id, OrderDTO orderDTO, Double price) {
        this.id = id;
        this.orderDTO = orderDTO;
        this.price = price;
    }

    public TicketDTO(Long id, Double price) {
        this.id = id;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrderDTO getOrderDTO() {
        return orderDTO;
    }

    public void setOrderDTO(OrderDTO orderDTO) {
        this.orderDTO = orderDTO;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
