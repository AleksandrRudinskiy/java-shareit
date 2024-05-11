package ru.practicum.shareit.booking;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.booking.dto.BookingInfo;
import ru.practicum.shareit.booking.model.Booking;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query(value = " select * from bookings " +
            "join items i on bookings.item_id = i.id " +
            "where booker_id = ?1 " +
            "order by end_date desc", nativeQuery = true)
    List<Booking> getByBookerId(long bookerId, Pageable page);

    @Query(value = " select * from bookings " +
            "join items i on bookings.item_id = i.id " +
            "where owner_id = ?1 " +
            "order by start_date desc", nativeQuery = true)
    List<Booking> getOwnerBookings(long userId, Pageable page);

    List<Booking> findByBooker_Id(Long bookerId);

    List<BookingInfo> findByItem_Id(Long itemId);

}
