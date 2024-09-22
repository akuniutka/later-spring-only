package ru.practicum.itemnote;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface ItemNoteMapper {

    ItemNote mapToItemNote(NewItemNoteDto dto);

    ItemNoteDto mapToDto(ItemNote itemNote);

    List<ItemNoteDto> mapToDto(List<ItemNote> itemNotes);
}
