package ru.practicum.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.request.dto.ItemRequestDto;
import ru.practicum.request.dto.ItemRequestWithItemsDto;

import java.util.List;

@RestController
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
@Slf4j
public class ItemRequestController {
    private final ItemRequestService requestService;


    @PostMapping
    public ItemRequestDto addRequest(@RequestHeader(value = "X-Sharer-User-Id") long requestorId,
                                     @Validated
                                     @RequestBody ItemRequestDto request) {
        return requestService.addRequest(requestorId, request);
    }

    @GetMapping
    public List<ItemRequestWithItemsDto> getUserRequests(@RequestHeader(value = "X-Sharer-User-Id") long userId) {
        return requestService.getByRequestorId(userId);
    }

    @GetMapping("/{id}")
    public ItemRequestWithItemsDto getById(@RequestHeader(value = "X-Sharer-User-Id") long userId,
                                           @PathVariable long id) {
        log.info("GET-запрос на получение данных о запросе: id = {}", id);
        return requestService.getById(userId, id);
    }

    @GetMapping("/all")
    public List<ItemRequestWithItemsDto> getAll(@RequestHeader(value = "X-Sharer-User-Id") long userId,
                                                @RequestParam(defaultValue = "0") int from,
                                                @RequestParam(defaultValue = "10") int size) {
        return requestService.getAll(userId, from, size);
    }


}
