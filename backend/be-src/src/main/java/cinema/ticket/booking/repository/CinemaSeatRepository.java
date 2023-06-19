package cinema.ticket.booking.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cinema.ticket.booking.model.CinemaHall;
import cinema.ticket.booking.model.CinemaSeat;

@Repository
public interface CinemaSeatRepository extends JpaRepository<CinemaSeat, Long> {
	List<CinemaSeat> findByCinemaHall(CinemaHall hall);
	List<CinemaSeat> findByCinemaHallId(String hallID);
	
	Optional<CinemaSeat> findByCinemaHallIdAndRowIndexAndColIndex(String hallID, int row, int col);
	void deleteAllByCinemaHallId(String hallID);
}
