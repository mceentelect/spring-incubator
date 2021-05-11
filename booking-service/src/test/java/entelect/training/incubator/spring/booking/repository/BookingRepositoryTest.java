package entelect.training.incubator.spring.booking.repository;

import entelect.training.incubator.spring.booking.model.Booking;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class BookingRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BookingRepository bookingRepository;

    @Test
    public void whenFindingBookingById_thenCorrect() {
        String refNum = "IssaTest1";
        Booking booking = createBooking(refNum);
        entityManager.persistAndFlush(booking);

        Optional<Booking> bookingByReferenceNumber = bookingRepository.findBookingByReferenceNumber(refNum);
        assertThat(bookingByReferenceNumber).isPresent();
        assertThat(bookingByReferenceNumber.get().getReferenceNumber()).isEqualTo(refNum);
    }

    private Booking createBooking(String refNum) {
        Booking booking = new Booking();
        booking.setCustomerId(1);
        booking.setBookingDate(LocalDateTime.now());
        booking.setFlightId(2);
        booking.setReferenceNumber(refNum);

        return booking;
    }
}