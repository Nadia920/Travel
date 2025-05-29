package com.java.Travel.controller.dto;

import java.util.Objects;

public class CityFrAndTo {
    private Long cityFr;
    private Long cityTo;

    public CityFrAndTo(Long cityFr, Long cityTo) {
        this.cityFr = cityFr;
        this.cityTo = cityTo;
    }

    public CityFrAndTo() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CityFrAndTo cityFrAndTo = (CityFrAndTo) o;
        return Objects.equals(cityFr, cityFrAndTo.cityFr) &&
                Objects.equals(cityTo, cityFrAndTo.cityTo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cityFr, cityTo);
    }

    public Long getCityFr() {
        return cityFr;
    }

    public void setCityFr(Long cityFr) {
        this.cityFr = cityFr;
    }

    public Long getCityTo() {
        return cityTo;
    }

    public void setCityTo(Long cityTo) {
        this.cityTo = cityTo;
    }
}
