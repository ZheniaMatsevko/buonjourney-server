package org.naukma.buonjourneyserver.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.naukma.buonjourneyserver.repository.IItemRepository;
import org.naukma.buonjourneyserver.service.IItemService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemService implements IItemService {
    private final IItemRepository itemRepository;

}
