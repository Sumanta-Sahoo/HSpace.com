package com.hSpace.HSpace.Service.IService;

import com.hSpace.HSpace.DTO.Response;
import com.hSpace.HSpace.Entity.Booking;

public interface IBookingService {

    Response saveBooking(Long roomId, Long userId, Booking bookingRequest);
    Response findBookingByConfirmationCode(String confirmationCode);
    Response getAllBookings();
    Response cancelBooking(Long bookingId);
}
