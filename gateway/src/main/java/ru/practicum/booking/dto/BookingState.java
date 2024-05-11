package ru.practicum.booking.dto;

import java.util.Optional;

/**
 * По состоянию бронирования могут быть следующими
 * {@link #ALL}
 * {@link #CURRENT}
 * {@link #FUTURE}
 * {@link #PAST}
 * {@link #REJECTED}
 * {@link #WAITING}
 */
public enum BookingState {
    /**
     * Все
     */
    ALL,
    /**
     * Текущие
     */
    CURRENT,
    /**
     * Будующие
     */
    FUTURE,
    /**
     * Завершенные
     */
    PAST,
    /**
     * Отклоненные
     */
    REJECTED,
    /**
     * Ожидающие подтверждения
     */
    WAITING;

    public static Optional<BookingState> from(String stringState) {
        for (BookingState state : values()) {
            if (state.name().equalsIgnoreCase(stringState)) {
                return Optional.of(state);
            }
        }
        return Optional.empty();
    }
}
