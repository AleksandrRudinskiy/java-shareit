package ru.practicum;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.practicum.exception.*;

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

    @ExceptionHandler(NotAvailableException.class)
    public ResponseEntity<Map<String, String>> handleNotAvailableException(final NotAvailableException e) {
        log.error("400 : {}", e.getMessage());
        return new ResponseEntity<>(
                Map.of("error", "Вещь не доступна для бронирования.",
                        "errorMessage", e.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(NotCorrectDataException.class)
    public ResponseEntity<Map<String, String>> handleNotCorrectDataException(final NotCorrectDataException e) {
        log.error("400 : {}", e.getMessage());
        return new ResponseEntity<>(
                Map.of("error", "Неверно указаны даты.",
                        "errorMessage", e.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(NotOwnerException.class)
    public ResponseEntity<Map<String, String>> handleNotOwnerException(final NotOwnerException e) {
        log.error("500 : {}", e.getMessage());
        return new ResponseEntity<>(
                Map.of("error", "Обновить может только собственник.",
                        "errorMessage", e.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }


    @ExceptionHandler(NotSupportedStateException.class)
    public ResponseEntity<Map<String, String>> handleNotSupportedStateException(final NotSupportedStateException e) {
        log.error("500 : {}", e.getMessage());
        return new ResponseEntity<>(
                Map.of("error", "Unknown state: UNSUPPORTED_STATUS",
                        "errorMessage", e.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
