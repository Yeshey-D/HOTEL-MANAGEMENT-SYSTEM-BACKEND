package com.Java.hotelmanagementsystem.room.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Room {

    @Id
    @GeneratedValue()
    private Long id;

    private String name;

    private String type;

    private Long price;

    private boolean available;

    public RoomDTO getRoomDto() {
        RoomDTO roomDto = new RoomDTO();

        roomDto.setId(id);
        roomDto.setName(name);
        roomDto.setType(type);
        roomDto.setPrice(price);

        return roomDto;
    }
}
