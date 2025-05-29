package com.java.Travel.schedule;


import com.java.Travel.model.TripEntity;
import com.java.Travel.model.TripStatus;
import com.java.Travel.repository.OrderEntityRepository;
import com.java.Travel.repository.TripEntityRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Component
public class Scheduled {

    private final TripEntityRepository tripRepository;
    private final OrderEntityRepository orderRepository;

    public Scheduled(TripEntityRepository tripRepository,
                     OrderEntityRepository orderRepository) {
        this.tripRepository = tripRepository;
        this.orderRepository = orderRepository;
    }

    /**
     * Update every 5 minutes
     */
    @Transactional
    @org.springframework.scheduling.annotation.Scheduled(fixedDelay = 300000)
    public void setTripStatusFinished() {
        List<TripEntity> tripEntityList = tripRepository.findAllByStatusActiveAndArrivalDateBefore(new Date());

        if (tripEntityList.size() != 0) {
            tripEntityList.forEach(a -> orderRepository.setStatusFinishedWhereTripId(a.getId()));
            tripEntityList.forEach(a->a.setStatus(TripStatus.FINISHED));
        }

    }

}
