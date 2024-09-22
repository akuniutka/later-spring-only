package ru.practicum.itemnote;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemNoteRepository extends JpaRepository<ItemNote, Long> {

    List<ItemNote> findAllByItemUserId(long userId);

    List<ItemNote> findAllByItemUserIdAndItemUrlContains(long userId, String urlPattern);

    @Query("SELECT itemNote FROM ItemNote AS itemNote JOIN itemNote.item AS item JOIN item.user AS user WHERE user.id = ?1 AND ?2 MEMBER OF item.tags")
    List<ItemNote> findAllByItemUserIdAndItemTag(long userId, String tag);
}
