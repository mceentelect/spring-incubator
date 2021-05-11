package entelect.training.incubator.spring.booking.service;

import entelect.training.incubator.spring.booking.model.Booking;
import entelect.training.incubator.spring.booking.model.BookingResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BookingAdaptor {
    public static BookingResponse toBookingResponse(Booking booking) {
        if (booking == null)
            return null;

        BookingResponse bookingResponse = new BookingResponse();
        bookingResponse.setCustomerId(booking.getCustomerId());
        bookingResponse.setFlightId(booking.getFlightId());
        bookingResponse.setReferenceNumber(booking.getReferenceNumber());

        return bookingResponse;
    }

    public static List<BookingResponse> toBookingResponse(List<Booking> bookings) {
        if (bookings == null || bookings.isEmpty()) {
            return null;
        }

        return bookings.stream().map(BookingAdaptor::toBookingResponse).collect(Collectors.toList());
    }
}
