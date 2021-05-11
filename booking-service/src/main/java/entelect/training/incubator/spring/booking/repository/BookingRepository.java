package entelect.training.incubator.spring.booking.repository;

import entelect.training.incubator.spring.booking.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {

    List<Booking> findAllByCustomerId(Integer customerId);

    Optional<Booking> findBookingByReferenceNumber(String referenceNumber);

    List<Booking> findAllByReferenceNumber(String referenceNumber);
}
