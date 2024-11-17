package org.naukma.buonjourneyserver.dto.createDto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventCreateDto {

    @NotBlank(message = "Title can not be blank")
    @Size(max = 100, message = "Title length can be maximum 100 characters")
    private String title;

    @Future(message = "Date and time must be in the future")
    private LocalDateTime dateTime;

    @Size(max = 1000, message = "Description length can be maximum 1000 characters")
    private String description;

    @Size(max = 100, message = "Address length can be maximum 100 characters")
    private String address;

    @NotNull
    private Long tripId;
}
