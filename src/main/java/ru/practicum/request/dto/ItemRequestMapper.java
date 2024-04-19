package ru.practicum.request.dto;


import lombok.experimental.UtilityClass;
import ru.practicum.item.ItemInfo;
import ru.practicum.item.model.Item;
import ru.practicum.request.model.ItemRequest;
import ru.practicum.user.model.User;

import java.util.List;

@UtilityClass
public class ItemRequestMapper {

    public static ItemRequestDto convertToItemRequestDto(ItemRequest request) {
        return new ItemRequestDto(
                request.getId(),
                request.getDescription(),
                request.getRequestor().getId(),
                request.getCreated()
        );
    }

    public static ItemRequest convertToItemRequest(ItemRequestDto requestDto, User requestor) {
        return new ItemRequest(
                requestDto.getId(),
                requestDto.getDescription(),
                requestor,
                requestDto.getCreated()
        );
    }

    public static ItemRequestWithItemsDto convertToItemRequestWithItemsDto(ItemRequest request, List<ItemInfo> items) {
        return new ItemRequestWithItemsDto(
                request.getId(),
                request.getDescription(),
                request.getRequestor().getId(),
                request.getCreated(),
                items
        );
    }
}
