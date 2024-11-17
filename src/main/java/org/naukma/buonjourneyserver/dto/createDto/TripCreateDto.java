package org.naukma.buonjourneyserver.dto.createDto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.naukma.buonjourneyserver.entity.enums.TripStatus;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TripCreateDto {

    @Future(message = "Start date must be in the future")
    private LocalDate startDate;

    @Future(message = "End date must be in the future")
    private LocalDate endDate;

    @NotBlank(message = "Title can not be blank")
    @Size(max = 50, message = "Title length can be maximum 50 characters")
    private String title;

    @NotBlank(message = "Destination can not be blank")
    @Size(max = 100, message = "Destination length can be maximum 50 characters")
    private String destination;

    private TripStatus status;

    @NotNull
    private Long userId;
}
