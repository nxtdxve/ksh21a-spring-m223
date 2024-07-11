package ch.zli.m223.controller.booking;

import ch.zli.m223.controller.booking.dto.BookingRequestDto;
import ch.zli.m223.model.impl.BookingImpl;
import ch.zli.m223.service.user.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/admin/bookings")
public class BookingAdminController {

    @Autowired
    private BookingService bookingService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getBooking(@PathVariable Long id) {
        try {
            BookingImpl booking = bookingService.getBookingById(id);
            return ResponseEntity.status(HttpStatus.OK).body(booking);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Booking not found"));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBooking(@PathVariable Long id, @RequestBody Map<String, String> updateRequest) {
        try {
            String status = updateRequest.get("status");
            if (!status.equals("Confirmed") && !status.equals("Rejected")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Invalid status"));
            }
            bookingService.updateBookingStatus(id, status);
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Action successful"));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Booking not found"));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBooking(@PathVariable Long id) {
        try {
            bookingService.deleteBooking(id);
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Action successful"));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Booking not found"));
        }
    }

    @PostMapping("")
    public ResponseEntity<?> createBooking(@RequestBody BookingRequestDto bookingRequestDto) {
        try {
            bookingService.createBooking(bookingRequestDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "Booking request created", "status", "Pending"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/report")
    public ResponseEntity<?> getWeeklyReport(@RequestParam("date") String date) {
        try {
            Map<String, Long> report = bookingService.getWeeklyReport(LocalDate.parse(date));
            return ResponseEntity.status(HttpStatus.OK).body(report);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }
}
