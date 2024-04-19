package ru.practicum.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.booking.dto.BookingInfo;
import ru.practicum.booking.model.Booking;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query(value = " select * from bookings b " +
            "join items i on b.item_id = i.id " +
            "where owner_id = ?1 " +
            "order by start_date desc", nativeQuery = true)
    List<Booking> getOwnerBookings(long userId);

    @Query(value = " select * from bookings b " +
            "join items i on b.item_id = i.id " +
            "where booker_id = ?1 " +
            "order by start_date desc", nativeQuery = true)
    List<Booking> getBookerBookings(long userId);

    List<Booking> findByBooker_Id(Long bookerId);

    List<BookingInfo> findByItem_Id(Long itemId);

}
