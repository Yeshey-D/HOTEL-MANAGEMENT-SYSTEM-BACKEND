package com.Java.hotelmanagementsystem.room.controller;

import com.Java.hotelmanagementsystem.room.model.Room;
import com.Java.hotelmanagementsystem.room.service.RoomService;
import com.Java.hotelmanagementsystem.util.constants.ExceptionConstants;
import com.Java.hotelmanagementsystem.util.exception.GlobalExceptionWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rooms")
@PreAuthorize("hasAuthority('ADMIN')")  // Ensures only admin can access these endpoints
public class RoomController {

    @Autowired
    private RoomService roomService;

    @PostMapping
    public ResponseEntity<Room> createRoom(@Validated @RequestBody Room room) {
        try {
            Room savedRoom = roomService.save(room);
            return ResponseEntity.ok(savedRoom);
        } catch (AccessDeniedException e) {
            throw new GlobalExceptionWrapper.UnauthorizedAccessException(ExceptionConstants.UNAUTHORIZED_OPERATION);
        }
    }

    @GetMapping
    public ResponseEntity<List<Room>> getAllRooms() {
        List<Room> rooms = roomService.findAll();
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Room> getTheatreById(@PathVariable long id) {
        Room theatre = roomService.findById(id);
        return ResponseEntity.ok(theatre);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Room> updateRoom(@PathVariable Long id, @Validated @RequestBody Room roomDetails) {
        Room updatedRoom = roomService.updateRoom(id, roomDetails);
        return ResponseEntity.ok(updatedRoom);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRoom(@PathVariable long id) {
        String message = roomService.deleteById(id);
        return ResponseEntity.ok(message);
    }

}