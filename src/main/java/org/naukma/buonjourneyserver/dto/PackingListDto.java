package org.naukma.buonjourneyserver.dto;

import lombok.*;

import java.util.Set;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PackingListDto {
    private Long id;

    private String name;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<ItemDto> items;
}
