package com.java.Travel.exception;


import com.java.Travel.controller.dto.UserDTO;

public class RegistrationParameterIsExistException extends RuntimeException {

    UserDTO userDTO;


    public RegistrationParameterIsExistException(String message, UserDTO userDTO) {
        super(message);
        this.userDTO = userDTO;
    }

}
