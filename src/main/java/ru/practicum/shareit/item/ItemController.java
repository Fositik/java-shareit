package ru.practicum.shareit.item;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping("/items")
@Slf4j
@AllArgsConstructor
@Validated
public class ItemController {

    private final ItemService itemService;

    @PostMapping
    public ItemDto create(@RequestHeader(name = "X-Sharer-User-Id") Long userId,
                          @Valid @RequestBody ItemDto itemDto,
                          BindingResult result) {
        log.info("Request received to /items create endpoint with headers {}", userId);
        if (result.hasErrors()) {
            String errorMessage = result.getFieldError().getDefaultMessage();
            log.warn(errorMessage);
            throw new ValidationException(errorMessage);
        }
        return itemService.createItem(itemDto, userId);
    }

    @GetMapping
    public List<ItemDto> getAll(@RequestHeader(name = "X-Sharer-User-Id") Long userId) {
        log.info("Endpoint request received: /items getAll with headers {}", userId);
        return itemService.findAllItems(userId);
    }

    @GetMapping("/{id}")
    public ItemDto getById(@PathVariable("id") Long id) {
        log.info("Request received to endpoint: /items geById with id={}", id);
        return itemService.findItemById(id);
    }

    @PatchMapping("/{id}")
    public ItemDto update(@RequestHeader(name = "X-Sharer-User-Id") Long userId,
                          @PathVariable("id") Long itemId,
                          @RequestBody ItemDto itemDto,
                          BindingResult result) {
        log.info("Endpoint request received: /items update with ItemId={} with headers {}", itemId, userId);
        if (result.hasErrors()) {
            String errorMessage = result.getFieldError().getDefaultMessage();
            log.warn(errorMessage);
            throw new ValidationException(errorMessage);
        }
        return itemService.updateItem(itemId, itemDto, userId);
    }

    @DeleteMapping("/{id}")
    public HttpStatus delete(@PathVariable("id") Long id) {
        log.info("Request received to endpoint: /items delete with id={}", id);
        itemService.removeItem(id);
        return HttpStatus.OK;
    }

    @GetMapping("/search")
    public List<ItemDto> search(@RequestParam("text") String text) {
        log.info("Endpoint request received: items/search with text: {}", text);
        return itemService.searchItems(text);
    }
}
