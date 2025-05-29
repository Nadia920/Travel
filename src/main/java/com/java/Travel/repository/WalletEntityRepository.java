package com.java.Travel.repository;


import com.java.Travel.model.WalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface WalletEntityRepository extends JpaRepository<WalletEntity, Long> {

    WalletEntity findByOwnerId(Long id);
}
