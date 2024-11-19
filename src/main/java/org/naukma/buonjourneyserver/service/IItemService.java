package org.naukma.buonjourneyserver.service;


import org.naukma.buonjourneyserver.dto.ItemDto;
import org.naukma.buonjourneyserver.dto.createDto.ItemCreateDto;
import org.naukma.buonjourneyserver.dto.updateDto.UpdateItemNameDto;
import org.naukma.buonjourneyserver.dto.updateDto.UpdateItemPackedDto;

import java.util.List;

public interface IItemService {
    ItemDto addItem(ItemCreateDto itemCreateDto);

    void removeItem(Long itemId);

    ItemDto updateItemName(UpdateItemNameDto itemDto);

    ItemDto updateItemPacked(UpdateItemPackedDto itemDto);

    List<ItemDto> getAllByByPackingListId(Long packingListId);

    ItemDto getItemById(Long itemId);
}
