package com.Java.hotelmanagementsystem.room.service;

import com.Java.hotelmanagementsystem.room.model.RoomDTO;
import com.Java.hotelmanagementsystem.room.model.RoomResponseDTO;

public interface IRoomService {

    boolean postRoom(RoomDTO roomDTO);

    RoomResponseDTO getAllRooms(int pageNumber);

    RoomDTO getRoomById(Long id);

    boolean updateRoom(Long id, RoomDTO roomDTO);

    void deleteRoom(Long id);
}
