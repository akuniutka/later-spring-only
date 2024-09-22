package ru.practicum.itemnote;

import java.util.List;

public interface ItemNoteService {

    ItemNote addNewNote(long userId, ItemNote itemNote);

    List<ItemNote> getItemNotes(long userId, String urlPattern, String tag);
}
