package ru.practicum.itemnote;

import lombok.Data;

@Data
public class NewItemNoteDto {

    private ItemDto item;
    private String text;
}
