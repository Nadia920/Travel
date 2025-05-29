package com.java.Travel.service.ServiceImpl;


import java.text.ParseException;
import java.util.List;

import com.java.Travel.controller.dto.*;
import com.java.Travel.exception.*;
import com.java.Travel.model.*;
import com.java.Travel.repository.*;
import com.java.Travel.service.CityService;
import com.java.Travel.service.PaymentService;
import com.java.Travel.service.TripService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.convert.QueryByExamplePredicateBuilder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.Predicate;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TripServiceImpl implements TripService {

    private final static Logger LOGGER = LogManager.getLogger();


    private final int GET_HOURS_FROM_MILLISECONDS = 3_600_000;
    private final int THREE_DAYS = 72;
    private final int DAY_IN_MILLISECONDS = 86_399_000;

    @Autowired
    private TripEntityRepository tripRepository;
    @Autowired
    private BusEntityRepository busRepository;
    @Autowired
    private BusStationEntityRepository busStationRepository;
    @Autowired
    private CityService cityService;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private CityEntityRepository cityRepository;
    @Autowired
    private CompanyEntityRepository companyRepository;



    @Transactional
    @Override
    public void addTrip(TripCreateUpdateDTO tripDTO) {
        LOGGER.info("Create trip where tripDTO: " + tripDTO);
        Optional<BusEntity> busEntity = busRepository.findById(tripDTO.getBusId());

        if (busEntity.isEmpty()) {
            LOGGER.error("BusEntity with id=" + tripDTO.getBusId() + " not found");
            throw new EntityNotFoundException("BusEntity with id=" + tripDTO.getBusId() + " not found");
        }

        Timestamp dateDeparture = new Timestamp(tripDTO.getDateDeparture());
        Timestamp dateArrival = new Timestamp(tripDTO.getDateArrival());

        if ((dateArrival.getTime() - dateDeparture.getTime()) / GET_HOURS_FROM_MILLISECONDS > THREE_DAYS) {
            LOGGER.error("trip duration more than three days. Departure date: " + dateArrival + ", Arrival date: " + dateDeparture);
            throw new TripDateIncorrectException("trip duration more than three days", tripDTO);
        }

        for (TripEntity tripEntity : busEntity.get().getTrips()) {

            //first exception
            if (dateDeparture.after(tripEntity.getDepartureDate()) && dateDeparture.before(tripEntity.getArrivalDate())) {
                LOGGER.error("This bus already has a trip in this time lapse");
                throw new TripDateIncorrectException("This bus already has a trip in this time lapse", tripDTO);
            }

            //second exception
            if (dateArrival.after(tripEntity.getDepartureDate()) && dateArrival.before(tripEntity.getArrivalDate())) {
                LOGGER.error("This bus already has a trip in this time lapse");
                throw new TripDateIncorrectException("This bus already has a trip in this time lapse", tripDTO);
            }

        }

        TripEntity tripEntity = new TripEntity();
        tripEntity.setAllSeats(tripDTO.getAllSeats());
        tripEntity.setArrivalDate(dateArrival);
        tripEntity.setDepartureDate(dateDeparture);
        tripEntity.setFreeSeats(tripDTO.getFreeSeats());
        tripEntity.setPrice(tripDTO.getTicketPrice());
        tripEntity.setBusStationArrival(busStationRepository.findById(tripDTO.getBusStationToId()).get());
        tripEntity.setBusStationDeparture(busStationRepository.findById(tripDTO.getBusStationFromId()).get());
        tripEntity.setBus(busEntity.get());
        tripEntity.setStatus(TripStatus.ACTIVE);
        tripRepository.save(tripEntity);
    }

    @Override
    public TripDTO getById(Long id) {
        Optional<TripEntity> tripEntity = tripRepository.findById(id);

        if (!tripEntity.isPresent()) {
            throw new EntityNotFoundException("trip with id=" + id + " not found");
        }

        return mapTripDTO(tripEntity.get());
    }

    @Transactional
    @Override
    public void edit(TripCreateUpdateDTO tripDTO) {
        LOGGER.info("Edit trip where new tripDTO: " + tripDTO);

        TripEntity editEntity = tripRepository.findById(tripDTO.getId()).get();

        if (editEntity == null) {
            LOGGER.error("trip with id=" + tripDTO.getId() + " not found");
            throw new EntityNotFoundException("trip with id=" + tripDTO.getId() + " not found");
        }

        Timestamp dateDeparture = new Timestamp(tripDTO.getDateDeparture());
        Timestamp dateArrival = new Timestamp(tripDTO.getDateArrival());

       BusEntity editBus = busRepository.findById(tripDTO.getBusId()).get();

        if (tripDTO.getBusId() != editEntity.getBus().getId()) {

            for (TripEntity tripEntity : editBus.getTrips()) {

                if (dateDeparture.after(tripEntity.getDepartureDate()) &&
                        dateDeparture.before(tripEntity.getArrivalDate())) {
                    LOGGER.error("This bus already has a trip in this time lapse");
                    throw new TripDateIncorrectException("This bus already has a trip in this time lapse", tripDTO);
                }

                if (dateArrival.after(tripEntity.getDepartureDate()) &&
                        dateArrival.before(tripEntity.getArrivalDate())) {
                    LOGGER.error("This bus already has a trip in this time lapse");
                    throw new TripDateIncorrectException("This bus already has a trip in this time lapse", tripDTO);
                }
            }

            editEntity.setBus(editEntity.getBus());

        } else if (!editEntity.getArrivalDate().equals(dateArrival) &&
                !editEntity.getDepartureDate().equals(dateDeparture)) {

            for (TripEntity tripEntity : editBus.getTrips()) {

                if (tripDTO.getId() == tripEntity.getId())
                    continue;

                if (dateDeparture.after(tripEntity.getDepartureDate()) &&
                        dateDeparture.before(tripEntity.getArrivalDate())) {
                    LOGGER.error("This bus already has a trip in this time lapse");

                    throw new TripDateIncorrectException("This bus already has a trip in this time lapse", tripDTO);
                }

                if (dateArrival.after(tripEntity.getDepartureDate()) &&
                        dateArrival.before(tripEntity.getArrivalDate())) {
                    LOGGER.error("This bus already has a trip in this time lapse");

                    throw new TripDateIncorrectException("This bus already has a trip in this time lapse", tripDTO);
                }
            }

            editEntity.setArrivalDate(dateArrival);
            editEntity.setDepartureDate(dateDeparture);

        }


        if (tripDTO.getBusStationToId() != editEntity.getBusStationArrival().getId()) {
            editEntity.setBusStationArrival(busStationRepository.findById(tripDTO.getBusStationToId()).get());
        }

        if (tripDTO.getBusStationFromId() != editEntity.getBusStationDeparture().getId()) {
            editEntity.setBusStationDeparture(busStationRepository.findById(tripDTO.getBusStationFromId()).get());
        }

        editEntity.setAllSeats(tripDTO.getAllSeats());
        editEntity.setFreeSeats(tripDTO.getFreeSeats());
        editEntity.setPrice(tripDTO.getTicketPrice());

        tripRepository.save(editEntity);

    }

    @Override
    public List<TripDTO> findAll() {
        List<TripEntity> tripEntityList = tripRepository.findAll(Sort.by("departureDate", "status").ascending());
        return mapListTripDTO(tripEntityList);
    }

    @Override
    public Integer getNumberSoldTicketById(Long id) {
        TripEntity tripEntity = tripRepository.findById(id).get();
        return tripEntity.getOrders().stream()
                .mapToInt((a) -> a.getTickets().size()).sum();
    }

    @Transactional
    @Override
    public void canceledTrip(Long idTrip) {
        LOGGER.info("Cancel trip where trip id: " + idTrip);

        Optional<TripEntity> tripEntity = tripRepository.findById(idTrip);

        if (tripEntity.get().getStatus() != TripStatus.ACTIVE) {
            LOGGER.error("trip status incorrect for cancellation. Current status: " + tripEntity.get().getStatus());
            throw new TripStatusIncorrectException("trip_status_is_incorrect_for_cancellation");
        }


        if (tripEntity.isPresent()) {

            if (paymentService.returnMoneyForTripCancellation(tripEntity.get())) {
                tripEntity.get().setStatus(TripStatus.CANCELED);
                tripEntity.get().getOrders().stream().forEach(a -> a.setStatus(OrderStatus.CANCELED));

                tripRepository.save(tripEntity.get());
            } else {
                LOGGER.error("Money back error. trip uncanceled");
                throw new PaymentException("Money_back_error");
            }

        }

    }


    @Override
    public List<TripDTO> findTripsByCriteria(TripCriteriaDTO tripCriteriaDTO) {
        LOGGER.info("Find trip by criteria: " + tripCriteriaDTO);
        if (tripCriteriaDTO.getIdCityDeparture() == null || tripCriteriaDTO.getIdCityArrival() == null || tripCriteriaDTO.getDepartureDate().equals("")) {
            LOGGER.warn("Fill in all the fields");
            throw new DatasException("Fill_in_all_the_fields", cityService.findOne(tripCriteriaDTO.getIdCityDeparture()), cityService.findOne(tripCriteriaDTO.getIdCityArrival()), tripCriteriaDTO.getDepartureDate(), tripCriteriaDTO);
        }

        if (tripCriteriaDTO.getIdCityDeparture() == tripCriteriaDTO.getIdCityArrival()) {
            LOGGER.error("Enter different cities");
            throw new CityIncorrectException("Enter_different_cities", cityService.findOne(tripCriteriaDTO.getIdCityDeparture()), cityService.findOne(tripCriteriaDTO.getIdCityArrival()), tripCriteriaDTO.getDepartureDate(), tripCriteriaDTO);
        }

        List<TripEntity> tripEntities = new ArrayList<>();
        try {

            Timestamp dateD = parseDate(tripCriteriaDTO.getDepartureDate());
            Timestamp currD = parseDate(parseDate(new Timestamp(new Date().getTime())));
            Timestamp finishD = new Timestamp(dateD.getTime() + DAY_IN_MILLISECONDS);

            if (dateD.before(currD)) {
                LOGGER.error("Incorrect dates");
                throw new DatasException("Incorrect_dates", cityService.findOne(tripCriteriaDTO.getIdCityDeparture()), cityService.findOne(tripCriteriaDTO.getIdCityArrival()), tripCriteriaDTO.getDepartureDate(), tripCriteriaDTO);
            }

            if (dateD.equals(currD)) {
                dateD = new Timestamp(new Date().getTime());
            }

            tripCriteriaDTO.setStatus(TripStatus.ACTIVE);
            Example<TripEntity> tripEntityExample = Example.of(createTripEntityExample(tripCriteriaDTO));

            tripEntities = tripRepository.findAll(getSpecAndExample(dateD, finishD, tripCriteriaDTO, tripEntityExample), Sort.by("departureDate"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return mapListTripDTO(tripEntities);
    }

    private String parseTime(Timestamp timestamp) {
        String parseTime;
        int hours = timestamp.getHours();
        int minutes = timestamp.getMinutes();

        if (hours < 10) {
            parseTime = "0" + hours + ":";
        } else {
            parseTime = hours + ":";
        }

        if (minutes < 10) {
            parseTime += "0" + minutes;
        } else {
            parseTime += minutes + "";
        }
        return parseTime;
    }

    public String parseDate(Timestamp timestamp) {
        return new SimpleDateFormat("yyyy-MM-dd ").format(timestamp.getTime());

    }

    public Timestamp parseDate(String date) throws ParseException {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date dateD = formatter.parse(date);
        Timestamp timestampD = new Timestamp(dateD.getTime());
        return timestampD;
    }

    private String calcTravelTime(Timestamp timeD, Timestamp timeA) {

        long milliseconds = (timeA.getTime() - timeD.getTime());
        int seconds = (int) milliseconds / 1000;
        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        String travelTime = hours + "h " + minutes + " m";
        return travelTime;
    }

    @Override
    public TripDTO mapTripDTO(TripEntity tripEntity) {
        TripDTO tripDTO = new TripDTO();

        tripDTO.setId(tripEntity.getId());
        tripDTO.setAllSeats(tripEntity.getAllSeats());
        tripDTO.setFreeSeats(tripEntity.getFreeSeats());
        tripDTO.setPrice(tripEntity.getPrice());
        tripDTO.setDepartureDate(tripEntity.getDepartureDate());
        tripDTO.setArrivalDate(tripEntity.getArrivalDate());
        tripDTO.setStatus(tripEntity.getStatus());

        CountryDTO countryDepartureDTO = new CountryDTO();
        countryDepartureDTO.setId(tripEntity.getBusStationDeparture().getCityEntity().getCountryEntity().getId());
        countryDepartureDTO.setName(tripEntity.getBusStationDeparture().getCityEntity().getCountryEntity().getName());

        CityDTO cityDepartureDTO = new CityDTO();
        cityDepartureDTO.setId(tripEntity.getBusStationDeparture().getCityEntity().getId());
        cityDepartureDTO.setName(tripEntity.getBusStationDeparture().getCityEntity().getName());
        cityDepartureDTO.setCountryDTO(countryDepartureDTO);

        BusStationDTO busStationDepartureDTO = new BusStationDTO();
        busStationDepartureDTO.setId(tripEntity.getBusStationDeparture().getId());
        busStationDepartureDTO.setCode(tripEntity.getBusStationDeparture().getCode());
        busStationDepartureDTO.setName(tripEntity.getBusStationDeparture().getName());
        busStationDepartureDTO.setCityDTO(cityDepartureDTO);

        tripDTO.setBusStationDeparture(busStationDepartureDTO);

        CountryDTO countryArrivalDTO = new CountryDTO();
        countryArrivalDTO.setId(tripEntity.getBusStationArrival().getCityEntity().getCountryEntity().getId());
        countryArrivalDTO.setName(tripEntity.getBusStationArrival().getCityEntity().getCountryEntity().getName());

        CityDTO cityArrivalDTO = new CityDTO();
        cityArrivalDTO.setId(tripEntity.getBusStationArrival().getCityEntity().getId());
        cityArrivalDTO.setName(tripEntity.getBusStationArrival().getCityEntity().getName());
        cityArrivalDTO.setCountryDTO(countryArrivalDTO);

        BusStationDTO busStationArrivalDTO = new BusStationDTO();
        busStationArrivalDTO.setId(tripEntity.getBusStationArrival().getId());
        busStationArrivalDTO.setCode(tripEntity.getBusStationArrival().getCode());
        busStationArrivalDTO.setName(tripEntity.getBusStationArrival().getName());
        busStationArrivalDTO.setCityDTO(cityArrivalDTO);

        tripDTO.setBusStationArrival(busStationArrivalDTO);

        BusDTO busDTO = new BusDTO();
        busDTO.setId(tripEntity.getBus().getId());
        busDTO.setSideNumber(tripEntity.getBus().getSideNumber());
        busDTO.setName(tripEntity.getBus().getName());

        CompanyDTO companyDTO = new CompanyDTO();
        companyDTO.setId(tripEntity.getBus().getCompany().getId());
        companyDTO.setName(tripEntity.getBus().getCompany().getName());
        companyDTO.setRating(tripEntity.getBus().getCompany().getRating());
        busDTO.setCompanyDTO(companyDTO);

        tripDTO.setBus(busDTO);


        String travelTime = calcTravelTime(tripDTO.getDepartureDate(), tripDTO.getArrivalDate());
        tripDTO.setTravelTime(travelTime);

        String timeD = parseTime(tripDTO.getDepartureDate());
        tripDTO.setTimeDeparture(timeD);

        String timeA = parseTime(tripDTO.getArrivalDate());
        tripDTO.setTimeArrival(timeA);

        tripDTO.setDateDeparture(parseDate(tripDTO.getDepartureDate()));
        tripDTO.setDateArrival(parseDate(tripDTO.getArrivalDate()));

        return tripDTO;
    }

    @Override
    public List<TripDTO> mapListTripDTO(List<TripEntity> tripEntityList) {
        List<TripDTO> tripDTOList = tripEntityList.stream()
                .map(a -> new TripDTO(
                        a.getId(),
                        a.getFreeSeats(),
                        a.getAllSeats(),
                        a.getPrice(),
                        a.getDepartureDate(),
                        a.getArrivalDate(),
                        a.getOrders().stream().mapToInt((b) -> b.getTickets().size()).sum(),
                        a.getStatus(),
                        new BusDTO(a.getBus().getId(), a.getBus().getName(), a.getBus().getSideNumber(),
                                new CompanyDTO(a.getBus().getCompany().getId(), a.getBus().getCompany().getName(), a.getBus().getCompany().getRating())),
                        new BusStationDTO(a.getBusStationDeparture().getId(), a.getBusStationDeparture().getName(), a.getBusStationDeparture().getCode(),
                                new CityDTO(a.getBusStationDeparture().getCityEntity().getId(), a.getBusStationDeparture().getCityEntity().getName(),
                                        new CountryDTO(a.getBusStationDeparture().getCityEntity().getCountryEntity().getId(), a.getBusStationDeparture().getCityEntity().getCountryEntity().getName()))), /*busStationDeparture*/
                        new BusStationDTO(a.getBusStationArrival().getId(), a.getBusStationArrival().getName(), a.getBusStationArrival().getCode(),
                                new CityDTO(a.getBusStationArrival().getCityEntity().getId(), a.getBusStationArrival().getCityEntity().getName(),
                                        new CountryDTO(a.getBusStationArrival().getCityEntity().getCountryEntity().getId(), a.getBusStationArrival().getCityEntity().getCountryEntity().getName()))) /*busStationArrival*/
                ))
                .collect(Collectors.toList());

        String travelTime;
        String timeD;
        String timeA;

        for (TripDTO tripDTO : tripDTOList) {

            travelTime = calcTravelTime(tripDTO.getDepartureDate(), tripDTO.getArrivalDate());
            tripDTO.setTravelTime(travelTime);

            timeD = parseTime(tripDTO.getDepartureDate());
            tripDTO.setTimeDeparture(timeD);

            timeA = parseTime(tripDTO.getArrivalDate());
            tripDTO.setTimeArrival(timeA);

            tripDTO.setDateDeparture(parseDate(tripDTO.getDepartureDate()));
            tripDTO.setDateArrival(parseDate(tripDTO.getArrivalDate()));

        }
        return tripDTOList;
    }

    @Override
    public List<TripDTO> findAllByStatus(TripStatus status) {
        return mapListTripDTO(tripRepository.findAllByStatus(status, Sort.by("departureDate").ascending()));

    }

    public TripEntity createTripEntityExample(TripCriteriaDTO tripCriteriaDTO) {
        TripEntity tripEntity = new TripEntity();

        if (tripCriteriaDTO.getIdCityDeparture() != null && !tripCriteriaDTO.getIdCityDeparture().equals("")) {
            BusStationEntity busStationEntityDepartures = new BusStationEntity();
            busStationEntityDepartures.setCityEntity(cityRepository.findById(tripCriteriaDTO.getIdCityDeparture()).get());
            tripEntity.setBusStationDeparture(busStationEntityDepartures);
        }

        if (tripCriteriaDTO.getIdCityArrival() != null && !tripCriteriaDTO.getIdCityArrival().equals("")) {
            BusStationEntity busStationEntityArrival = new BusStationEntity();
            busStationEntityArrival.setCityEntity(cityRepository.findById(tripCriteriaDTO.getIdCityArrival()).get());
            tripEntity.setBusStationArrival(busStationEntityArrival);
        }


        if (tripCriteriaDTO.getIdCompany() != null && !tripCriteriaDTO.getIdCompany().equals("")) {
            BusEntity busEntity = new BusEntity();
            busEntity.setCompany(companyRepository.findById(tripCriteriaDTO.getIdCompany()).get());
            tripEntity.setBus(busEntity);
        } else if (tripCriteriaDTO.getRatingCompany() != null && !tripCriteriaDTO.getRatingCompany().equals("")) {
            BusEntity busEntity = new BusEntity();
            CompanyEntity companyEntity = new CompanyEntity();
            companyEntity.setRating(tripCriteriaDTO.getRatingCompany());
            busEntity.setCompany(companyEntity);
            tripEntity.setBus(busEntity);
        }

        tripEntity.setStatus(tripCriteriaDTO.getStatus());
        return tripEntity;
    }

    public Specification<TripEntity> getSpecAndExample(Timestamp dateD, Timestamp finishD, TripCriteriaDTO tripCriteriaDTO, Example<TripEntity> example) {
        return (Specification<TripEntity>) (root, query, builder) -> {
            final List<Predicate> predicates = new ArrayList<>();

            if (tripCriteriaDTO.getMinPrice() != null && !tripCriteriaDTO.getMinPrice().equals("")) {
                predicates.add(builder.greaterThanOrEqualTo(root.get("price"), tripCriteriaDTO.getMinPrice()));
            }

            if (tripCriteriaDTO.getMaxPrice() != null && !tripCriteriaDTO.getMinPrice().equals("")) {
                predicates.add(builder.lessThanOrEqualTo(root.get("price"), tripCriteriaDTO.getMaxPrice()));
            }

            if (tripCriteriaDTO.getCountSeats() != null && tripCriteriaDTO.getCountSeats() != 0 && !tripCriteriaDTO.getIdCityArrival().equals("")) {
                predicates.add(builder.greaterThanOrEqualTo(root.get("freeSeats"), tripCriteriaDTO.getCountSeats()));
            }

            predicates.add(builder.greaterThan(root.get("departureDate"), dateD));
            predicates.add(builder.lessThan(root.get("departureDate"), finishD));

            predicates.add(QueryByExamplePredicateBuilder.getPredicate(root, builder, example));

            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}
