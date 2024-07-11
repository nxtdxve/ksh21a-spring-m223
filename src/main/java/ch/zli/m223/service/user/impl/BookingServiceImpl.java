package ch.zli.m223.service.user.impl;

import ch.zli.m223.controller.booking.dto.BookingRequestDto;
import ch.zli.m223.model.impl.AppUserImpl;
import ch.zli.m223.model.impl.BookingImpl;
import ch.zli.m223.repository.BookingRepository;
import ch.zli.m223.repository.UserRepository;
import ch.zli.m223.service.user.BookingService;
import ch.zli.m223.service.user.exception.UnauthorizedAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public BookingImpl createBooking(BookingRequestDto bookingRequestDto) {
        BookingImpl booking = new BookingImpl();
        booking.setDate(bookingRequestDto.getDate());
        booking.setBookingType(bookingRequestDto.getBookingType());
        booking.setStatus("Pending");

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AppUserImpl user = (AppUserImpl) userRepository.findByEmail(userDetails.getUsername()).orElseThrow(() -> new NoSuchElementException("User not found"));
        booking.setUserId(user.getId());

        return bookingRepository.save(booking);
    }

    @Override
    public String getBookingStatus(Long id) {
        BookingImpl booking = bookingRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Booking not found"));

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AppUserImpl user = (AppUserImpl) userRepository.findByEmail(userDetails.getUsername()).orElseThrow(() -> new NoSuchElementException("User not found"));
        if (!booking.getUserId().equals(user.getId())) {
            throw new UnauthorizedAccessException();
        }

        return booking.getStatus();
    }

    @Override
    public void cancelBooking(Long id) {
        BookingImpl booking = bookingRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Booking not found"));

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AppUserImpl user = (AppUserImpl) userRepository.findByEmail(userDetails.getUsername()).orElseThrow(() -> new NoSuchElementException("User not found"));
        if (!booking.getUserId().equals(user.getId())) {
            throw new UnauthorizedAccessException();
        }

        bookingRepository.delete(booking);
    }

    @Override
    public BookingImpl getBookingById(Long id) {
        return bookingRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Booking not found"));
    }

    @Override
    public void updateBookingStatus(Long id, String status) {
        BookingImpl booking = bookingRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Booking not found"));
        booking.setStatus(status);
        bookingRepository.save(booking);
    }

    @Override
    public void deleteBooking(Long id) {
        BookingImpl booking = bookingRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Booking not found"));
        bookingRepository.delete(booking);
    }

    @Override
    public Map<String, Long> getWeeklyReport(LocalDate date) {
        LocalDate startOfWeek = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate endOfWeek = date.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));

        List<BookingImpl> bookings = bookingRepository.findAllByDateBetween(startOfWeek, endOfWeek);

        long confirmed = bookings.stream().filter(booking -> "Confirmed".equals(booking.getStatus())).count();
        long rejected = bookings.stream().filter(booking -> "Rejected".equals(booking.getStatus())).count();
        long pending = bookings.stream().filter(booking -> "Pending".equals(booking.getStatus())).count();

        Map<String, Long> report = new HashMap<>();
        report.put("Confirmed", confirmed);
        report.put("Rejected", rejected);
        report.put("Pending", pending);

        return report;
    }
}
