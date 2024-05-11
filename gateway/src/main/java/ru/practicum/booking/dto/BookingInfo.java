package ru.practicum.booking.dto;

import java.time.LocalDateTime;

public interface BookingInfo {

    long getId();

    long getBookerId();

    LocalDateTime getStart();

    LocalDateTime getEnd();
}
