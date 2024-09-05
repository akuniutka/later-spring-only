package ru.practicum.item;

import java.util.List;

public interface ItemRepository {

    List<Item> finaByUserId(long userId);

    Item save(Item item);

    void deleteByUserIdAndItemId(long userId, long itemId);
}
