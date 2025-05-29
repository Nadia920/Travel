package com.java.Travel.service.ServiceImpl;


import com.java.Travel.controller.dto.BusStationDTO;
import com.java.Travel.controller.dto.CityDTO;
import com.java.Travel.controller.dto.CountryDTO;
import com.java.Travel.exception.UserNotFoundException;
import com.java.Travel.model.BusStationEntity;
import com.java.Travel.model.CityEntity;
import com.java.Travel.model.CountryEntity;
import com.java.Travel.repository.CountryEntityRepository;
import com.java.Travel.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Set;

import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CountryServiceImpl implements CountryService {
    @Autowired
    private CountryEntityRepository countryRepository;


    @Override
    public Page<CountryEntity> findAll(Pageable pageable) {
        return countryRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        countryRepository.deleteById(id);
    }


    @Override
    public CountryDTO findOne(Long id) {
        Optional<CountryEntity> countryEntity = countryRepository.findById(id);
        return mapToCountryDTO(countryEntity);
    }

    @Nullable
    private CountryDTO mapToCountryDTO(Optional<CountryEntity> countryEntity) {
        if (countryEntity.isPresent()) {
            CountryDTO countryDTO = new CountryDTO();
            countryDTO.setName(countryEntity.get().getName());
            countryDTO.setId(countryEntity.get().getId());

            Set<CityDTO> cityDTOSet = countryEntity.get().getCities().stream().map(a -> new CityDTO(a.getId(), a.getName())).collect(Collectors.toSet());
            countryDTO.setCityDTOSet(cityDTOSet);

            return countryDTO;
        } else {
            return null;
        }
    }


    @Override
    public Long getCountryIdByName(String name) {
        return countryRepository.getIdCountryByName(name);
    }

    @Transactional
    @Override
    public void saveOrUpdate(CountryDTO countryDTO) {
        CountryEntity countryEntity = new CountryEntity();
        countryEntity.setId(countryDTO.getId());
        countryEntity.setName(countryDTO.getName());

        if (countryEntity.getId() == null) {
            countryRepository.save(countryEntity);
        } else {
            CountryEntity editCountryEntity;
            if (countryRepository.findById(countryEntity.getId()).isPresent()) {
                editCountryEntity = countryRepository.findById(countryEntity.getId()).get();
                editCountryEntity.setName(countryEntity.getName());
                countryRepository.save(editCountryEntity);
            } else {
                throw new UserNotFoundException("Country with id=" + countryEntity.getId() + " not found");
            }

        }
    }


    @Override
    public CountryDTO findCountryByName(String name) {
        Optional<CountryEntity> countryEntity = countryRepository.findByName(name);
        return mapToCountryDTO(countryEntity);
    }

    @Override
    public List<CountryDTO> findAll() {
        List<CountryEntity> countryEntities = countryRepository.findAll(Sort.by("name").ascending());
        return countryEntities.stream().map(a -> new CountryDTO(a.getId(), a.getName())).collect(Collectors.toList());
    }

    @Override
    public List<CountryDTO> findAll(Sort name) {
        List<CountryEntity> countryEntityList = countryRepository.findAll(name);

        List<CountryDTO> countryDTOList = new ArrayList<>();
        CountryDTO countryDTO;
        CityDTO cityDTO;
        for (CountryEntity countryEntity : countryEntityList) {
            countryDTO = new CountryDTO(countryEntity.getId(), countryEntity.getName());

            Set<CityDTO> cityDTOSet = new HashSet<>();
            for (CityEntity cityEntity : countryEntity.getCities()) {
                cityDTO = new CityDTO(cityEntity.getId(), cityEntity.getName());

                List<BusStationDTO> busStationDTOList = new ArrayList<>();
                for (BusStationEntity busStationEntity : cityEntity.getBusStations()) {
                    BusStationDTO busStationDTO = new BusStationDTO(busStationEntity.getId(), busStationEntity.getName(), busStationEntity.getCode());
                    busStationDTOList.add(busStationDTO);
                }

                cityDTO.setBusStationDTOList(busStationDTOList);
                cityDTOSet.add(cityDTO);
            }
            countryDTO.setCityDTOSet(cityDTOSet);
            countryDTOList.add(countryDTO);
        }
        return countryDTOList;
    }

    @Override
    public Set<CityDTO> checkCityDTOSet(Set<CityDTO> cityDTOSet) {
        return cityDTOSet.size() == 0 || cityDTOSet == null ? null : cityDTOSet;
    }


}
