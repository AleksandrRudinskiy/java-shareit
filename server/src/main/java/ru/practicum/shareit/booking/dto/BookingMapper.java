package ru.practicum.shareit.booking.dto;

import lombok.experimental.UtilityClass;

import ru.practicum.shareit.item.ItemInfo;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserInfo;
import ru.practicum.shareit.user.model.User;



import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@UtilityClass
public class BookingMapper {
    private final DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

    public static Booking convertDtoToBooking(BookingDto bookingDto, User booker, Item item) {
        return new Booking(
                bookingDto.getId(),
                LocalDateTime.parse(bookingDto.getStart()),
                LocalDateTime.parse(bookingDto.getEnd()),
                item,
                booker,
                bookingDto.getStatus()
        );
    }

    public static BookingFullDto convertToFullDto(Booking booking, ItemInfo itemInfo, UserInfo booker) {
        return new BookingFullDto(
                booking.getId(),
                booking.getStart().format(formatter),
                booking.getEnd().format(formatter),
                itemInfo,
                booker,
                booking.getStatus()
        );
    }

    public static BookingDto convertToBookingDto(Booking booking, Item item) {
        return new BookingDto(
                booking.getId(),
                booking.getStart().format(formatter),
                booking.getEnd().format(formatter),
                item.getId(),
                item.getName(),
                booking.getBooker().getId(),
                booking.getStatus()
        );
    }

}
