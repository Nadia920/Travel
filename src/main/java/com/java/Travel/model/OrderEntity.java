package com.java.Travel.model;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "User_order")
public class OrderEntity extends BaseEntity {


    @NotNull
    @Column(name = "final_cost")
    private Double finalCost;

    @Column(name = "status", length = 8)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @NotNull
    @Column(name = "order_date",columnDefinition = "timestamp")
    private Timestamp orderDate;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @NotNull
    private UserEntity user;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_id")
    @NotNull
    private TripEntity trip;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<TicketEntity> tickets;

    public OrderEntity() {
    }

    public OrderEntity(@NotNull Double finalCost, OrderStatus status, @NotNull Timestamp orderDate, @NotNull UserEntity user, @NotNull TripEntity trip) {
        this.finalCost = finalCost;
        this.orderDate = orderDate;
        this.status = status;
        this.user = user;
        this.trip = trip;
    }

    public Timestamp getOrderDate() {
        return orderDate;
    }

    public List<TicketEntity> getTickets() {
        return tickets;
    }

    public void setTickets(List<TicketEntity> tickets) {
        this.tickets = tickets;
    }

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity owner) {
        this.user = owner;
    }

    public TripEntity getTrip() {
        return trip;
    }

    public void setTrip(TripEntity trip) {
        this.trip = trip;
    }

    public Double getFinalCost() {
        return finalCost;
    }

    public void setFinalCost(Double finalCost) {
        this.finalCost = finalCost;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
