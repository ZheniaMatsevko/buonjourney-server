package org.naukma.buonjourneyserver.service.implementation;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.naukma.buonjourneyserver.dto.ItemDto;
import org.naukma.buonjourneyserver.dto.PackingListDto;
import org.naukma.buonjourneyserver.dto.TripDto;
import org.naukma.buonjourneyserver.dto.createDto.ItemCreateDto;
import org.naukma.buonjourneyserver.dto.createDto.PackingListCreateDto;
import org.naukma.buonjourneyserver.dto.updateDto.UpdateItemNameDto;
import org.naukma.buonjourneyserver.dto.updateDto.UpdateItemPackedDto;
import org.naukma.buonjourneyserver.entity.ItemEntity;
import org.naukma.buonjourneyserver.entity.PackingListEntity;
import org.naukma.buonjourneyserver.mapper.IItemMapper;
import org.naukma.buonjourneyserver.mapper.IPackingListMapper;
import org.naukma.buonjourneyserver.mapper.ITripMapper;
import org.naukma.buonjourneyserver.repository.IItemRepository;
import org.naukma.buonjourneyserver.repository.IPackingListRepository;
import org.naukma.buonjourneyserver.service.IItemService;
import org.naukma.buonjourneyserver.service.IPackingListService;
import org.naukma.buonjourneyserver.service.ITripService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PackingListService implements IPackingListService, IItemService {
    private final IPackingListRepository packingListRepository;
    private final ITripService tripService;
    private final IItemRepository itemRepository;

    @Override
    public List<PackingListDto> getAllByTripId(Long tripId) {
        List<PackingListEntity> packingListEntities = packingListRepository.findAllByTripId(tripId);
        return packingListEntities.stream().map(IPackingListMapper.INSTANCE::entityToDto).toList();
    }

    @Override
    @Transactional
    public PackingListDto createPackingList(PackingListCreateDto packingListCreateDto) throws EntityNotFoundException {
        log.info("Creating packing list");

        TripDto tripDto = tripService.getTripById(packingListCreateDto.getTripId());

        PackingListEntity listToCreate = IPackingListMapper.INSTANCE.createDtoToEntity(packingListCreateDto);
        listToCreate.setTrip(ITripMapper.INSTANCE.dtoToEntity(tripDto));
        listToCreate.setItems(new ArrayList<>());

        PackingListEntity createdList = packingListRepository.save(listToCreate);
        PackingListDto resultList = IPackingListMapper.INSTANCE.entityToDto(createdList);

        for (ItemDto itemDto : packingListCreateDto.getItems()) {
            resultList.getItems().add(this.addItem(itemDto, createdList));
        }

        log.info("Packing list created successfully.");

        return resultList;
    }

    @Override
    public PackingListDto createPackingList(PackingListDto packingListDto, TripDto tripDto) {
        PackingListEntity listToCreate = IPackingListMapper.INSTANCE.dtoToEntity(packingListDto);
        listToCreate.setTrip(ITripMapper.INSTANCE.dtoToEntity(tripDto));
        listToCreate.setItems(new ArrayList<>());

        PackingListEntity createdList = packingListRepository.save(listToCreate);
        PackingListDto resultList = IPackingListMapper.INSTANCE.entityToDto(createdList);

        for (ItemDto itemDto : packingListDto.getItems()) {
            resultList.getItems().add(this.addItem(itemDto, createdList));
        }

        log.info("Packing list created successfully.");

        return resultList;
    }

    @Transactional
    @Override
    public PackingListDto updatePackingList(PackingListDto packingList) throws EntityNotFoundException {
        Optional<PackingListEntity> optional = packingListRepository.findById(packingList.getId());
        if (optional.isPresent()) {
            PackingListEntity packingListEntity = optional.get();

            packingListEntity.setName(packingList.getName() == null ? packingListEntity.getName() : packingList.getName());

            PackingListEntity editedList = packingListRepository.save(packingListEntity);
            log.info("Updated packing list with id " + editedList.getId());

            return IPackingListMapper.INSTANCE.entityToDto(editedList);
        } else {
            throw new EntityNotFoundException("Packing list not found for editing");
        }
    }

    @Override
    public void deletePackingList(Long packingListId) throws EntityNotFoundException {
        if (packingListRepository.existsById(packingListId)) {
            packingListRepository.deleteById(packingListId);
            log.info("Deleted packing list with ID: {}", packingListId);
        } else {
            throw new EntityNotFoundException("Packing list not found for deletion");
        }
    }

    @Override
    public PackingListDto getPackingListById(Long packingListId) throws EntityNotFoundException {
        PackingListEntity packingListEntity = packingListRepository.findById(packingListId).orElse(null);
        if (packingListEntity != null) {
            log.info("Retrieved packing list with ID: {}", packingListEntity.getId());
        } else {
            log.warn("Packing list not found with ID: {}", packingListId);
            throw new EntityNotFoundException("Packing list with id " + packingListId + " not found");
        }
        return IPackingListMapper.INSTANCE.entityToDto(packingListEntity);
    }

    @Override
    @Transactional
    public ItemDto addItem(ItemCreateDto itemCreateDto) throws EntityNotFoundException {
        log.info("Creating item");

        PackingListDto packingList = this.getPackingListById(itemCreateDto.getPackingListId());

        ItemEntity itemToCreate = IItemMapper.INSTANCE.createDtoToEntity(itemCreateDto);
        itemToCreate.setPackingList(IPackingListMapper.INSTANCE.dtoToEntity(packingList));

        ItemEntity createdItem = itemRepository.save(itemToCreate);

        log.info("Item created successfully.");
        return IItemMapper.INSTANCE.entityToDto(createdItem);
    }

    private ItemDto addItem(ItemDto itemDto, PackingListEntity packingListEntity) {
        log.info("Creating item");

        ItemEntity itemToCreate = IItemMapper.INSTANCE.dtoToEntity(itemDto);
        itemToCreate.setPackingList(packingListEntity);

        ItemEntity createdItem = itemRepository.save(itemToCreate);

        log.info("Item created successfully.");
        return IItemMapper.INSTANCE.entityToDto(createdItem);
    }

    @Override
    public void removeItem(Long itemId) throws EntityNotFoundException {
        if (itemRepository.existsById(itemId)) {
            itemRepository.deleteById(itemId);
            log.info("Deleted item with ID: {}", itemId);
        } else {
            throw new EntityNotFoundException("Item not found for deletion");
        }
    }

    @Override
    public ItemDto updateItemName(UpdateItemNameDto itemDto) throws EntityNotFoundException {
        Optional<ItemEntity> optional = itemRepository.findById(itemDto.getId());
        if (optional.isPresent()) {
            ItemEntity itemEntity = optional.get();
            itemEntity.setName(itemDto.getName());

            ItemEntity editedItem = itemRepository.save(itemEntity);
            log.info("Updated item with id " + editedItem.getId());

            return IItemMapper.INSTANCE.entityToDto(editedItem);
        } else {
            throw new EntityNotFoundException("Item not found for editing");
        }
    }

    @Override
    public ItemDto updateItemPacked(UpdateItemPackedDto itemDto) throws EntityNotFoundException {
        Optional<ItemEntity> optional = itemRepository.findById(itemDto.getId());
        if (optional.isPresent()) {
            ItemEntity itemEntity = optional.get();
            itemEntity.setPacked(itemDto.isPacked());

            ItemEntity editedItem = itemRepository.save(itemEntity);
            log.info("Updated item with id " + editedItem.getId());

            return IItemMapper.INSTANCE.entityToDto(editedItem);
        } else {
            throw new EntityNotFoundException("Item not found for editing");
        }
    }

    @Override
    public List<ItemDto> getAllByByPackingListId(Long packingListId) throws EntityNotFoundException {
        PackingListDto packingList = this.getPackingListById(packingListId);
        return packingList.getItems();
    }

    @Override
    public ItemDto getItemById(Long itemId) throws EntityNotFoundException {
        ItemEntity itemEntity = itemRepository.findById(itemId).orElse(null);
        if (itemEntity != null) {
            log.info("Retrieved item with ID: {}", itemEntity.getId());
        } else {
            log.warn("Item not found with ID: {}", itemId);
            throw new EntityNotFoundException("Item with id " + itemId + " not found");
        }
        return IItemMapper.INSTANCE.entityToDto(itemEntity);
    }
}
