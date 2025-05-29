package com.java.Travel.exception;


import com.java.Travel.controller.dto.BusStationDTO;

public class EditBusStationParametersExistException extends RuntimeException {

    BusStationDTO busStationDTO;

    public EditBusStationParametersExistException() {
    }

    public EditBusStationParametersExistException(String message, BusStationDTO busStationDTO) {
        super(message);
        this.busStationDTO = busStationDTO;
    }

    public EditBusStationParametersExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public EditBusStationParametersExistException(Throwable cause) {
        super(cause);
    }

}
