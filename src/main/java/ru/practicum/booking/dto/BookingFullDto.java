package ru.practicum.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.booking.Status;
import ru.practicum.item.ItemInfo;
import ru.practicum.user.UserInfo;

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
