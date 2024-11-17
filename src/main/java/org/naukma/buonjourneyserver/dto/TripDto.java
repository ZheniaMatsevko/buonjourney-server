package org.naukma.buonjourneyserver.dto;

import lombok.*;
import org.naukma.buonjourneyserver.entity.enums.TripStatus;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TripDto {
    private Long id;

    private LocalDate startDate;

    private LocalDate endDate;

    private String title;

    private String destination;

    private TripStatus status;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<PackingListDto> packingLists;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<EventDto> events;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<TicketDto> tickets;
}
