package ru.practicum.shareit.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.comment.model.Comment;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(value = " select * from comments c " +
            "join users u on c.author_id = u.id " +
            "where c.item_id = ?1 ", nativeQuery = true)
    List<Comment> findByItem_Id(Long itemId);
}
