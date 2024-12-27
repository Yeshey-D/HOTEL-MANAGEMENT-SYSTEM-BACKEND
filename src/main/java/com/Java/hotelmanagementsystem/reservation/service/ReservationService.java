package com.Java.hotelmanagementsystem.reservation.service;

import com.Java.hotelmanagementsystem.reservation.model.Reservation;
import com.Java.hotelmanagementsystem.reservation.model.ReservationStatus;
import com.Java.hotelmanagementsystem.reservation.repository.ReservationRepository;
import com.Java.hotelmanagementsystem.room.service.RoomService;
import com.Java.hotelmanagementsystem.user.service.UserService;
import com.Java.hotelmanagementsystem.util.constants.ExceptionConstants;
import com.Java.hotelmanagementsystem.util.exception.GlobalExceptionWrapper;
import com.Java.hotelmanagementsystem.util.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservationService implements IReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private RoomService roomService;

    @Autowired
    private UserService userService;

    @Override
    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    @Override
    public Reservation save(Reservation reservation) {
        try {
            // Validate if room and user exist
            roomService.findById(reservation.getRoom().getId());
            userService.findById(reservation.getUser().getId());

            // Check room availability
            if (!checkRoomAvailability(reservation.getRoom().getId(),
                    reservation.getCheckInDate(),
                    reservation.getCheckOutDate())) {
                throw new GlobalExceptionWrapper.BadRequestException(
                        ExceptionConstants.ROOM_NOT_AVAILABLE);
            }

            // Set initial status
            reservation.setStatus(ReservationStatus.PENDING);
            reservation.setBookingDate(LocalDateTime.now());

            return reservationRepository.save(reservation);
        } catch (DataIntegrityViolationException e) {
            throw new GlobalExceptionWrapper.BadRequestException(
                    ExceptionConstants.RESERVATION_ALREADY_EXISTS);
        }
    }

    @Override
    public Reservation fetchById(long id) {
        return findById(id);
    }

    @Override
    public Reservation findById(long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Reservation not found with id: " + id));
    }

    @Override
    public String update(long id, Reservation reservation) {
        Reservation existingReservation = findById(id);
        updateReservation(id, reservation);
        return "Reservation updated successfully";
    }

    @Override
    public String deleteById(long id) {
        Reservation reservation = findById(id);
        if (reservation.getStatus() == ReservationStatus.CHECKED_IN) {
            throw new GlobalExceptionWrapper.BadRequestException(
                    ExceptionConstants.CANNOT_DELETE_ACTIVE_RESERVATION);
        }
        reservationRepository.delete(reservation);
        return "Reservation deleted successfully";
    }

    @Override
    public Reservation updateReservation(Long id, Reservation reservationDetails) {
        Reservation existingReservation = findById(id);

        // Don't allow updates to checked-in reservations
        if (existingReservation.getStatus() == ReservationStatus.CHECKED_IN) {
            throw new GlobalExceptionWrapper.BadRequestException(
                    ExceptionConstants.CANNOT_UPDATE_ACTIVE_RESERVATION);
        }

        // If dates are being updated, check availability
        if (!existingReservation.getCheckInDate().equals(reservationDetails.getCheckInDate()) ||
                !existingReservation.getCheckOutDate().equals(reservationDetails.getCheckOutDate())) {

            if (!checkRoomAvailability(reservationDetails.getRoom().getId(),
                    reservationDetails.getCheckInDate(),
                    reservationDetails.getCheckOutDate())) {
                throw new GlobalExceptionWrapper.BadRequestException(
                        ExceptionConstants.ROOM_NOT_AVAILABLE);
            }
        }

        // Update the reservation
        existingReservation.setRoom(reservationDetails.getRoom());
        existingReservation.setUser(reservationDetails.getUser());
        existingReservation.setCheckInDate(reservationDetails.getCheckInDate());
        existingReservation.setCheckOutDate(reservationDetails.getCheckOutDate());
        existingReservation.setStatus(reservationDetails.getStatus());
        existingReservation.setTotalPrice(reservationDetails.getTotalPrice());
        existingReservation.setLastModifiedDate(LocalDateTime.now());

        return reservationRepository.save(existingReservation);
    }

    @Override
    public boolean checkRoomAvailability(Long roomId, LocalDateTime checkIn, LocalDateTime checkOut) {
        // Validate dates
        if (checkIn == null || checkOut == null || checkIn.isAfter(checkOut)) {
            throw new IllegalArgumentException("Invalid date range");
        }

        List<Reservation> conflictingReservations = reservationRepository
                .findOverlappingReservations(roomId, checkIn, checkOut);

        return conflictingReservations.isEmpty();
    }
}
