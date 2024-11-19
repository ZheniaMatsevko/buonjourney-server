package org.naukma.buonjourneyserver.repository;

import org.naukma.buonjourneyserver.entity.PackingListEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPackingListRepository extends JpaRepository<PackingListEntity, Long> {
    List<PackingListEntity> findAllByTripId(Long tripId);

}
