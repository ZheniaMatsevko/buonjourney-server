package org.naukma.buonjourneyserver.dto.updateDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTicketUsedDto {
    private Long id;

    private boolean isUsed;
}
