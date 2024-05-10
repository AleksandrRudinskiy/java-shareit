package ru.practicum.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.booking.dto.BookingInfo;
import ru.practicum.comment.dto.CommentAuthorNameDto;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemWithCommentsDto {
    private long id;
    private String name;
    private String description;
    private Boolean available;
    private long ownerId;
    private long requestId;
    private BookingInfo lastBooking;
    private BookingInfo nextBooking;
    private List<CommentAuthorNameDto> comments;
}

