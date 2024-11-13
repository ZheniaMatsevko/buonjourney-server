package org.naukma.buonjourneyserver.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    @NotBlank
    @Size(min = 3, max = 30, message = "Username length must be between 3 and 30 characters")
    private String username;

    @NotBlank
    @Size(min = 6, message = "Password length must be more than 6 characters")
    private String password;

    @Email
    private String email;

    private String name;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<PlaceDto> placesToVisit;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<TripDto> trips;
}
