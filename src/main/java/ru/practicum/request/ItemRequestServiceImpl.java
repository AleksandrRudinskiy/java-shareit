package ru.practicum.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.exception.NotCorrectDataException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.item.ItemInfo;
import ru.practicum.item.ItemRepository;
import ru.practicum.request.dto.ItemRequestDto;
import ru.practicum.request.dto.ItemRequestMapper;
import ru.practicum.request.dto.ItemRequestWithItemsDto;
import ru.practicum.request.model.ItemRequest;
import ru.practicum.user.UserRepository;
import ru.practicum.user.model.User;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemRequestServiceImpl implements ItemRequestService {
    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    @Override
    public ItemRequestDto addRequest(long userId, ItemRequestDto requestDto) {
        existsUser(userId);
        requestDto.setRequestorId(userId);
        User requestor = userRepository.getById(userId);
        return ItemRequestMapper.convertToItemRequestDto(
                requestRepository.save(ItemRequestMapper.convertToItemRequest(requestDto, requestor)));
    }

    @Override
    public List<ItemRequestWithItemsDto> getByRequestorId(long userId) {
        existsUser(userId);
        List<ItemRequest> requests = requestRepository.getByRequestorId(userId);
        return requests.stream()
                .map(r -> ItemRequestMapper.convertToItemRequestWithItemsDto(
                        r, itemRepository.getByRequestId(r.getId())))
                .collect(Collectors.toList());
    }

    @Override
    public ItemRequestWithItemsDto getById(long userId, long id) {
        existsUser(userId);
        if (!requestRepository.existsById(id)) {
            throw new NotFoundException("Запроса с requestId = " + id + " не существует.");
        }
        List<ItemInfo> items = itemRepository.getByRequestId(id);
        return ItemRequestMapper.convertToItemRequestWithItemsDto(requestRepository.getByRequestId(id), items);
    }

    @Override
    public List<ItemRequestWithItemsDto> getAll(long requestorId, int from, int size) {
        if (from < 0 || size <= 0) {
            throw new NotCorrectDataException("Параметр from не должен быть меньше 1.");
        }
        PageRequest page = PageRequest.of(from > 0 ? from / size : 0, size);
        List<ItemRequest> requests = requestRepository.findAllNotRequestorId(requestorId, page).toList();
        return requests.stream()
                .map(r -> ItemRequestMapper.convertToItemRequestWithItemsDto(
                        r, itemRepository.getByRequestId(r.getId())))
                .collect(Collectors.toList());
    }

    private void existsUser(long userId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("Пользователь с id = " + userId + " не существует.");
        }
    }
}
