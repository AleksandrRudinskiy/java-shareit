package ru.practicum.request;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.request.model.ItemRequest;

public interface RequestRepository extends JpaRepository<ItemRequest, Long> {

}
