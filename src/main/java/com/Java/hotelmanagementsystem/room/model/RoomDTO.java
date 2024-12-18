package com.Java.hotelmanagementsystem.room.model;

import lombok.Data;

@Data
public class RoomDTO {

    private Long id;

    private String name;

    private String type;

    private Long price;

    private boolean available;
}
