package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestWithItemsDto;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
@Slf4j
public class ItemRequestController {
    private final ItemRequestService requestService;


    @PostMapping
    public ItemRequestDto addRequest(@RequestHeader(value = "X-Sharer-User-Id") long requestorId,
                                     @RequestBody @Valid ItemRequestDto request) {
        return requestService.addRequest(requestorId, request);
    }

    @GetMapping
    public List<ItemRequestWithItemsDto> getUserRequests(@RequestHeader(value = "X-Sharer-User-Id") long userId) {
        return requestService.getRequestsByRequestorId(userId);
    }

    @GetMapping("/{id}")
    public ItemRequestWithItemsDto getRequestById(@RequestHeader(value = "X-Sharer-User-Id") long userId,
                                                  @PathVariable long id) {
        log.info("GET-запрос на получение данных о запросе: id = {}", id);
        return requestService.getRequestById(userId, id);
    }

    @GetMapping("/all")
    public List<ItemRequestWithItemsDto> getAllRequests(@RequestHeader(value = "X-Sharer-User-Id") long userId,
                                                        @RequestParam(defaultValue = "0") int from,
                                                        @RequestParam(defaultValue = "10") int size) {
        return requestService.getAllRequests(userId, from, size);
    }

    @DeleteMapping("/{id}")
    public void deleteRequest(@PathVariable long id) {
        requestService.deleteRequestById(id);
    }


}
