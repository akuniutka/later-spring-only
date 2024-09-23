package ru.practicum.note;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Instant;

@Data
@EqualsAndHashCode(of = "id")
public class ItemNoteDto {

    private Long id;
    private Long itemId;
    private String text;

    @JsonFormat(pattern = "yyyy-MM-dd, HH:mm:ss", timezone = "UTC")
    private Instant dateOfNote;

    private String itemUrl;
}
