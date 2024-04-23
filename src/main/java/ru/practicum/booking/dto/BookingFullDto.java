package ru.practicum.booking.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.booking.Status;
import ru.practicum.item.ItemInfo;
import ru.practicum.user.UserInfo;

import java.time.LocalDateTime;

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
