package com.java.Travel.exception;

import com.java.Travel.controller.dto.UserDTO;


public class EditUsersParametersExistException extends RuntimeException {

    UserDTO userDTO;

    public EditUsersParametersExistException(String message, UserDTO userDTO) {
        super(message);
        this.userDTO = userDTO;
    }
}
