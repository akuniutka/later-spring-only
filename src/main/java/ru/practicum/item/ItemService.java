package ru.practicum.item;

import java.util.List;
import java.util.Set;

public interface ItemService {

    List<Item> getItems(long userId, Set<String> tags);

    Item addNewItem(long userId, Item item);

    void deleteItem(long userId, long itemId);
}
