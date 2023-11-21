package ru.practicum.shareit.item;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.service.ItemService;

import javax.validation.Valid;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/items")
public class ItemController {
    private static final String OWNER_ID_HEADER = "X-Sharer-User-Id";
    private ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @ResponseBody
    @PostMapping
    public ItemDto create(@Valid @RequestBody ItemDto itemDto, @RequestHeader(OWNER_ID_HEADER) Long ownerId) {
        log.info("Received a POST-request to the endpoint: '/items' to add an item by the owner with ID = {}", ownerId);
        return itemService.create(itemDto, ownerId);
    }

    @ResponseBody
    @PatchMapping("/{itemId}")
    public ItemDto update(@RequestBody ItemDto itemDto, @PathVariable Long itemId, @RequestHeader(OWNER_ID_HEADER) Long ownerId) {
        log.info("Received a PATCH-request to the endpoint: '/items' to update item with ID = {}", itemId);
        return itemService.update(itemDto, ownerId, itemId);
    }

    @DeleteMapping("/{itemId}")
    public ItemDto delete(@PathVariable Long itemId, @RequestHeader(OWNER_ID_HEADER) Long ownerId) {
        log.info("Received a DELETE-request to the endpoint: '/items' to delete item with ID = {}", itemId);
        return itemService.delete(itemId, ownerId);
    }

    @GetMapping
    public List<ItemDto> getItemsByOwner(@RequestHeader(OWNER_ID_HEADER) Long ownerId) {
        log.info("Received a GET-request to the endpoint: '/items' to get all items of owner with ID = {}", ownerId);
        return itemService.getItemsByOwner(ownerId);
    }

    @GetMapping("/search")
    public List<ItemDto> getItemsBySearchQuery(@RequestParam String text) {
        log.info("Received a GET-request to the endpoint: '/items/search' to search item with text = {}", text);
        return itemService.getItemsBySearchQuery(text);
    }

    @GetMapping("/{itemId}")
    public ItemDto getItemById(@PathVariable Long itemId) {
        log.info("Received a GET-request to the endpoint: '/items' to get an item with ID = {}", itemId);
        return itemService.getItemById(itemId);
    }
}