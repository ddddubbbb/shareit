package ru.practicum.shareit.request.service;

import ru.practicum.shareit.request.dto.ItemRequestDto;

import java.util.List;

public interface ItemRequestService {
    ItemRequestDto create(ItemRequestDto itemRequestDto, Long userId);

    ItemRequestDto findById(Long userId, Long requestId);

    List<ItemRequestDto> findRequests(Long userId, int from, int size);

    List<ItemRequestDto> findUserRequests(Long userId);
}
