package com.java.Travel.service;

import com.java.Travel.controller.dto.BusDTO;
import com.java.Travel.model.BusEntity;

import java.util.List;

public interface BusService {

    BusEntity add(BusEntity plain);

    List<BusEntity> getAll();

    BusEntity findById();

    BusDTO findOne(Long id);

    void deleteById(Long id);

    void saveOrUpdate(BusDTO busTO, Long companyId, String companyName);

    Long getCompanyIdByBusId(Long id);

    Long getBusIdBySideNumber(String sideNumber);
}
