package com.hSpace.HSpace.Service.IService;

import com.hSpace.HSpace.DTO.Response;
import com.hSpace.HSpace.Entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface IRoomService {

    Response addNewRoom(MultipartFile photo, String roomType, BigDecimal roomPrice, String roomDescription);
    List<String> getAllRoomTypes();
    Response getAllRooms();
    Response deleteRoom(Long roomId);
    Response updateRoom(Long roomId, String roomType, BigDecimal roomPrice, MultipartFile photo);
    Response getRoomById(Long roomId);
    Response getAvailableRoomByDatesAndTypes(LocalDate checkInDate, LocalDate checkOutDate, String roomType);
    Response getAvailableRooms();

}
