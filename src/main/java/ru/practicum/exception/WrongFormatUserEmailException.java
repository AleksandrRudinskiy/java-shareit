package ru.practicum.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class WrongFormatUserEmailException extends RuntimeException {
    public WrongFormatUserEmailException(String message) {
        super(message);
    }
}
