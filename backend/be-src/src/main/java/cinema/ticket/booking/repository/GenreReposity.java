package cinema.ticket.booking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cinema.ticket.booking.model.Genre;
import jakarta.validation.constraints.NotBlank;

public interface GenreReposity extends JpaRepository<Genre, Long>{
	
	boolean existsByGenre(@NotBlank String genre);
	List<Genre> findAllByGenre(String genre);
	List<Genre> findByGenreContaining(String genre);
	Genre findByGenre(String genre);
}
