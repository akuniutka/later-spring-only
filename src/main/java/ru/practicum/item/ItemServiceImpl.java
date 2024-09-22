package ru.practicum.item;

import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.user.User;
import ru.practicum.user.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ItemServiceImpl implements ItemService {

    private final ItemRepository repository;
    private final UserService userService;

    @Override
    @Transactional
    public Item addNewItem(final long userId, final Item item) {
        final User user = userService.getUser(userId);
        item.setUser(user);
        return repository.save(item);
    }

    @Override
    public Item getItem(final long userId, final long id) {
        return repository.findByUserIdAndId(userId, id).orElseThrow();
    }

    public List<Item> getItems(final long userId) {
        return repository.findByUserId(userId);
    }

    @Override
    public List<Item> getItems(final long userId, final Set<String> tags) {
        if (tags == null) {
            return getItems(userId);
        }
        final BooleanExpression byUserId = QItem.item.user.id.eq(userId);
        final BooleanExpression byAnyTag = QItem.item.tags.any().in(tags);
        final List<Item> items = new ArrayList<>();
        repository.findAll(byUserId.and(byAnyTag)).forEach(items::add);
        return items;
    }

    @Override
    @Transactional
    public void deleteItem(final long userId, final long id) {
        repository.deleteByUserIdAndId(userId, id);
    }
}
