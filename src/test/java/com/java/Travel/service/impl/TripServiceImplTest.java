package com.java.Travel.service.impl;

import com.java.Travel.controller.dto.TripCreateUpdateDTO;
import com.java.Travel.controller.dto.TripCriteriaDTO;
import com.java.Travel.exception.*;
import com.java.Travel.model.*;
import com.java.Travel.repository.BusEntityRepository;
import com.java.Travel.repository.BusStationEntityRepository;
import com.java.Travel.repository.TripEntityRepository;

import com.java.Travel.service.CityService;
import com.java.Travel.service.PaymentService;
import com.java.Travel.service.ServiceImpl.TripServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.persistence.EntityNotFoundException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TripServiceImplTest {

    private final int GET_HOURS_FROM_MILLISECONDS = 3_600_000;
    private final int THREE_DAYS = 72;
    private final int DAY_IN_MILLISECONDS = 86_399_000;

    @Mock
    private TripEntityRepository tripRepository;

    @Mock
    private BusEntityRepository busRepository;

    @Mock
    private BusStationEntityRepository busStationRepository;

    @Mock
    private CityService cityService;

    @Mock
    private PaymentService paymentService;

    @InjectMocks
    private TripServiceImpl tripService;

    @Test(expected = EntityNotFoundException.class)
    public void addTripBusNotFoundException() {
        tripService.addTrip(new TripCreateUpdateDTO());
        Optional<BusEntity> busEntityEmpty = Optional.empty();
        when(busRepository.findById(1L)).thenReturn(busEntityEmpty);
    }

    @Test(expected = TripDateIncorrectException.class)
    public void addTrip_DateMoreThreeDays() {
        TripCreateUpdateDTO tripCreateUpdateDTO = new TripCreateUpdateDTO();
        tripCreateUpdateDTO.setBusId(1L);
        Optional<BusEntity> busEntity = Optional.of(new BusEntity("Maz", "M9273"));
        when(busRepository.findById(tripCreateUpdateDTO.getBusId())).thenReturn(busEntity);
        tripCreateUpdateDTO.setDateDeparture(1579726264000L); //2020-12-22 20-51
        tripCreateUpdateDTO.setDateArrival(1580071864000L); //2020-12-26 20-51
        tripService.addTrip(tripCreateUpdateDTO);
    }

    @Test(expected = TripDateIncorrectException.class)
    public void addTripDate_DateDepartBetweenTripsDate() {
        //first exception
        TripCreateUpdateDTO tripCreateUpdateDTO = new TripCreateUpdateDTO();
        tripCreateUpdateDTO.setBusId(1L);
        Optional<BusEntity> busEntity = Optional.of(new BusEntity("Maz", "M9273"));

        TripEntity tripEntity = new TripEntity();
        tripEntity.setId(2L);
        tripEntity.setDepartureDate(new Timestamp(1579726264000L));
        tripEntity.setArrivalDate(new Timestamp(1579903204000L));
        busEntity.get().setTrips(Set.of(tripEntity));

        when(busRepository.findById(tripCreateUpdateDTO.getBusId())).thenReturn(busEntity);
        tripCreateUpdateDTO.setDateDeparture(1579812664000L);
        tripCreateUpdateDTO.setDateArrival(1579899064000L);
        tripService.addTrip(tripCreateUpdateDTO);
    }

    @Test(expected = TripDateIncorrectException.class)
    public void addTripDateArrivalBetweenTripDate() {
        //second exception
        TripCreateUpdateDTO tripCreateUpdateDTO = new TripCreateUpdateDTO();
        tripCreateUpdateDTO.setBusId(1L);
        Optional<BusEntity> busEntity = Optional.of(new BusEntity("Maz", "M9273"));

        TripEntity tripEntity = new TripEntity();
        tripEntity.setId(2L);
        tripEntity.setDepartureDate(new Timestamp(1579726264000L));
        tripEntity.setArrivalDate(new Timestamp(1579903204000L));
        busEntity.get().setTrips(Set.of(tripEntity));

        when(busRepository.findById(tripCreateUpdateDTO.getBusId())).thenReturn(busEntity);
        tripCreateUpdateDTO.setDateDeparture(1579626004000L);
        tripCreateUpdateDTO.setDateArrival(1579795204000L);
        tripService.addTrip(tripCreateUpdateDTO);
    }

    @Test()
    public void addTrip() {
        TripCreateUpdateDTO tripCreateUpdateDTO = new TripCreateUpdateDTO();
        tripCreateUpdateDTO.setBusId(1L);

        Optional<BusEntity> busEntity = Optional.of(new BusEntity("Maz", "M9273"));
        busEntity.get().setTrips(Set.of());
        busEntity.get().setId(1L);

        when(busRepository.findById(tripCreateUpdateDTO.getBusId())).thenReturn(busEntity);
        tripCreateUpdateDTO.setDateDeparture(1579626004000L);
        tripCreateUpdateDTO.setDateArrival(1579795204000L);

        Optional<BusStationEntity> busStationEntityFrom = Optional.of(new BusStationEntity());
        busStationEntityFrom.get().setId(2L);
        Optional<BusStationEntity> busStationEntityTo = Optional.of(new BusStationEntity());
        busStationEntityTo.get().setId(3L);
        tripCreateUpdateDTO.setBusStationFromId(2L);
        tripCreateUpdateDTO.setBusStationToId(3L);

        when(busStationRepository.findById(busStationEntityFrom.get().getId())).thenReturn(busStationEntityFrom);
        when(busStationRepository.findById(busStationEntityTo.get().getId())).thenReturn(busStationEntityTo);

        tripService.addTrip(tripCreateUpdateDTO);
        TripEntity tripEntity = new TripEntity();
        tripEntity.setBus(busEntity.get());
        tripEntity.setDepartureDate(new Timestamp(1579626004000L));
        tripEntity.setArrivalDate(new Timestamp(1579795204000L));
        tripEntity.setBusStationDeparture(busStationEntityFrom.get());
        tripEntity.setBusStationArrival(busStationEntityTo.get());
        tripEntity.setStatus(TripStatus.ACTIVE);

        verify(tripRepository).save(refEq(tripEntity));


    }

    @Test
    public void getNumberSoldTicketById() {
        Optional<TripEntity> tripEntity = Optional.of(new TripEntity());
        tripEntity.get().setId(1L);
        OrderEntity orderEntityFirst = new OrderEntity();
        orderEntityFirst.setTickets(Arrays.asList(new TicketEntity(), new TicketEntity(), new TicketEntity()));
        OrderEntity orderEntitySecond = new OrderEntity();
        orderEntitySecond.setTickets(Arrays.asList(new TicketEntity(), new TicketEntity()));
        tripEntity.get().setOrders(Set.of(orderEntityFirst, orderEntitySecond));
        when(tripRepository.findById(tripEntity.get().getId())).thenReturn(tripEntity);
        Integer actualNumberSoldTickets = tripService.getNumberSoldTicketById(tripEntity.get().getId());
        Integer expected = 5;
        assertEquals(expected, actualNumberSoldTickets);
    }

    @Test(expected = TripStatusIncorrectException.class)
    public void canceledTrip_TripStatusIncorrect() {
        Optional<TripEntity> tripEntity = Optional.of(new TripEntity());
        tripEntity.get().setId(1L);
        tripEntity.get().setStatus(TripStatus.CANCELED);
        when(tripRepository.findById(tripEntity.get().getId())).thenReturn(tripEntity);
        tripService.canceledTrip(tripEntity.get().getId());
    }

    @Test(expected = PaymentException.class)
    public void canceledTrip_WithPaymentException() {
        Optional<TripEntity> tripEntity = Optional.of(new TripEntity());
        tripEntity.get().setId(1L);
        tripEntity.get().setStatus(TripStatus.ACTIVE);
        when(tripRepository.findById(tripEntity.get().getId())).thenReturn(tripEntity);
        when(paymentService.returnMoneyForTripCancellation(tripEntity.get())).thenReturn(false);
        tripService.canceledTrip(tripEntity.get().getId());
    }

    @Test()
    public void canceledTrip() {
        Optional<TripEntity> tripEntity = Optional.of(new TripEntity());
        tripEntity.get().setId(1L);
        tripEntity.get().setStatus(TripStatus.ACTIVE);
        OrderEntity orderEntityFirst = new OrderEntity();
        orderEntityFirst.setStatus(OrderStatus.ACTIVE);
        tripEntity.get().setOrders(Set.of(orderEntityFirst));
        when(tripRepository.findById(tripEntity.get().getId())).thenReturn(tripEntity);
        when(paymentService.returnMoneyForTripCancellation(tripEntity.get())).thenReturn(true);
        tripService.canceledTrip(tripEntity.get().getId());
        tripEntity.get().setStatus(TripStatus.CANCELED);
        tripEntity.get().getOrders().stream().forEach(a -> a.setStatus(OrderStatus.CANCELED));
        verify(tripRepository).save(tripEntity.get());
    }

    @Test(expected = DatasException.class)
    public void findTripByCriteria_BlankFields() {
        TripCriteriaDTO tripCriteriaDTO = new TripCriteriaDTO();
        tripService.findTripsByCriteria(tripCriteriaDTO);
    }

    @Test(expected = CityIncorrectException.class)
    public void findTripByCriteria_EqualsCities() {
        TripCriteriaDTO tripCriteriaDTO = new TripCriteriaDTO();
        tripCriteriaDTO.setIdCityDeparture(1L);
        tripCriteriaDTO.setIdCityArrival(1L);
        tripCriteriaDTO.setDepartureDate("2020-01-29");
        tripService.findTripsByCriteria(tripCriteriaDTO);
    }

    @Test(expected = DatasException.class)
    public void findTripByCriteria_DatesDepartBeforeCurrDate() {
        TripCriteriaDTO tripCriteriaDTO = new TripCriteriaDTO();
        tripCriteriaDTO.setIdCityDeparture(1L);
        tripCriteriaDTO.setIdCityArrival(2L);
        tripCriteriaDTO.setDepartureDate("2020-01-01");
        tripService.findTripsByCriteria(tripCriteriaDTO);
    }

}
