package ua.kpi.tef.demo_ticket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ua.kpi.tef.demo_ticket.dto.TicketDto;
import ua.kpi.tef.demo_ticket.entity.Ticket;
import ua.kpi.tef.demo_ticket.entity.Trip;
import ua.kpi.tef.demo_ticket.entity.User;
import ua.kpi.tef.demo_ticket.entity.enums.RoleType;
import ua.kpi.tef.demo_ticket.entity.enums.TripType;
import ua.kpi.tef.demo_ticket.repository.TripRepository;
import ua.kpi.tef.demo_ticket.repository.UserRepository;
import ua.kpi.tef.demo_ticket.service.TicketService;

@SpringBootApplication
public class DemoTicketApplication implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TripRepository tripRepository;
    @Autowired
    private TicketService ticketService;

    public static void main(String[] args) {
        SpringApplication.run(DemoTicketApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        User user = User.builder()
                .firstName("qwe")
                .lastName("qwe")
                .email("qwe")
                .username("qwe")
                .password(new BCryptPasswordEncoder().encode("qwe"))
                .role(RoleType.ROLE_USER)
                .balance(400)
                .enabled(true)
                .credentialsNonExpired(true)
                .accountNonLocked(true)
                .accountNonExpired(true)
                .build();
        User createdUser = userRepository.save(user);
        Trip trip = Trip.builder()
                .tripType(TripType.BUS)
                .fromWhere("Kyiv")
                .whereTo("Kyiv")
                .price(200)
                .placeAmount(10)
                .build();
        Trip createdTrip = tripRepository.save(trip);
        TicketDto ticketDto = TicketDto.builder()
                .firstName("Qwe")
                .lastName("Qwe")
                .totalPrice(250)
                .tripId(createdTrip.getId())
                .build();
        Ticket ticket = ticketService.buyTicket(ticketDto, createdUser);
    }
}
