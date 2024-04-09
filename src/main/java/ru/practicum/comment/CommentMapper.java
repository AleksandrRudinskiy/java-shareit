package ru.practicum.comment;

import ru.practicum.comment.dto.CommentAuthorNameDto;
import ru.practicum.comment.dto.CommentDto;
import ru.practicum.comment.model.Comment;
import ru.practicum.item.model.Item;
import ru.practicum.user.model.User;

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
