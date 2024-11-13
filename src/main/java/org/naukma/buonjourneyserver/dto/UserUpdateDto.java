package org.naukma.buonjourneyserver.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UserUpdateDto {
    private Long id;
    private String email;
    private String name;
    private String username;
}
