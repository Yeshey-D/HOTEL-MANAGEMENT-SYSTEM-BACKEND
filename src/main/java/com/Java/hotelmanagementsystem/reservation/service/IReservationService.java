package com.Java.hotelmanagementsystem.reservation.service;

import com.Java.hotelmanagementsystem.reservation.model.Reservation;
import com.Java.hotelmanagementsystem.util.IGenericCrudService;

import java.time.LocalDateTime;
import java.util.List;

public interface IReservationService {
    List<Reservation> findAll();
    Reservation save(Reservation reservation);
    Reservation fetchById(long id);
    Reservation findById(long id);
    String update(long id, Reservation reservation);
    String deleteById(long id);
    Reservation updateReservation(Long id, Reservation reservationDetails);
    boolean checkRoomAvailability(Long roomId, LocalDateTime checkIn, LocalDateTime checkOut);
}