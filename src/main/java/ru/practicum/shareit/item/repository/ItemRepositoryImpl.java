package ru.practicum.shareit.item.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exceptions.ItemNotFoundException;
import ru.practicum.shareit.exceptions.UserIdValidationException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ItemRepositoryImpl implements ItemRepository {
    private final AtomicLong id = new AtomicLong(0L);
    private final Map<Long, Item> itemMap = new HashMap<>();

    @Override
    public Item createItem(Item item) {
        Long itemId = id.incrementAndGet();
        item.setId(itemId);
        itemMap.put(itemId, item);
        return item;
    }

    @Override
    public List<Item> findAllItems(User user) {
        return itemMap.values().stream().filter(item -> item.getOwner().equals(user)).collect(Collectors.toList());
    }

    @Override
    public Item findItemById(Long id) {
        return itemMap.values().stream()
                .filter(item -> item.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ItemNotFoundException(String.format("User with id: %d not found", id)));
    }

    @Override
    public Item updateItem(Long id, Item item) {
        Item updatedItem = itemMap.get(id);

        if (!updatedItem.getOwner().equals(item.getOwner())) {
            log.warn("No access. Id = {} not found for this item!", item.getId());
            throw new UserIdValidationException("No access. Id not found for this item!");
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

        itemMap.put(id, updatedItem);

        return updatedItem;
    }

    @Override
    public void removeItem(Long id) {
        itemMap.remove(id);
    }

    @Override
    public List<Item> search(String text) {
        List<Item> searchResultList = new ArrayList<>();
        if (text.isBlank()) {
            return searchResultList;
        }

        return itemMap.values().stream()
                .filter(item -> item.getName().toLowerCase().contains(text) ||
                        item.getDescription().toLowerCase().contains(text) &&
                                item.getAvailable())
                .collect(Collectors.toList());
    }
}
