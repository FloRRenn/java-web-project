package cinema.ticket.booking.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cinema.ticket.booking.model.CinemaHall;
import cinema.ticket.booking.request.SeatEditRequest;
import cinema.ticket.booking.response.MyApiResponse;
import cinema.ticket.booking.response.SeatsResponse;

@Service
public interface CinemaSeatService {
	
	List<SeatsResponse> getAllSeatsFromHall(String hallID);
	
	public void CreateListSeats(CinemaHall hall);
	public void RemoveAllSeatsFromHall(String hallID);
	
	public MyApiResponse Edit(String hallID, SeatEditRequest seatReq);
	
	public boolean isExist(String hallID, int row, int column);
}
