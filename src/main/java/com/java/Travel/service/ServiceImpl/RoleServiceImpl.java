package com.java.Travel.service.ServiceImpl;


import com.java.Travel.controller.dto.RoleDTO;
import com.java.Travel.controller.dto.UserDTO;
import com.java.Travel.model.RoleEntity;

import java.util.List;

import com.java.Travel.model.UserEntity;

import com.java.Travel.repository.RoleEntityRepository;
import com.java.Travel.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleEntityRepository roleRepository;



    @Override
    public List<RoleDTO> findAllRoles() {
        List<RoleEntity> rolesEntity = roleRepository.findAll();
        return rolesEntity.stream().map(a -> new RoleDTO(a.getId(), a.getRole())).collect(Collectors.toList());
    }

    @Override
    public RoleEntity findByRole(String role) {
        return roleRepository.findByRole(role);
    }

    @Override
    public List<UserDTO> getUsersByRole(String role) {
        RoleEntity roleEntity = roleRepository.findByRole(role);
        List<UserEntity> users = roleEntity.getUsers();
        List<UserDTO> userDTOList = users.stream()
                .map(a -> new UserDTO(
                        a.getId(),
                        a.getLogin(),
                        a.getPassword(),
                        a.getEmail(),
                        a.getFirstName(),
                        a.getLastName(),
                        a.getPatronymic(),
                        a.getPhoneNumber(),
                        a.getRoleEntity().getRole()
                ))
                .collect(Collectors.toList());

        return userDTOList;
    }
}
