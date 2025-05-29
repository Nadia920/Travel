package com.java.Travel.service;


import com.java.Travel.controller.dto.CityDTO;
import com.java.Travel.controller.dto.CountryDTO;
import com.java.Travel.model.CountryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Set;

public interface CountryService {

    Page<CountryEntity> findAll(Pageable pageable);

    void delete(Long id);

    CountryDTO findOne(Long id);

    Long getCountryIdByName(String name);

    void saveOrUpdate(CountryDTO countryDTO);

    CountryDTO findCountryByName(String name);

    List<CountryDTO> findAll();

    List<CountryDTO> findAll(Sort name);

    Set<CityDTO> checkCityDTOSet(Set<CityDTO> cityDTOSet);
}
