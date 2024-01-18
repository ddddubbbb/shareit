package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.shareit.Marker;
import ru.practicum.shareit.request.dto.ItemRequestDto;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Controller
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
@Validated
public class ItemRequestController {
    private static final String OWNER_ID_HEADER = "X-Sharer-User-Id";
    private final ItemRequestClient itemRequestClient;

    @PostMapping
    public ResponseEntity<Object> createRequest(@RequestHeader(OWNER_ID_HEADER) Long userId,
                                                @Validated({Marker.OnCreate.class}) @RequestBody ItemRequestDto itemRequestDto) {
        return itemRequestClient.createRequest(userId, itemRequestDto);
    }

    @GetMapping("{requestId}")
    public ResponseEntity<Object> getRequestById(@RequestHeader(OWNER_ID_HEADER) Long userId,
                                                 @PathVariable Long requestId) {
        return itemRequestClient.getRequestById(userId, requestId);
    }

    @GetMapping
    public ResponseEntity<Object> getAllUserRequest(
            @RequestHeader(OWNER_ID_HEADER) Long userId) {
        return itemRequestClient.getAllUserRequest(userId);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllRequests(
            @RequestHeader(OWNER_ID_HEADER) Long userId,
            @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
            @Positive @RequestParam(defaultValue = "10") Integer size) {
        return itemRequestClient.getAllRequest(userId, from, size);
    }
}