package ru.practicum.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.item.model.Item;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    @Query(value = " select * from items i " +
            "where i.is_available = true and (upper(i.name) like upper(concat('%', ?1, '%')) " +
            " or upper(i.description) like upper(concat('%', ?1, '%')))", nativeQuery = true)
    List<Item> search(String text);

    List<Item> getByOwner_id(Long userId);

    ItemInfo findAllById(Long itemId);

    List<ItemInfo> getByRequestId(long requestId);


}
