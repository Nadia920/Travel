package com.java.Travel.controller;

import com.java.Travel.controller.dto.*;
import com.java.Travel.model.Rating;
import com.java.Travel.model.TripStatus;
import com.java.Travel.service.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/trip")
public class TripController {

    private final static Logger LOGGER = LogManager.getLogger();

    private TripService tripService;
    private CountryService countryService;
    private CityService cityService;
    private CompanyService companyService;
    private EmailSender emailSender;
    private OrderService orderService;

    private List<CountryDTO> countryDTOList;
    private List<CompanyDTO> companyDTOList;

    public TripController(TripService tripService,
                            CountryService countryService,
                            CityService cityService,
                            CompanyService companyService,
                            EmailSender emailSender,
                            OrderService orderService) {
        this.tripService = tripService;
        this.countryService = countryService;
        this.cityService = cityService;
        this.companyService = companyService;
        this.emailSender = emailSender;
        this.orderService = orderService;
    }

    @ModelAttribute("countries")
    public List<CountryDTO> getCountries() {
        countryDTOList = countryService.findAll(Sort.by("name").ascending());
        return countryDTOList;
    }

    @GetMapping("/countries")
    @ResponseBody
    public List<CountryDTO> getCountriesJson() {
        return countryDTOList;
    }

    @ModelAttribute("companies")
    public List<CompanyDTO> getCompanies() {
        companyDTOList = companyService.findAll();
        return companyDTOList;
    }

    @GetMapping("/companies")
    @ResponseBody
    public List<CompanyDTO> getCompaniesJson() {
        return companyDTOList;
    }

    @GetMapping("/create")
    public String getAddTripView(Model model) {
        model.addAttribute("trip", new TripDTO());
        return "trip/addTrip";
    }

