package com.java.Travel.repository;


import com.java.Travel.model.CityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;
import java.util.List;

public interface CityEntityRepository extends JpaRepository<CityEntity, Long> {

    void deleteById(Long id);

    @Query(value = "SELECT c.id FROM CityEntity c WHERE c.name = ?1")
    Long getIdExistCityByName(String name);

    List<CityEntity> findAllByCountryEntity_Id(Long id);

    @Query(value = "select ct.countryEntity.id from CityEntity ct where ct.id=?1")
    Long getIdCountryByCityId(Long id);

}
