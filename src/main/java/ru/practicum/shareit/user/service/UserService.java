package ru.practicum.shareit.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.AlreadyExistsException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper mapper;

    @Autowired
    public UserService(@Qualifier("UserRepositoryImpl") UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.mapper = userMapper;
    }

    public UserDto create(UserDto userDto) {
        User user = mapper.toUser(userDto);
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new ValidationException("User email can not be empty.");
        }
        if (user.getName() == null || user.getName().isBlank()) {
            throw new ValidationException("User name can not be empty.");
        }
        Long idFromDbByEmail = userRepository.getUserIdByEmail(user.getEmail());
        if (idFromDbByEmail != null) {
            throw new AlreadyExistsException("User with e-mail = " + user.getEmail() + " already exists.");
        }
        return mapper.toUserDto(userRepository.create(user));
    }

    public UserDto update(UserDto userDto, Long id) {
        userDto.setId(id);
        if (userDto.getName() == null) {
            userDto.setName(userRepository.getUserById(id).getName());
        }
        if (userDto.getEmail() == null) {
            userDto.setEmail(userRepository.getUserById(id).getEmail());
        }
        User user = mapper.toUser(userDto);
        if (userRepository.getUserById(user.getId()) == null) {
            throw new NotFoundException("User with ID = " + user.getId() + " not found.");
        }
        if (user.getId() == null) {
            throw new ValidationException("User ID can not be empty.");
        }
        final Long idFromDbByEmail = userRepository.getUserIdByEmail(user.getEmail());
        if (idFromDbByEmail != null && !user.getId().equals(idFromDbByEmail)) {
            throw new AlreadyExistsException("User with e-mail=" + user.getEmail() + " already exists.");
        }
        User updateUser = userRepository.update(user);
        return mapper.toUserDto(updateUser);
    }

    public UserDto delete(Long userId) {
        if (userId == null) {
            throw new ValidationException("User ID can not be empty.");
        }
        if (!userRepository.isExistUserInDb(userId)) {
            throw new NotFoundException("User with ID = " + userId + " not found.");
        }
        return mapper.toUserDto(userRepository.delete(userId));
    }

    public List<UserDto> getUsers() {
        return userRepository.getUsers().stream()
                .map(mapper::toUserDto)
                .collect(toList());
    }

    public UserDto getUserById(Long id) {
        User user = userRepository.getUserById(id);
        if (user == null) {
            throw new NotFoundException("User with ID = " + id + " not found.");
        }
        return mapper.toUserDto(userRepository.getUserById(id));
    }
}