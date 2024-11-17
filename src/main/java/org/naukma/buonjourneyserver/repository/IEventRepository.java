package org.naukma.buonjourneyserver.repository;

import org.naukma.buonjourneyserver.entity.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IEventRepository extends JpaRepository<EventEntity, Long> {
    List<EventEntity> findAllByTripId(Long tripId);
}
