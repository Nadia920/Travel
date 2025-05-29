package com.java.Travel.repository;

import com.java.Travel.model.BusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;
import java.util.Optional;
import java.util.Set;

public interface BusEntityRepository extends JpaRepository<BusEntity, Long> {

    Optional<BusEntity> findById(Long id);

    BusEntity findByName(String name);

    Set<BusEntity> findAllByCompanyId(Long companyId);

    void delete(BusEntity busEntity);

    void deleteById(Long id);

    @Query(value = "select b.id  from BusEntity b where b.number=?1")
    Long findIdByNumber(String sideNumber);

    @Query(value = "select b.company.id from BusEntity b where b.id=?1")
    Long getCompanyIdByBusId(Long id);
}
