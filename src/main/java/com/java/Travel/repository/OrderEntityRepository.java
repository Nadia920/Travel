package com.java.Travel.repository;


import com.java.Travel.model.OrderEntity;
import com.java.Travel.model.OrderStatus;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;
import java.util.List;


public interface OrderEntityRepository extends JpaRepository<OrderEntity, Long> {

    @Query(value = "select o.id from OrderEntity o where o.trip.id=?1 and o.user.id=?2")
    Long getOrderIdByTripIdAndUserId(Long idTrip, Long idUser);

    List<OrderEntity> findAllByUserId(Long id);

    List<OrderEntity> findAllByTripId(Long id);

    List<OrderEntity> findAllByUserIdAndStatus(Long id, OrderStatus status, Sort sort);

    @Modifying
    @Query("UPDATE OrderEntity o " +
            "SET o.status = com.java.Travel.model.OrderStatus.FINISHED " +
            "WHERE o.trip.id=:id")
    void setStatusFinishedWhereTripId(Long id);
}
