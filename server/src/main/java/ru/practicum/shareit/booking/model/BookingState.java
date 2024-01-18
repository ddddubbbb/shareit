package ru.practicum.shareit.booking.model;

import java.util.Arrays;

public enum BookingState {
    ALL,
    CURRENT,
    FUTURE,
    WAITING,
    REJECTED,
    PAST;

    public static BookingState from(String bookingState) {
        return Arrays.stream(BookingState.values())
                .filter(value -> value.name().equals(bookingState))
                .findFirst()
                .orElse(null);
    }
}
