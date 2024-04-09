package ru.practicum.item;

import ru.practicum.comment.dto.CommentDto;
import ru.practicum.item.dto.ItemDatesDto;
import ru.practicum.item.dto.ItemDto;
import ru.practicum.item.dto.ItemWithCommentsDto;

import java.util.List;

public interface ItemService {

    ItemDto addItem(long userId, ItemDto itemDto);

    List<ItemDatesDto> getUserItems(long userId);

    void deleteItem(long itemId);

    ItemWithCommentsDto getItemById(long itemId, long userId);

    List<ItemDto> search(long userId, String text);

    ItemDto update(long userId, long itemId, ItemDto itemDto);

    CommentDto addComment(CommentDto commentDto, long authorId, Long commentId);

}
