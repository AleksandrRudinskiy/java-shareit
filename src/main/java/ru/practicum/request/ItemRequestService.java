package ru.practicum.request;


import ru.practicum.request.dto.ItemRequestDto;
import ru.practicum.request.dto.ItemRequestWithItemsDto;

import java.util.List;

public interface ItemRequestService {

    ItemRequestDto addRequest(long requestorId, ItemRequestDto requestDto);

    List<ItemRequestWithItemsDto> getRequestsByRequestorId(long userId);

    ItemRequestWithItemsDto getRequestById(long userId, long id);

    List<ItemRequestWithItemsDto> getAllRequests(long userId, int from, int size);

    void deleteRequestById(long id);

}
