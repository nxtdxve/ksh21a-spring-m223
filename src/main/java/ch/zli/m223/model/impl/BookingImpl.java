package ch.zli.m223.model.impl;

import ch.zli.m223.model.Booking;
import ch.zli.m223.model.BookingType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity(name = "Booking")
public class BookingImpl implements Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private BookingType bookingType;

    private String status;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public LocalDate getDate() {
        return date;
    }

    @Override
    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public BookingType getBookingType() {
        return bookingType;
    }

    @Override
    public void setBookingType(BookingType bookingType) {
        this.bookingType = bookingType;
    }

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public void setStatus(String status) {
        this.status = status;
    }
}
