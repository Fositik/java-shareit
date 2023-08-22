package ru.practicum.shareit.item;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.UserMapper;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    public ItemDto createItem(ItemDto itemDto, Long userId) {
        User user = userRepository.findUserById(userId);
        Item item = ItemMapper.toItem(itemDto, user);
        Item createdItem = itemRepository.createItem(item);
        return ItemMapper.toItemDto(createdItem);
    }

    public List<ItemDto> findAllItems(Long userId) {
        User user = userRepository.findUserById(userId);
        List<Item> itemList = itemRepository.findAllItems(user);
        return itemList.stream().map(ItemMapper::toItemDto).collect(Collectors.toList());
    }

    public ItemDto findItemById(Long id) {
        Item item = itemRepository.findItemById(id);
        return ItemMapper.toItemDto(item);
    }

    public ItemDto updateItem(Long itemId, ItemDto itemDto, Long userId) {
        User user = userRepository.findUserById(userId);
        Item item = ItemMapper.toItem(itemDto, user);
        Item updatedItem = itemRepository.updateItem(itemId, item);
        return ItemMapper.toItemDto(updatedItem);
    }

    public void removeItem(Long id) {
        itemRepository.removeItem(id);
    }

    public List<ItemDto> searchItems(String text) {
        List<Item> searchResult = itemRepository.search(text.toLowerCase(Locale.ROOT));
        return searchResult.stream().map(ItemMapper::toItemDto).collect(Collectors.toList());
    }
}
