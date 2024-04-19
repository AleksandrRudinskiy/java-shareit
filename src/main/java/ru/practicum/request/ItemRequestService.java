package ru.practicum.request;


import ru.practicum.request.dto.ItemRequestDto;
import ru.practicum.request.dto.ItemRequestWithItemsDto;

import java.util.List;

public interface ItemRequestService {

    ItemRequestDto addRequest(long requestorId, ItemRequestDto requestDto);

    List<ItemRequestWithItemsDto> getByRequestorId(long userId);

    ItemRequestWithItemsDto getById(long userId, long id);

    List<ItemRequestWithItemsDto> getAll(long userId, int from, int size);

}
