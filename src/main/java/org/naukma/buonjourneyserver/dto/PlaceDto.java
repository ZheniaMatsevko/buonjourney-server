package org.naukma.buonjourneyserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlaceDto {
    private Long id;

    private String name;

    private String address;

    private String description;
}
