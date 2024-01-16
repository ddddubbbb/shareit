package ru.practicum.shareit.booking;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.shareit.booking.dto.OutputBookingDto;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class BookingDtoTest {
    @Autowired
    JacksonTester<OutputBookingDto> json;

    @Test
    void testBookingDto() throws Exception {
        OutputBookingDto bookingDto = OutputBookingDto
                .builder()
                .id(1L)
                .start(LocalDateTime.of(2022, 12, 12, 10, 10, 1))
                .end(LocalDateTime.of(2022, 12, 20, 10, 10, 1))
                .build();

        JsonContent<OutputBookingDto> result = json.write(bookingDto);

        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        assertThat(result).extractingJsonPathStringValue("$.start")
                .isEqualTo(LocalDateTime.of(2022, 12, 12, 10, 10, 1).toString());
        assertThat(result).extractingJsonPathStringValue("$.end")
                .isEqualTo(LocalDateTime.of(2022, 12, 20, 10, 10, 1).toString());
    }
}