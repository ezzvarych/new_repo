package ua.kpi.tef.demo_ticket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.kpi.tef.demo_ticket.dto.TicketDto;
import ua.kpi.tef.demo_ticket.entity.Ticket;
import ua.kpi.tef.demo_ticket.entity.Trip;
import ua.kpi.tef.demo_ticket.entity.User;
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

    @Transactional
    public Ticket buyTicket(TicketDto ticketDto, User user) {
        Trip trip = tripService.getById(ticketDto.getTripId());
        if (trip.getPrice() > ticketDto.getTotalPrice()) {
            throw new RuntimeException("Invalid price");
        }
        if (user.getBalance() < ticketDto.getTotalPrice()) {
            throw new RuntimeException("Not enough money on user balance");
        }
        if (trip.getTicketsSold().size() >= trip.getPlaceAmount()) {
            throw new RuntimeException("No free places for this trip");
        }

        long newBalance = user.getBalance() - trip.getPrice();
        user.setBalance(newBalance);
        userRepository.save(user);
        Ticket ticket = new Ticket();
        ticket.setUser(user);
        ticket.setTrip(trip);
        ticket.setFirstName(ticketDto.getFirstName());
        ticket.setLastName(ticketDto.getLastName());
        ticket.setTotalPrice(ticketDto.getTotalPrice());
        return ticketRepository.save(ticket);
    }
}
