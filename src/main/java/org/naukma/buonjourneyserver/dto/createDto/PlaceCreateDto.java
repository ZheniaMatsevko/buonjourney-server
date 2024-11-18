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
public class PlaceCreateDto {

    @NotNull
    private Long userId;

    @NotBlank(message = "Name can not be blank")
    @Size(max = 100, message = "Name length can be maximum 100 characters")
    private String name;

    @Size(max = 500, message = "Address length can be maximum 500 characters")
    private String address;

    @Size(max = 1000, message = "Description length can be maximum 1000 characters")
    private String description;
}