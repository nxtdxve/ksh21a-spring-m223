package ch.zli.m223.controller.ticketing.dto;

import ch.zli.m223.model.BookingType;

import java.time.LocalDate;

public class BookingRequestDto {
    private LocalDate date;
    private BookingType bookingType;

    // Getters and Setters
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BookingType getBookingType() {
        return bookingType;
    }

    public void setBookingType(BookingType bookingType) {
        this.bookingType = bookingType;
    }

    // Constructors
    public BookingRequestDto() {}
}
