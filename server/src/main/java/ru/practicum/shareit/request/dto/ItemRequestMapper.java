package ru.practicum.shareit.request.dto;


import lombok.experimental.UtilityClass;
import ru.practicum.shareit.item.ItemInfo;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@UtilityClass
public class ItemRequestMapper {
    private final DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

    public static ItemRequestDto convertToItemRequestDto(ItemRequest request) {
        return new ItemRequestDto(
                request.getId(),
                request.getDescription(),
                request.getRequestor().getId(),
                request.getCreated().toString()
        );
    }

    public static ItemRequest convertToItemRequest(ItemRequestDto requestDto, User requestor) {
        return new ItemRequest(
                requestDto.getId(),
                requestDto.getDescription(),
                requestor,
                LocalDateTime.parse(requestDto.getCreated())
        );
    }

    public static ItemRequestWithItemsDto convertToItemRequestWithItemsDto(ItemRequest request, List<ItemInfo> items) {
        return new ItemRequestWithItemsDto(
                request.getId(),
                request.getDescription(),
                request.getRequestor().getId(),
                request.getCreated().format(formatter),
                items
        );
    }
}
