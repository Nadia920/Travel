package com.java.Travel.controller.dto;

import java.math.BigInteger;

public class TripCreateUpdateDTO {

    private Long cityFromId;
    private Long cityToId;
    private Long busStationFromId;
    private Long busStationToId;
    private Long dateDeparture;
    private Long dateArrival;
    private Long busId;
    private Long companyId;
    private Integer allSeats;
    private Integer freeSeats;
    private Double ticketPrice;
    private Long id;

    public Long getCityFromId() {
        return cityFromId;
    }

    public void setCityFromId(Long cityFromId) {
        this.cityFromId = cityFromId;
    }

    public Long getCityToId() {
        return cityToId;
    }

    public void setCityToId(Long cityToId) {
        this.cityToId = cityToId;
    }

    public Long getBusStationFromId() {
        return busStationFromId;
    }

    public void setBusStationFromId(Long busStationFromId) {
        this.busStationFromId = busStationFromId;
    }

    public Long getBusStationToId() {
        return busStationToId;
    }

    public void setBusStationToId(Long busStationToId) {
        this.busStationToId = busStationToId;
    }

    public Long getDateDeparture() {
        return dateDeparture;
    }

    public void setDateDeparture(Long dateDeparture) {
        this.dateDeparture = dateDeparture;
    }

    public Long getDateArrival() {
        return dateArrival;
    }

    public void setDateArrival(Long dateArrival) {
        this.dateArrival = dateArrival;
    }

    public Long getBusId() {
        return busId;
    }

    public void setBusId(Long busId) {
        this.busId = busId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Integer getAllSeats() {
        return allSeats;
    }

    public void setAllSeats(Integer allSeats) {
        this.allSeats = allSeats;
    }

    public Integer getFreeSeats() {
        return freeSeats;
    }

    public void setFreeSeats(Integer freeSeats) {
        this.freeSeats = freeSeats;
    }

    public Double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(Double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "TripCreateUpdateDTO{" +
                "cityFromId=" + cityFromId +
                ", cityToId=" + cityToId +
                ", busStationFromId=" + busStationFromId +
                ", busStationToId=" + busStationToId +
                ", dateDeparture=" + dateDeparture +
                ", dateArrival=" + dateArrival +
                ", busId=" + busId +
                ", companyId=" + companyId +
                ", allSeats=" + allSeats +
                ", freeSeats=" + freeSeats +
                ", ticketPrice=" + ticketPrice +
                ", id=" + id +
                '}';
    }
}
