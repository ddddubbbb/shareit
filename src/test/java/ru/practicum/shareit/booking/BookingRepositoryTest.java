package ru.practicum.shareit.booking;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class BookingRepositoryTest {

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private UserRepository userRepository;

    Item item;
    User booker;
    User owner;

    @BeforeEach
    void init() {

        booker = userRepository.save(User.builder()
                .name("booker")
                .email("booker@mail.ru")
                .build());


        owner = userRepository.save(User.builder()
                .name("owner")
                .email("owner@mail.ru")
                .build());


        item = itemRepository.save(Item.builder()
                .name("itemB")
                .description("description")
                .available(true)
                .ownerId(owner.getId())
                .build());
    }

    @Test
    public void testFindByBookerIdIsEmpty() {
        Long bookerId = booker.getId();

        List<Booking> bookings = bookingRepository.findByBookerIdOrderByStartDesc(bookerId, PageRequest.of(0, 10, Sort.unsorted()));

        assertNotNull(bookings);
        assertTrue(bookings.isEmpty());
    }

    @Test
    public void testFindByItemOwnerIdIsEmpty() {
        Long ownerId = owner.getId();

        List<Booking> bookings = bookingRepository.findByItemOwnerId(ownerId, PageRequest.of(0, 10, Sort.unsorted()));

        assertNotNull(bookings);
        assertTrue(bookings.isEmpty());
    }

    @Test
    public void testFindByBookerIdAndStartIsAfterAndStatusIs() {
        LocalDateTime now = LocalDateTime.now();

        Booking booking1 = Booking.builder()
                .id(1L)
                .booker(booker)
                .item(item)
                .start(now.minusMinutes(30))
                .status(BookingStatus.APPROVED)
                .end(now)
                .build();

        bookingRepository.save(booking1);

        Booking booking2 = Booking.builder()
                .id(2L)
                .booker(booker)
                .item(item)
                .status(BookingStatus.WAITING)
                .start(now.plusMinutes(10))
                .end(now.plusMinutes(100))
                .build();

        bookingRepository.save(booking2);

        List<Booking> testBookings = bookingRepository.findByBookerIdAndStartIsAfterAndStatusIsOrderByStartDesc(
                booker.getId(), now, BookingStatus.WAITING, PageRequest.of(0, 10, Sort.unsorted()));

        int expectedSizeList = 1;

        assertNotNull(testBookings);
        assertFalse(testBookings.isEmpty());
        assertEquals(expectedSizeList, testBookings.size());
    }

    @AfterEach
    void deleteAllAfterEach() {
        userRepository.deleteAll();
        itemRepository.deleteAll();
        bookingRepository.deleteAll();
    }
}