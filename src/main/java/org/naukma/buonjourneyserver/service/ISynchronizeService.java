package org.naukma.buonjourneyserver.service;

import org.naukma.buonjourneyserver.dto.UserDto;

public interface ISynchronizeService {
    UserDto createUserWithAllData(UserDto user);
}
