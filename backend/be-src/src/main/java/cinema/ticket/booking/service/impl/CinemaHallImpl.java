package cinema.ticket.booking.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import cinema.ticket.booking.exception.MyNotFoundException;
import cinema.ticket.booking.model.CinemaHall;
import cinema.ticket.booking.repository.CinemaHallRepository;
import cinema.ticket.booking.request.CinemaHallRequest;
import cinema.ticket.booking.response.MyApiResponse;
import cinema.ticket.booking.response.ErrorResponse;
import cinema.ticket.booking.security.InputValidationFilter;
import cinema.ticket.booking.service.CinemaHallService;
import cinema.ticket.booking.service.CinemaSeatService;

@Service
public class CinemaHallImpl implements CinemaHallService {
	
	@Autowired
	CinemaHallRepository hallREPO;
	
	@Autowired
	private CinemaSeatService hallSeatSER;
	
	@Autowired
	InputValidationFilter inputFilter;

	@Override
	public MyApiResponse newHall(CinemaHall c) {
		if (!inputFilter.checkInput(c.getName()))
			return new ErrorResponse("Illeagal charaters in name", HttpStatus.BAD_REQUEST);
		if (hallREPO.existsByName(c.getName()))
			return new ErrorResponse("This hall is existed", HttpStatus.BAD_REQUEST);
		if (c.getTotalCol() < 5 || c.getTotalRow() < 5)
			return new ErrorResponse("Row/Column number must be greater than 5", HttpStatus.BAD_REQUEST);
		
		hallREPO.save(c);
		
		CinemaHall hall = hallREPO.findByName(c.getName()).get();
		hallSeatSER.CreateListSeats(hall);
		
		return new MyApiResponse("Success");
	}

	@Override
	public MyApiResponse editHall(String hallID, CinemaHallRequest c) {
		if (!inputFilter.checkInput(c.getName()))
			return new ErrorResponse("Illeagal charaters in name", HttpStatus.BAD_REQUEST);
		
		CinemaHall hall = hallREPO.findById(hallID).orElseThrow(() -> new MyNotFoundException("Hall is not found"));
		int oldRow = hall.getTotalRow();
		int oldCol = hall.getTotalCol();
		
		hall.setName(c.getName());
		hall.setTotalCol(c.getTotalCol());
		hall.setTotalRow(c.getTotalRow());
	
		if (oldCol != c.getTotalCol() || oldRow != c.getTotalRow()) {
			hallSeatSER.RemoveAllSeatsFromHall(hall.getId());
			hallSeatSER.CreateListSeats(hall);
		}
		System.out.println("==> " + hall.getName() + " - " + c.getTotalCol() + " - " + c.getTotalRow());
		hallREPO.save(hall);
		
		return new MyApiResponse("Success");
	}

	@Override
	public MyApiResponse removeHall(String HallID) {
		CinemaHall hall = hallREPO.findById(HallID).orElseThrow(() -> new MyNotFoundException("Hall is not found"));
		hallSeatSER.RemoveAllSeatsFromHall(hall.getId());
		hallREPO.delete(hall);
		return new MyApiResponse("Success");
	}

	@Override
	public boolean isExistByName(String hallName) {
		return hallREPO.existsByName(hallName);
	}

	@Override
	public boolean isExistById(String ID) {
		return hallREPO.existsById(ID);
	}

	@Override
	public List<CinemaHall> getAllHalls() {
		return hallREPO.findAll();
	}

	@Override
	public CinemaHall getHallById(String id) {
		return hallREPO.findById(id).orElseThrow(() -> new MyNotFoundException("Hall is not found"));
	}
	
}
