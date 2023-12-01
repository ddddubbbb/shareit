package ru.practicum.shareit.item.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.shareit.item.comment.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.service.ItemService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/items")
@AllArgsConstructor
@Slf4j
public class ItemController {
    private static final String OWNER_ID_HEADER = "X-Sharer-User-Id";
    private final ItemService itemService;

    @PostMapping
    public ItemDto create(@RequestHeader(OWNER_ID_HEADER) Long userId, @Valid @RequestBody ItemDto itemDto) {
        log.info("Received a POST-request to the endpoint: '/items' to add an item by the owner with ID = {}", userId);
        return itemService.create(userId, itemDto);
    }

    @GetMapping("/{itemId}")
    public ItemDto findById(@RequestHeader(OWNER_ID_HEADER) Long userId, @PathVariable Long itemId) {
        log.info("Received a GET-request to the endpoint: '/items' to get an item with ID = {}", itemId);
        return itemService.findItemById(itemId, userId);
    }

    @GetMapping
    public List<ItemDto> findAll(@RequestHeader(OWNER_ID_HEADER) Long userId,
                                 @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                 @Positive @RequestParam(defaultValue = "10") Integer size) {
        log.info("Received a GET-request to the endpoint: '/items' to get all items of owner with ID = {}", userId);
        return itemService.findUserItems(userId, from, size);
    }

    @PatchMapping("/{itemId}")
    public ItemDto save(@RequestHeader(OWNER_ID_HEADER) Long userId, @PathVariable long itemId,
                        @RequestBody ItemDto itemDto) {
        log.info("Received a PATCH-request to the endpoint: '/items' to update item with ID = {}", itemId);
        return itemService.save(itemDto, itemId, userId);
    }

    @DeleteMapping("/{itemId}")
    public void delete(@PathVariable Long itemId) {
        log.info("Received a DELETE-request to the endpoint: '/items' to delete item with ID = {}", itemId);
        itemService.deleteById(itemId);
    }

    @GetMapping("/search")
    public Collection<ItemDto> search(@RequestParam String text,
                                      @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                      @Positive @RequestParam(defaultValue = "10") Integer size) {
        log.info("Received a GET-request to the endpoint: '/items/search' to search item with text = {}", text);
        return itemService.search(text, from, size);
    }

    @PostMapping("/{itemId}/comment")
    public CommentDto createComment(@RequestHeader(OWNER_ID_HEADER) Long userId,
                                    @PathVariable Long itemId,
                                    @Valid @RequestBody CommentDto commentDto) {
        log.info("Received a POST-request to the endpoint: '/items/{itemId}/comment' to add a comment");
        return itemService.addComment(itemId, userId, commentDto);
    }
}