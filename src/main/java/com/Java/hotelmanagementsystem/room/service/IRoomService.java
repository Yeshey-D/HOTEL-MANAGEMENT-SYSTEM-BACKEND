package com.Java.hotelmanagementsystem.room.service;

import com.Java.hotelmanagementsystem.room.model.Room;
import com.Java.hotelmanagementsystem.util.IGenericCrudService;

public interface IRoomService extends IGenericCrudService<Room, Room> {
    Room findById(long id);
    Room updateRoom(Long id, Room roomDetails);
}
