package com.java.Travel.service.ServiceImpl;

import com.java.Travel.controller.dto.BusStationDTO;

import java.util.List;

import com.java.Travel.controller.dto.CityDTO;
import com.java.Travel.controller.dto.CountryDTO;
import com.java.Travel.exception.BusStationAddParameterExistException;
import com.java.Travel.exception.EditBusStationParametersExistException;
import com.java.Travel.model.BusStationEntity;
import com.java.Travel.model.CityEntity;
import com.java.Travel.repository.BusStationEntityRepository;
import com.java.Travel.repository.CityEntityRepository;
import com.java.Travel.service.BusStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;

@Service
public class BusStationServiceImpl implements BusStationService {

    @Autowired
    private BusStationEntityRepository busStationRepository;
    @Autowired
    private CityEntityRepository cityRepository;


    @Override
    public List<BusStationDTO> findAll() {
        List<BusStationEntity> busStationEntities = busStationRepository.findAll();
        busStationEntities.sort(Comparator.comparing(o -> o.getCityEntity().getCountryEntity().getName()));

        List<BusStationDTO> busStationDTOs = new ArrayList<>();

        for (BusStationEntity busStationEntity : busStationEntities) {

            BusStationDTO busStationDTO = new BusStationDTO(busStationEntity.getId(), busStationEntity.getName(), busStationEntity.getCode());
            CityDTO cityDTO = new CityDTO(busStationEntity.getCityEntity().getId(), busStationEntity.getCityEntity().getName());
            CountryDTO countryDTO = new CountryDTO(busStationEntity.getCityEntity().getCountryEntity().getId(), busStationEntity.getCityEntity().getCountryEntity().getName());

            cityDTO.setCountryDTO(countryDTO);
            busStationDTO.setCityDTO(cityDTO);

            busStationDTOs.add(busStationDTO);
        }
        return busStationDTOs;
    }

    @Override
    public BusStationDTO findById(Long id) {
        Optional<BusStationEntity> busStationEntity = busStationRepository.findById(id);
        BusStationDTO busStationDTO = new BusStationDTO();
        if (busStationEntity.isPresent()) {

            busStationDTO.setId(busStationEntity.get().getId());
            busStationDTO.setName(busStationEntity.get().getName());
            busStationDTO.setCode(busStationEntity.get().getCode());

            CityDTO cityDTO = new CityDTO();
            cityDTO.setId(busStationEntity.get().getCityEntity().getId());
            cityDTO.setName(busStationEntity.get().getCityEntity().getName());

            CountryDTO countryDTO = new CountryDTO();
            countryDTO.setId(busStationEntity.get().getCityEntity().getCountryEntity().getId());
            countryDTO.setName(busStationEntity.get().getCityEntity().getCountryEntity().getName());

            cityDTO.setCountryDTO(countryDTO);
            busStationDTO.setCityDTO(cityDTO);

        } else {
            throw new EntityNotFoundException("BusStationEntity with id=" + id + " not found");
        }
        return busStationDTO;
    }

    @Transactional
    @Override
    public void updateBusStation(BusStationDTO busStationDTO) {

        if (busStationDTO.getId() != null) {

            Optional<BusStationEntity> busStationEntity = busStationRepository.findById(busStationDTO.getId());

            if (busStationEntity.isPresent()) {


                if (busStationRepository.findIdByName(busStationDTO.getName()) != busStationEntity.get().getId() && busStationRepository.findIdByName(busStationDTO.getName()) != null) {
                    throw new EditBusStationParametersExistException("BusStation_with_this_name_already_exist", busStationDTO);
                }

                if (busStationRepository.findIdByCode(busStationDTO.getCode()) != busStationEntity.get().getId() && busStationRepository.findIdByCode(busStationDTO.getCode()) != null) {
                    throw new EditBusStationParametersExistException("BusStation_with_this_code_already_exist", busStationDTO);
                }

                busStationEntity.get().setName(busStationDTO.getName());
                busStationEntity.get().setCode(busStationDTO.getCode());

                busStationRepository.save(busStationEntity.get());


            } else {
                throw new EntityNotFoundException("BusStation with id=" + busStationEntity.get().getId() + " not found");
            }

        } else {
            throw new EntityNotFoundException("Id == null");
        }

    }

    @Transactional
    @Override
    public void delete(Long id) {
        busStationRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void save(BusStationDTO busStationDTO) {

        if (busStationRepository.findIdByName(busStationDTO.getName()) != null) {
            throw new BusStationAddParameterExistException("BusStation_with_this_name_already_exist", busStationDTO);
        }

        if (busStationRepository.findIdByCode(busStationDTO.getCode()) != null) {
            throw new BusStationAddParameterExistException("BusStation_with_this_code_already_exist", busStationDTO);
        }

        CityEntity cityEntity = cityRepository.findById(busStationDTO.getCityDTO().getId()).get();
        BusStationEntity busStationEntity = new BusStationEntity();
        busStationEntity.setName(busStationDTO.getName());
        busStationEntity.setCode(busStationDTO.getCode());
        busStationEntity.setCityEntity(cityEntity);
        busStationRepository.save(busStationEntity);
    }
}