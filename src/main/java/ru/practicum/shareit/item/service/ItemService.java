package ru.practicum.shareit.item.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.service.UserService;

import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final ItemMapper mapper;

    private final UserService userService;

    @Autowired
    public ItemService(@Qualifier("ItemRepositoryImpl") ItemRepository itemRepository, ItemMapper itemMapper, UserService userService) {
        this.itemRepository = itemRepository;
        this.mapper = itemMapper;
        this.userService = userService;
    }

    public ItemDto create(ItemDto itemDto, Long ownerId) {
        ItemDto newItemDto = null;
        if (userService.getUserById(ownerId) != null) {
            newItemDto = mapper.toItemDto(itemRepository.create(mapper.toItem(itemDto, ownerId)));
        }
        return newItemDto;
    }

    public ItemDto update(ItemDto itemDto, Long ownerId, Long itemId) {
        if (itemDto.getName() == null) {
            itemDto.setName(itemRepository.getItemById(itemId).getName());
        }
        if (itemDto.getDescription() == null) {
            itemDto.setDescription(itemRepository.getItemById(itemId).getDescription());
        }
        if (itemDto.getAvailable() == null) {
            itemDto.setAvailable(itemRepository.getItemById(itemId).getAvailable());
        }
        if (userService.getUserById(ownerId) == null) {
            throw new NotFoundException("User with ID = " + ownerId + " not found.");
        }
        if (itemDto.getId() == null) {
            itemDto.setId(itemId);
        }
        Item oldItem = itemRepository.getItemById(itemId);
        if (!oldItem.getOwnerId().equals(ownerId)) {
            throw new NotFoundException("User have no such item.");
        }
        return mapper.toItemDto(itemRepository.update(mapper.toItem(itemDto, ownerId)));
    }

    public ItemDto delete(Long itemId, Long ownerId) {
        Item item = itemRepository.getItemById(itemId);
        if (!item.getOwnerId().equals(ownerId)) {
            throw new NotFoundException("User have no such item.");
        }
        return mapper.toItemDto(itemRepository.delete(itemId));
    }

    public List<ItemDto> getItemsByOwner(Long ownerId) {
        return itemRepository
                .getItemsByOwner(ownerId)
                .stream()
                .map(mapper::toItemDto)
                .collect(toList());
    }

    public List<ItemDto> getItemsBySearchQuery(String text) {
        if (text == null || text.isBlank()) {
            return Collections.emptyList();
        }
        text = text.toLowerCase();
        return itemRepository.getItemsBySearchQuery(text)
                .stream()
                .map(mapper::toItemDto)
                .collect(toList());
    }

    public ItemDto getItemById(Long itemId) {
        return mapper.toItemDto(itemRepository.getItemById(itemId));
    }

    public void deleteItemsByOwner(Long ownerId) {
        itemRepository.deleteItemsByOwner(ownerId);
    }
}