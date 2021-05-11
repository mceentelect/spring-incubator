package entelect.training.incubator.spring.booking.service;

import entelect.training.incubator.spring.booking.loyality.LoyaltyClient;
import entelect.training.incubator.spring.booking.model.Booking;
import entelect.training.incubator.spring.booking.model.BookingResponse;
import entelect.training.incubator.spring.booking.model.BookingSearchRequest;
import entelect.training.incubator.spring.booking.model.SearchType;
import entelect.training.incubator.spring.booking.repository.BookingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Supplier;

@Service
public class BookingsService {
    private final BookingRepository bookingRepository;
    private final LoyaltyClient loyaltyClient;

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    public BookingsService(BookingRepository bookingRepository, LoyaltyClient loyaltyClient) {
        this.bookingRepository = bookingRepository;
        this.loyaltyClient = loyaltyClient;
    }


    public BookingResponse getBookingById(Integer id) {
        Optional<Booking> booking = bookingRepository.findById(id);

        return BookingAdaptor.toBookingResponse(booking.orElse(null));
    }

    public List<BookingResponse> getBookings() {
        return BookingAdaptor.toBookingResponse(bookingRepository.findAll());
    }

    private void setUpPoints() {
        try {
            loyaltyClient.getRewardsBalance("abc123");
        } catch (Exception e) {
            LOGGER.warn("Couldn't add rewards to this booking", e);
        }
    }

    public Booking createBooking(Booking booking) {
        booking.setReferenceNumber(generateReference());
        booking.setBookingDate(generateBookingDate());

        setUpPoints();

        return bookingRepository.save(booking);
    }

    private LocalDateTime generateBookingDate() {
        Random random = new Random();

        return LocalDateTime.now().plusDays(random.nextInt(90));
    }

    private String generateReference() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    public List<Booking> searchBookings(BookingSearchRequest bookingSearchRequest) {
        Map<SearchType, Supplier<List<Booking>>> searchStrategies = new HashMap<>();

        if (bookingSearchRequest.getCustomerId() != 0) {
            bookingSearchRequest.setSearchType(SearchType.CUSTOMER_SEARCH);
        }

        if (bookingSearchRequest.getReferenceNumber() != null) {
            bookingSearchRequest.setSearchType(SearchType.REFERENCE_SEARCH);
        }

        searchStrategies.put(SearchType.CUSTOMER_SEARCH, () -> bookingRepository.findAllByCustomerId(bookingSearchRequest.getCustomerId()));
        searchStrategies.put(SearchType.REFERENCE_SEARCH, () -> bookingRepository.findAllByReferenceNumber(bookingSearchRequest.getReferenceNumber()));

        return searchStrategies
                .get(bookingSearchRequest.getSearchType())
                .get();
    }
}
