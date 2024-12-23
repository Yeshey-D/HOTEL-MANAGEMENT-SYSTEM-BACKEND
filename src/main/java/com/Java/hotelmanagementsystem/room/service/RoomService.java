package com.Java.hotelmanagementsystem.room.service;

import com.Java.hotelmanagementsystem.room.model.Room;
import com.Java.hotelmanagementsystem.room.repository.RoomRepository;
import com.Java.hotelmanagementsystem.util.constants.ExceptionConstants;
import com.Java.hotelmanagementsystem.util.exception.GlobalExceptionWrapper;
import com.Java.hotelmanagementsystem.util.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
import java.util.List;

@Service
public class RoomService implements IRoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Override
    public List<Room> findAll() {
        // Fetch all theaters from the database
        return roomRepository.findAll();
    }

    @Override
    public Room save(Room room) {
        try {
            return roomRepository.save(room);
        } catch (DataIntegrityViolationException e) {
            throw new GlobalExceptionWrapper.BadRequestException(ExceptionConstants.ROOM_NAME_EXISTS);
        }
    }
    @Override
    public Room fetchById(long id) {
        return findById(id);
    }

    @Override
    public Room findById(long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with id: " + id));
    }

    @Override
    public String update(long id, Room room) {
        Room existingTheatre = findById(id);
        updateRoom(id, room);
        return "Room updated successfully";
    }

    @Override
    public String deleteById(long id) {
        Room room = findById(id);
        roomRepository.delete(room);
        return "Room deleted successfully";
    }

    @Override
    public Room updateRoom(Long id, Room roomDetails) {
        Room existingTheatre = findById(id);

        existingTheatre.setName(roomDetails.getName());
        existingTheatre.setType(roomDetails.getType());
        existingTheatre.setPrice(roomDetails.getPrice());
        existingTheatre.setAvailable(roomDetails.isAvailable());

        return roomRepository.save(existingTheatre);
    }

}