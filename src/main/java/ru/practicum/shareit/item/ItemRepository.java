package ru.practicum.shareit.item;

import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemRepository {

    Item add(Item item);

    List<Item> getItems();

    Item getItemById(long itemId);

    Item update(Item item);

    void delete(long itemId);


}
