package com.java.Travel.service.ServiceImpl;

import com.java.Travel.controller.dto.UserDTO;
import com.java.Travel.exception.EditUsersParametersExistException;
import com.java.Travel.exception.UserNotFoundException;
import com.java.Travel.model.UserEntity;

import java.util.List;
import java.util.Optional;


import com.java.Travel.model.WalletEntity;
import com.java.Travel.repository.RoleEntityRepository;
import com.java.Travel.repository.UserEntityRepository;
import com.java.Travel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {

    private final Double DEFAULT_SUM = 0D;

    @Autowired
    private UserEntityRepository userRepository;
    @Autowired
    private RoleEntityRepository roleRepository;

    @Transactional
    @Override
    public boolean save(UserDTO userDTO, String role) {

        Long idExistUser = userRepository.getIdUserByEmail(userDTO.getEmail());
        if (idExistUser != null) {
            throw new EditUsersParametersExistException("User_with_this_email_already_exist", userDTO);
        }

        idExistUser = userRepository.getIdUserByLogin(userDTO.getLogin());
        if (idExistUser != null) {
            throw new EditUsersParametersExistException("This_login_is_exist", userDTO);
        }

        idExistUser = userRepository.getIdUserByPhoneNumber(userDTO.getPhoneNumber());
        if (idExistUser != null) {
            throw new EditUsersParametersExistException("This_phone_number_already_exist", userDTO);
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setLogin(userDTO.getLogin());
        userEntity.setEmail(userDTO.getEmail());
        userEntity.setPassword(userDTO.getPassword());
        userEntity.setFirstName(userDTO.getFirstName());
        userEntity.setLastName(userDTO.getLastName());
        userEntity.setPatronymic(userDTO.getPatronymic());
        userEntity.setPhoneNumber(userDTO.getPhoneNumber());
        userEntity.setRoleEntity(roleRepository.findByRole(role));

        if (role.equals("ROLE_CLIENT")) {
            userEntity.setWallet(new WalletEntity(DEFAULT_SUM));
        }
        userRepository.save(userEntity);

        return true;
    }

    @Override
    public List<UserDTO> getUsersByRole(String role) {

        List<UserEntity> userEntityList = userRepository.findAllByRoleEntity_Role(role);
        List<UserDTO> userDTOList = userEntityList.stream()
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

    @Transactional
    @Override
    public void deleteUserById(Long id) throws UserNotFoundException {
        Optional<UserEntity> userEntity = userRepository.findById(id);

        if (userEntity.isPresent()) {
            userRepository.deleteById(id);
        } else {
            throw new UserNotFoundException("User with id=" + id + " not found");
        }
    }

    @Override
    public UserDTO findUserById(Long id) {
        Optional<UserEntity> userEntity = userRepository.findById(id);
        return userEntity.isPresent() ? mapUserDTO(userEntity.get()) : null;
    }

    private UserDTO mapUserDTO(UserEntity userEntity) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userEntity.getId());
        userDTO.setEmail(userEntity.getEmail());
        userDTO.setLogin(userEntity.getLogin());
        userDTO.setPassword(userEntity.getPassword());
        userDTO.setFirstName(userEntity.getFirstName());
        userDTO.setLastName(userEntity.getLastName());
        userDTO.setPatronymic(userEntity.getPatronymic());
        userDTO.setPhoneNumber(userEntity.getPhoneNumber());
        userDTO.setRole(userEntity.getRoleEntity().getRole());
        return userDTO;
    }

    @Transactional
    @Override
    public void update(UserDTO user) {

        Long idExistUser = userRepository.getIdUserByEmail(user.getEmail());

        if (idExistUser != null && !idExistUser.equals(user.getId())) {
            throw new EditUsersParametersExistException("User_with_this_email_already_exist", user);
        }

        idExistUser = userRepository.getIdUserByLogin(user.getLogin());
        if (idExistUser != null && !idExistUser.equals(user.getId())) {
            throw new EditUsersParametersExistException("This_login_is_exist", user);
        }

        idExistUser = userRepository.getIdUserByPhoneNumber(user.getPhoneNumber());
        if (idExistUser != null && !idExistUser.equals(user.getId())) {
            throw new EditUsersParametersExistException("This_phone_number_already_exist", user);
        }

        Optional<UserEntity> editUserEntity = userRepository.findById(user.getId());
        if (editUserEntity.isPresent()) {

            editUserEntity.get().setLogin(user.getLogin());
            editUserEntity.get().setPassword(user.getPassword());
            editUserEntity.get().setEmail(user.getEmail());
            editUserEntity.get().setFirstName(user.getFirstName());
            editUserEntity.get().setLastName(user.getLastName());
            editUserEntity.get().setPatronymic(user.getPatronymic());
            editUserEntity.get().setPhoneNumber(user.getPhoneNumber());

            userRepository.save(editUserEntity.get());
        } else {
            throw new UserNotFoundException("User with id=" + user.getId() + " not found");
        }

    }

    @Override
    public Optional<UserEntity> findByLogin(String login) {
        return Optional.ofNullable(userRepository.findByLogin(login));
    }

}
