package cinema.ticket.booking.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cinema.ticket.booking.model.CinemaHall;
import cinema.ticket.booking.request.CinemaHallRequest;
import cinema.ticket.booking.request.SeatEditRequest;
import cinema.ticket.booking.service.CinemaHallService;
import cinema.ticket.booking.service.CinemaSeatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import cinema.ticket.booking.response.ErrorResponse;
import cinema.ticket.booking.response.MyApiResponse;
import cinema.ticket.booking.response.SeatsResponse;

@RestController
@RequestMapping("/api/hall")
@Tag(name = "3. Cinema Hall Endpoint")
public class CinemaHallController {

	@Autowired
	private CinemaHallService hallSER;

	@Autowired
	private CinemaSeatService hallSeatSER;

	@GetMapping("/{hall_id}")
	@Operation(summary = "Get Hall By ID Information (Admin is required)", responses = {
			@ApiResponse(responseCode = "200", description = "Hall's information.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CinemaHall.class))),
			@ApiResponse(responseCode = "404", description = "Hall is not found.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	}, parameters = {
			@Parameter(name = "Authorication", in = ParameterIn.HEADER, schema = @Schema(type = "string"), example = "Bearer <token>", required = true)
	})
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> getbyID(@Valid @PathVariable(value = "hall_id") String hall_id) {
		return ResponseEntity.ok().body(hallSER.getHallById(hall_id));
	}

	@GetMapping("/getall")
	@Operation(summary = "Getall Hall Service (User is required)", responses = {
			@ApiResponse(responseCode = "200", description = "List Hall's information.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CinemaHall.class))),
	}, parameters = {
			@Parameter(name = "Authorication", in = ParameterIn.HEADER, schema = @Schema(type = "string"), example = "Bearer <token>", required = true)
	})
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> getAll() {
		return ResponseEntity.ok().body(hallSER.getAllHalls());
	}

	@PutMapping("/new")
	@Operation(summary = "Add a New Hall Service (Admin is required)", responses = {
			@ApiResponse(responseCode = "200", description = "Add a new hall successfully.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MyApiResponse.class))),
			@ApiResponse(responseCode = "400", description = "This hall is existed or Illegal characters in name or Row/Column number must be greater than 5.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	}, parameters = {
			@Parameter(name = "Authorication", in = ParameterIn.HEADER, schema = @Schema(type = "string"), example = "Bearer <token>", required = true)
	}

	)
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> getall(@Valid @RequestBody CinemaHallRequest cReq) {
		CinemaHall hall = new CinemaHall(cReq);
		return ResponseEntity.ok().body(hallSER.newHall(hall));
	}

	@DeleteMapping("/{hall_id}/delete")
	@Operation(summary = "Delete Hall Service (Admin is required)", responses = {
			@ApiResponse(responseCode = "200", description = "Delete Hall successfully.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MyApiResponse.class))),
			@ApiResponse(responseCode = "404", description = "Hall is not found.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	}, parameters = {
			@Parameter(name = "Authorication", in = ParameterIn.HEADER, schema = @Schema(type = "string"), example = "Bearer <token>", required = true)
	}

	)
	@Transactional
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> test1(@PathVariable(value = "hall_id") String hall_id) {
		return ResponseEntity.ok().body(hallSER.removeHall(hall_id));
	}

	@PutMapping("/{hall_id}/edit")
	@Operation(summary = "Edit Hall Service (Admin is required)", responses = {
			@ApiResponse(responseCode = "200", description = "Edit hall successfully.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MyApiResponse.class))),
			@ApiResponse(responseCode = "400", description = "Illeagal charaters in name.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "404", description = "Hall is not found.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	}, parameters = {
			@Parameter(name = "Authorication", in = ParameterIn.HEADER, schema = @Schema(type = "string"), example = "Bearer <token>", required = true)
	})
	@Transactional
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> edit(@PathVariable(value = "hall_id") String hall_id,
			@RequestBody CinemaHallRequest cReq) {
		return ResponseEntity.ok().body(hallSER.editHall(hall_id, cReq));
	}

	@GetMapping("/{hall_id}/seat/getall")
	@Operation(summary = "Get All Seat In Hall (Admin is required)", responses = {
			@ApiResponse(responseCode = "200", description = "Seat's in hall information.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SeatsResponse.class))),
	}, parameters = {
			@Parameter(name = "Authorication", in = ParameterIn.HEADER, schema = @Schema(type = "string"), example = "Bearer <token>", required = true)
	})
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> geAllSeatstbyHallID(@PathVariable(value = "hall_id") String hall_id) {
		return ResponseEntity.ok().body(hallSeatSER.getAllSeatsFromHall(hall_id));
	}

	@PutMapping("/{hall_id}/seat/edit")
	@Operation(summary = "Edit Seat In Hall Service (Admin is required)", responses = {
			@ApiResponse(responseCode = "200", description = "Edit seat in hall successfully.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MyApiResponse.class))),
			@ApiResponse(responseCode = "404", description = "Seat not found or Type is not found or Status is not found.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	}, parameters = {
			@Parameter(name = "Authorication", in = ParameterIn.HEADER, schema = @Schema(type = "string"), example = "Bearer <token>", required = true)
	}

	)
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> getall(@PathVariable(value = "hall_id") String hall_id,
			@RequestBody SeatEditRequest cReq) {
		return ResponseEntity.ok().body(hallSeatSER.Edit(hall_id, cReq));
	}
}
