package com.hSpace.HSpace.Service.Impl;

import com.hSpace.HSpace.DTO.Response;
import com.hSpace.HSpace.DTO.RoomDTO;
import com.hSpace.HSpace.Entity.Room;
import com.hSpace.HSpace.Exception.CustomException;
import com.hSpace.HSpace.Repository.RoomRepository;
import com.hSpace.HSpace.Service.AwsS3Service;
import com.hSpace.HSpace.Service.IService.IRoomService;
import com.hSpace.HSpace.Utils.JWTUtils;
import com.hSpace.HSpace.Utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class RoomServiceImpl implements IRoomService {

    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private AwsS3Service awsS3Service;


    @Override
    public Response addNewRoom(MultipartFile photo, String roomType, BigDecimal roomPrice, String roomDescription) {
        Response response = new Response();
        try{
            String imageUrl = awsS3Service.saveImageToS3(photo);
            Room room = new Room();
            room.setRoomPhotoUrl(imageUrl);
            room.setRoomType(roomType);
            room.setRoomDescription(roomDescription);
            room.setRoomPrice(roomPrice);
            Room savedRoom = roomRepository.save(room);
            RoomDTO roomDTO = Utils.mapRoomEntityToRoomDTO(savedRoom);
            response.setStatusCode(200);
            response.setMessage("Successfull");
            response.setRoom(roomDTO);

        }catch(CustomException ce){
            response.setStatusCode(404);
            response.setMessage(ce.getMessage());
        }catch(Exception e){
            response.setStatusCode(500);
            response.setMessage("Error Occurred During Add New Room");
        }
        return response;
    }

    @Override
    public List<String> getAllRoomTypes() {
        return roomRepository.findDistinctRoomTypes();
    }

    @Override
    public Response getAllRooms() {
        Response response = new Response();
        try{
            List<Room> roomList = roomRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
            List<RoomDTO> roomDTOList = Utils.mapRoomListEntityToRoomDTOList(roomList);
            response.setStatusCode(200);
            response.setMessage("Successfull");
            response.setRoomList(roomDTOList);

        }catch(CustomException ce){
            response.setStatusCode(404);
            response.setMessage(ce.getMessage());
        }catch(Exception e){
            response.setStatusCode(500);
            response.setMessage("Error Occurred During Add New Room");
        }
        return response;
    }

    @Override
    public Response deleteRoom(Long roomId) {
        Response response = new Response();
        try{
            roomRepository.findById(roomId).orElseThrow(()-> new CustomException("Room Not Found with id : " + roomId));
            roomRepository.deleteById(roomId);
            response.setStatusCode(200);
            response.setMessage("Successfull");
        }catch(CustomException ce){
            response.setStatusCode(404);
            response.setMessage(ce.getMessage());
        }catch(Exception e){
            response.setStatusCode(500);
            response.setMessage("Error Occurred During Add New Room");
        }
        return response;
    }

    @Override
    public Response updateRoom(Long roomId, String roomDescription,String roomType, BigDecimal roomPrice, MultipartFile photo) {
        Response response = new Response();
        try{
            String imageUrl = null;
            if(photo!=null && !photo.isEmpty()){
                imageUrl = awsS3Service.saveImageToS3(photo);
            }
            Room room = roomRepository.findById(roomId).orElseThrow(()-> new CustomException("Room Not Found with : " + roomId));
            if(roomType!=null) room.setRoomType(roomType);
            if(roomDescription!=null) room.setRoomDescription(roomDescription);
            if(roomPrice!=null) room.setRoomPrice(roomPrice);
            if(imageUrl!=null) room.setRoomPhotoUrl(imageUrl);

            Room updatedRoom = roomRepository.save(room);
            RoomDTO roomDTO = Utils.mapRoomEntityToRoomDTO(updatedRoom);
            response.setStatusCode(200);
            response.setMessage("Successfull");
            response.setRoom(roomDTO);


        }catch(CustomException ce){
            response.setStatusCode(404);
            response.setMessage(ce.getMessage());
        }catch(Exception e){
            response.setStatusCode(500);
            response.setMessage("Error Occurred During Add New Room");
        }
        return response;
    }

    @Override
    public Response getRoomById(Long roomId) {
        Response response = new Response();
        try{
            Room room = roomRepository.findById(roomId).orElseThrow(()-> new CustomException("Room Not Found with id : " + roomId));
            RoomDTO roomDTO = Utils.mapRoomEntityToRoomDTOPlusBookings(room);
            response.setStatusCode(200);
            response.setMessage("Successfull");
            response.setRoom(roomDTO);

        }catch(CustomException ce){
            response.setStatusCode(404);
            response.setMessage(ce.getMessage());
        }catch(Exception e){
            response.setStatusCode(500);
            response.setMessage("Error Occurred During Add New Room");
        }
        return response;
    }

    @Override
    public Response getAvailableRoomByDatesAndTypes(LocalDate checkInDate, LocalDate checkOutDate, String roomType) {
        Response response = new Response();
        try{
            List<Room> availableRoomsList = roomRepository.findAvailableRoomsByDatesAndTypes(checkInDate, checkOutDate, roomType);
            List<RoomDTO> availableRoomsDtoList = Utils.mapRoomListEntityToRoomDTOList(availableRoomsList);
            for(RoomDTO elem : availableRoomsDtoList){
                System.out.println(elem.toString());
            }
            response.setStatusCode(200);
            response.setMessage("Successfull");
            response.setRoomList(availableRoomsDtoList);

        }catch(Exception e){
            response.setStatusCode(500);
            response.setMessage("Error Occurred During Add New Room");
        }
        return response;
    }

    @Override
    public Response getAvailableRooms() {
        Response response = new Response();
        try{
            List<Room> availableRoomList = roomRepository.getAllAvailableRooms();
            List<RoomDTO> availableRoomDTOList = Utils.mapRoomListEntityToRoomDTOList(availableRoomList);
            response.setStatusCode(200);
            response.setMessage("Successfull");
            response.setRoomList(availableRoomDTOList);
        }catch(CustomException ce){
            response.setStatusCode(404);
            response.setMessage(ce.getMessage());
        }catch(Exception e){
            response.setStatusCode(500);
            response.setMessage("Error Occurred During Add New Room");
        }
        return response;
    }
}
