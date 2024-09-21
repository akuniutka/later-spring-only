package ru.practicum.item;

import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ItemServiceImpl implements ItemService {

    private final ItemRepository repository;

    public List<Item> getItems(final long userId) {
        return repository.findByUserId(userId);
    }

    @Override
    public List<Item> getItems(final long userId, final Set<String> tags) {
        if (tags == null) {
            return getItems(userId);
        }
        final BooleanExpression byUserId = QItem.item.userId.eq(userId);
        final BooleanExpression byAnyTag = QItem.item.tags.any().in(tags);
        final List<Item> items = new ArrayList<>();
        repository.findAll(byUserId.and(byAnyTag)).forEach(items::add);
        return items;
    }

    @Override
    @Transactional
    public Item addNewItem(final long userId, final Item item) {
        item.setUserId(userId);
        return repository.save(item);
    }

    @Override
    @Transactional
    public void deleteItem(final long userId, final long id) {
        repository.deleteByUserIdAndId(userId, id);
    }
}
