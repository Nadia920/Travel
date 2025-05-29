package com.java.Travel.controller;

import com.java.Travel.controller.dto.ApiError;
import com.java.Travel.controller.dto.BusDTO;
import com.java.Travel.service.BusService;
import com.java.Travel.service.CompanyService;
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
@RequestMapping("/buses")
public class BusController {

    private BusService busService;
    private CompanyService companyService;

    public BusController(BusService busService,
                         CompanyService companyService) {
        this.busService = busService;
        this.companyService = companyService;
    }

    @GetMapping(path = {"/edit", "/edit/{id}"})
    public String getAddOrEditBusView(Model model,
                                        @RequestParam(name = "company", required = false) String company,
                                        @PathVariable(value = "id") Optional<Long> id) {

        if (id.isPresent()) {
            BusDTO busDTO = busService.findOne(id.get());
            if (busDTO != null) {
                model.addAttribute("companyName", busDTO.getCompanyDTO().getName());
                model.addAttribute("companyId", busDTO.getCompanyDTO().getId());
                model.addAttribute("bus", busDTO);
            } else {
                throw new EntityNotFoundException("Bus with id=" + id + " not found");
            }

            return "bus/editBus";
        } else {
            model.addAttribute("companyName", company);
            model.addAttribute("bus", new BusDTO());
            return "bus/editBus";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteBus(@PathVariable(value = "id") Long id) {
        Long idCompany = busService.getCompanyIdByBusId(id);
        busService.deleteById(id);
        return idCompany == null ? "redirect:/companies" : "redirect:/companies/" + idCompany + "/buses";
    }


    @PostMapping(path = "/edit")
    public String addOrEditBus(@Valid @ModelAttribute("bus") BusDTO busDTO,
                                 @RequestParam("companyName") String companyName,
                                 BindingResult result, Model model) {
        Long companyId = companyService.getCompanyIdByName(companyName);
        if (result.hasErrors()) {
            ApiError apiError = new ApiError();
            String message = "";
            for (FieldError str : result.getFieldErrors()) {
                message += str.getDefaultMessage();
                apiError.setMessage(message);
            }
            model.addAttribute("bus", busDTO);
            model.addAttribute("companyId", companyId);
            model.addAttribute("companyName", companyName);
            model.addAttribute("apiError", apiError);
            return "bus/editBus";
        }

        busService.saveOrUpdate(busDTO, companyId, companyName);


        return "redirect:/companies/" + companyId + "/buses";
    }


}
