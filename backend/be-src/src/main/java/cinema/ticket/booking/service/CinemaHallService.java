package cinema.ticket.booking.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cinema.ticket.booking.model.CinemaHall;
import cinema.ticket.booking.request.CinemaHallRequest;
import cinema.ticket.booking.response.MyApiResponse;

@Service
public interface CinemaHallService {
	List<CinemaHall> getAllHalls();
	CinemaHall getHallById(String id);
	
	MyApiResponse newHall(CinemaHall c);
	MyApiResponse editHall(String hallID, CinemaHallRequest c);
	MyApiResponse removeHall(String HallID);
	
	boolean isExistByName(String hallName);
	boolean isExistById(String ID);
}
