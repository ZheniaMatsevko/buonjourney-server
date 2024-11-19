package org.naukma.buonjourneyserver.service;


import org.naukma.buonjourneyserver.dto.PackingListDto;
import org.naukma.buonjourneyserver.dto.createDto.PackingListCreateDto;

import java.util.List;

public interface IPackingListService {
    List<PackingListDto> getAllByTripId(Long tripId);

    PackingListDto createPackingList(PackingListCreateDto packingListCreateDto);

    PackingListDto updatePackingList(PackingListDto packingList);

    void deletePackingList(Long packingListId);

    PackingListDto getPackingListById(Long packingListId);

}
