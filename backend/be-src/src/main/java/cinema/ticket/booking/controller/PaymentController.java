package cinema.ticket.booking.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cinema.ticket.booking.request.HashRequest;
import cinema.ticket.booking.request.PaymentRequest;
import cinema.ticket.booking.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import cinema.ticket.booking.response.ErrorResponse;
import cinema.ticket.booking.response.PaymentResponse;
import cinema.ticket.booking.response.MyApiResponse;

@RestController
@RequestMapping("/api/payment")
@Tag(name = "9. Payment Endpoint")
public class PaymentController {

	@Autowired
	private PaymentService paymentSER;

	@PostMapping("/create")
	@Operation(hidden = true, summary = "Create Payment Service (User is required)", responses = {
			@ApiResponse(responseCode = "200", description = "Create payment successfully.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PaymentResponse.class))),
			@ApiResponse(responseCode = "400", description = "This ticket have been already paid or canceled before..", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "404", description = "Ticket' ID is not found.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	}, parameters = {
			@Parameter(name = "Authorication", in = ParameterIn.HEADER, schema = @Schema(type = "string"), example = "Bearer <token>", required = true)
	})
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> createPayment(Principal principal, @Valid @RequestBody PaymentRequest request,
			HttpServletRequest servletRequest) {
		return ResponseEntity.ok().body(paymentSER.create(principal.getName(), request, "13.160.92.202")); // servletRequest.getRemoteAddr()
	}

	@GetMapping("/{id}")
	@Operation(summary = "Id Payment (User is required)", responses = {
			@ApiResponse(responseCode = "200", description = "Create ID payment successfully.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PaymentResponse.class))),
			@ApiResponse(responseCode = "404", description = "Payment is not found.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	}, parameters = {
			@Parameter(name = "Authorication", in = ParameterIn.HEADER, schema = @Schema(type = "string"), example = "Bearer <token>", required = true)
	})
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> getPaymentById(Principal principal, @Valid @PathVariable(name = "id") String id) {
		return ResponseEntity.ok().body(paymentSER.getFromId(principal.getName(), id));
	}

	@PostMapping("/{id}/verify")
	@Operation(summary = "Id Payment (User is required)", responses = {
			@ApiResponse(responseCode = "200", description = "Verify ID payment successfully.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MyApiResponse.class))),
			@ApiResponse(responseCode = "404", description = "Payment ID is not found.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	}, parameters = {
			@Parameter(name = "Authorication", in = ParameterIn.HEADER, schema = @Schema(type = "string"), example = "Bearer <token>", required = true)
	})
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> verifyPaymentById(Principal principal, @Valid @PathVariable(name = "id") String id) {
		return ResponseEntity.ok().body(paymentSER.verifyPayment(principal.getName(), id));
	}

	@GetMapping("/getall")
	@Operation(summary = "Get All  Payment Of User (User is required)", responses = {
			@ApiResponse(responseCode = "200", description = "Get all  payment of user successfully.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MyApiResponse.class))),
			@ApiResponse(responseCode = "404", description = "User is not found.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	}, parameters = {
			@Parameter(name = "Authorication", in = ParameterIn.HEADER, schema = @Schema(type = "string"), example = "Bearer <token>", required = true)
	}

	)
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> getPaymentAllByUsername(Principal principal) {
		return ResponseEntity.ok().body(paymentSER.getAllPaymentsOfUser(principal.getName()));
	}

	@PostMapping("/createhash")
	@Operation(hidden = true)
	public ResponseEntity<?> getHash(@Valid @RequestBody HashRequest data) {
		return ResponseEntity.ok().body(paymentSER.createHash(data));
	}
}
