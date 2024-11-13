package org.naukma.buonjourneyserver.dto;

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

    private LocalDateTime dateTime;

    private String caption;

    private boolean isUsed;

    private TicketType ticketType;
}
