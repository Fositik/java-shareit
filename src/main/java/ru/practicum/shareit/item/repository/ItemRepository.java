package ru.practicum.shareit.item.repository;

import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.util.List;

public interface ItemRepository {
    Item createItem(Item item);

    List<Item> findAllItems(User user);

    Item findItemById(Long id);

    Item updateItem(Long id, Item item);

    void removeItem(Long id);

    List<Item> search(String text);
}
