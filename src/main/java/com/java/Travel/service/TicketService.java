package com.java.Travel.service;


import com.java.Travel.controller.dto.TicketDTO;
import com.java.Travel.model.TicketEntity;

import java.util.List;

public interface TicketService {

    List<TicketDTO> mapTicketDTOList(List<TicketEntity> tickets);
}
