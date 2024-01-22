package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.shareit.Marker;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Collections;

@Controller
@RequestMapping(path = "/items")
@RequiredArgsConstructor
@Validated
public class ItemController {
    private static final String OWNER_ID_HEADER = "X-Sharer-User-Id";
    private final ItemClient itemClient;

    @PostMapping
    public ResponseEntity<Object> createItem(@RequestHeader(OWNER_ID_HEADER) Long userId,
                                             @Validated({Marker.OnCreate.class}) @RequestBody ItemDto itemDto) {
        return itemClient.createItem(userId, itemDto);
    }

    @PatchMapping("{itemId}")
    public ResponseEntity<Object> updateItem(@RequestHeader(OWNER_ID_HEADER) Long userId,
                                             @Validated({Marker.OnUpdate.class}) @RequestBody ItemDto itemDto,
                                             @PathVariable Long itemId) {
        return itemClient.updateItem(itemDto, itemId, userId);
    }

    @GetMapping("{itemId}")
    public ResponseEntity<Object> getItemByItemId(@RequestHeader(OWNER_ID_HEADER) Long userId,
                                                  @PathVariable Long itemId) {
        return itemClient.getItemById(itemId, userId);
    }

    @GetMapping
    public ResponseEntity<Object> getAllUsersItems(@RequestHeader(OWNER_ID_HEADER) Long userId,
                                                   @PositiveOrZero @RequestParam(value = "from", defaultValue = "0") Integer from,
                                                   @Positive @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return itemClient.getAllUsersItems(userId, from, size);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> searchItems(@RequestHeader(OWNER_ID_HEADER) Long userId,
                                              @RequestParam String text,
                                              @PositiveOrZero @RequestParam(value = "from", defaultValue = "0") Integer from,
                                              @Positive @RequestParam(value = "size", defaultValue = "10") Integer size) {
        if (text.isBlank()) {
            return ResponseEntity.ok(Collections.emptyList());
        }
        return itemClient.searchItems(userId, text, from, size);
    }

    @PostMapping("/{itemId}/comment")
    public ResponseEntity<Object> addComment(@PathVariable Long itemId,
                                             @RequestHeader(OWNER_ID_HEADER) Long userId,
                                             @Valid @RequestBody CommentDto commentDto) {
        return itemClient.addComment(itemId, userId, commentDto);
    }
}