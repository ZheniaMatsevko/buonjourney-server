package org.naukma.buonjourneyserver.repository;

import org.naukma.buonjourneyserver.entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IItemRepository extends JpaRepository<ItemEntity, Long> {
}
