package com.Java.hotelmanagementsystem.reservation.repository;

import com.Java.hotelmanagementsystem.reservation.model.Reservation;
import com.Java.hotelmanagementsystem.reservation.model.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("SELECT r FROM Reservation r WHERE r.room.id = :roomId " +
            "AND r.status != 'CANCELLED' " +
            "AND ((r.checkInDate < :checkOut) AND (r.checkOutDate > :checkIn))")
    List<Reservation> findOverlappingReservations(
            @Param("roomId") Long roomId,
            @Param("checkIn") LocalDateTime checkIn,
            @Param("checkOut") LocalDateTime checkOut
    );

    // Alternative method
    List<Reservation> findByRoomIdAndStatusNotAndCheckInDateBeforeAndCheckOutDateAfter(
            Long roomId,
            ReservationStatus status,  // Use enum here
            LocalDateTime checkOut,
            LocalDateTime checkIn
    );

    List<Reservation> findByUserId(Long userId);

    List<Reservation> findByStatus(ReservationStatus status);  // Use enum here
}
