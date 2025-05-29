package com.java.Travel.repository;


import com.java.Travel.model.CompanyEntity;
import com.java.Travel.model.Rating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CompanyEntityRepository extends JpaRepository<CompanyEntity, Long> {

    Optional<CompanyEntity> findById(Long id);

    List<CompanyEntity> findAll();

    CompanyEntity findByName(String name);

    Set<CompanyEntity> findByRating(Rating rating);

    CompanyEntity findByNameAndRating(String name, Rating rating);

    void delete(CompanyEntity companyEntity);

    void deleteById(Long id);

    @Query("SELECT comp.id FROM CompanyEntity comp WHERE comp.name = ?1")
    Long getCompanyIdByName(String name);

    @Query("select comp from CompanyEntity comp order by comp.name asc")
    List<CompanyEntity> findAllAndOrderByName();

    @Query("select company from CompanyEntity company where LOWER(company.name) like  %?#{[0].toLowerCase()}% ")
    Page<CompanyEntity> findAllByNameIgnoreCase(String name, Pageable pageable);
}
