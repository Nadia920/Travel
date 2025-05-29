package com.java.Travel.repository;


import com.java.Travel.model.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.List;

public interface RoleEntityRepository extends JpaRepository<RoleEntity, Long> {

    RoleEntity findByRole(String role);

    List<RoleEntity> findAll();


}
