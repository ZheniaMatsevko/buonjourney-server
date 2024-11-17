package org.naukma.buonjourneyserver.dto.updateDto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.naukma.buonjourneyserver.entity.enums.TripStatus;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TripUpdateDto {
    private Long id;

    @Future(message = "Start date must be in future")
    private LocalDate startDate;

    @Future(message = "End date must be in future")
    private LocalDate endDate;

    @Size(max = 50, message = "Title length can be maximum 50 characters")
    private String title;

    @Size(max = 100, message = "Destination length can be maximum 50 characters")
    private String destination;

    private TripStatus status;
}
