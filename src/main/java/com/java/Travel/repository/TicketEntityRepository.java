package com.java.Travel.repository;


import com.java.Travel.model.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;
import java.util.List;

public interface TicketEntityRepository extends JpaRepository<TicketEntity, Long> {

    List<TicketEntity> findAllByOrderId(Long id);

    @Modifying
    @Query("delete from TicketEntity u where u.id = ?1")
    void deleteById(Long id);

}
