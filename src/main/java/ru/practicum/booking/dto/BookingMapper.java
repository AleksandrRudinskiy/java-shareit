package ru.practicum.booking.dto;

import lombok.experimental.UtilityClass;
import ru.practicum.booking.model.Booking;
import ru.practicum.item.ItemInfo;
import ru.practicum.item.model.Item;
import ru.practicum.user.UserInfo;
import ru.practicum.user.model.User;

import java.time.LocalDateTime;

@UtilityClass
public class BookingMapper {

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
                booking.getStart().toString(),
                booking.getEnd().toString(),
                itemInfo,
                booker,
                booking.getStatus()
        );
    }

    public static BookingDto convertToBookingDto(Booking booking, Item item){
        return new BookingDto(
                booking.getId(),
                booking.getStart().toString(),
                booking.getEnd().toString(),
                item.getId(),
                item.getName(),
                booking.getBooker().getId(),
                booking.getStatus()
        );
    }

}
