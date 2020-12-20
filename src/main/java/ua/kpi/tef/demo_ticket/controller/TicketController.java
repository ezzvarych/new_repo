package ua.kpi.tef.demo_ticket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import ua.kpi.tef.demo_ticket.dto.TicketDto;
import ua.kpi.tef.demo_ticket.entity.User;
import ua.kpi.tef.demo_ticket.repository.TicketRepository;
import ua.kpi.tef.demo_ticket.service.TicketService;
import ua.kpi.tef.demo_ticket.service.TripService;

@Controller
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private TicketRepository ticketRepository;

    @PostMapping("/buy-ticket")
    public String buyTicket(TicketDto ticketDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        ticketService.buyTicket(ticketDto, user);
        return "redirect:users";
    }
}
