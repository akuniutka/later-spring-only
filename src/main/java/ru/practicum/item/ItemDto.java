package ru.practicum.item;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Data
@EqualsAndHashCode(of = "id")
public class ItemDto {

    private Long id;
    private String url;
    private Set<String> tags;
}
