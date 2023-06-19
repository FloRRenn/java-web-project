package cinema.ticket.booking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cinema.ticket.booking.model.CinemaHall;

@Repository
public interface CinemaHallRepository extends JpaRepository<CinemaHall, String> {
	Optional<CinemaHall> findByName(String name);
	
	boolean existsByName(String name);
}
