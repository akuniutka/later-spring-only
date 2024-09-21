package ru.practicum.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
@Slf4j
public class ItemController {

    private final ItemService itemService;
    private final ItemMapper mapper;

    @GetMapping
    public List<ItemDto> get(
            @RequestHeader("X-Later-User-Id") final long userId,
            @RequestParam(required = false) final Set<String> tags
    ) {
        log.info("Received GET at /items?tags={} (X-Later-User-Id: {})", tags, userId);
        final List<ItemDto> dtos = mapper.mapToDto(itemService.getItems(userId, tags));
        log.info("Responded to GET /items?tags={}: {}", tags, dtos);
        return dtos;
    }

    @PostMapping
    public ItemDto add(@RequestHeader("X-Later-User-Id") final long userId,
            @RequestBody final NewItemDto newItemDto) {
        log.info("Received POST at /items: {} (X-Later-User-Id: {})", newItemDto, userId);
        final Item item = mapper.mapToItem(newItemDto);
        final ItemDto dto = mapper.mapToDto(itemService.addNewItem(userId, item));
        log.info("Responded to POST /items: {}", dto);
        return dto;
    }

    @DeleteMapping("/{itemId}")
    public void deleteItem(@RequestHeader("X-Later-User-Id") final long userId,
            @PathVariable final long itemId) {
        log.info("Received DELETE at /items/{} (X-Later-User-Id: {})", itemId, userId);
        itemService.deleteItem(userId, itemId);
        log.info("Responded to DELETE /items/{} with no body", itemId);
    }
}
