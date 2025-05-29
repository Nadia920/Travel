package com.java.Travel.controller;

import com.java.Travel.controller.dto.ApiError;
import com.java.Travel.controller.dto.CityDTO;
import com.java.Travel.exception.EditCityParametersExistException;
import com.java.Travel.service.CityService;
import com.java.Travel.service.CountryService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping(value = "/cities")
public class CityController {
    private final static Logger LOGGER = LogManager.getLogger();

    private CityService cityService;
    private CountryService countryService;

    public CityController(CityService cityService,
                          CountryService countryService) {
        this.cityService = cityService;
        this.countryService = countryService;
    }

    @GetMapping(path = {"/edit", "/edit/{id}"})
    public String getAddOrEditCityView(
            Model model,
            @RequestParam(name = "country", required = false) String country,
            @PathVariable(value = "id") Optional<Long> id) throws EntityNotFoundException {
        LOGGER.info("Get add or edit city view");
        if (id.isPresent()) {
            CityDTO cityDTO = cityService.findOne(id.get());
            if (cityDTO != null) {
                model.addAttribute("countryName", cityDTO.getCountryDTO().getName());
                model.addAttribute("city", cityDTO);
            } else {
                LOGGER.error("City with id=" + id + " not found");
                throw new EntityNotFoundException("City with id=" + id + " not found");
            }

            return "city/editCity";
        } else {
            model.addAttribute("countryName", country);
            model.addAttribute("city", new CityDTO());
            return "city/editCity";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteCity(@PathVariable(value = "id") Long id) {
        LOGGER.info("Delete city with id: " + id);
        Long idCountry = cityService.getCountryIdByCityId(id);
        cityService.delete(id);
        return "redirect:/country/" + idCountry + "/cities";
    }


    @PostMapping(path = "/edit")
    public String addOrEditCity(@Valid @ModelAttribute("city") CityDTO cityDTO,
                                @RequestParam(name = "countryName") String countryName,
                                BindingResult result,
                                Model model) {
        LOGGER.warn("Get add or edit city view");
        if (result.hasErrors()) {
            LOGGER.warn("Validation errors");
            ApiError apiError = new ApiError();
            String message = "";
            for (FieldError error : result.getFieldErrors()) {
                message += error.getDefaultMessage();
                apiError.setMessage(message);
            }
            model.addAttribute("city", cityDTO);
            model.addAttribute("countryName", cityDTO);
            model.addAttribute("apiError", apiError);
            return "city/editCity";
        }

        if (cityDTO.getId() != null) {

            if (cityService.getCityIdByName(cityDTO.getName()) != null
                    && cityService.getCityIdByName(cityDTO.getName()) != cityDTO.getId()) {
                LOGGER.error("City with name:" + cityDTO.getName() + " already exist");
                throw new EditCityParametersExistException("City_with_this_name_already_exist", cityDTO, countryName);
            }
            cityService.saveOrUpdate(cityDTO, null);
        } else {
            if (cityService.getCityIdByName(cityDTO.getName()) != null) {
                LOGGER.error("City with name:" + cityDTO.getName() + " already exist");
                throw new EditCityParametersExistException("City_with_this_name_already_exist", cityDTO, countryName);
            }
            cityService.saveOrUpdate(cityDTO, countryName);
        }
        Long idCountry = countryService.getCountryIdByName(countryName);
        return "redirect:/country/" + idCountry + "/cities";
    }


}
