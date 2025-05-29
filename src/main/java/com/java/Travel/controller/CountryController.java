package com.java.Travel.controller;


import com.java.Travel.controller.dto.ApiError;
import com.java.Travel.controller.dto.CountryDTO;
import com.java.Travel.exception.EditCountryParametersExistException;
import com.java.Travel.model.CountryEntity;
import com.java.Travel.service.CountryService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
@RequestMapping(value = "/country")
public class CountryController {

    private CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping()
    public String showPage(Model model,
                           @RequestParam(name = "page", defaultValue = "0") int page) {

        Page<CountryEntity> countryEntities = countryService.findAll(PageRequest.of(page, 8, Sort.by("name").ascending()));
        model.addAttribute("countries", countryEntities.getTotalElements() == 0 ? null : countryEntities);
        model.addAttribute("currentPage", page);
        return "country/countries";
    }


    @GetMapping(path = {"/edit", "/edit/{id}"})
    public String getAddOrEditCountryView(
            Model model,
            @PathVariable(value = "id") Optional<Long> id
    ) throws EntityNotFoundException {

        if (id.isPresent()) {
            CountryDTO countryDTO = countryService.findOne(id.get());
            if (countryDTO != null) {
                model.addAttribute("country", countryDTO);
            } else {
                throw new EntityNotFoundException("Country with id=" + id + " not found");
            }
            return "country/editCountry";
        } else {
            model.addAttribute("country", new CountryDTO());
            return "country/editCountry";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteCountry(@PathVariable(value = "id") Long id) {
        countryService.delete(id);
        return "redirect:/country";
    }


    @PostMapping(path = "/edit")
    public String addOrEditCountry(@Valid @ModelAttribute("country") CountryDTO country,
                                   BindingResult result,
                                   Model model) {

        if (result.hasErrors()) {
            ApiError apiError = new ApiError();
            String message = "";
            for (FieldError str : result.getFieldErrors()) {
                message += str.getDefaultMessage();
                apiError.setMessage(message);
            }
            model.addAttribute("country", country);
            model.addAttribute("apiError", apiError);
            return "country/editCountry";
        }

        if (country.getId() != null) {

            if (countryService.getCountryIdByName(country.getName()) != null
                    && countryService.getCountryIdByName(country.getName()) != country.getId()) {
                throw new EditCountryParametersExistException("Country_with_this_name_already_exist", country);
            }
            countryService.saveOrUpdate(country);
        } else {
            if (countryService.getCountryIdByName(country.getName()) != null) {
                throw new EditCountryParametersExistException("Country_with_this_name_already_exist", country);
            }
            countryService.saveOrUpdate(country);
        }
        return "redirect:/country";
    }


    @GetMapping("/{id}/cities")
    public String getCitiesByCountryId(@PathVariable(value = "id") Long id,
                                       Model model) {
        CountryDTO countryDTO = countryService.findOne(id);
        model.addAttribute("country", countryDTO);
        model.addAttribute("cities", countryService.checkCityDTOSet(countryDTO.getCityDTOSet()));
        return "city/cities";
    }
}