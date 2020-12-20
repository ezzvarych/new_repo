package ua.kpi.tef.demo_ticket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.kpi.tef.demo_ticket.dto.PaymentDto;
import ua.kpi.tef.demo_ticket.dto.TicketDto;
import ua.kpi.tef.demo_ticket.entity.Ticket;
import ua.kpi.tef.demo_ticket.entity.Trip;
import ua.kpi.tef.demo_ticket.entity.User;
import ua.kpi.tef.demo_ticket.entity.enums.PaymentStatus;
import ua.kpi.tef.demo_ticket.repository.TicketRepository;
import ua.kpi.tef.demo_ticket.repository.UserRepository;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TripService tripService;

    public Ticket getById(Long ticketId) {
        return ticketRepository.findById(ticketId)
                .orElse(new Ticket());
    }

    public Ticket finishPayment(PaymentDto paymentDto, User user) {
        Ticket ticket = ticketRepository.findById(paymentDto.getTicketId())
                .orElse(new Ticket());
        if (user.getBalance() < ticket.getTotalPrice()) {
            throw new RuntimeException("Not enough money on user balance");
        }
        long newBalance = user.getBalance() - ticket.getTotalPrice();
        user.setBalance(newBalance);
        userRepository.save(user);
        ticket.setPaymentStatus(PaymentStatus.FINISHED);
        return ticketRepository.save(ticket);
    }

    @Transactional
    public Ticket buyTicket(TicketDto ticketDto, User user) {
        Trip trip = tripService.getById(ticketDto.getTripId());
        if (trip.getPrice() > ticketDto.getTotalPrice()) {
            throw new RuntimeException("Invalid price");
        }
        if (trip.getTicketsSold().size() >= trip.getPlaceAmount()) {
            throw new RuntimeException("No free places for this trip");
        }
        Ticket ticket = new Ticket();
        ticket.setUser(user);
        ticket.setTrip(trip);
        ticket.setFirstName(ticketDto.getFirstName());
        ticket.setLastName(ticketDto.getLastName());
        ticket.setTotalPrice(ticketDto.getTotalPrice());
        ticket.setPaymentStatus(PaymentStatus.PENDING);
        return ticketRepository.save(ticket);
    }
}
