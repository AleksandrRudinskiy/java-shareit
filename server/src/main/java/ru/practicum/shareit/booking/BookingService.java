package ru.practicum.shareit.booking;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingFullDto;

import java.util.List;

public interface BookingService {

    BookingFullDto addBooking(long userId, BookingDto bookingDto);

    BookingFullDto update(long bookingId, long userId, Boolean approved, BookingDto bookingDto);

    BookingFullDto getBookingById(long bookingId, long userId);

    List<BookingFullDto> getOwnerBookings(long userId, String state, int from, int size);

    List<BookingFullDto> getBookerBookings(long userId, String state, int from, int size);

}
