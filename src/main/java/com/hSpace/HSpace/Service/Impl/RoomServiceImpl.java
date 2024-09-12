package com.hSpace.HSpace.Service.Impl;

import com.hSpace.HSpace.DTO.Response;
import com.hSpace.HSpace.Entity.Room;
import com.hSpace.HSpace.Exception.CustomException;
import com.hSpace.HSpace.Repository.RoomRepository;
import com.hSpace.HSpace.Service.IService.IRoomService;
import com.hSpace.HSpace.Utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
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


    @Override
    public Response addNewRoom(MultipartFile photo, String roomType, BigDecimal roomPrice, String roomDescription) {
        Response response = new Response();
        try{

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
        return List.of();
    }

    @Override
    public Response getAllRooms() {
        Response response = new Response();
        try{

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
    public Response updateRoom(Long roomId, String roomType, BigDecimal roomPrice, MultipartFile photo) {
        Response response = new Response();
        try{

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
    public Response getAvailableRooms() {
        Response response = new Response();
        try{

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
