package com.java.Travel.controller.dto;

public class TicketCreateUpdateDTO {

    private Long id;
    private Long idTrip;
    private Long idClient;
    private Integer countSeats;
    private Double finalCost;

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

    public Long getIdClient() {
        return idClient;
    }

    public void setIdClient(Long idClient) {
        this.idClient = idClient;
    }

    public Integer getCountSeats() {
        return countSeats;
    }

    public void setCountSeats(Integer countSeats) {
        this.countSeats = countSeats;
    }

    public Double getFinalCost() {
        return finalCost;
    }

    public void setFinalCost(Double finalCost) {
        this.finalCost = finalCost;
    }

    @Override
    public String toString() {
        return "TicketCreateUpdateDTO{" +
                "id=" + id +
                ", idTrip=" + idTrip +
                ", idClient=" + idClient +
                ", countSeats=" + countSeats +
                ", finalCost=" + finalCost +
                '}';
    }
}