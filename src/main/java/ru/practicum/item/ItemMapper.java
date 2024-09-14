package ru.practicum.item;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface ItemMapper {

    Item mapToItem(NewItemDto dto);

    ItemDto mapToDto(Item item);

    List<ItemDto> mapToDto(List<Item> items);
}
