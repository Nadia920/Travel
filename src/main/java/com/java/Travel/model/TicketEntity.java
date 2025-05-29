package com.java.Travel.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Ticket")
public class TicketEntity extends BaseEntity {

    @NotNull
    private Double price;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    @NotNull
    private OrderEntity order;

    public TicketEntity() {
    }

    public TicketEntity(@NotNull Double price, @NotNull OrderEntity order) {
        this.price = price;
        this.order = order;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public OrderEntity getOrder() {
        return order;
    }

    public void setOrder(OrderEntity order) {
        this.order = order;
    }


}
