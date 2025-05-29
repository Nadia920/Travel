package com.java.Travel.repository;

import com.java.Travel.model.BusStationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;


public interface BusStationEntityRepository extends JpaRepository<BusStationEntity, Long> {

    @Query(value = "SELECT bs.id FROM BusStationEntity bs WHERE bs.name = ?1")
    Long findIdByName(String name);

    @Query(value = "SELECT bs.id FROM BusStationEntity bs WHERE bs.code = ?1")
    Long findIdByCode(String code);

}
