package com.java.Travel.service;


import com.java.Travel.controller.dto.TourDTO;

import java.util.List;

public interface InfoService {
    List<TourDTO> getDataForTour();
}
