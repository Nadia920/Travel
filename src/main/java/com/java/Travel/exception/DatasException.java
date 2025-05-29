package com.java.Travel.exception;

import com.java.Travel.controller.dto.CityDTO;
import com.java.Travel.controller.dto.TripCriteriaDTO;


public class DatasException extends RuntimeException {

    CityDTO cityDTODepart;
    CityDTO cityDTOArriv;
    String departureDate;
    TripCriteriaDTO TripCriteriaDTO;

    public DatasException(String msg, CityDTO cityDTODepart, CityDTO cityDTOArriv, String departureDate, TripCriteriaDTO TripCriteriaDTO) {
        super(msg);
        this.cityDTODepart = cityDTODepart;
        this.cityDTOArriv = cityDTOArriv;
        this.departureDate = departureDate;
        this.TripCriteriaDTO = TripCriteriaDTO;
    }
}
