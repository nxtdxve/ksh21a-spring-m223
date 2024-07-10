package ch.zli.m223.model;

import java.time.LocalDate;

public interface Booking {
    Long getId();
    void setId(Long id);

    Long getUserId();
    void setUserId(Long userId);

    LocalDate getDate();
    void setDate(LocalDate date);

    BookingType getBookingType();
    void setBookingType(BookingType bookingType);

    String getStatus();
    void setStatus(String status);
}
