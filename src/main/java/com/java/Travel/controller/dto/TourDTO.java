package com.java.Travel.controller.dto;

public class TourDTO {

    private String tripName;
    private Long countTrip;

    public TourDTO(String tripName, Long countTrip) {
        this.tripName = tripName;
        this.countTrip = countTrip;
    }

    public TourDTO() {
    }

    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public Long getCountTrip() {
        return countTrip;
    }

    public void setCountTrip(Long countTrip) {
        this.countTrip = countTrip;
    }
}
