package com.java.Travel.service;

import com.java.Travel.controller.dto.BusStationDTO;

import java.util.List;

public interface BusStationService {
    List<BusStationDTO> findAll();

    BusStationDTO findById(Long id);

    void updateBusStation(BusStationDTO busStationDTO);

    void delete(Long id);

    void save(BusStationDTO busStationDTO);
}
