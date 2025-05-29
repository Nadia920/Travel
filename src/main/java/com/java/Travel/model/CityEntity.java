package com.java.Travel.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "City")
public class CityEntity extends BaseEntity {

    @Column(name = "name")
    private String name;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "cityEntity")
    List<BusStationEntity> busStations;

    @ManyToOne()
    @JoinColumn(name = "country_id")
    private CountryEntity countryEntity;

    public CityEntity() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<BusStationEntity> getBusStations() {
        return busStations;
    }

    public void setBusStations(List<BusStationEntity> busStations) {
        this.busStations = busStations;
    }

    public CountryEntity getCountryEntity() {
        return countryEntity;
    }

    public void setCountryEntity(CountryEntity countryEntity) {
        this.countryEntity = countryEntity;
    }
}
