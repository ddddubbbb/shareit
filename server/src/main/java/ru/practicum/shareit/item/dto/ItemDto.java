package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.booking.dto.ShortItemBookingDto;
import ru.practicum.shareit.item.comment.dto.CommentDto;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemDto {
    private Long id;
    private String name;
    private String description;
    private Boolean available;
    private ShortItemBookingDto lastBooking;
    private ShortItemBookingDto nextBooking;
    private List<CommentDto> comments;
    private Long requestId;
}