package ru.practicum.shareit.request.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.mapper.ItemRequestMapper;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.repository.ItemRequestRepository;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.service.UserServiceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemRequestServiceImpl implements ItemRequestService {
    private final ItemRequestRepository requestRepository;
    private final UserServiceImpl userService;
    private final ItemRepository itemRepository;

    @Override
    @Transactional
    public ItemRequestDto create(ItemRequestDto itemRequestDto, Long userId) {
        ItemRequest itemRequest = ItemRequest.builder()
                .description(itemRequestDto.getDescription())
                .requester(UserMapper.toUser(userService.findUserById(userId)))
                .created(LocalDateTime.now())
                .build();
        return ItemRequestMapper.toItemRequestDto(requestRepository.save(itemRequest));
    }

    @Override
    @Transactional
    public ItemRequestDto findById(Long userId, Long requestId) {
        ItemRequest itemRequest = requestRepository.findById(requestId)
                .orElseThrow(() -> new NotFoundException(String.format("Request with id = %d not found.", requestId)));
        itemRequest.setItems(itemRepository.findAllByItemRequest(itemRequest));
        ItemRequestDto itemRequestDto = ItemRequestMapper.toItemRequestDto(itemRequest);
        itemRequestDto.setRequester(userService.findUserById(userId));
        return itemRequestDto;
    }

    @Override
    @Transactional
    public List<ItemRequestDto> findRequests(Long userId, int from, int size) {
        UserMapper.toUser(userService.findUserById(userId));
        Pageable page = PageRequest.of(from / size, size, Sort.by("created"));
        List<ItemRequestDto> list = new ArrayList<>();
        List<ItemRequest> findAllByRequesterIdIsNot = requestRepository.findByRequesterIdIsNot(userId, page);
        findAllByRequesterIdIsNot.forEach(itemRequest -> {
            itemRequest.setItems(itemRepository.findAllByItemRequest(itemRequest));
            ItemRequestDto itemRequestDto = ItemRequestMapper.toItemRequestDto(itemRequest);
            list.add(itemRequestDto);
        });
        return list;

    }

    @Override
    @Transactional
    public List<ItemRequestDto> findUserRequests(Long userId) {
        userService.findUserById(userId);
        List<ItemRequestDto> list = new ArrayList<>();
        List<ItemRequest> findAllByRequesterIdOrderByCreatedDesc = requestRepository.findByRequesterIdOrderByCreatedDesc(userId);
        findAllByRequesterIdOrderByCreatedDesc.forEach(i -> {
            i.setItems(itemRepository.findAllByItemRequest(i));
            ItemRequestDto itemRequestDto = ItemRequestMapper.toItemRequestDto(i);
            list.add(itemRequestDto);
        });
        return list;
    }
}