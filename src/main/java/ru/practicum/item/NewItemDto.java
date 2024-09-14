package ru.practicum.item;

import lombok.Data;

import java.util.Set;

@Data
public class NewItemDto {

    private String url;
    private Set<String> tags;
}
