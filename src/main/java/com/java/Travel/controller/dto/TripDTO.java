package com.java.Travel.controller.dto;

import com.java.Travel.model.TripStatus;


import javax.validation.constraints.NotNull;
import java.sql.Timestamp;


public class TripDTO {

    private  Long id;

    @NotNull
    private Integer freeSeats;

    @NotNull
    private Integer allSeats;

    @NotNull
    private Double price;

    @NotNull
    private Timestamp departureDate;

    private String timeDeparture;

    private String dateDeparture;

    private String timeArrival;

    private String dateArrival;

    @NotNull
    private Timestamp arrivalDate;

    private String travelTime;

    private Integer soldTickets;

    private TripStatus status;

    private BusDTO bus;

    private BusStationDTO busStationDeparture;

    private BusStationDTO busStationArrival;

    public TripDTO(Long id, Integer freeSeats, Integer allSeats, Double price, Timestamp departureDate, Timestamp arrivalDate, Integer soldTickets, TripStatus status , BusDTO busDTO, BusStationDTO busStationDeparture, BusStationDTO busStationArrival) {
        this.id = id;
        this.freeSeats  = freeSeats;
        this.allSeats = allSeats;
        this.price = price;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.status = status;
        this.bus = busDTO;
        this.busStationDeparture = busStationDeparture;
        this.busStationArrival = busStationArrival;
        this.soldTickets =soldTickets;
    }

    public TripDTO() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getFreeSeats() {
        return freeSeats;
    }

    public void setFreeSeats(Integer freeSeats) {
        this.freeSeats = freeSeats;
    }

    public Integer getAllSeats() {
        return allSeats;
    }

    public void setAllSeats(Integer allSeats) {
        this.allSeats = allSeats;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Timestamp getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Timestamp departureDate) {
        this.departureDate = departureDate;
    }

    public Timestamp getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Timestamp arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public BusDTO getBus() {
        return bus;
    }

    public void setBus(BusDTO bus) {
        this.bus = bus;
    }

    public BusStationDTO getBusStationDeparture() {
        return busStationDeparture;
    }

    public void setBusStationDeparture(BusStationDTO busStationDeparture) {
        this.busStationDeparture = busStationDeparture;
    }

    public BusStationDTO getBusStationArrival() {
        return busStationArrival;
    }

    public void setBusStationArrival(BusStationDTO busStationArrival) {
        this.busStationArrival = busStationArrival;
    }

    public String getTravelTime() {
        return travelTime;
    }

    public void setTravelTime(String travelTime) {
        this.travelTime = travelTime;
    }

    public String getTimeDeparture() {
        return timeDeparture;
    }

    public void setTimeDeparture(String timeDeparture) {
        this.timeDeparture = timeDeparture;
    }

    public String getDateDeparture() {
        return dateDeparture;
    }

    public void setDateDeparture(String dateDeparture) {
        this.dateDeparture = dateDeparture;
    }

    public String getTimeArrival() {
        return timeArrival;
    }

    public void setTimeArrival(String timeArrival) {
        this.timeArrival = timeArrival;
    }

    public String getDateArrival() {
        return dateArrival;
    }

    public void setDateArrival(String dateArrival) {
        this.dateArrival = dateArrival;
    }

    public Integer getSoldTickets() {
        return soldTickets;
    }

    public void setSoldTickets(Integer soldTickets) {
        this.soldTickets = soldTickets;
    }

    public TripStatus getStatus() {
        return status;
    }

    public void setStatus(TripStatus status) {
        this.status = status;
    }
}
