package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

public interface ItemService {

    ItemDto addItem(long userId, ItemDto itemDto);

    List<ItemDto> getAllItems();

    List<ItemDto> getUserItems(long userId);

    void deleteItem(long itemId);

    ItemDto getItemById(long itemId);

    List<ItemDto> search(long userId, String text);

    ItemDto update(long userId, long itemId, ItemDto itemDto);

}
