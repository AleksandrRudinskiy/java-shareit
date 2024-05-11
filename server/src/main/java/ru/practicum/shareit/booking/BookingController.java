package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingFullDto;

import java.util.List;

@RestController
@RequestMapping(path = "/bookings")
@Slf4j
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    public BookingFullDto add(@RequestHeader(value = "X-Sharer-User-Id") long userId,
                              @Validated
                              @RequestBody BookingDto bookingDto) {
        log.info("POST-запрос на бронирование от пользователя с userId {}", userId);
        return bookingService.addBooking(userId, bookingDto);
    }

    @PatchMapping("/{bookingId}")
    public BookingFullDto updateBooking(@PathVariable long bookingId,
                                        @RequestHeader(value = "X-Sharer-User-Id") long userId,
                                        @RequestParam(required = false) Boolean approved,
                                        @RequestBody(required = false) BookingDto bookingDto) {
        log.info("PATCH-запрос на подтверждение или отклонение запроса на бронирование");
        return bookingService.update(bookingId, userId, approved, bookingDto);
    }

    @GetMapping("/{bookingId}")
    public BookingFullDto getBookingById(@RequestHeader(value = "X-Sharer-User-Id") long userId,
                                         @PathVariable long bookingId) {
        log.info("GET-запрос на получение данных о бронировании: userId = {}, bookingId = {}", userId, bookingId);
        return bookingService.getBookingById(bookingId, userId);
    }

    @GetMapping
    public List<BookingFullDto> getBookerBookings(@RequestHeader(value = "X-Sharer-User-Id") long userId,
                                                  @RequestParam(defaultValue = "ALL", required = false) String state,
                                                  @RequestParam(defaultValue = "0") int from,
                                                  @RequestParam(defaultValue = "10") int size) {
        log.info("GET-запрос на получение всех бронирований пользователя");
        return bookingService.getBookerBookings(userId, state, from, size);
    }

    @GetMapping("/owner")
    public List<BookingFullDto> getOwnerBookings(@RequestHeader(value = "X-Sharer-User-Id") long userId,
                                                 @RequestParam(defaultValue = "ALL", required = false) String state,
                                                 @RequestParam(defaultValue = "0") int from,
                                                 @RequestParam(defaultValue = "10") int size) {
        log.info("GET-запрос на получение всех бронирований собственника вещи");
        return bookingService.getOwnerBookings(userId, state, from, size);
    }
}
