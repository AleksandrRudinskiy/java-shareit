package ru.practicum.exception;

public class NotCorrectDataException extends RuntimeException {

    public NotCorrectDataException(String message) {
        super(message);
    }
}
