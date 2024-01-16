package ru.practicum.shareit.item;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class ItemRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemRepository itemRepository;

    Item item;

    User user;

    @BeforeEach
    void init() {

        user = userRepository.save(User.builder()
                .name("userI")
                .email("userI@ya.ru")
                .build());

        item = itemRepository.save(Item.builder()
                .name("itemI")
                .description("descriptionI")
                .available(true)
                .ownerId(user.getId())
                .build());
    }

    @Test
    void findAllByOwnerId() {
        Long ownerId = user.getId();

        List<Item> items = itemRepository.findAllByOwnerId(ownerId, PageRequest.of(0, 1));

        assertEquals(items.size(), 1);
        assertEquals(items.get(0).getName(), "itemI");
    }

    @AfterEach
    void deleteAllAfterEach() {
        userRepository.deleteAll();
        itemRepository.deleteAll();
    }
}