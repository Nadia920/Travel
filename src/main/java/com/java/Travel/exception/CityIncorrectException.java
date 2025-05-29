package com.java.Travel.exception;

import com.java.Travel.controller.dto.CityDTO;
import com.java.Travel.controller.dto.TripCriteriaDTO;


public class CityIncorrectException extends RuntimeException {

    CityDTO cityDepart;
    CityDTO cityArr;
    String date;
    TripCriteriaDTO TripCriteriaDTO;

    public CityIncorrectException(String message, CityDTO cityDepart, CityDTO cityArr, String date, TripCriteriaDTO TripCriteriaDTO) {
        super(message);
        this.cityDepart = cityDepart;
        this.cityArr = cityArr;
        this.date = date;
        this.TripCriteriaDTO = TripCriteriaDTO;
    }
}
