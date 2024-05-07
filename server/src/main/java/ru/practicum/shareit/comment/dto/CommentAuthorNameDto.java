package ru.practicum.shareit.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentAuthorNameDto {
    private long id;
    private String text;
    private long authorId;
    private String authorName;
    private LocalDateTime created;
}
