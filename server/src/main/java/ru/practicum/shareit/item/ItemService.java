package ru.practicum.shareit.item;


import ru.practicum.shareit.comment.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDatesDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemWithCommentsDto;

import java.util.List;

public interface ItemService {

    ItemDto addItem(long userId, ItemDto itemDto);

    List<ItemDatesDto> getUserItems(long userId, int from, int size);

    void deleteItem(long itemId);

    ItemWithCommentsDto getItemById(long itemId, long userId);

    List<ItemDto> search(long userId, String text, int from, int size);

    ItemDto update(long userId, long itemId, ItemDto itemDto);

    CommentDto addComment(CommentDto commentDto, long authorId, Long commentId);

}
