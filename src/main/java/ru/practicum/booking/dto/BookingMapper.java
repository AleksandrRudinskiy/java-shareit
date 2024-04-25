package ru.practicum.booking.dto;

import lombok.experimental.UtilityClass;
import ru.practicum.booking.model.Booking;
import ru.practicum.item.ItemInfo;
import ru.practicum.item.model.Item;
import ru.practicum.user.UserInfo;
import ru.practicum.user.model.User;

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
