package entelect.training.incubator.spring.booking.controller;

import entelect.training.incubator.spring.booking.model.Booking;
import entelect.training.incubator.spring.booking.model.BookingResponse;
import entelect.training.incubator.spring.booking.model.BookingSearchRequest;
import entelect.training.incubator.spring.booking.service.BookingsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("bookings")
public class BookingController {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private final BookingsService bookingsService;

    @Autowired
    public BookingController(BookingsService bookingsService) {
        this.bookingsService = bookingsService;
    }

    @PostMapping(path = "/search")
    public ResponseEntity<?> searchBookings(@RequestBody BookingSearchRequest bookingSearchRequest) {
        LOGGER.info("Processing booking search request for request {}", bookingSearchRequest);
        List<Booking> bookings = bookingsService.searchBookings(bookingSearchRequest);
        if (bookings == null || bookings.isEmpty()) {
            LOGGER.trace("Bookings not found");
            return ResponseEntity.notFound().build();
        }

        LOGGER.trace("Found bookings");
        return ResponseEntity.ok(bookings);
    }

    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody Booking booking) {
        LOGGER.info("Processing booking creation request for customer={} and flight={}", booking.getCustomerId(), booking.getFlightId());
        Booking savedBooking = bookingsService.createBooking(booking);
        LOGGER.trace("Booking created");

        return ResponseEntity.ok(savedBooking);
    }

    @GetMapping
    public ResponseEntity<?> getAllBookings() {
        List<BookingResponse> bookings = bookingsService.getBookings();
        if (bookings == null || bookings.isEmpty()) {
            LOGGER.trace("Bookings not found");
            return ResponseEntity.notFound().build();
        }

        LOGGER.trace("Found bookings");
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getBookingById(@PathVariable Integer id) {
        LOGGER.info("Processing booking search request for booking id={}", id);
        BookingResponse bookingById = bookingsService.getBookingById(id);
        if (bookingById == null) {
            LOGGER.trace("Booking not found");
            return ResponseEntity.notFound().build();
        }

        LOGGER.trace("Found booking");
        return ResponseEntity.ok(bookingById);
    }


}
