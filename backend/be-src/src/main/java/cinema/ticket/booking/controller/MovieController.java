package cinema.ticket.booking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cinema.ticket.booking.model.Movie;
import cinema.ticket.booking.response.MyApiResponse;
import cinema.ticket.booking.response.MovieInfoResponse;
import cinema.ticket.booking.service.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import cinema.ticket.booking.response.ErrorResponse;

@RestController
@Tag(name = "5. Movie Endpoint")

@RequestMapping("/api/movie")
public class MovieController {

	@Autowired
	private MovieService mService;

	@GetMapping("/getall")
	@Operation(summary = "Get All Movie Service", responses = {
			@ApiResponse(responseCode = "200", description = "Movie's information.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MovieInfoResponse.class))),
	})
	public ResponseEntity<List<MovieInfoResponse>> getMovies(@RequestParam(defaultValue = "0") Integer pageNumber,
			@RequestParam(defaultValue = "100000") @Valid Integer pageSize) {
		return new ResponseEntity<List<MovieInfoResponse>>(mService.getMovies(pageNumber, pageSize), HttpStatus.OK);
	}

	@GetMapping("/searchbytitle")
	@Operation(summary = "Search By Title Service", responses = {
			@ApiResponse(responseCode = "200", description = "Search by title successfully.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MovieInfoResponse.class))),
			@ApiResponse(responseCode = "400", description = "Ticket is not found.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	})
	public ResponseEntity<List<MovieInfoResponse>> getMovieName(@RequestParam @Valid String key,
			@RequestParam(defaultValue = "0") @Valid Integer pageNumber,
			@RequestParam(defaultValue = "50") @Valid Integer pageSize) {
		return new ResponseEntity<List<MovieInfoResponse>>(mService.getMatchingName(key, pageNumber, pageSize),
				HttpStatus.OK);
	}

	@GetMapping("/{id}")
	@Operation(summary = "Movie Booking", responses = {
			@ApiResponse(responseCode = "200", description = "Movie's information.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MovieInfoResponse.class))),
			@ApiResponse(responseCode = "404", description = "Movie ID is not found.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	})
	public MovieInfoResponse getMovie(@PathVariable(name = "id") @Valid Long id) {
		return mService.getMovie(id);
	}

	@GetMapping("/searchbygenre")
	@Operation(summary = "Search By Genre Service (User is required)", responses = {
			@ApiResponse(responseCode = "200", description = "Search by genre successfully.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MovieInfoResponse.class))),
			@ApiResponse(responseCode = "400", description = "Data containt illegal character.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	})
	public ResponseEntity<?> getMovieGenre(@RequestParam @Valid String key,
			@RequestParam(defaultValue = "0") @Valid Integer pageNumber,
			@RequestParam(defaultValue = "50") @Valid Integer pageSize) {
		return new ResponseEntity<>(mService.getMatchingGenre(key, pageSize, pageSize), HttpStatus.OK);
	}

	// FOR ADMIN ROLE:

	@PostMapping("/add")
	@Operation(summary = "Add Movie Service (Admin is required)", responses = {
			@ApiResponse(responseCode = "200", description = "Add movie successfully.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Movie.class))),
			@ApiResponse(responseCode = "400", description = "This title is existed.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	}, parameters = {
			@Parameter(name = "Authorication", in = ParameterIn.HEADER, schema = @Schema(type = "string"), example = "Bearer <token>", required = true)
	})
	@PreAuthorize("hasRole('ADMIN')")
	public Movie saveMovie(@RequestBody @Valid Movie movie) {
		return mService.saveMovie(movie);
	}

	@PostMapping("/addlist")
	@Operation(summary = "Add List Movie Service (Admin is required)", responses = {
			@ApiResponse(responseCode = "200", description = "Add list movie successfully.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MyApiResponse.class))),
	}, parameters = {
			@Parameter(name = "Authorication", in = ParameterIn.HEADER, schema = @Schema(type = "string"), example = "Bearer <token>", required = true)
	})
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<MyApiResponse> saveMovieList(@RequestBody @Valid List<Movie> movies) {
		return ResponseEntity.ok().body(mService.saveMovieList(movies));
	}

	@PutMapping("/{id}/edit")
	@Operation(summary = "Edit Movie Service (Admin is required)", responses = {
			@ApiResponse(responseCode = "200", description = "Edit movie successfully.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Movie.class))),
	}, parameters = {
			@Parameter(name = "Authorication", in = ParameterIn.HEADER, schema = @Schema(type = "string"), example = "Bearer <token>", required = true)
	})
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Movie> updateMovie(@PathVariable(name = "id") @Valid Long id,
			@RequestBody @Valid Movie movie) {
		return new ResponseEntity<Movie>(mService.updateMovie(movie), HttpStatus.OK);
	}

	@DeleteMapping("/{id}/delete")
	@Operation(summary = "Delete Movie Service (Admin is required)", responses = {
			@ApiResponse(responseCode = "200", description = "Delete movie successfully.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MyApiResponse.class))),
			@ApiResponse(responseCode = "404", description = "Movie is not found.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	}, parameters = {
			@Parameter(name = "Authorication", in = ParameterIn.HEADER, schema = @Schema(type = "string"), example = "Bearer <token>", required = true)
	})
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<MyApiResponse> deleteMovie(@PathVariable(name = "id") @Valid Long id) {
		return ResponseEntity.ok().body(mService.deleteMovie(id));
	}
}
