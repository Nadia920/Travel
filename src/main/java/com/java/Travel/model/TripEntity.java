package com.java.Travel.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;


@Entity
@Table(name = "Trip")
public class TripEntity extends BaseEntity {

    @NotNull
    @Column(name = "free_seats")
    private Integer freeSeats;

    @NotNull
    @Column(name = "all_seats")
    private Integer allSeats;

    @NotNull
    @Column(name = "price")
    private Double price;

    @NotNull
    @Column(name = "departure_date", columnDefinition = "timestamp")
    private Timestamp departureDate;

    @NotNull
    @Column(name = "arrival_date", columnDefinition = "timestamp")
    private Timestamp arrivalDate;

    @OneToMany(mappedBy = "trip")
    private Set<OrderEntity> orders;

    @ManyToOne(optional = false, cascade = ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "bus_id")
    @NotNull
    private BusEntity bus;

    @ManyToOne(optional = false, fetch = FetchType.EAGER, cascade = ALL)
    @JoinColumn(name = "bus_station_departure_id")
    @NotNull
    private BusStationEntity busStationDeparture;

    @ManyToOne(optional = false, fetch = FetchType.EAGER, cascade = ALL)
    @JoinColumn(name = "bus_station_arrival_id")
    @NotNull
    private BusStationEntity busStationArrival;

    @Column(name = "status", length = 8)
    @Enumerated(EnumType.STRING)
    private TripStatus status;

    public TripEntity() {
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

    public Set<OrderEntity> getOrders() {
        return orders;
    }

    public void setOrders(Set<OrderEntity> orders) {
        this.orders = orders;
    }

    public BusEntity getBus() {
        return bus;
    }

    public void setBus(BusEntity bus) {
        this.bus = bus;
    }

    public BusStationEntity getBusStationDeparture() {
        return busStationDeparture;
    }

    public void setBusStationDeparture(BusStationEntity busStationDeparture) {
        this.busStationDeparture = busStationDeparture;
    }

    public BusStationEntity getBusStationArrival() {
        return busStationArrival;
    }

    public void setBusStationArrival(BusStationEntity busStationArrival) {
        this.busStationArrival = busStationArrival;
    }

    public TripStatus getStatus() {
        return status;
    }

    public void setStatus(TripStatus status) {
        this.status = status;
    }


}
