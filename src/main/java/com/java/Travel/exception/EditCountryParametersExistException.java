package com.java.Travel.exception;

import com.java.Travel.controller.dto.CountryDTO;


public class EditCountryParametersExistException extends RuntimeException {

    CountryDTO countryDTO;

    public EditCountryParametersExistException(String message, CountryDTO countryDTO) {
        super(message);
        this.countryDTO = countryDTO;
    }

}
