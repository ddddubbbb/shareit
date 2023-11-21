package ru.practicum.shareit.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.service.UserService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/users")
public class UserController {
    private UserService userService;
    private ItemService itemService;

    @Autowired
    public UserController(UserService userService, ItemService itemService) {
        this.userService = userService;
        this.itemService = itemService;
    }

    @ResponseBody
    @PostMapping
    public UserDto create(@Valid @RequestBody UserDto userDto) {
        log.info("Received a POST-request to the endpoint: '/users' to add user.");
        return userService.create(userDto);
    }

    @ResponseBody
    @PatchMapping("/{userId}")
    public UserDto update(@RequestBody UserDto userDto, @PathVariable Long userId) {
        log.info("Received a PATCH-request to the endpoint: '/users' to update user with ID = {}", userId);
        return userService.update(userDto, userId);
    }

    @DeleteMapping("/{userId}")
    public UserDto delete(@PathVariable Long userId) {
        log.info("Received a DELETE-request to the endpoint: '/users' to delete user with ID = {}", userId);
        UserDto userDto = userService.delete(userId);
        itemService.deleteItemsByOwner(userId);
        return userDto;
    }

    @GetMapping
    public List<UserDto> getUsers() {
        log.info("Received a GET-request to the endpoint: '/users' to get all users");
        return userService.getUsers();
    }

    @GetMapping("/{userId}")
    public UserDto getUserById(@PathVariable Long userId) {
        log.info("Received a GET-request to the endpoint: '/users' to get user with ID = {}", userId);
        return userService.getUserById(userId);
    }
}