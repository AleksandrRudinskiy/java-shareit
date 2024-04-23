package ru.practicum.shareit.item;


import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.booking.BookingRepository;
import ru.practicum.booking.Status;
import ru.practicum.booking.model.Booking;
import ru.practicum.comment.CommentRepository;
import ru.practicum.item.ItemRepository;
import ru.practicum.item.ItemService;
import ru.practicum.item.ItemServiceImpl;
import ru.practicum.item.dto.ItemDatesDto;
import ru.practicum.item.dto.ItemDto;
import ru.practicum.item.dto.ItemMapper;
import ru.practicum.item.model.Item;
import ru.practicum.request.RequestRepository;
import ru.practicum.request.model.ItemRequest;
import ru.practicum.user.UserRepository;
import ru.practicum.user.model.User;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ItemServiceTest {
    private ItemRepository itemRepository = Mockito.mock(ItemRepository.class);
    private UserRepository userRepository = Mockito.mock(UserRepository.class);
    private BookingRepository bookingRepository = Mockito.mock(BookingRepository.class);
    private CommentRepository commentRepository = Mockito.mock(CommentRepository.class);
    private RequestRepository requestRepository = Mockito.mock(RequestRepository.class);
    private ItemService itemService = new ItemServiceImpl(
            itemRepository, userRepository, bookingRepository, commentRepository, requestRepository);

    @Test
    public void getUserItemTest() {
        User user = new User(1L, "Petia", "newemail@mail.com");
        User requestor = new User(2L, "Kolya", "mail@mail.com");
        ItemRequest request = new ItemRequest(1L, "нужна дрель", requestor, LocalDateTime.now());
        Item item = new Item(1L, "Дрель", "Простая дрель", true, user, request);
        Booking booking =
                new Booking(1L, LocalDateTime.now().plusHours(1), LocalDateTime.now().plusDays(1), item,
                        requestor, Status.WAITING);

        when(userRepository.existsById(Mockito.anyLong()))
                .thenReturn(true);
        when(itemRepository.getByOwner_id(anyLong()))
                .thenReturn(List.of(item));
        when(itemRepository.getById(anyLong()))
                .thenReturn(item);

        List<ItemDatesDto> items = itemService.getUserItems(1);

        Assertions.assertEquals(1, items.size());
    }

    @Test
    public void itemUpdateTest() {
        User user = new User(1L, "Petia", "newemail@mail.com");
        User requestor = new User(2L, "Kolya", "mail@mail.com");
        ItemRequest request = new ItemRequest(1L, "нужна дрель", requestor, LocalDateTime.now());
        Item item = new Item(1L, "Дрель", "Простая дрель", true, user, request);
        Item updateItem = new Item(1L, "НеДрель", "Непростая дрель", true, user, request);
        when(userRepository.existsById(Mockito.anyLong()))
                .thenReturn(true);
        when(itemRepository.getById(anyLong()))
                .thenReturn(item);
        when(itemRepository.existsById(anyLong()))
                .thenReturn(true);
        when(itemRepository.save(any()))
                .thenReturn(updateItem);
        ItemDto itemDto = ItemMapper.convertItemToDto(updateItem);
        ItemDto updateItemDto = itemService.update(1L, 1L, itemDto);
        Assertions.assertEquals("НеДрель", updateItemDto.getName());
    }


}
