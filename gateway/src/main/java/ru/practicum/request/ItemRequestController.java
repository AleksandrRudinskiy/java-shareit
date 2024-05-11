package ru.practicum.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.request.dto.ItemRequestDto;

import javax.validation.Valid;

@Controller
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
@Slf4j
@Validated
public class ItemRequestController {
    private final ItemRequestClient itemRequestClient;

    @PostMapping
    public ResponseEntity<Object> addRequest(@RequestHeader(value = "X-Sharer-User-Id") long requestorId,
                                             @RequestBody @Valid ItemRequestDto request) {
        return itemRequestClient.addRequest(requestorId, request);
    }

    @GetMapping
    public ResponseEntity<Object> getUserRequests(@RequestHeader(value = "X-Sharer-User-Id") long userId) {
        return itemRequestClient.getRequestsByRequestorId(userId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getRequestById(@RequestHeader(value = "X-Sharer-User-Id") long userId,
                                                 @PathVariable long id) {
        log.info("GET-запрос на получение данных о запросе: id = {}", id);
        return itemRequestClient.getRequestById(userId, id);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllRequests(@RequestHeader(value = "X-Sharer-User-Id") long userId,
                                                 @RequestParam(defaultValue = "0") int from,
                                                 @RequestParam(defaultValue = "10") int size) {
        return itemRequestClient.getAllRequests(userId, from, size);
    }

    @DeleteMapping("/{id}")
    public void deleteRequest(@PathVariable long id) {
        itemRequestClient.deleteRequestById(id);
    }

}