    @GetMapping("/edit/{id}")
    public String getEditTripView(@PathVariable Long id, Model model) {
        TripDTO tripDTO = tripService.getById(id);

        String dDeparture = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date(tripDTO.getDepartureDate().getTime()));
        String dArrival = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date(tripDTO.getArrivalDate().getTime()));

        model.addAttribute("id", tripDTO.getId());
        model.addAttribute("countries_fr", tripDTO.getBusStationDeparture().getCityDTO().getCountryDTO());
        model.addAttribute("countries_to", tripDTO.getBusStationArrival().getCityDTO().getCountryDTO());
        model.addAttribute("cities_fr", tripDTO.getBusStationDeparture().getCityDTO());
        model.addAttribute("cities_to", tripDTO.getBusStationArrival().getCityDTO());
        model.addAttribute("busStations_fr", tripDTO.getBusStationDeparture());
        model.addAttribute("busStations_to", tripDTO.getBusStationArrival());
        model.addAttribute("picker1", dDeparture);
        model.addAttribute("picker2", dArrival);
        model.addAttribute("companies", tripDTO.getBus().getCompanyDTO());
        model.addAttribute("buss", tripDTO.getBus());
        model.addAttribute("allSeats", tripDTO.getAllSeats());
        model.addAttribute("freeSeats", tripDTO.getFreeSeats());
        model.addAttribute("price",tripDTO.getPrice());
        model.addAttribute("soldTickets", tripService.getNumberSoldTicketById(tripDTO.getId()));
        return "trip/editTrip";
    }

    @GetMapping(value = "/countries/{id}")
    @ResponseBody
    public List<CityDTO> getCities(@PathVariable Long id) {
        return cityService.getCityListByCountry(id);
    }

    @GetMapping(value = "/cities/{id}")
    @ResponseBody
    public List<BusStationDTO> getBusStations(@PathVariable Long id) {
        return cityService.findOne(id).getBusStationDTOList();
    }

    @GetMapping(value = "/companies/{id}")
    @ResponseBody
    public List<BusDTO> getBuses(@PathVariable Long id) {
        return companyService.findOne(id).getBusDTOList();
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public TripCreateUpdateDTO addTrip(@RequestBody TripCreateUpdateDTO tripDTO) {
        LOGGER.info("Create Trip where tripCreateUpdateDTO: " + tripDTO);
        tripService.addTrip(tripDTO);
        return tripDTO;
    }

    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public TripCreateUpdateDTO editTrip(@PathVariable Long id, @RequestBody TripCreateUpdateDTO tripDTO) {
        LOGGER.info("Edit trip where new tripCreateUpdateDTO: " + tripDTO);
        tripService.edit(tripDTO);
        return tripDTO;
    }


    @PreAuthorize("hasAnyRole('WORKER','ADMIN')")
    @GetMapping()
    public String getTripView(@RequestParam(value = "status", required = false, defaultValue = "ACTIVE") TripStatus status, Model model) {
        List<TripDTO> tripDTOList = tripService.findAllByStatus(status);
        model.addAttribute("trips", tripDTOList.size() != 0 ? tripDTOList : null);
        return "withrole/showTrips";
    }

    @PreAuthorize("hasAnyRole('CLIENT')")
    @GetMapping("/show/{id}")
    public String getShowTripViewByClient(@PathVariable Long id, Model model) {
        model.addAttribute("trip", tripService.getById(id));
        return "trip/checkoutOrder";
    }

    @GetMapping("/canceled/{id}")
    public String canceledTrip(@PathVariable(name = "id") Long idTrip) {
        LOGGER.info("Cancel Trip where trip id: " + idTrip);
        tripService.canceledTrip(idTrip);
        sendСancellationСonfirmToEmail(idTrip);
        return "redirect:/trip";
    }

    @GetMapping("/{id}/orders")
    public String getTripTicketsSold(@PathVariable Long id, Model model) {
        model.addAttribute("orders", orderService.findAllByTripId(id));
        return "order/showOrdersOnTrip";
    }

    private void sendСancellationСonfirmToEmail(Long idTrip) {
        LOGGER.info("Send a cancellation email to all users");
        List<OrderDTO> orderDTOList = orderService.findAllByTripId(idTrip);
        orderDTOList.stream().forEach(a -> emailSender.sendСancellationСonfirmToEmail(a));
    }

    @GetMapping("/findTrips")
    public ModelAndView getTripByCriteria() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("trip/showTrips");
        modelAndView.addObject("trips", null);
        modelAndView.addObject("rating", Rating.values());
        modelAndView.addObject("tripCriteriaDTO", new TripCriteriaDTO());
        return modelAndView;
    }

    @PostMapping("/findTrips")
    public ModelAndView getTripByCriteria(@ModelAttribute("tripCriteriaDTO") TripCriteriaDTO tripCriteriaDTO,
                                            @RequestParam(value = "companyChoice", required = false) Long companyChoice) {
        LOGGER.info("Get trips by criteria: " + tripCriteriaDTO);
        ModelAndView modelAndView = new ModelAndView("trip/showTrips");
        modelAndView.addObject("picker1", tripCriteriaDTO.getDepartureDate());
        modelAndView.addObject("city_from", cityService.findOne(tripCriteriaDTO.getIdCityDeparture()));
        modelAndView.addObject("city_to", cityService.findOne(tripCriteriaDTO.getIdCityArrival()));
        modelAndView.addObject("companyChoice", tripCriteriaDTO.getIdCompany() == null ? null : companyService.findOne(tripCriteriaDTO.getIdCompany()).getName());
        modelAndView.addObject("tripCriteriaDTO", tripCriteriaDTO);

        List<TripDTO> tripDTOList = null;
        try {
            tripDTOList = tripService.findTripsByCriteria(tripCriteriaDTO);
        } catch (ParseException e) {
            LOGGER.error("Parse dates error");
            e.printStackTrace();
        }
        modelAndView.addObject("trips", tripDTOList.size() == 0 ? null : tripDTOList);
        return modelAndView;
    }

}
