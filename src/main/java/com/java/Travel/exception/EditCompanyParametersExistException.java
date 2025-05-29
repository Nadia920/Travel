package com.java.Travel.exception;

import com.java.Travel.controller.dto.CompanyDTO;

public class EditCompanyParametersExistException extends RuntimeException {

    CompanyDTO companyDTO;

    public EditCompanyParametersExistException(String message, CompanyDTO companyDTO) {
        super(message);
        this.companyDTO = companyDTO;
    }
}
