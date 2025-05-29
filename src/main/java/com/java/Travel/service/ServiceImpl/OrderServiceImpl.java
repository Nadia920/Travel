package com.java.Travel.service.ServiceImpl;

import com.java.Travel.controller.dto.OrderCreateUpdateDTO;
import com.java.Travel.controller.dto.OrderDTO;
import com.java.Travel.controller.dto.UserDTO;
import com.java.Travel.exception.OrderOnThisBusStationAlreadyExistException;
import com.java.Travel.exception.OrderSeatsException;
import com.java.Travel.model.*;

import java.util.List;


import com.java.Travel.repository.OrderEntityRepository;
import com.java.Travel.repository.TicketEntityRepository;
import com.java.Travel.repository.TripEntityRepository;
import com.java.Travel.repository.UserEntityRepository;
import com.java.Travel.service.OrderService;
import com.java.Travel.service.PaymentService;
import com.java.Travel.service.TicketService;
import com.java.Travel.service.TripService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private final static Logger LOGGER = LogManager.getLogger();

    @Autowired
    private TripEntityRepository tripRepository;
    @Autowired
    private UserEntityRepository userRepository;
    @Autowired
    private OrderEntityRepository orderRepository;
    @Autowired
    private TicketEntityRepository ticketRepository;
    @Autowired
    private TripService tripService;
    @Autowired
    private TicketService ticketService;
    @Autowired
    private PaymentService paymentService;


    private DecimalFormat decimalFormat = new DecimalFormat("#.##");


    @Transactional
    @Override
    public OrderDTO add(OrderCreateUpdateDTO orderDTO) {
        LOGGER.info("Add order: "+orderDTO );
        OrderDTO savedOrder = new OrderDTO();

        if (orderRepository.getOrderIdByTripIdAndUserId(orderDTO.getIdTrip(), orderDTO.getIdClient()) != null) {
            LOGGER.error("Order on this trip already exist");
            throw new OrderOnThisBusStationAlreadyExistException("Order on this trip already exist", orderDTO);
        }

        TripEntity tripEntity = tripRepository.findById(orderDTO.getIdTrip()).get();


        if (orderDTO.getCountSeats() > tripEntity.getFreeSeats()) {
            LOGGER.error("The number of free seats per trip is less than what you choose");
            throw new OrderSeatsException("The number of free seats per trip is less than what you choose");
        }

        if (orderDTO.getCountSeats() == 0) {
            LOGGER.error("Count seats = 0");
            throw new OrderSeatsException("Count seats = 0");
        }

        if (tripEntity.getFreeSeats() == 0) {
            LOGGER.error("No empty seats");
            throw new OrderSeatsException("No empty seats");
        }

        UserEntity userEntity = userRepository.findById(orderDTO.getIdClient()).get();
        if (paymentService.payOrder(orderDTO, tripEntity, userEntity)) {
            Double finalCost = Double.parseDouble(decimalFormat.format(orderDTO.getCountSeats() * tripEntity.getPrice()).replace(',', '.'));
            OrderEntity orderEntity = orderRepository.save(new OrderEntity(finalCost, OrderStatus.ACTIVE, new Timestamp(new Date().getTime()), userEntity, tripEntity));
            List<TicketEntity> savedTickets = new ArrayList<>();
            for (int i = 0; i < orderDTO.getCountSeats(); i++) {
                TicketEntity savedTicket = ticketRepository.save(new TicketEntity(orderDTO.getPriceOneSeat(), orderEntity));
                savedTickets.add(savedTicket);
            }

            tripEntity.setFreeSeats(tripEntity.getFreeSeats() - orderDTO.getCountSeats());
            tripRepository.save(tripEntity);

            orderEntity.setTickets(savedTickets);
            return mapOrderDTO(orderEntity);
        }
        return savedOrder;
    }

    @Override
    public List<OrderDTO> getOrdersByUserId(Long id) {
        return mapOrderDTOList(orderRepository.findAllByUserId(id));
    }

    @Override
    public OrderDTO findById(Long id) {
        return mapOrderDTO(orderRepository.findById(id).get());
    }


    @Override
    @Transactional
    public void deleteTicketsOnTripByUSer(OrderCreateUpdateDTO order) {
        LOGGER.info("delete Tickets On Trip where order: " + order);
        OrderEntity orderEntity = orderRepository.findById(order.getId()).get();
        TripEntity tripEntity = orderEntity.getTrip();
        Integer currFreeSeats = tripEntity.getFreeSeats();

        if (order.getReturnTickets() > orderEntity.getTickets().size() || order.getReturnTickets() == 0) {
            LOGGER.error("Incorrect seats number.\nYou want return:" + order.getReturnTickets() + "\nMax return only:" + orderEntity.getTickets().size());
            throw new OrderSeatsException("Incorrect seats number.\nYou want return:" + order.getReturnTickets() + "\nMax return only:" + orderEntity.getTickets().size());
        } else if (order.getReturnTickets() == orderEntity.getTickets().size()) {
            if (paymentService.returnMoney(order, tripEntity, userRepository.findById(order.getIdClient()).get())) {
                orderRepository.deleteById(order.getId());
            }
        } else {
            if (paymentService.returnMoney(order, tripEntity, userRepository.findById(order.getIdClient()).get())) {
                List<TicketEntity> ticketEntityList = ticketRepository.findAllByOrderId(order.getId());
                for (int i = 0; i < order.getReturnTickets(); i++) {
                    ticketRepository.deleteById(ticketEntityList.get(i).getId());
                }

                Double mustReturn = order.getReturnTickets() * tripEntity.getPrice();

                orderEntity.setFinalCost(Double.parseDouble(decimalFormat
                        .format(orderEntity.getFinalCost() - mustReturn)
                        .replace(',', '.')));

                orderRepository.save(orderEntity);
            }
        }

        tripEntity.setFreeSeats(currFreeSeats + order.getReturnTickets());
        tripRepository.save(tripEntity);

    }

    @Transactional
    @Override
    public OrderDTO takeMoreTickets(OrderCreateUpdateDTO order) {
        OrderDTO savedOrderDTO = null;
        OrderEntity orderEntity = orderRepository.findById(order.getId()).get();
        Integer currTickets = orderEntity.getTickets().size();

        if (orderEntity.getTrip().getFreeSeats() == 0) {
            throw new OrderSeatsException("No empty seats");
        }

        if (order.getCountSeats() > 0 && (order.getCountSeats() <= orderEntity.getTrip().getFreeSeats())) {

            TripEntity tripEntity = orderEntity.getTrip();

            if (paymentService.payOrder(order, tripEntity, userRepository.findById(order.getIdClient()).get())) {

                for (int i = 0; i < order.getCountSeats(); i++) {
                    ticketRepository.save(new TicketEntity(order.getPriceOneSeat(), orderEntity));
                }

                currTickets += order.getCountSeats();

                tripEntity.setFreeSeats(tripEntity.getFreeSeats() - order.getCountSeats());
                orderEntity.setFinalCost(Double.parseDouble(decimalFormat.format(currTickets * tripEntity.getPrice()).replace(',', '.')));
                orderEntity.setTrip(tripEntity);
                orderEntity.setTickets(ticketRepository.findAllByOrderId(orderEntity.getId()));

                orderRepository.save(orderEntity);
                tripRepository.save(tripEntity);

                savedOrderDTO = mapOrderDTO(orderEntity);
            }

        } else {
            throw new OrderSeatsException("Incorrect seats number");
        }
        return savedOrderDTO;
    }

    @Override
    public List<OrderDTO> findAllByTripId(Long idTrip) {
        return mapOrderDTOList(orderRepository.findAllByTripId(idTrip));
    }

    @Override
    public List<OrderDTO> getOrdersByUserIdAndStatus(Long id, OrderStatus status) {
        return mapOrderDTOList(orderRepository.findAllByUserIdAndStatus(id, status, Sort.by("orderDate").ascending()));
    }


    private List<OrderDTO> mapOrderDTOList(List<OrderEntity> orderEntityList) {
        return orderEntityList.stream()
                .map(a -> new OrderDTO(
                        a.getId(),
                        a.getFinalCost(),
                        a.getStatus(),
                        a.getOrderDate(),
                        new UserDTO(
                                a.getUser().getLogin(),
                                a.getUser().getPassword(),
                                a.getUser().getEmail(),
                                a.getUser().getFirstName(),
                                a.getUser().getLastName(),
                                a.getUser().getPatronymic(),
                                a.getUser().getPhoneNumber()
                        ),
                        tripService.mapTripDTO(a.getTrip()),
                        ticketService.mapTicketDTOList(a.getTickets())

                ))
                .collect(Collectors.toList());
    }

    private OrderDTO mapOrderDTO(OrderEntity orderEntity) {
        return new OrderDTO(orderEntity.getId(),
                orderEntity.getFinalCost(),
                orderEntity.getStatus(),
                orderEntity.getOrderDate(),
                new UserDTO(
                        orderEntity.getUser().getLogin(),
                        orderEntity.getUser().getPassword(),
                        orderEntity.getUser().getEmail(),
                        orderEntity.getUser().getFirstName(),
                        orderEntity.getUser().getLastName(),
                        orderEntity.getUser().getPatronymic(),
                        orderEntity.getUser().getPhoneNumber()
                ),
                tripService.mapTripDTO(orderEntity.getTrip()),
                ticketService.mapTicketDTOList(orderEntity.getTickets()));
    }


}
