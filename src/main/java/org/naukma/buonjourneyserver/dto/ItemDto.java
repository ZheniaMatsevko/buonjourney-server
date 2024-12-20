package org.naukma.buonjourneyserver.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemDto {
    private Long id;

    @NotBlank
    @Size(max = 100, message = "Name length can be maximum 100 characters")
    private String name;

    private boolean isPacked;
}
