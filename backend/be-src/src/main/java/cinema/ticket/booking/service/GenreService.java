package cinema.ticket.booking.service;

import java.util.List;

import cinema.ticket.booking.model.Genre;
import cinema.ticket.booking.response.MyApiResponse;

public interface GenreService {
	
	List<Genre> getGenres();
	
	Genre saveGenre (Genre genre);
	
	MyApiResponse saveListGenres(List<Genre> genre);
	
	Genre getGenre (Long id);

	MyApiResponse deleteGenre (Long id);
	
	Genre updateGenre (Genre genre);

}

