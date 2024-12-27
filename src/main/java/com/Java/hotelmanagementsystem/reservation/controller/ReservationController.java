package com.Java.hotelmanagementsystem.reservation.controller;



import com.Java.hotelmanagementsystem.reservation.model.Reservation;
import com.Java.hotelmanagementsystem.reservation.service.ReservationService;
import com.Java.hotelmanagementsystem.util.constants.ExceptionConstants;
import com.Java.hotelmanagementsystem.util.exception.GlobalExceptionWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @PostMapping
    public ResponseEntity<Reservation> createReservation(@Validated @RequestBody Reservation reservation) {
        try {
            Reservation savedReservation = reservationService.save(reservation);
            return new ResponseEntity<>(savedReservation, HttpStatus.CREATED);
        } catch (AccessDeniedException e) {
            throw new GlobalExceptionWrapper.UnauthorizedAccessException(ExceptionConstants.UNAUTHORIZED_OPERATION);
        }
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> getAllReservations() {
        List<Reservation> reservations = reservationService.findAll();
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable Long id) {
        Reservation reservation = reservationService.findById(id);
        return new ResponseEntity<>(reservation, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateReservation(@PathVariable Long id, @Validated @RequestBody Reservation reservation) {
        String result = reservationService.update(id, reservation);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReservation(@PathVariable Long id) {
        String result = reservationService.deleteById(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}


