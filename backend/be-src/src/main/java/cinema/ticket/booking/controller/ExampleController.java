package cinema.ticket.booking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cinema.ticket.booking.response.ErrorResponse;
import cinema.ticket.booking.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/test")
@Tag(name = "0. Testing")
public class ExampleController {
	
	@Autowired
	private UserService userSER;
	
	@GetMapping("/user/info/{username}")
	@Operation(
        summary = "Get information of user",
		responses = {
    		 @ApiResponse( responseCode = "200", description = "Infomation of user.",
							content = @Content( mediaType = "application/json", schema = @Schema(implementation = UserDetails.class))),
    		 @ApiResponse( responseCode = "401", description = "Invalid token.",
							content = @Content( mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
    		 @ApiResponse( responseCode = "403", description = "User do not have permission to get this data.",
							content = @Content( mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
    		 @ApiResponse( responseCode = "404", description = "Username not found.",
							content = @Content( mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
	     }
    )
	public ResponseEntity<?> getUserByUsername(@PathVariable(value = "username") String username) {
		return ResponseEntity.ok(userSER.loadUserByUsername(username));
	}
}
