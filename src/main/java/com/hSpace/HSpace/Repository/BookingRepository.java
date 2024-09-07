package com.hSpace.HSpace.Repository;

import com.hSpace.HSpace.Entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByRoomId(Long roomId);
    List<Booking> findByBookingConfirmationCode(String bookingConfirmationCode);
    List<Booking> findByUserId(Long userId);
}
