package com.java.Travel.exception;

import com.java.Travel.controller.dto.ApiError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class UserControllerHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserControllerHandler.class);

    //Registration parameter
    @ExceptionHandler(RegistrationParameterIsExistException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public ModelAndView handleRegistrationParameterIsExist(RegistrationParameterIsExistException ex, WebRequest request) {
        String error = ex.getMessage();

        ApiError apiError = new ApiError(HttpStatus.CONFLICT, ex.getLocalizedMessage(), error);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("registration");
        modelAndView.addObject("user", ex.userDTO);
        modelAndView.addObject("apiError", apiError);
        return modelAndView;
    }

    //Edit parameter
    @ExceptionHandler(EditUsersParametersExistException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public ModelAndView handleEditParameterIsExist(EditUsersParametersExistException ex, WebRequest request) {
        String error = ex.getMessage();

        ApiError apiError = new ApiError(HttpStatus.CONFLICT, ex.getLocalizedMessage(), error);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("user/addEditUser");
        modelAndView.addObject("user", ex.userDTO);
        modelAndView.addObject("apiError", apiError);
        return modelAndView;
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleUserNotFound(UserNotFoundException ex, WebRequest request) {
        String error = ex.getMessage();
        ApiError apiError =
                new ApiError(HttpStatus.NOT_FOUND, ex.getLocalizedMessage(), error);
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

}
