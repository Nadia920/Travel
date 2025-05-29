package com.java.Travel.service.ServiceImpl;


import java.util.List;

import com.java.Travel.controller.dto.CityFrAndTo;
import com.java.Travel.controller.dto.TourDTO;
import com.java.Travel.model.BusStationEntity;
import com.java.Travel.model.TripEntity;
import com.java.Travel.repository.CityEntityRepository;
import com.java.Travel.repository.TripEntityRepository;
import com.java.Travel.service.InfoService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;
import java.util.Set;

import static com.java.Travel.model.TripStatus.ACTIVE;
import static com.java.Travel.model.TripStatus.FINISHED;


@Service
public class InfoServiceImpl implements InfoService {

    @Autowired
    private TripEntityRepository tripRepository;
    @Autowired
    private CityEntityRepository cityRepository;



    @Override
    public List<TourDTO> getDataForTour() {
        List<TourDTO> tourDTOList = new ArrayList<>();

        Optional<Set<CityFrAndTo>> dtoSet = getCityFrAndToSet();

        if (dtoSet.isPresent()) {

            TourDTO tourDTO;
            for (CityFrAndTo cityFrAndTo : dtoSet.get()) {

                tourDTO = new TourDTO();

                Example<TripEntity> entityExample = Example.of(createTripExample(cityFrAndTo));

                long count = tripRepository.count(entityExample);
                tourDTO.setCountTrip(count);

                String cityFrName = cityRepository.findById(cityFrAndTo.getCityFr()).get().getName();
                String cityToName = cityRepository.findById(cityFrAndTo.getCityTo()).get().getName();
                tourDTO.setTripName(cityFrName + " - " + cityToName);

                tourDTOList.add(tourDTO);
            }
            tourDTOList.sort(Comparator.comparing(TourDTO::getCountTrip).reversed());
        }
        return tourDTOList;
    }

    @NotNull
    private Optional<Set<CityFrAndTo>> getCityFrAndToSet() {
        return Optional.of(tripRepository.findCityFrAndToAndSort(ACTIVE, FINISHED));
    }

    @NotNull
    private TripEntity createTripExample(CityFrAndTo cityFrAndTo) {
        TripEntity tripEntity = new TripEntity();

        BusStationEntity busStationEntityArrival = new BusStationEntity();
        busStationEntityArrival.setCityEntity(cityRepository.findById(cityFrAndTo.getCityFr()).get());
        tripEntity.setBusStationArrival(busStationEntityArrival);

        BusStationEntity busStationEntityDepartures = new BusStationEntity();
        busStationEntityDepartures.setCityEntity(cityRepository.findById(cityFrAndTo.getCityTo()).get());
        tripEntity.setBusStationDeparture(busStationEntityDepartures);

        return tripEntity;
    }


}
