package cinema.ticket.booking.controller;

import java.security.Principal;


import org.springframework.beans.factory.annotation.Autowired;
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

import cinema.ticket.booking.request.BookingRequest;
import cinema.ticket.booking.response.BookingResponse;
import cinema.ticket.booking.response.MyApiResponse;
import cinema.ticket.booking.response.ErrorResponse;
import cinema.ticket.booking.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/booking")
@Tag(name="8. Booking Endpoint")
public class BookingController {
	
	@Autowired
	private BookingService bookingSER;
	
	@GetMapping("/{booking_id}")
	@Operation(
			summary = "Booking ID Service (User is required)",
			responses = {
					@ApiResponse( responseCode = "200", description = "Get all ticket's information.",
							content = @Content( mediaType = "application/json", schema = @Schema(implementation = BookingResponse.class))),
					@ApiResponse( responseCode = "404", description = "Ticket or User is not found.",
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
	public ResponseEntity<?> getBookingByID(Principal principal, @Valid @PathVariable(value = "booking_id") String booking_id) {
		return ResponseEntity.ok().body(bookingSER.getBookingFromID(principal.getName(), booking_id));
	}
	
	@PostMapping("/add")
	@Operation(
			summary = "Add Booking Service (User is required)",
			responses = {
					@ApiResponse( responseCode = "200", description = "Add booking successfully.",
							content = @Content( mediaType = "application/json", schema = @Schema(implementation = BookingResponse.class))),
					@ApiResponse( responseCode = "400", description = "You can not reverse more than 4 seats at the time.",
		    				content = @Content( mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
					@ApiResponse( responseCode = "404", description = "Show or User is not found.",
							content = @Content( mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
					@ApiResponse( responseCode = "403", description = "Tickets already exist in this show or seats of this show are full. Please choose another show.",
							content = @Content( mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
					@ApiResponse( responseCode = "409", description = "Seats are reserved, please choose another seat.",
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
	public ResponseEntity<?> createBooking(Principal principal, @Valid @RequestBody BookingRequest bookingReq) {
		return ResponseEntity.ok().body(bookingSER.createBooking(principal.getName(), bookingReq));
	}
	
	@GetMapping("/getall")
	@Operation(
	        summary = "Get All User's Booking Information (User is required)",
	        responses = {
	        		@ApiResponse( responseCode = "200", description = "Ticket's information.",
	        	    		content = @Content( mediaType = "application/json", schema = @Schema(implementation = BookingResponse.class))),
	        	    @ApiResponse( responseCode = "404", description = "User is not found.",
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
	public ResponseEntity<?> getAllBookingsFromUser(Principal principal) {
		return ResponseEntity.ok().body(bookingSER.listOfBooking(principal.getName()));
	}
	
	@DeleteMapping("/{booking_id}/cancel")
	@Operation(
			summary = "Booking Cancel Service (User is required)",
			responses = {
					@ApiResponse( responseCode = "200", description = "Ticket's information",
	    					content = @Content( mediaType = "application/json", schema = @Schema(implementation = MyApiResponse.class))),
	    	    	@ApiResponse( responseCode = "400", description = "This ticket can not be cancled.",
	    					content = @Content( mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
	    	    	@ApiResponse( responseCode = "404", description = "Booking ticket is not found.",
							content = @Content( mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
	    	    	@ApiResponse( responseCode = "409", description = "This ticket does not belong to user.",
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
	public ResponseEntity<?> deleteBookingByID(Principal principal, @Valid @PathVariable(value = "booking_id") String booking_id) {
		return ResponseEntity.ok().body(bookingSER.cancleBooking(principal.getName(), booking_id));
	}
	
	@GetMapping("/user/{username}/getall")
	@Operation(
			summary = "Get all List of Booking Infomartion (Admin is required)",
			responses = {
					@ApiResponse( responseCode = "200", description = "Get all list of booking information",
							content = @Content( mediaType = "application/json", schema = @Schema(implementation = BookingResponse.class))),
					@ApiResponse( responseCode = "404", description = "User is not found.",
							content = @Content( mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),					
			},
			parameters = {
					@Parameter( name = "Authorication", in = ParameterIn.HEADER,
							schema = @Schema(type = "string"), example = "Bearer <token>",
		    		        required = true
		            )
			}     	         
	)
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> getAllBookingsFromUser(@Valid @PathVariable(value = "username") String username) {
		return ResponseEntity.ok().body(bookingSER.listOfBooking(username));
	}
	
	@GetMapping("/user/{username}/{booking_id}")
	@Operation(
			summary = "Get Booking From ID User (Admin is required)",
			responses = {
					@ApiResponse( responseCode = "200", description = "Get booking from id user information",
							content = @Content( mediaType = "application/json", schema = @Schema(implementation = BookingResponse.class))),					
					@ApiResponse( responseCode = "404", description = "Ticket or user is not found.",
							content = @Content( mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),					
			},
			parameters = {
					@Parameter( name = "Authorication", in = ParameterIn.HEADER,
							schema = @Schema(type = "string"), example = "Bearer <token>",
		    		        required = true
		                 )
			}     
	               
	)
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> getBookingsByIDFromUser(@Valid @PathVariable(value = "username") String username, @Valid @PathVariable(value = "booking_id") String booking_id) {
		return ResponseEntity.ok().body(bookingSER.getBookingFromID(username, booking_id));
	}
	
	@PutMapping("/user/{username}/{booking_id}/setstatus")
	@Operation(
			summary = "Set Booking Status Service (Admin is required)",
			responses = {
					@ApiResponse( responseCode = "200", description = " Set booking satus successfully.",
	    					content = @Content( mediaType = "application/json", schema = @Schema(implementation = MyApiResponse.class))),
	    	    	@ApiResponse( responseCode = "400", description = "This ticket already have this status or not found status.",
	    					content = @Content( mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
	    	    	@ApiResponse( responseCode = "404", description = "Ticket or user is not found.",
							content = @Content( mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
	    	},
	        parameters = {
	                @Parameter( name = "Authorication", in = ParameterIn.HEADER,
	    		             schema = @Schema(type = "string"), example = "Bearer <token>",
	    		             required = true
	                 )
			}
	         
	)
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> setBookingStatusFromUsername(@Valid @PathVariable(value = "username") String username, @Valid @PathVariable(value = "booking_id") String booking_id,
			@RequestParam("value") @Valid String status) {
		return ResponseEntity.ok().body(bookingSER.setBookingStatus(username, booking_id, status));
	}
}
