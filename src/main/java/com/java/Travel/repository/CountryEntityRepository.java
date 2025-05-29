package com.java.Travel.repository;


import com.java.Travel.model.CountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;
import java.util.Optional;

public interface CountryEntityRepository extends JpaRepository<CountryEntity, Long> {

    void deleteById(Long id);

    Optional<CountryEntity> findById(Long id);

    @Query(value = "SELECT c.id FROM CountryEntity c WHERE c.name = ?1")
    Long getIdCountryByName(String name);

    Optional<CountryEntity> findByName(String name);
}
