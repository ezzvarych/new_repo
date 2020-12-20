package ua.kpi.tef.demo_ticket.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDto {
    private Long ticketId;
    private String cardNumber;
    private String cvv;
    private String cardOwner;
    private String monthEnd;
    private String yearEnd;
}
