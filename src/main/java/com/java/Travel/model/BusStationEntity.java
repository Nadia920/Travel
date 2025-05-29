package com.java.Travel.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "Bus_station")
public class BusStationEntity extends BaseEntity {

    @NotNull
    @Column(name = "name", length = 30)
    private String name;

    @NotNull
    @Column(name = "code", length = 5)
    private String code;

    @ManyToOne(optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "city_id")
    private CityEntity cityEntity;

    @OneToMany(mappedBy = "busStationDeparture", fetch = FetchType.EAGER)
    private List<TripEntity> tripDepartures;

    @OneToMany(mappedBy = "busStationArrival", fetch = FetchType.EAGER)
    private List<TripEntity> tripArrivals;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public CityEntity getCityEntity() {
        return cityEntity;
    }

    public void setCityEntity(CityEntity cityEntity) {
        this.cityEntity = cityEntity;
    }

    public List<TripEntity> getTripDepartures() {
        return tripDepartures;
    }

    public void setTripDepartures(List<TripEntity> tripDepartures) {
        this.tripDepartures = tripDepartures;
    }

    public List<TripEntity> getTripArrivals() {
        return tripArrivals;
    }

    public void setTripArrivals(List<TripEntity> tripArrivals) {
        this.tripArrivals = tripArrivals;
    }

    @Override
    public String toString() {
        return "BusStationEntity{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", cityEntity=" + cityEntity +
                ", tripDepartures=" + tripDepartures +
                ", tripArrivals=" + tripArrivals +
                '}';
    }
}
