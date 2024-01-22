package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.practicum.shareit.Marker;
import ru.practicum.shareit.user.dto.UserDto;

@Controller
@RequestMapping(path = "/users")
@RequiredArgsConstructor
@Validated
public class UserController {
    private final UserClient userClient;

    @PostMapping
    public ResponseEntity<Object> createUser(@Validated({Marker.OnCreate.class}) @RequestBody UserDto userDto) {
        return userClient.createUser(userDto);
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> getUserById(@PathVariable("id") Long userId) {
        return userClient.getUserById(userId);
    }

    @GetMapping
    public ResponseEntity<Object> getUsers() {
        return userClient.getAllUsers();
    }

    @PatchMapping("{id}")
    public ResponseEntity<Object> updateUser(@Validated({Marker.OnUpdate.class}) @RequestBody UserDto userDto,
                                             @PathVariable("id") Long userId) {
        return userClient.updateUser(userDto, userId);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable("id") Long userId) {
        return userClient.deleteUser(userId);
    }
}