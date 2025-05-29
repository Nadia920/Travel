package com.java.Travel.service.ServiceImpl;


import com.java.Travel.controller.dto.OrderDTO;
import com.java.Travel.controller.dto.TicketDTO;
import com.java.Travel.model.TicketEntity;

import java.util.List;

import com.java.Travel.service.TicketService;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class TicketServiceImpl implements TicketService {

    @Override
    public List<TicketDTO> mapTicketDTOList(List<TicketEntity> tickets) {
        return tickets.stream().map(a -> new TicketDTO(
                        a.getId(),
                        new OrderDTO(a.getOrder().getId()),
                        a.getPrice()))
                .collect(Collectors.toList());
    }
}

