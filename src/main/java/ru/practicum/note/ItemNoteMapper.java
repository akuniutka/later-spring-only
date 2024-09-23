package ru.practicum.note;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface ItemNoteMapper {

    @Mapping(source = "itemId", target = "item.id")
    ItemNote mapToItemNote(NewItemNoteDto dto);

    @Mapping(source = "item.id", target = "itemId")
    @Mapping(source = "item.url", target = "itemUrl")
    ItemNoteDto mapToDto(ItemNote itemNote);

    List<ItemNoteDto> mapToDto(List<ItemNote> itemNotes);
}
