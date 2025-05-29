package com.java.Travel.controller;


import com.java.Travel.controller.dto.OrderCreateUpdateDTO;
import com.java.Travel.controller.dto.OrderDTO;
import com.java.Travel.model.OrderStatus;
import com.java.Travel.security.CustomUserDetail;
import com.java.Travel.service.EmailSender;
import com.java.Travel.service.OrderService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {
    private final static Logger LOGGER = LogManager.getLogger();

    private OrderService orderService;
    private EmailSender emailSender;

    public OrderController(OrderService orderService,
                           EmailSender emailSender) {
        this.orderService = orderService;
        this.emailSender = emailSender;
    }

    @PreAuthorize("hasAnyRole('CLIENT')")
    @PostMapping("/checkout")
    @ResponseBody
    public OrderDTO addTicket(@RequestBody OrderCreateUpdateDTO orderDTO, @AuthenticationPrincipal CustomUserDetail currentUser) {
        LOGGER.info("Buy Trip tickets. Order: " + orderDTO + ", Client with login: " + currentUser.getLogin());
        orderDTO.setIdClient(currentUser.getId());
        return orderService.add(orderDTO);

    }

    @PreAuthorize("hasAnyRole('CLIENT')")
    @GetMapping("/client")
    public String getOrdersByUser(@RequestParam(value = "status", required = false, defaultValue = "ACTIVE") OrderStatus status, Model model, @AuthenticationPrincipal CustomUserDetail currUser) {
        List<OrderDTO> orderDTOList = orderService.getOrdersByUserIdAndStatus(currUser.getId(), status);
        model.addAttribute("orders", orderDTOList.size() != 0 ? orderDTOList : null);
        return "withrole/client/myTrips";
    }

    @PreAuthorize("hasAnyRole('CLIENT')")
    @GetMapping("/return/{id}")
    public String getOrderById(@PathVariable Long id, Model model) {
        model.addAttribute("order", orderService.findById(id));
        return "order/returnTickets";
    }

    @PreAuthorize("hasAnyRole('CLIENT')")
    @PostMapping("/return")
    @ResponseBody
    public OrderCreateUpdateDTO deleteTicketsOnTripByUser(@RequestBody OrderCreateUpdateDTO order, @AuthenticationPrincipal CustomUserDetail currUser) {
        order.setIdClient(currUser.getId());
        orderService.deleteTicketsOnTripByUSer(order);
        return order;
    }

    @PreAuthorize("hasAnyRole('CLIENT')")
    @GetMapping("/{id}/moreTickets")
    public String getTakeMoreTicketsView(@PathVariable Long id, Model model) {
        model.addAttribute("order", orderService.findById(id));
        return "order/takeMoreTickets";
    }

    @PreAuthorize("hasAnyRole('CLIENT')")
    @PostMapping("/{id}/moreTickets")
    @ResponseBody
    public OrderDTO takeMoreTickets(@RequestBody OrderCreateUpdateDTO order, @AuthenticationPrincipal CustomUserDetail currUser) {
        order.setIdClient(currUser.getId());
        return orderService.takeMoreTickets(order);
    }

    @PostMapping("/sendСonfirmPurchaseToEmail")
    @ResponseBody
    public OrderDTO sendСonfirmPurchaseToEmail(@RequestBody OrderDTO orderDTO, @AuthenticationPrincipal CustomUserDetail currUser) {
        LOGGER.info("Send Сonfirm Purchase To Email. Order: " + orderDTO);
        emailSender.sendСonfirmPurchaseToEmail(orderDTO);
        return orderDTO;
    }


}