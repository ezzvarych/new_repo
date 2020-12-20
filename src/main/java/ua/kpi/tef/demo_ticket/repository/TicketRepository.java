package ua.kpi.tef.demo_ticket.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ua.kpi.tef.demo_ticket.entity.Ticket;

public interface TicketRepository extends CrudRepository<Ticket, Long> {
    @Query("select count(t) from Ticket t where t.trip.id = :tripId")
    int countByTripId(Long tripId);
}
