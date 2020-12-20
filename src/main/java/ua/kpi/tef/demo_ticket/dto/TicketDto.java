package ua.kpi.tef.demo_ticket.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TicketDto {
    private Long tripId;
    private String firstName;
    private String lastName;
    private long totalPrice;
}
