package org.naukma.buonjourneyserver.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.naukma.buonjourneyserver.repository.ITripRepository;
import org.naukma.buonjourneyserver.service.ITripService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TripService  implements ITripService {
    private final ITripRepository tripRepository;

}
