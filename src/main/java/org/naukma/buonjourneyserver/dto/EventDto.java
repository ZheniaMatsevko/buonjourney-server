package org.naukma.buonjourneyserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventDto {
    private Long id;

    private String title;

    private LocalDateTime dateTime;

    private String description;

    private String address;
}
