package org.naukma.buonjourneyserver.dto.createDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemCreateDto {
    @NotBlank
    @Size(max = 100, message = "Name length can be maximum 100 characters")
    private String name;

    private boolean isPacked;

    @NotNull
    private Long packingListId;
}