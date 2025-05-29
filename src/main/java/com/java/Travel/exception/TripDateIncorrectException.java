package com.java.Travel.exception;

import com.java.Travel.controller.dto.TripCreateUpdateDTO;

public class TripDateIncorrectException extends RuntimeException {

    TripCreateUpdateDTO tripDTO;

    public TripDateIncorrectException(String msg, TripCreateUpdateDTO tripDTO) {
        super(msg);
        this.tripDTO = tripDTO;
    }


}
