package ru.practicum.request;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.request.model.ItemRequest;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<ItemRequest, Long> {

    List<ItemRequest> getByRequestorId(long requestorId);

    @Query(value = "select * from requests " +
            "where id = ?1 ", nativeQuery = true)
    ItemRequest getByRequestId(long id);


    @Query(value = "select * from requests " +
            "where requestor_id <> ?1 ", nativeQuery = true)
    Page<ItemRequest> findAllNotRequestorId(long requestorId, Pageable page);

}
