package org.naukma.buonjourneyserver.dto.updateDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateItemNameDto {
    private Long id;

    @Size(max = 100, message = "Name length can be maximum 100 characters")
    @NotBlank(message = "Name can not be empty")
    private String name;
}
