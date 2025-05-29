package com.java.Travel.exception;


import com.java.Travel.controller.dto.BusStationDTO;

public class BusStationAddParameterExistException extends RuntimeException {

    public BusStationDTO busStationDTO;

    public BusStationAddParameterExistException(String message, BusStationDTO busStationDTO) {
        super(message);
        this.busStationDTO = busStationDTO;
    }
}
