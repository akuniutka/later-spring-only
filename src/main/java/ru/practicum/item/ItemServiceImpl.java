package ru.practicum.item;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class ItemServiceImpl implements ItemService {

    private final ItemRepository repository;

    @Override
    public List<Item> getItems(final long userId) {
        return repository.findByUserId(userId);
    }

    @Override
    public Item addNewItem(final long userId, final Item item) {
        item.setUserId(userId);
        return repository.save(item);
    }

    @Override
    public void deleteItem(final long userId, final long id) {
        repository.deleteByUserIdAndId(userId, id);
    }
}
