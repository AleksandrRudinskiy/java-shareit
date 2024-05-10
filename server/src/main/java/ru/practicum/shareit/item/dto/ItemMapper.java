package ru.practicum.shareit.item.dto;


import ru.practicum.shareit.booking.dto.BookingInfo;
import ru.practicum.shareit.comment.dto.CommentAuthorNameDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.model.User;

import java.util.List;

public class ItemMapper {

    public static ItemDto convertItemToDto(Item item) {
        return new ItemDto(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getAvailable(),
                item.getOwner().getId(),
                item.getRequest() != null ? item.getRequest().getId() : 0
        );
    }

    public static Item convertDtoToItem(ItemDto itemDto, User user, ItemRequest request) {
        return new Item(itemDto.getId(),
                itemDto.getName(),
                itemDto.getDescription(),
                itemDto.getAvailable(),
                user,
                request

        );
    }

    public static ItemDatesDto convertItemToItemDatesDto(Item item, BookingInfo lastBooking, BookingInfo nextBooking) {
        return new ItemDatesDto(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getAvailable(),
                item.getOwner().getId(),
                null,
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
                0,
                itemDatesDto.getLastBooking(),
                itemDatesDto.getNextBooking(),
                comments
        );
    }

}
