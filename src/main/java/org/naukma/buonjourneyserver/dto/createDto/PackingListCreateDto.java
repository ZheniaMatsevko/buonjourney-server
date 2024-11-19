package org.naukma.buonjourneyserver.dto.createDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.naukma.buonjourneyserver.dto.ItemDto;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PackingListCreateDto {

    @Size(max = 100, message = "Name length can be maximum 100 characters")
    @NotBlank
    private String name;

    @NotNull
    private Long tripId;

    private List<ItemDto> items;
}
