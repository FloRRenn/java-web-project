package cinema.ticket.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cinema.ticket.booking.model.CinemaShow;

@Repository
public interface ShowRepository extends JpaRepository<CinemaShow, String> {

}
