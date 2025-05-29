package com.java.Travel.controller.dto;

public class OrderCreateUpdateDTO {

    private Long id;
    private Long idTrip;
    private Integer countSeats;
    private Long idClient;
    private Double priceOneSeat;
    private Double finalCost;
    private Double returnMoney;
    private Integer returnTickets;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdTrip() {
        return idTrip;
    }

    public void setIdTrip(Long idTrip) {
        this.idTrip = idTrip;
    }

    public Integer getCountSeats() {
        return countSeats;
    }

    public void setCountSeats(Integer countSeats) {
        this.countSeats = countSeats;
    }

    public Long getIdClient() {
        return idClient;
    }

    public void setIdClient(Long idClient) {
        this.idClient = idClient;
    }

    public Double getPriceOneSeat() {
        return priceOneSeat;
    }

    public void setPriceOneSeat(Double priceOneSeat) {
        this.priceOneSeat = priceOneSeat;
    }

    public Double getFinalCost() {
        return finalCost;
    }

    public void setFinalCost(Double finalCost) {
        this.finalCost = finalCost;
    }

    public Double getReturnMoney() {
        return returnMoney;
    }

    public void setReturnMoney(Double returnMoney) {
        this.returnMoney = returnMoney;
    }

    public Integer getReturnTickets() {
        return returnTickets;
    }

    public void setReturnTickets(Integer returnTickets) {
        this.returnTickets = returnTickets;
    }

    @Override
    public String toString() {
        return "OrderCreateUpdateDTO{" +
                "id=" + id +
                ", idTrip=" + idTrip +
                ", countSeats=" + countSeats +
                ", idClient=" + idClient +
                ", priceOneSeat=" + priceOneSeat +
                ", finalCost=" + finalCost +
                ", returnMoney=" + returnMoney +
                ", returnTickets=" + returnTickets +
                '}';
    }
}
