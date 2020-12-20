package ua.kpi.tef.demo_ticket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import ua.kpi.tef.demo_ticket.dto.PaymentDto;
import ua.kpi.tef.demo_ticket.dto.TicketDto;
import ua.kpi.tef.demo_ticket.entity.Ticket;
import ua.kpi.tef.demo_ticket.entity.User;
import ua.kpi.tef.demo_ticket.service.TicketService;

@Controller
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping("/buy-ticket")
    public String buyTicket(TicketDto ticketDto, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Ticket ticket = ticketService.buyTicket(ticketDto, user);
        model.addAttribute("ticket", ticket);
        return "redirect:payment";
    }

    @PostMapping("/payment")
    public String finishPayment(PaymentDto paymentDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        ticketService.finishPayment(paymentDto, user);
        return "redirect:success-payment";
    }
}
