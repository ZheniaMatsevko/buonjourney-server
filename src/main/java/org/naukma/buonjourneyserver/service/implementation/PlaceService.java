package org.naukma.buonjourneyserver.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.naukma.buonjourneyserver.repository.IPlaceRepository;
import org.naukma.buonjourneyserver.service.IPlaceService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlaceService implements IPlaceService {
    private final IPlaceRepository placeRepository;

}
