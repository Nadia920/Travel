package com.java.Travel.controller;


import com.java.Travel.controller.dto.BusStationDTO;
import com.java.Travel.controller.dto.CityDTO;
import com.java.Travel.service.BusStationService;
import com.java.Travel.service.CityService;
import com.java.Travel.service.CountryService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/busStation")
public class BusStationController {
    private final static Logger LOGGER = LogManager.getLogger();

    private BusStationService busStationService;
    private CountryService countryService;
    private CityService cityService;

    public BusStationController(BusStationService busStationService,
                             CountryService countryService,
                             CityService cityService) {
        this.busStationService = busStationService;
        this.countryService = countryService;
        this.cityService = cityService;
    }

    @GetMapping
    public String getShowBusStationsView(Model model) {
        LOGGER.info("Get showBusStationsView: busStation/showBusStations.html");
        List<BusStationDTO> busStationDTOs = busStationService.findAll();
        model.addAttribute("busStations", busStationDTOs.size() == 0 ? null : busStationDTOs);
        return "busStation/showBusStations";
    }

    @GetMapping("/edit/{id}")
    public String getEditBusStationView(@PathVariable Long id, Model model) {
        LOGGER.info("Get editBusStationView: busStation/editBusStation");
        BusStationDTO busStationDTO = busStationService.findById(id);
        model.addAttribute("busStation", busStationDTO);
        return "busStation/editBusStation";
    }

    @PostMapping("/edit")
    public String editBusStation(@RequestParam Long id,
                              @RequestParam String name,
                              @RequestParam String code) {
        LOGGER.info("Update busStation where id: " + id + ", new name: " + name + ", new code: " + code);
        busStationService.updateBusStation(new BusStationDTO(id, name, code));
        return "redirect:/busStation";
    }

    @GetMapping("/delete/{id}")
    public String deleteBusStation(@PathVariable Long id) {
        LOGGER.info("Delete busStation where id: " + id);
        busStationService.delete(id);
        return "redirect:/busStations";
    }

    @GetMapping("/add")
    public String getAddBusStationView(Model model) {
        LOGGER.info("Get addBusStation: busStation/addBusStation");
        model.addAttribute("countries", countryService.findAll());
        model.addAttribute("cities", new ArrayList<CityDTO>());

        return "busStation/addBusStation";
    }


    @GetMapping(value = "/countries/{id}")
    @ResponseBody
    public List<CityDTO> getCities(@PathVariable Long id) {
        LOGGER.info("Get cities where country id: " + id);
        return cityService.getCityListByCountry(id);
    }

    @PostMapping("/add")
    public String addBusStation(@RequestParam String name,
                             @RequestParam String code,
                             @RequestParam Long idCity) {
        LOGGER.info("Add busStation with name: " + name + ", code: " + code + ", idCity: " + idCity);
        busStationService.save(new BusStationDTO(name, code, idCity));
        return "redirect:/busStation";
    }


}
