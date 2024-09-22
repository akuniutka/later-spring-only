package ru.practicum.item;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.common.BaseController;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
@Transactional(readOnly = true)
@Slf4j
public class ItemController extends BaseController {

    private final ItemService itemService;
    private final ItemMapper mapper;

    @PostMapping
    @Transactional
    public ItemDto add(
            @RequestHeader("X-Later-User-Id") final long userId,
            @RequestBody final NewItemDto newItemDto,
            final HttpServletRequest request
    ) {
        logRequest(request, newItemDto);
        final Item item = mapper.mapToItem(newItemDto);
        final ItemDto dto = mapper.mapToDto(itemService.addNewItem(userId, item));
        logResponse(request, dto);
        return dto;
    }

    @GetMapping
    public List<ItemDto> get(
            @RequestHeader("X-Later-User-Id") final long userId,
            @RequestParam(name = "tags", required = false) final Set<String> tags,
            final HttpServletRequest request
    ) {
        logRequest(request);
        final List<ItemDto> dtos = mapper.mapToDto(itemService.getItems(userId, tags));
        logResponse(request, dtos);
        return dtos;
    }

    @DeleteMapping("/{itemId}")
    @Transactional
    public void deleteItem(
            @RequestHeader("X-Later-User-Id") final long userId,
            @PathVariable(name = "itemId") final long itemId,
            final HttpServletRequest request
    ) {
        logRequest(request);
        itemService.deleteItem(userId, itemId);
        logResponse(request);
    }
}
