package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.item.exception.ItemValidationException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserService;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserMapper;
import ru.practicum.shareit.user.exception.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UserService userService;

    @Override
    public ItemDto addItem(long userId, ItemDto itemDto) {
        validate(ItemMapper.convertDtoToItem(itemDto));
        UserDto userDto = userService.getUserById(userId);
        itemDto.setOwner(UserMapper.convertDtotoUser(userDto));
        return ItemMapper.convertItemToDto(itemRepository.add(ItemMapper.convertDtoToItem(itemDto)));
    }

    @Override
    public List<ItemDto> getAllItems() {
        return itemRepository.getItems().stream()
                .map(ItemMapper::convertItemToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ItemDto> getUserItems(long userId) {
        return getAllItems().stream()
                .filter(i -> i.getOwner().getId() == userId)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteItem(long itemId) {
        itemRepository.delete(itemId);
    }

    @Override
    public ItemDto getItemById(long itemId) {
        return ItemMapper.convertItemToDto(itemRepository.getItemById(itemId));
    }

    @Override
    public ItemDto update(long userId, long itemId, ItemDto itemDto) {
        log.info("Поле userId, передаваемое при PATCH = {}", userId);
        ItemDto oldItemDto = getItemById(itemId);
        long trueOwnerId = oldItemDto.getOwner().getId();
        log.info("ID собственника обновляемой вещи {} ", trueOwnerId);
        log.info("ID, которое передается при PATCH {}", userId);
        if (trueOwnerId != userId) {
            throw new NotFoundException("Попытка обновить пользователя вещи.");
        }
        if (itemDto.getAvailable() != null) {
            oldItemDto.setAvailable(itemDto.getAvailable());
        }
        if (itemDto.getDescription() != null) {
            oldItemDto.setDescription(itemDto.getDescription());
        }
        if (itemDto.getName() != null) {
            oldItemDto.setName(itemDto.getName());
        }
        return ItemMapper.convertItemToDto(itemRepository.update(ItemMapper.convertDtoToItem(oldItemDto)));
    }

    @Override
    public List<ItemDto> search(long userId, String text) {
        if (text.isEmpty()) {
            return new ArrayList<>();
        } else {
            return getAllItems().stream()

                    .filter(i -> i.getDescription().toLowerCase().contains(text.toLowerCase())
                            || i.getName().toLowerCase().contains(text.toLowerCase()))
                    .filter(ItemDto::getAvailable)
                    .collect(Collectors.toList());
        }
    }

    private void validate(Item item) {
        if (item.getAvailable() == null || item.getName().isEmpty() || item.getDescription() == null) {
            throw new ItemValidationException("Ошибка валидации item.");
        }
    }
}
