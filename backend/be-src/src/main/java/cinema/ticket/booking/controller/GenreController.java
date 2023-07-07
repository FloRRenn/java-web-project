package cinema.ticket.booking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cinema.ticket.booking.model.Genre;
import cinema.ticket.booking.repository.GenreReposity;
import cinema.ticket.booking.response.MyApiResponse;
import cinema.ticket.booking.service.GenreService;
import cinema.ticket.booking.response.ErrorResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/genre")
@Tag(name = "6. Genre Endpoint")
public class GenreController {
	@Autowired
	private GenreService gService;

	@Autowired
	private GenreReposity gReposity;

	@GetMapping("/getall")
	@Operation(summary = "Get All Genre Service", responses = {
			@ApiResponse(responseCode = "200", description = "Genre's information.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Genre.class))),
	})
	public ResponseEntity<List<Genre>> getGenre() {
		return new ResponseEntity<List<Genre>>(gService.getGenres(), HttpStatus.OK);
	}

	@GetMapping("/byid/{id}")
	@Operation(summary = "Genre ID", responses = {
			@ApiResponse(responseCode = "200", description = "Genre's ID information.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Genre.class))),
			@ApiResponse(responseCode = "404", description = "Genre ID is not found.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	}

	)
	public Genre getGenre(@PathVariable(name = "id") @Valid Long id) {
		return gService.getGenre(id);
	}

	@GetMapping("/byname/{genre}")
	@Operation(summary = "Name genre", responses = {
			@ApiResponse(responseCode = "200", description = "Genre's name information.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Genre.class))),
			@ApiResponse(responseCode = "404", description = "Genre name is not found.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	}

	)
	public List<Genre> findByGenre(@PathVariable @Valid String genre) {
		return gReposity.findAllByGenre(genre);
	}

	// For ADMIN role:

	@PostMapping("/add")
	@Operation(summary = "Add Genre Service (Admin is required)", responses = {
			@ApiResponse(responseCode = "200", description = "Add genre successfully.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Genre.class))),
			@ApiResponse(responseCode = "400", description = "This genre is existed.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	}, parameters = {
			@Parameter(name = "Authorication", in = ParameterIn.HEADER, schema = @Schema(type = "string"), example = "Bearer <token>", required = true)
	})
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Genre> saveGenre(@RequestBody @Valid Genre genre) {
		return ResponseEntity.ok(gService.saveGenre(genre));
	}

	@PostMapping("/addlist")
	@Operation(summary = "Add Genre Service (Admin is required)", responses = {
			@ApiResponse(responseCode = "200", description = "Add list genre successfully.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MyApiResponse.class))),
	}, parameters = {
			@Parameter(name = "Authorication", in = ParameterIn.HEADER, schema = @Schema(type = "string"), example = "Bearer <token>", required = true)
	}

	)
	@PreAuthorize("hasRole('ADMIN')")
	public MyApiResponse saveGenreList(@RequestBody @Valid List<Genre> genres) {
		return gService.saveListGenres(genres);
	}

	@PutMapping("/{id}/update")
	@Operation(summary = "Update Genre Service (Admin is required)", responses = {
			@ApiResponse(responseCode = "200", description = "Update genre successfully.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Genre.class))),
			@ApiResponse(responseCode = "400", description = "Can update this name becasue another genre has this one.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "404", description = "Genre ID not found.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	}, parameters = {
			@Parameter(name = "Authorication", in = ParameterIn.HEADER, schema = @Schema(type = "string"), example = "Bearer <token>", required = true)
	})
	@PreAuthorize("hasRole('ADMIN')")
	public Genre updateGenre(@PathVariable(name = "id") @Valid Long id, @RequestBody @Valid Genre genre) {
		genre.setId(id);
		return gService.updateGenre(genre);
	}

	// @DeleteMapping("/{id}/delete")
	// @PreAuthorize("hasRole('ADMIN')")
	// public ApiResponse deleteGenre (@PathVariable(name="id") @Valid Long id) {
	// return gService.deleteGenre(id);
	// }
}
