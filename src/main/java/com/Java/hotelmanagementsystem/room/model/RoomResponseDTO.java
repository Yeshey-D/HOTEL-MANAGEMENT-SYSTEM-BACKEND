package com.Java.hotelmanagementsystem.room.model;

import lombok.Data;

import java.util.List;

@Data
public class RoomResponseDTO {

    private List<RoomDTO> roomDTOList;

    private Integer totalPages;

    private Integer pageNumber;
}
