package ru.practicum.shareit.booking.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.booking.Status;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDto {
    private long id;
    @NotNull
    private String start;
    @NotNull
    private String end;
    private long itemId;
    private String itemName;
    @JsonProperty("booker")
    private long bookerId;
    private Status status;
}
