package ch.zli.m223.service.user;

import ch.zli.m223.controller.booking.dto.BookingRequestDto;
import ch.zli.m223.model.impl.BookingImpl;

public interface BookingService {
    BookingImpl createBooking(BookingRequestDto bookingRequestDto);
    String getBookingStatus(Long id);
    void cancelBooking(Long id);
    BookingImpl getBookingById(Long id);
    void updateBookingStatus(Long id, String status);
    void deleteBooking(Long id);
}