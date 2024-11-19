package org.naukma.buonjourneyserver.repository;

import org.naukma.buonjourneyserver.entity.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITicketRepository extends JpaRepository<TicketEntity, Long> {
    List<TicketEntity> findAllByTripId(Long tripId);

}
