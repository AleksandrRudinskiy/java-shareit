package ru.practicum.shareit.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.booking.Status;
import ru.practicum.shareit.item.ItemInfo;
import ru.practicum.shareit.user.UserInfo;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingFullDto {
    private long id;
    private String start;
    private String end;
    private ItemInfo item;
    private UserInfo booker;
    private Status status;
}
