package org.naukma.buonjourneyserver.repository;

import org.naukma.buonjourneyserver.entity.PlaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPlaceRepository extends JpaRepository<PlaceEntity, Long> {
}
