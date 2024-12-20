package org.naukma.buonjourneyserver.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.naukma.buonjourneyserver.entity.enums.TicketType;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketDto {
    private Long id;

    private String fileUrl;

    @Future(message = "Date and time must be in the future")
    private LocalDateTime dateTime;

    @Size(max = 100, message = "Caption length can be maximum 100 characters")
    private String caption;

    private TicketType ticketType;
}
