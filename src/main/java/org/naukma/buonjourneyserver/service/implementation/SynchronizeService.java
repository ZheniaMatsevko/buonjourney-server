package org.naukma.buonjourneyserver.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.naukma.buonjourneyserver.dto.*;
import org.naukma.buonjourneyserver.service.*;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SynchronizeService implements ISynchronizeService {
    private final IUserService userService;
    private final ITripService tripService;
    private final IPlaceService placeService;
    private final IEventService eventService;
    private final ITicketService ticketService;
    private final IPackingListService packingListService;
    
    @Override
    public UserDto createUserWithAllData(UserDto user) {
        UserDto userCopy = createUserCopy(user);
        UserDto createdUser = userService.createUser(userCopy);

        if(user.getPlacesToVisit() != null && !user.getPlacesToVisit().isEmpty()) {
            createPlaces(user, createdUser);
        }

        if(user.getTrips() != null && !user.getTrips().isEmpty()) {
            createTrips(user, createdUser);
        }
        return createdUser;
    }

    private UserDto createUserCopy(UserDto user) {
        UserDto copy = new UserDto();
        copy.setName(user.getName());
        copy.setUsername(user.getUsername());
        copy.setEmail(user.getEmail());
        copy.setPassword(user.getPassword());

        return copy;
    }

    private void createTrips(UserDto user, UserDto createdUser) {
        for (TripDto tripDto: user.getTrips()){
            TripDto createdTrip = tripService.createTrip(tripDto, createdUser);
            createdUser.getTrips().add(createdTrip);

            if(tripDto.getEvents() != null && !tripDto.getEvents().isEmpty()) {
                createEvents(tripDto, createdTrip);
            }

            if(tripDto.getTickets() != null && !tripDto.getTickets().isEmpty()){
                createTickets(tripDto, createdTrip);
            }

            if(tripDto.getPackingLists() != null && !tripDto.getPackingLists().isEmpty()){
                createPackingLists(tripDto, createdTrip);
            }
        }
    }

    private void createPackingLists(TripDto tripDto, TripDto createdTrip) {
        for(PackingListDto packingListDto: tripDto.getPackingLists()){
            createdTrip.getPackingLists().add(packingListService.createPackingList(packingListDto, createdTrip));
        }
    }

    private void createTickets(TripDto tripDto, TripDto createdTrip) {
        for (TicketDto ticketDto: tripDto.getTickets()){
            createdTrip.getTickets().add(ticketService.createTicket(ticketDto, createdTrip));
        }
    }

    private void createEvents(TripDto tripDto, TripDto createdTrip) {
        for(EventDto eventDto: tripDto.getEvents()){
            createdTrip.getEvents().add(eventService.createEvent(eventDto, createdTrip));
        }
    }

    private void createPlaces(UserDto user, UserDto createdUser) {
        for(PlaceDto place: user.getPlacesToVisit()){
            createdUser.getPlacesToVisit().add(placeService.createPlace(place, createdUser));
        }
    }
}
