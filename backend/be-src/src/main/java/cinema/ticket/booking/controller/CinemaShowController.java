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

import cinema.ticket.booking.repository.CinemaShowRepository;
import cinema.ticket.booking.request.ShowRequest;
import cinema.ticket.booking.response.MyApiResponse;
import cinema.ticket.booking.service.CinemaShowService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import cinema.ticket.booking.response.ShowInfoResponse;
import cinema.ticket.booking.response.ErrorResponse;
import cinema.ticket.booking.response.ShowSeatResponse;


@RestController
@RequestMapping("/api/show")
@Tag(name="4. Cinema Show Endpoint")
public class CinemaShowController {
	
	@Autowired
	private CinemaShowService cinemaShowService;
	
	// @Autowired
	// private CinemaShowRepository cinemaShowRepository;
	
	@GetMapping("/{show_id}")
	@Operation(
			summary = "Show ID Service (User is required)",
			responses = {
					@ApiResponse( responseCode = "200", description = "Show's information.",
							content = @Content( mediaType = "application/json", schema = @Schema(implementation = ShowInfoResponse.class))),
					@ApiResponse( responseCode = "404", description = "Show is not found or Seat.",
							content = @Content( mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
			},
			parameters = {
					@Parameter( name = "Authorication", in = ParameterIn.HEADER,
							schema = @Schema(type = "string"), example = "Bearer <token>",
							required = true
					)
			}	         
	)
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> getShowByID(@Valid @PathVariable(value = "show_id") String show_id) {
		return ResponseEntity.ok().body(cinemaShowService.getShowInfo(show_id));
	}
	
	@GetMapping("/{show_id}/seats")
	@Operation(
			summary = "Seat In Show (User is required)",
			responses = {
					@ApiResponse( responseCode = "200", description = "Show's seat information.",
							content = @Content( mediaType = "application/json", schema = @Schema(implementation = ShowSeatResponse.class))),					
			},
			parameters = {
					@Parameter( name = "Authorication", in = ParameterIn.HEADER,
							schema = @Schema(type = "string"), example = "Bearer <token>",
							required = true
					)
			}  
	)
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> getAllseats(@Valid @PathVariable(value = "show_id") String show_id) {
		return ResponseEntity.ok().body(cinemaShowService.getAllShowSeats(show_id));
	}
	
	@GetMapping("/frommovie")
	@Operation(
			summary = "From Movie Service (User is required)",
			responses = {
					@ApiResponse( responseCode = "200", description = "Show's movie information.",
							content = @Content( mediaType = "application/json", schema = @Schema(implementation = ShowInfoResponse.class))),					
			},
			parameters = {
					@Parameter( name = "Authorication", in = ParameterIn.HEADER,
							schema = @Schema(type = "string"), example = "Bearer <token>",
							required = true
					)
			} 
			
	)
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> getShowByMovieID(@Valid @RequestParam(value = "id") String movie_id) {
		return ResponseEntity.ok().body(cinemaShowService.getAllShowByMovieID(movie_id));
	}
	
	@GetMapping("/fromhall")
	@Operation(
			summary = "From Hall Service (Admin is required)",
			responses = {
					@ApiResponse( responseCode = "200", description = "List show's information.",
							content = @Content( mediaType = "application/json", schema = @Schema(implementation = ShowInfoResponse.class))),					
			},
			parameters = {
					@Parameter( name = "Authorication", in = ParameterIn.HEADER,
							schema = @Schema(type = "string"), example = "Bearer <token>",
							required = true
					)
			} 	         
	)
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> getShowByHallID(@Valid @RequestParam(value = "id") String hall_id) {
		return ResponseEntity.ok().body(cinemaShowService.getAllShowByHallID(hall_id));
	}

	@GetMapping("/getall")
	@Operation(
			summary = "Get all show Service (Admin is required)",
			responses = {
					@ApiResponse( responseCode = "200", description = "Get all show's information.",
							content = @Content( mediaType = "application/json", schema = @Schema(implementation = ShowInfoResponse.class))),					
			},
			parameters = {
					@Parameter( name = "Authorication", in = ParameterIn.HEADER,
							schema = @Schema(type = "string"), example = "Bearer <token>",
							required = true
					)
			}	         	         
	)
	@PreAuthorize("hasRole('ADMIN')")
	public List<ShowInfoResponse> getAllShows() {
		return cinemaShowService.getAllShows();
	}
	
	@PostMapping("/add")
	@Operation(
			summary = "Add Show Service (Admin is required)",
			responses = {
					@ApiResponse( responseCode = "200", description = "Add show successfully.",
							content = @Content( mediaType = "application/json", schema = @Schema(implementation = MyApiResponse.class))),
			},
			parameters = {
					@Parameter( name = "Authorication", in = ParameterIn.HEADER,
							schema = @Schema(type = "string"), example = "Bearer <token>",
							required = true
					)
			}	         
	)
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<MyApiResponse> addShow(@RequestBody @Valid ShowRequest showRequest){
		return new ResponseEntity<MyApiResponse>(cinemaShowService.addShow(showRequest), HttpStatus.OK);
	}
	
	@PostMapping("/addlist")
	@Operation(
			summary = "Add Show Service (Admin is required)",
			responses = {
					@ApiResponse( responseCode = "200", description = "Add List show successfully.",
							content = @Content( mediaType = "application/json", schema = @Schema(implementation = MyApiResponse.class))),
			},
			parameters = {
					@Parameter( name = "Authorication", in = ParameterIn.HEADER,
							schema = @Schema(type = "string"), example = "Bearer <token>",
							required = true
					)
			}	         
	)
	@PreAuthorize("hasRole('ADMIN')")
	public List<MyApiResponse> addShow(@RequestBody @Valid List<ShowRequest> showRequest){
		return cinemaShowService.addListShows(showRequest);
	}
	
	@PutMapping("/{id}/update")
	@Operation(
			summary = "Update Show Service (Admin is required)",
			responses = {
					@ApiResponse( responseCode = "200", description = "Update show successfully.",
							content = @Content( mediaType = "application/json", schema = @Schema(implementation = MyApiResponse.class))),
					@ApiResponse( responseCode = "404", description = "Show is not found.",
							content = @Content( mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
			},
			parameters = {
					@Parameter( name = "Authorication", in = ParameterIn.HEADER,
							schema = @Schema(type = "string"), example = "Bearer <token>",
							required = true
					)
			}	        	         
	)
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<MyApiResponse> updateShow(@PathVariable(name = "id") @Valid String id, @RequestBody @Valid ShowRequest showReq){
		return ResponseEntity.ok().body(cinemaShowService.updateShow(id, showReq));
	}
	
	@DeleteMapping("/{show_id}/delete")
	@Operation(
			summary = "Delete Show Service (Admin is required)",
			responses = {
					@ApiResponse( responseCode = "200", description = "Delete show successfully.",
							content = @Content( mediaType = "application/json", schema = @Schema(implementation = MyApiResponse.class))),
					@ApiResponse( responseCode = "404", description = "Show is not found.",
							content = @Content( mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
			},
			parameters = {
					@Parameter( name = "Authorication", in = ParameterIn.HEADER,
							schema = @Schema(type = "string"), example = "Bearer <token>",
							required = true
					)
			}	        	      	         
	)
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> deleteShowByID(@PathVariable(name = "show_id") @Valid String show_id) {
		return ResponseEntity.ok().body(cinemaShowService.deleteShow(show_id));
	}
	
	@DeleteMapping("/deletebyhallandmovie")
	@Operation(
			summary = "Delete By Hall An Movie Service (Admin is required)",
			responses = {
					@ApiResponse( responseCode = "200", description = "Delete by hall an movie successfully.",
							content = @Content( mediaType = "application/json", schema = @Schema(implementation = MyApiResponse.class))),
					@ApiResponse( responseCode = "400", description = "Invaild date format, it must be dd/MM/yyyy HH:mm.",
							content = @Content( mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
			},
			parameters = {
					@Parameter( name = "Authorication", in = ParameterIn.HEADER,
							schema = @Schema(type = "string"), example = "Bearer <token>",
							required = true
					)
			}	        	 	         
	)
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> deleteShowByHallIDMovieID(@RequestBody @Valid ShowRequest showReq){
		return ResponseEntity.ok().body(cinemaShowService.deleteShowByHallIDMovieID(showReq));
	}
			
}
