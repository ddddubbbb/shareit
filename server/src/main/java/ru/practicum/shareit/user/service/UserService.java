package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto create(UserDto userDto);

    UserDto findUserById(Long userId);

    List<UserDto> findAllUsers();

    UserDto save(UserDto userDto, Long userId);

    void delete(Long userId);
}