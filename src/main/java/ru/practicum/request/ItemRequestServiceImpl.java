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

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemRequestServiceImpl implements ItemRequestService {
    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    @Override
    public ItemRequestDto addRequest(long userId, ItemRequestDto requestDto) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("Пользователь с id = " + userId + " не существует.");
        }
        requestDto.setRequestorId(userId);
        User requestor = userRepository.getById(userId);
        return ItemRequestMapper.convertToItemRequestDto(
                requestRepository.save(ItemRequestMapper.convertToItemRequest(requestDto, requestor)));
    }

    @Override
    public List<ItemRequestWithItemsDto> getByRequestorId(long userId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("Пользователь с id = " + userId + " не существует.");
        }
        List<ItemRequest> requests = requestRepository.getByRequestorId(userId);

        List<ItemRequestWithItemsDto> itemRequestWithItemsDtos = new ArrayList<>();
//переписать через единый запрос без цикла!
        for (ItemRequest request : requests) {


            List<ItemInfo> items = itemRepository.getByRequestId(request.getId());


            ItemRequestWithItemsDto itemRequestWithItemsDto = ItemRequestMapper.convertToItemRequestWithItemsDto(request, items);
            itemRequestWithItemsDtos.add(itemRequestWithItemsDto);
        }
        return itemRequestWithItemsDtos;
    }


    @Override
    public ItemRequestWithItemsDto getById(long userId, long id) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("Пользователь с id = " + userId + " не существует.");
        }

        if (!requestRepository.existsById(id)) {
            throw new NotFoundException("Запроса с requestId = " + id + " не существует.");
        }


        List<ItemInfo> items = itemRepository.getByRequestId(id);
        return ItemRequestMapper.convertToItemRequestWithItemsDto(requestRepository.getByRequestId(id), items);
    }

    @Override
    public List<ItemRequestWithItemsDto> getAll(long requestorId, int from, int size) {
        if (from < 0 || size <= 0) {
            throw new NotCorrectDataException("Параметр from не должен быть меньше 1");
        }


        PageRequest page = PageRequest.of(from > 0 ? from / size : 0, size);

        List<ItemRequest> requests = requestRepository.findAllNotRequestorId(requestorId, page).toList();

        List<ItemRequestWithItemsDto> itemRequestWithItemsDtos = new ArrayList<>();

//переписать через единый запрос без цикла!

        for (ItemRequest request : requests) {
            List<ItemInfo> items = itemRepository.getByRequestId(request.getId());
            ItemRequestWithItemsDto itemRequestWithItemsDto = ItemRequestMapper.convertToItemRequestWithItemsDto(request, items);
            itemRequestWithItemsDtos.add(itemRequestWithItemsDto);
        }
        return itemRequestWithItemsDtos;
    }
}
