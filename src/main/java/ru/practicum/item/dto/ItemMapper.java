package ru.practicum.item.dto;

import ru.practicum.booking.dto.BookingInfo;
import ru.practicum.comment.dto.CommentAuthorNameDto;
import ru.practicum.item.model.Item;
import ru.practicum.user.model.User;

import java.util.List;

public class ItemMapper {

    public static ItemDto convertItemToDto(Item item) {
        return new ItemDto(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getAvailable(),
                item.getOwner().getId(),
                item.getRequestId()
        );
    }

    public static Item convertDtoToItem(ItemDto itemDto, User user) {
        return new Item(itemDto.getId(),
                itemDto.getName(),
                itemDto.getDescription(),
                itemDto.getAvailable(),
                user,
                itemDto.getRequestId()
        );
    }

    public static ItemDatesDto convertItemToItemDatesDto(Item item, BookingInfo lastBooking, BookingInfo nextBooking) {
        return new ItemDatesDto(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getAvailable(),
                item.getOwner().getId(),
                item.getRequestId(),
                lastBooking,
                nextBooking
        );
    }

    public static ItemWithCommentsDto convertToItemWithCommentsDto(ItemDatesDto itemDatesDto,
                                                                   List<CommentAuthorNameDto> comments) {
        return new ItemWithCommentsDto(
                itemDatesDto.getId(),
                itemDatesDto.getName(),
                itemDatesDto.getDescription(),
                itemDatesDto.getAvailable(),
                itemDatesDto.getOwnerId(),
                itemDatesDto.getRequestId(),
                itemDatesDto.getLastBooking(),
                itemDatesDto.getNextBooking(),
                comments
        );
    }

}
