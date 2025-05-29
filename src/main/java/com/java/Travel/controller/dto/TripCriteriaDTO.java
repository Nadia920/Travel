package com.java.Travel.controller.dto;


import com.java.Travel.model.Rating;
import com.java.Travel.model.TripStatus;

public class TripCriteriaDTO {

    private Long idCityDeparture;
    private Long idCityArrival;
    private String departureDate;
    private Integer countSeats;
    private Long idCompany;
    private Rating ratingCompany;
    private Double minPrice;
    private Double maxPrice;
    private TripStatus status;

    public TripCriteriaDTO() {
    }

    public TripCriteriaDTO(Long idCityDeparture, Long idCityArrival, String departureDate, Integer countSeats, Long idCompany, Rating ratingCompany, Double minPrice, Double maxPrice, TripStatus status) {
        this.idCityDeparture = idCityDeparture;
        this.idCityArrival = idCityArrival;
        this.departureDate = departureDate;
        this.countSeats = countSeats;
        this.idCompany = idCompany;
        this.ratingCompany = ratingCompany;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.status = status;
    }

    public Long getIdCityDeparture() {
        return idCityDeparture;
    }

    public void setIdCityDeparture(Long idCityDeparture) {
        this.idCityDeparture = idCityDeparture;
    }

    public Long getIdCityArrival() {
        return idCityArrival;
    }

    public void setIdCityArrival(Long idCityArrival) {
        this.idCityArrival = idCityArrival;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public Integer getCountSeats() {
        return countSeats;
    }

    public void setCountSeats(Integer countSeats) {
        this.countSeats = countSeats;
    }

    public Long getIdCompany() {
        return idCompany;
    }

    public void setIdCompany(Long idCompany) {
        this.idCompany = idCompany;
    }

    public Rating getRatingCompany() {
        return ratingCompany;
    }

    public void setRatingCompany(Rating ratingCompany) {
        this.ratingCompany = ratingCompany;
    }

    public Double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Double minPrice) {
        this.minPrice = minPrice;
    }

    public Double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public TripStatus getStatus() {
        return status;
    }

    public void setStatus(TripStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "TripCriteriaDTO{" +
                "idCityDeparture=" + idCityDeparture +
                ", idCityArrival=" + idCityArrival +
                ", departureDate='" + departureDate + '\'' +
                ", countSeats=" + countSeats +
                ", idCompany=" + idCompany +
                ", ratingCompany=" + ratingCompany +
                ", minPrice=" + minPrice +
                ", maxPrice=" + maxPrice +
                ", status=" + status +
                '}';
    }
}
