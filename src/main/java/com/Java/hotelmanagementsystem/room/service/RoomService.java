package com.Java.hotelmanagementsystem.room.service;

import com.Java.hotelmanagementsystem.room.model.Room;
import com.Java.hotelmanagementsystem.room.model.RoomDTO;
import com.Java.hotelmanagementsystem.room.model.RoomResponseDTO;
import com.Java.hotelmanagementsystem.room.repository.RoomRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomService implements IRoomService {

    private final RoomRepository roomRepository;

    public boolean postRoom(RoomDTO roomDTO) {
        try{
            Room room = new Room();

            room.setName(roomDTO.getName());
            room.setPrice(roomDTO.getPrice());
            room.setType(roomDTO.getType());
            room.setAvailable(true);

            roomRepository.save(room);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public RoomResponseDTO getAllRooms(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 6);
        Page<Room> roomPage = roomRepository.findAll(pageable);

        RoomResponseDTO roomResponseDTO = new RoomResponseDTO();

        roomResponseDTO.setPageNumber(roomPage.getPageable().getPageNumber());
        roomResponseDTO.setTotalPages(roomPage.getTotalPages());
        roomResponseDTO.setRoomDTOList(roomPage.stream().map(Room::getRoomDto).collect(Collectors.toList()));

        return roomResponseDTO;
    }

    public RoomDTO getRoomById(Long id) {
        Optional<Room> optionalRoom = roomRepository.findById(id);
        if (optionalRoom.isPresent()){
            return optionalRoom.get().getRoomDto();
        } else {
            throw new EntityNotFoundException("Room not found!");
        }
    }

    public boolean updateRoom(Long id, RoomDTO roomDTO) {
        Optional<Room> optionalRoom = roomRepository.findById(id);

        if (optionalRoom.isPresent()){
            Room existingRoom = optionalRoom.get();

            existingRoom.setName(roomDTO.getName());
            existingRoom.setPrice(roomDTO.getPrice());
            existingRoom.setType(roomDTO.getType());

            roomRepository.save(existingRoom);
            return true;
        }
        return false;
    }

    public void deleteRoom(Long id) {

        Optional<Room> optionalRoom = roomRepository.findById(id);

        if (optionalRoom.isPresent()) {
            roomRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Room does not exist!");
        }
    }
}
