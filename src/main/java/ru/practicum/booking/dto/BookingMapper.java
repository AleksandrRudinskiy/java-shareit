package ru.practicum.booking.dto;

import lombok.experimental.UtilityClass;
import ru.practicum.booking.model.Booking;
import ru.practicum.item.ItemInfo;
import ru.practicum.item.model.Item;
import ru.practicum.user.UserInfo;
import ru.practicum.user.model.User;

@UtilityClass
public class BookingMapper {

    public static Booking convertDtoToBooking(BookingDto bookingDto, User booker, Item item) {
        return new Booking(
                bookingDto.getId(),
                bookingDto.getStart(),
                bookingDto.getEnd(),
                item,
                booker,
                bookingDto.getStatus()
        );
    }

    public static BookingFullDto convertToFullDto(Booking booking, ItemInfo itemInfo, UserInfo booker) {
        return new BookingFullDto(
                booking.getId(),
                booking.getStart(),
                booking.getEnd(),
                itemInfo,
                booker,
                booking.getStatus()
        );
    }

}
