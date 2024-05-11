package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.comment.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDatesDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemWithCommentsDto;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
@Slf4j
public class ItemController {
    private final ItemService itemService;

    @PostMapping
    public ItemDto add(@RequestHeader(value = "X-Sharer-User-Id") long userId,
                       @RequestBody @Valid ItemDto itemDto) {
        log.info("POST-запрос на доавление вещи пользователя с userId {}", userId);
        return itemService.addItem(userId, itemDto);
    }

    @GetMapping
    public List<ItemDatesDto> getUserItems(@RequestHeader(value = "X-Sharer-User-Id") long userId,
                                           @RequestParam(defaultValue = "0") int from,
                                           @RequestParam(defaultValue = "10") int size) {
        log.info("GET-запрос на получение всех вещей пользователя c id {}", userId);
        return itemService.getUserItems(userId, from, size);
    }

    @GetMapping("/{itemId}")
    public ItemWithCommentsDto getItemById(@RequestHeader(value = "X-Sharer-User-Id") long userId,
                                           @PathVariable long itemId) {
        log.info("GET-запрос на получение вещи по id {}", itemId);
        return itemService.getItemById(itemId, userId);
    }

    @GetMapping("/search")
    public List<ItemDto> searchItems(@RequestHeader(value = "X-Sharer-User-Id") long userId,
                                     @RequestParam String text,
                                     @RequestParam(defaultValue = "0") int from,
                                     @RequestParam(defaultValue = "10") int size) {
        log.info("GET-запрос search с параметром {}", text);
        return itemService.search(userId, text, from, size);
    }

    @PatchMapping("/{itemId}")
    public ItemDto patchItem(@RequestHeader(value = "X-Sharer-User-Id") long userId,
                             @PathVariable long itemId, @RequestBody ItemDto itemDto) {
        log.info("PATCH-запрос обновление вещи с id {}", itemId);
        return itemService.update(userId, itemId, itemDto);
    }

    @DeleteMapping("/{itemId}")
    public void deleteItem(@PathVariable long itemId) {
        log.info("DELETE-запрос на удаление вещи с itemId {}", itemId);
        itemService.deleteItem(itemId);
    }

    @PostMapping("/{itemId}/comment")
    public CommentDto addComment(@RequestHeader(value = "X-Sharer-User-Id") long authorId,
                                 @RequestBody @Valid CommentDto commentDto,
                                 @PathVariable long itemId) {
        log.info("POST-запрос на добавление комментария authorId = {}, itemId = {}", authorId, itemId);
        return itemService.addComment(commentDto, authorId, itemId);
    }

}
