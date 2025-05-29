package com.java.Travel.controller;


import com.java.Travel.controller.dto.TourDTO;
import com.java.Travel.service.InfoService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/info")
public class InfoController {

    private final InfoService infoService;

    public InfoController(InfoService infoService) {
        this.infoService = infoService;
    }

    @GetMapping("/tour")
    public ModelAndView getTourView() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("tour/tour");
        return modelAndView;
    }

    @GetMapping("/tour/data")
    public List<TourDTO> getTourData() {
        return infoService.getDataForTour();
    }
}
