package com.hSpace.HSpace.Service.Impl;

import com.hSpace.HSpace.DTO.BookingDTO;
import com.hSpace.HSpace.DTO.Response;
import com.hSpace.HSpace.Entity.Booking;
import com.hSpace.HSpace.Entity.Room;
import com.hSpace.HSpace.Entity.User;
import com.hSpace.HSpace.Exception.CustomException;
import com.hSpace.HSpace.Repository.BookingRepository;
import com.hSpace.HSpace.Repository.RoomRepository;
import com.hSpace.HSpace.Repository.UserRepository;
import com.hSpace.HSpace.Service.IService.IBookingService;
import com.hSpace.HSpace.Service.IService.IRoomService;
import com.hSpace.HSpace.Utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingServiceImpl implements IBookingService {

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private IRoomService roomService;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Response saveBooking(Long roomId, Long userId, Booking bookingRequest) {
        Response response = new Response();
        try{
            if(bookingRequest.getCheckInDate().isAfter(bookingRequest.getCheckOutDate())){
                throw new IllegalArgumentException("Check in date must come before check out date");
            }
            Room room = roomRepository.findById(roomId).orElseThrow(()-> new CustomException("Room Not Found with id : " + roomId));
            User user = userRepository.findById(userId).orElseThrow(()-> new CustomException("User Not Found with id : " + userId));

            List<Booking> existingBooking = room.getBookings();
            if(!roomIsAvailable(bookingRequest, existingBooking)){
                throw new CustomException("Room Not Available For Selected Date Range");
            }
            bookingRequest.setRoom(room);
            bookingRequest.setUser(user);
            String bookingConfirmationCode = Utils.generateAlpnanumeric(10);
            bookingRequest.setBookingConfirmationCode(bookingConfirmationCode);
            bookingRepository.save(bookingRequest);
            response.setStatusCode(200);
            response.setBookingConfirmationCode(bookingConfirmationCode);
            response.setMessage("Successfull");
        }catch (CustomException ce){
            response.setStatusCode(404);
            response.setMessage(ce.getMessage());
        }catch (Exception e){
            e.printStackTrace();
            response.setStatusCode(500);
            response.setMessage("Error Occurred During save bookings");
        }
        return response;
    }

    @Override
    public Response findBookingByConfirmationCode(String confirmationCode) {
        Response response = new Response();
        try{
            Booking booking = bookingRepository.findByBookingConfirmationCode(confirmationCode).orElseThrow(()-> new CustomException("Booking not found with code : " + confirmationCode));
            BookingDTO bookingDTO = Utils.mapBookingEntityToBookingDTOPlusBookedRoom(booking, true);
            response.setStatusCode(200);
            response.setMessage("Successfull");
            response.setBooking(bookingDTO);
        }catch (CustomException ce){
            response.setStatusCode(404);
            response.setMessage(ce.getMessage());
        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error find confirmation code");
        }
        return response;
    }

    @Override
    public Response getAllBookings() {
        Response response = new Response();
        try{
            List<Booking> bookingList = bookingRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
            List<BookingDTO> bookingDTOList = Utils.mapBookingListEntityToBookingDTOList(bookingList);
            response.setStatusCode(200);
            response.setMessage("Successfull");
            response.setBookingDTOList(bookingDTOList);
        }catch (CustomException ce){
            response.setStatusCode(404);
            response.setMessage(ce.getMessage());
        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error find confirmation code");
        }
        return response;
    }

    @Override
    public Response cancelBooking(Long bookingId) {
        Response response = new Response();
        try{
            bookingRepository.findById(bookingId).orElseThrow(()-> new CustomException("Booking Not Found with id : " + bookingId));
            bookingRepository.deleteById(bookingId);
            response.setStatusCode(200);
            response.setMessage("Successfull");
        }catch (CustomException ce){
            response.setStatusCode(404);
            response.setMessage(ce.getMessage());
        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error cancelling booking with id : " + bookingId + e.getMessage());
        }
        return response;
    }

    private boolean roomIsAvailable(Booking bookingRequest, List<Booking> existingBookings){

        return existingBookings.stream()
                .noneMatch(
                        existingBooking ->
                                bookingRequest.getCheckInDate().equals(existingBooking.getCheckInDate())
                                        || bookingRequest.getCheckOutDate().isBefore(existingBooking.getCheckOutDate())
                                        || (bookingRequest.getCheckInDate().isAfter(existingBooking.getCheckInDate())
                                        && bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckOutDate()))
                                        || (bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckInDate())

                                        && bookingRequest.getCheckOutDate().equals(existingBooking.getCheckOutDate()))
                                        || (bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckInDate())

                                        && bookingRequest.getCheckOutDate().isAfter(existingBooking.getCheckOutDate()))

                                        || (bookingRequest.getCheckInDate().equals(existingBooking.getCheckOutDate())
                                        && bookingRequest.getCheckOutDate().equals(existingBooking.getCheckInDate()))

                                        || (bookingRequest.getCheckInDate().equals(existingBooking.getCheckOutDate())
                                        && bookingRequest.getCheckOutDate().equals(bookingRequest.getCheckInDate()))
                );
    }
}
