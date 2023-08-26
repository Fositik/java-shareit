package ru.practicum.shareit.item;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.utils.validation.Create;
import ru.practicum.shareit.utils.validation.Update;

import java.util.List;

import static ru.practicum.shareit.constants.HeaderConstants.USER_ID_HEADER;

@RestController
@RequestMapping("/items")
@Slf4j
@AllArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @PostMapping
    public ItemDto create(@RequestHeader(name = USER_ID_HEADER) Long userId,
                          @Validated(Create.class) @RequestBody ItemDto itemDto) {
        log.info("Request received to /items create endpoint with headers {}", userId);
        return itemService.createItem(itemDto, userId);
    }

    @GetMapping
    public List<ItemDto> getAll(@RequestHeader(name = USER_ID_HEADER) Long userId) {
        log.info("Endpoint request received: /items getAll with headers {}", userId);
        return itemService.findAllItems(userId);
    }

    @GetMapping("/{id}")
    public ItemDto getById(@PathVariable("id") Long id) {
        log.info("Request received to endpoint: /items geById with id={}", id);
        return itemService.findItemById(id);
    }

    @PatchMapping("/{id}")
    public ItemDto update(@RequestHeader(name = USER_ID_HEADER) Long userId,
                          @PathVariable("id") Long itemId,
                          @Validated(Update.class) @RequestBody ItemDto itemDto) {
        log.info("Endpoint request received: /items update with ItemId={} with headers {}", itemId, userId);
        return itemService.updateItem(itemId, itemDto, userId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        log.info("Request received to endpoint: /items delete with id={}", id);
        itemService.removeItem(id);
    }

    @GetMapping("/search")
    public List<ItemDto> search(@RequestParam("text") String text) {
        log.info("Endpoint request received: items/search with text: {}", text);
        return itemService.searchItems(text);
    }
}
