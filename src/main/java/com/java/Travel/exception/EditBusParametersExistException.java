package com.java.Travel.exception;

import com.java.Travel.controller.dto.BusDTO;


public class EditBusParametersExistException extends RuntimeException {

    BusDTO busDTO;
    String companyName;
    Long companyId;

    public EditBusParametersExistException(String message, BusDTO busDTO, String companyName, Long companyId) {
        super(message);
        this.busDTO = busDTO;
        this.companyName = companyName;
        this.companyId = companyId;
    }
}
