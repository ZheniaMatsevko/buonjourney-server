package org.naukma.buonjourneyserver.entity;

import jakarta.persistence.*;
import lombok.*;
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

    private String fileUrl;

    private LocalDateTime dateTime;

    @Column(nullable = false, length = 100)
    private String caption;

    private boolean isUsed;

    @Enumerated(EnumType.STRING)
    private TicketType ticketType;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "trip_id", nullable = false)
    private TripEntity trip;
}
