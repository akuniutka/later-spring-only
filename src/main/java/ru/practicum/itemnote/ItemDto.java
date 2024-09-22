package ru.practicum.itemnote;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = "id")
class ItemDto {

    private Long id;
}
