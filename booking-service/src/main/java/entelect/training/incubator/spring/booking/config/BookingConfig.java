package entelect.training.incubator.spring.booking.config;

import entelect.training.incubator.spring.booking.model.Booking;
import entelect.training.incubator.spring.booking.repository.BookingRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
public class BookingConfig {
    @Bean
    CommandLineRunner commandLineRunner(BookingRepository bookingRepository) {
        return args -> {
            Booking booking1 = createBooking(1, LocalDateTime.now(), 2, "IssaTest123");
            Booking booking2 = createBooking(2, LocalDateTime.now().plusMonths(3), 4, "Test123Issa");
            Booking booking3 = createBooking(3, LocalDateTime.now().plusDays(22), 6, "123TestIssa");

            List.of(booking1, booking2, booking3)
                    .stream()
                    .filter(booking -> bookingRepository.findBookingByReferenceNumber(booking.getReferenceNumber()).isEmpty())
                    .forEach(bookingRepository::save);

        };
    }

    private Booking createBooking(Integer custId, LocalDateTime bookingDate, Integer flightId, String refNum) {
        Booking booking = new Booking();
        booking.setCustomerId(custId);
        booking.setBookingDate(bookingDate);
        booking.setFlightId(flightId);
        booking.setReferenceNumber(refNum);

        return booking;
    }
}
