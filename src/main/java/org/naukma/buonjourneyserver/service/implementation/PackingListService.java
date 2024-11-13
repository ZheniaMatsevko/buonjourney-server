package org.naukma.buonjourneyserver.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.naukma.buonjourneyserver.repository.IPackingListRepository;
import org.naukma.buonjourneyserver.service.IPackingListService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PackingListService implements IPackingListService {
    private final IPackingListRepository packingListRepository;

}
