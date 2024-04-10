package ru.practicum.shareit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.practicum.shareit.item.exception.ItemValidationException;
import ru.practicum.shareit.user.exception.DuplicateUserEmailException;
import ru.practicum.shareit.user.exception.NoUserEmailException;
import ru.practicum.shareit.user.exception.NotFoundException;
import ru.practicum.shareit.user.exception.WrongFormatUserEmailException;

import java.util.Map;

@ControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler(DuplicateUserEmailException.class)
    public ResponseEntity<Map<String, String>> handleDuplicateUserEmailException(final DuplicateUserEmailException e) {
        log.error("409 Ошибка валидации: {}", e.getMessage());
        return new ResponseEntity<>(
                Map.of("error", "Ошибка валидации.",
                        "errorMessage", e.getMessage()),
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(WrongFormatUserEmailException.class)
    public ResponseEntity<Map<String, String>> handleWrongFormatUserEmailException(final WrongFormatUserEmailException e) {
        log.error("409 Ошибка валидации: {}", e.getMessage());
        return new ResponseEntity<>(
                Map.of("error", "Ошибка валидации.",
                        "errorMessage", e.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(NoUserEmailException.class)
    public ResponseEntity<Map<String, String>> handleNoUserMailException(final NoUserEmailException e) {
        log.error("400 Ошибка в email: {}", e.getMessage());
        return new ResponseEntity<>(
                Map.of("error", "Ошибка в email.",
                        "errorMessage", e.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<String, String>> handleNotFoundException(final NotFoundException e) {
        log.error("404 Не найден id: {}", e.getMessage());
        return new ResponseEntity<>(
                Map.of("error", "Не найден id.",
                        "errorMessage", e.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(ItemValidationException.class)
    public ResponseEntity<Map<String, String>> handleItemValidationException(final ItemValidationException e) {
        log.error("400 : {}", e.getMessage());
        return new ResponseEntity<>(
                Map.of("error", "Не найден id.",
                        "errorMessage", e.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }


}
