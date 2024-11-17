package org.naukma.buonjourneyserver.dto.updateDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UserUpdateDto {
    private Long id;

    @Email(message = "Email should be valid")
    private String email;

    @Size(max = 30, message = "Name length must be at most 30 characters")
    private String name;

    @Size(min = 3, max = 30, message = "Username length must be between 3 and 30 characters")
    private String username;
}
