package ru.practicum.note;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.item.Item;
import ru.practicum.item.ItemService;
import ru.practicum.user.UserService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ItemNoteServiceImpl implements ItemNoteService {

    private final ItemNoteRepository repository;
    private final UserService userService;
    private final ItemService itemService;

    @Override
    @Transactional
    public ItemNote addNewNote(final long userId, final ItemNote itemNote) {
        if (itemNote.getItem() == null || itemNote.getItem().getId() == null) {
            throw new RuntimeException();
        }
        final Item item = itemService.getItem(userId, itemNote.getItem().getId());
        itemNote.setItem(item);
        return repository.save(itemNote);
    }

    @Override
    public List<ItemNote> searchNotesByUrl(final long userId, final String urlPattern) {
        userService.getUser(userId);
        return repository.findAllByItemUserIdAndItemUrlContains(userId, urlPattern);
    }

    @Override
    public List<ItemNote> searchNotesByTag(final long userId, final String tag) {
        userService.getUser(userId);
        return repository.findAllByItemUserIdAndItemTag(userId, tag);
    }

    @Override
    public List<ItemNote> getItemNotes(final long userId, final int page, final int size) {
        userService.getUser(userId);
        Pageable pageRequest = PageRequest.of(Math.max(page, 1) - 1, size);
        return repository.findAllByItemUserId(userId, pageRequest);
    }
}
