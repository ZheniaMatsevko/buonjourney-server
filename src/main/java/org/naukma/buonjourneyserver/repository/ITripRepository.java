package org.naukma.buonjourneyserver.repository;

import org.naukma.buonjourneyserver.entity.TripEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITripRepository  extends JpaRepository<TripEntity, Long> {
    List<TripEntity> findAllByUserId(Long userId);
}
