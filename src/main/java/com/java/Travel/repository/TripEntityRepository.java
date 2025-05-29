package com.java.Travel.repository;


import com.java.Travel.controller.dto.CityFrAndTo;
import com.java.Travel.model.TripEntity;
import com.java.Travel.model.TripStatus;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Set;

public interface TripEntityRepository extends JpaRepository<TripEntity, Long>, JpaSpecificationExecutor<TripEntity> {

    List<TripEntity> findAllByStatus(TripStatus status, Sort departureDate);

    @Query("SELECT NEW com.java.Travel.controller.dto.CityFrAndTo(tr.busStationArrival.cityEntity.id,tr.busStationDeparture.cityEntity.id) " +
            "FROM TripEntity tr WHERE (tr.status = ?1 OR tr.status=?2)")
    Set<CityFrAndTo> findCityFrAndToAndSort(TripStatus status1, TripStatus status2);

    @Modifying
    @Query("FROM TripEntity tr " +
            "WHERE (tr.status = com.java.Travel.model.TripStatus.ACTIVE AND tr.arrivalDate<:now)")
    List<TripEntity> findAllByStatusActiveAndArrivalDateBefore(Date now);

}
