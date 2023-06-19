package cinema.ticket.booking.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cinema.ticket.booking.model.Movie;
import cinema.ticket.booking.response.MyApiResponse;
import cinema.ticket.booking.response.MovieInfoResponse;

@Service
public interface MovieService {
	
	List<MovieInfoResponse> getMovies(int pageNumber, int pageSize);
		
	Movie saveMovie (Movie movie);
	
	List<MovieInfoResponse> getMatchingName(String title, int pageNumber, int pageSize);
	
	Object[] getMatchingGenre(String genre, int pageNumber, int pageSize);
	
	MovieInfoResponse getMovie (Long id);

	MyApiResponse deleteMovie(Long id);
	
	Movie updateMovie (Movie movie);

	MyApiResponse saveMovieList(List<Movie> movies);

}
