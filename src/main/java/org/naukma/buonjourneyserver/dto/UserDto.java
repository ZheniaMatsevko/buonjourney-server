package org.naukma.buonjourneyserver.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;

    @NotBlank(message = "Username cannot be blank")
    @Size(min = 3, max = 30, message = "Username length must be between 3 and 30 characters")
    private String username;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 6, message = "Password length must be at least 8 characters")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$", message = "Password need to be minimum eight characters, at least one uppercase letter, one lowercase letter and one number")
    private String password;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email cannot be blank")
    private String email;

    @Size(max = 30, message = "Name length must be at most 30 characters")
    private String name;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<PlaceDto> placesToVisit;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<TripDto> trips;
}
