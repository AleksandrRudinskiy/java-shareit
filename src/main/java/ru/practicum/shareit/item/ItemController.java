package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
@Slf4j
public class ItemController {
    private final ItemService itemService;

    @GetMapping
    public List<ItemDto> getUserItems(@RequestHeader(value = "X-Sharer-User-Id") long userId) {
        log.info("GET-запрос на получение всех вещей пользователя c id {}", userId);
        return itemService.getUserItems(userId);
    }

    @GetMapping("/{itemId}")
    public ItemDto getItemById(@RequestHeader(value = "X-Sharer-User-Id") long userId,
                               @PathVariable long itemId) {
        log.info("GET-запрос на получение вещи по id {}", itemId);
        return itemService.getItemById(itemId);
    }

    @GetMapping("/search")
    public List<ItemDto> searchItems(@RequestHeader(value = "X-Sharer-User-Id") long userId,
                                     @RequestParam String text) {
        log.info("GET запрос search с параметром {}", text);
        return itemService.search(userId, text);
    }

    @PatchMapping("/{itemId}")
    public ItemDto patchItem(@RequestHeader(value = "X-Sharer-User-Id") long userId,
                             @PathVariable long itemId, @RequestBody ItemDto itemDto) {
        log.info("PATCH-запрос обновление вещи с id {}", itemId);
        return itemService.update(userId, itemId, itemDto);
    }

    @PostMapping
    public ItemDto add(@RequestHeader(value = "X-Sharer-User-Id") long userId,
                       @RequestBody ItemDto itemDto) {
        log.info("POST-запрос на доавление вещи пользователя с userId {}", userId);
        return itemService.addItem(userId, itemDto);
    }

    @DeleteMapping("/{itemId}")
    public void deleteItem(@PathVariable long itemId) {
        log.info("DELETE-запрос на удаление вещи с itemId {}", itemId);
        itemService.deleteItem(itemId);
    }

}
