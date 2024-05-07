package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.booking.dto.BookingInfo;


@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDatesDto extends ItemDto {
    private long id;
    private String name;
    private String description;
    private Boolean available;
    private long ownerId;
    private Long requestId;
    private BookingInfo lastBooking;
    private BookingInfo nextBooking;
}
