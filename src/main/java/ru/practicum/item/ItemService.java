package ru.practicum.item;

import java.util.List;
import java.util.Set;

public interface ItemService {

    Item addNewItem(long userId, Item item);

    Item getItem(long userId, long id);

    List<Item> getItems(long userId, Set<String> tags);

    void deleteItem(long userId, long id);
}
