package com.java.Travel.repository;


import com.java.Travel.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {

    @Query(value = "SELECT u.id FROM UserEntity u WHERE u.login = ?1")
    Long getIdUserByLogin(String login);

    @Query(value = "SELECT u.id FROM UserEntity u WHERE u.email = ?1")
    Long getIdUserByEmail(String email);

    @Query(value = "SELECT u.id FROM UserEntity u WHERE u.phoneNumber = ?1")
    Long getIdUserByPhoneNumber(String phoneNumber);

    List<UserEntity> findAllByRoleEntity_Role(String role);

    void deleteById(Long id);

    Optional<UserEntity> findById(Long id);

    UserEntity findByLogin(String login);


}
