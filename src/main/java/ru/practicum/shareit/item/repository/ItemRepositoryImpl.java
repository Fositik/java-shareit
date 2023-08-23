package ru.practicum.shareit.item.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exceptions.EntityNotFoundException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ItemRepositoryImpl implements ItemRepository {
    private final AtomicLong id = new AtomicLong(0L);
    private final Map<Long, List<Item>> userItemIndex = new LinkedHashMap<>();

    @Override
    public Item createItem(Item item) {
        Long itemId = id.incrementAndGet();
        item.setId(itemId);
        final List<Item> items = userItemIndex.computeIfAbsent(item.getOwner().getId(), k -> new ArrayList<>());
        items.add(item);
        return item;
    }

    @Override
    public List<Item> findAllItems(User user) {
        return userItemIndex.getOrDefault(user.getId(), new ArrayList<>());
    }

    @Override
    public Item findItemById(Long itemId) {
        return userItemIndex.values().stream()
                .flatMap(List::stream)
                .filter(item -> item.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(String.format("Item with id: %d not found", itemId)));
    }

    @Override
    public Item updateItem(Long itemId, Item item) {
        Item updatedItem = findItemById(itemId);

        if (!updatedItem.getOwner().equals(item.getOwner())) {
            log.warn("No access. Id = {} not found for this item!", item.getId());
            throw new EntityNotFoundException("No access. Id not found for this item!");
        }

        if (item.getName() != null && !item.getName().isBlank()) {
            updatedItem.setName(item.getName());
        }

        if (item.getDescription() != null && !item.getDescription().isBlank()) {
            updatedItem.setDescription(item.getDescription());
        }

        if (item.getAvailable() != null) {
            updatedItem.setAvailable(item.getAvailable());
        }

        return updatedItem;
    }

    @Override
    public void removeItem(Long itemId) {
        userItemIndex.forEach((userId, items) -> items.removeIf(item -> item.getId().equals(itemId)));
    }

    @Override
    public List<Item> search(String text) {
        if (text.isBlank()) {
            return new ArrayList<>();
        }

        return userItemIndex.values().stream()
                .flatMap(List::stream)
                .filter(item -> (item.getName().toLowerCase().contains(text) ||
                        item.getDescription().toLowerCase().contains(text)) &&
                        item.getAvailable())
                .collect(Collectors.toList());
    }
}
