package ru.practicum.shareit.comment;

import lombok.experimental.UtilityClass;
import ru.practicum.shareit.comment.dto.CommentAuthorNameDto;
import ru.practicum.shareit.comment.dto.CommentDto;
import ru.practicum.shareit.comment.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;


@UtilityClass
public class CommentMapper {

    public static CommentDto convertCommentToDto(Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getText(),
                comment.getItem().getId(),
                comment.getAuthor().getId(),
                comment.getAuthor().getName(),
                comment.getCreated());
    }

    public static Comment convertToComment(CommentDto commentDto, Item item, User author) {
        return new Comment(
                commentDto.getId(),
                commentDto.getText(),
                item,
                author,
                commentDto.getCreated()
        );
    }

    public static CommentAuthorNameDto convertToCommentsAuthorNameDto(Comment comment) {
        return new CommentAuthorNameDto(
                comment.getId(),
                comment.getText(),
                comment.getAuthor().getId(),
                comment.getAuthor().getName(),
                comment.getCreated()

        );

    }
}
