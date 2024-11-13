package org.naukma.buonjourneyserver.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.naukma.buonjourneyserver.entity.enums.TicketType;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tickets")
@NoArgsConstructor
@AllArgsConstructor
public class TicketEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500)
    private String fileUrl;

    private LocalDateTime dateTime;

    @Column(nullable = false, length = 100)
    private String caption;

    private boolean isUsed;

    @Enumerated(EnumType.STRING)
    private TicketType ticketType;
}
