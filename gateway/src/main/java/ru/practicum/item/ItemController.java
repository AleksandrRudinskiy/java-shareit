package ru.practicum.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.comment.dto.CommentDto;
import ru.practicum.item.dto.ItemDto;

import javax.validation.Valid;

@Controller
@RequestMapping(path = "/items")
@RequiredArgsConstructor
@Slf4j
@Validated
public class ItemController {

    private final ItemClient itemClient;

    @PostMapping
    public ResponseEntity<Object> add(@RequestHeader(value = "X-Sharer-User-Id") long userId,
                                      @RequestBody @Valid ItemDto itemDto) {
        log.info("POST - create item from user with userId {}", userId);
        return itemClient.createItem(userId, itemDto);
    }

    @GetMapping
    public ResponseEntity<Object> getUserItems(@RequestHeader(value = "X-Sharer-User-Id") long userId,
                                               @RequestParam(defaultValue = "0") int from,
                                               @RequestParam(defaultValue = "10") int size) {
        log.info("GET - all users items with userId {}", userId);
        return itemClient.getUserItems(userId, from, size);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<Object> getItemById(@RequestHeader(value = "X-Sharer-User-Id") long userId,
                                              @PathVariable long itemId) {
        log.info("GET - item with ItemId {} from user with userId {}", itemId, userId);
        return itemClient.getItemById(itemId, userId);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> searchItems(@RequestHeader(value = "X-Sharer-User-Id") long userId,
                                              @RequestParam String text,
                                              @RequestParam(defaultValue = "0") int from,
                                              @RequestParam(defaultValue = "10") int size) {
        log.info("GET - search with text: \"{}\"", text);
        return itemClient.search(userId, text, from, size);
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<Object> patchItem(@RequestHeader(value = "X-Sharer-User-Id") long userId,
                                            @PathVariable long itemId, @RequestBody ItemDto itemDto) {
        log.info("PATCH - update item with itemId {} from user with userId {}", itemId, userId);
        return itemClient.updateItem(userId, itemId, itemDto);
    }

    @DeleteMapping("/{itemId}")
    public void deleteItem(@PathVariable long itemId) {
        log.info("DELETE - delete item with itemId {}", itemId);
        itemClient.deleteItem(itemId);
    }

    @PostMapping("/{itemId}/comment")
    public ResponseEntity<Object> addComment(@RequestHeader(value = "X-Sharer-User-Id") long authorId,
                                             @RequestBody @Valid CommentDto commentDto,
                                             @PathVariable long itemId) {
        log.info("POST-запрос на добавление комментария authorId = {}, itemId = {}", authorId, itemId);
        return itemClient.addComment(commentDto, authorId, itemId);
    }
}
