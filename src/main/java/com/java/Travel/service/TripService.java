package com.java.Travel.service;


import com.java.Travel.controller.dto.TripCreateUpdateDTO;
import com.java.Travel.controller.dto.TripCriteriaDTO;
import com.java.Travel.controller.dto.TripDTO;
import com.java.Travel.model.TripEntity;
import com.java.Travel.model.TripStatus;

import java.text.ParseException;
import java.util.List;

public interface TripService {
    void addTrip(TripCreateUpdateDTO tripDTO);

    TripDTO getById(Long id);

    void edit(TripCreateUpdateDTO tripDTO);

    List<TripDTO> findAll();

    List<TripDTO> findTripsByCriteria(TripCriteriaDTO tripCriteriaDTO) throws ParseException;

    Integer getNumberSoldTicketById(Long id);

    void canceledTrip(Long idTrip);

    TripDTO mapTripDTO(TripEntity tripEntity);

    List<TripDTO> mapListTripDTO(List<TripEntity> tripEntityList);

    List<TripDTO> findAllByStatus(TripStatus status);
}
