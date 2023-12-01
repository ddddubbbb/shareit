package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.comment.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

public interface ItemService {
    ItemDto create(Long userId, ItemDto itemDto);

    ItemDto findItemById(Long itemId, Long userId);

    List<ItemDto> findUserItems(Long userId, Integer from, Integer size);

    ItemDto save(ItemDto itemDto, Long itemId, Long userId);

    ItemDto updateBookings(ItemDto itemDto);

    void deleteById(Long itemId);

    List<ItemDto> search(String text, Integer from, Integer size);

    Long findOwnerId(Long itemId);

    CommentDto addComment(Long itemId, Long userId, CommentDto commentDto);
}