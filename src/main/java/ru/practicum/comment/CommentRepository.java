package ru.practicum.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.comment.model.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(value = " select * from comments c " +
            "join users u on c.author_id = u.id " +
            "where c.item_id = ?1 ", nativeQuery = true)
    List<Comment> findByItem_Id(Long itemId);
}
