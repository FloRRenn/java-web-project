package cinema.ticket.booking.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cinema.ticket.booking.request.BookingRequest;
import cinema.ticket.booking.response.MyApiResponse;
import cinema.ticket.booking.response.BookingResponse;

@Service
public interface BookingService {
	public BookingResponse createBooking(String username, BookingRequest bookingReq);
	public MyApiResponse cancleBooking(String username, String booking_id);
	
	public BookingResponse getBookingFromID(String username, String booking_id);
	public List<BookingResponse> listOfBooking(String username);
	
	public MyApiResponse setBookingStatus(String username, String booking_id, String status);
}
