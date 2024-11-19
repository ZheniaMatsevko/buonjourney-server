package org.naukma.buonjourneyserver.dto;

import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PackingListDto {
    private Long id;

    @Size(max = 100, message = "Name length can be maximum 100 characters")
    private String name;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<ItemDto> items;
}
