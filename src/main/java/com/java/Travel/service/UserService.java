package com.java.Travel.service;

import com.java.Travel.controller.dto.UserDTO;
import com.java.Travel.model.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {

    boolean save(UserDTO userDTO, String role);

    List<UserDTO> getUsersByRole(String role);

    void deleteUserById(Long id);

    UserDTO findUserById(Long id);

    void update(UserDTO user);

    Optional<UserEntity> findByLogin(String login);
}
