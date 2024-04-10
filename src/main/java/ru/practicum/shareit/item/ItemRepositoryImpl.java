package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.model.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class ItemRepositoryImpl implements ItemRepository {
    private final Map<Long, Item> items;
    private long currentId;

    @Override
    public Item add(Item item) {
        currentId++;
        item.setId(currentId);
        items.put(item.getId(), item);
        return item;
    }

    @Override
    public List<Item> getItems() {
        return new ArrayList<>(items.values());
    }

    @Override
    public Item getItemById(long itemId) {
        return items.get(itemId);
    }

    @Override
    public Item update(Item item) {
        items.put(item.getId(), item);
        return item;
    }

    @Override
    public void delete(long itemId) {
        items.remove(itemId);
    }
}
