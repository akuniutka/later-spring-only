package ru.practicum.itemnote;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notes")
@Transactional(readOnly = true)
@Slf4j
public class ItemNoteController {

    private final ItemNoteService itemNoteService;
    private final ItemNoteMapper mapper;

    @PostMapping
    @Transactional
    public ItemNoteDto add(
            @RequestHeader("X-Later-User-Id") final long userId,
            @RequestBody final NewItemNoteDto newItemNoteDto
    ) {
        log.info("Received POST at /notes: {} (X-Later-User-Id: {})", newItemNoteDto, userId);
        final ItemNote itemNote = mapper.mapToItemNote(newItemNoteDto);
        final ItemNoteDto dto = mapper.mapToDto(itemNoteService.addNewNote(userId, itemNote));
        log.info("Responded to POST /items: {}", dto);
        return dto;
    }

    @GetMapping
    public List<ItemNoteDto> getAll(
            @RequestHeader("X-Later-User-Id") final long userId,
            @RequestParam(name = "urlPattern", required = false) final String urlPattern,
            @RequestParam(name = "tag", required = false) final String tag
    ) {
        log.info("Received GET at /notes?urlPattern={}&tag={} (X-Later-User-Id: {})", urlPattern, tag, userId);
        final List<ItemNoteDto> dtos = mapper.mapToDto(itemNoteService.getItemNotes(userId, urlPattern, tag));
        log.info("Responded to GET /notes?urlPattern={}&tag={}: {}", urlPattern, tag, dtos);
        return dtos;
    }
}
