package cinema.ticket.booking.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.media.Schema;

import cinema.ticket.booking.request.LoginRequest;
import cinema.ticket.booking.request.RefreshAccessTokenRequest;
import cinema.ticket.booking.request.SignUpRequest;
import cinema.ticket.booking.exception.MyNotFoundException;
import cinema.ticket.booking.response.MyApiResponse;
import cinema.ticket.booking.response.ErrorResponse;
import cinema.ticket.booking.response.AuthenticationResponse;
import cinema.ticket.booking.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "1. Authentication Endpoint")
public class AuthenticationController {

	@Autowired
	private AuthenticationService authService;

	@PostMapping("/signup")
	@Operation(summary = "Create a new account", responses = {
			@ApiResponse(responseCode = "200", description = "Successfully Signed Up!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MyApiResponse.class))),
			@ApiResponse(responseCode = "400", description = "Email/Username is existed or Bad password", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
	})
	public ResponseEntity<MyApiResponse> signup(@RequestBody @Valid SignUpRequest request,
			BindingResult bindingResult) {
		return ResponseEntity.ok(authService.signup(request, "1.2.3.4"));
	}

	@PostMapping("/admin/login")
	@Operation(summary = "Login an admin account", responses = {
			@ApiResponse(responseCode = "200", description = "Login successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthenticationResponse.class))),
			@ApiResponse(responseCode = "403", description = "Username or password is wrong", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
	})
	public ResponseEntity<AuthenticationResponse> adminLogin(@RequestBody @Valid LoginRequest loginrequest,
			HttpServletRequest servletRequest) {
		return ResponseEntity.ok(authService.login(loginrequest, servletRequest, true));
	}

	@PostMapping("/login")
	@Operation(summary = "Login a normal account", responses = {
			@ApiResponse(responseCode = "200", description = "Login successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthenticationResponse.class))),
			@ApiResponse(responseCode = "403", description = "Username or password is wrong", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
	})
	public ResponseEntity<AuthenticationResponse> login(@RequestBody @Valid LoginRequest loginrequest,
			HttpServletRequest servletRequest) {
		return ResponseEntity.ok(authService.login(loginrequest, servletRequest, false));
	}

	@PostMapping("/refresh")
	@Operation(summary = "Refresh Service", responses = {
			@ApiResponse(responseCode = "200", description = "Get new access token", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthenticationResponse.class))),
			@ApiResponse(responseCode = "403", description = "Refresh token is wrong", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
	})
	public ResponseEntity<AuthenticationResponse> refresh(@RequestBody @Valid RefreshAccessTokenRequest request,
			HttpServletRequest servletRequest) {
		return ResponseEntity.ok(authService.refreshAccessToken(request.getRefreshToken(), servletRequest));
	}

	@GetMapping("/verify/{code}")
	@Operation(summary = "Verify Account by Verifying Code (This code is sent via user's mail)", responses = {
			@ApiResponse(responseCode = "302", description = "Verified successfully, then redirect to login page.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthenticationResponse.class))),
			@ApiResponse(responseCode = "404", description = "Verified fail", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MyNotFoundException.class))),
	})
	public void verify(@PathVariable(value = "code") @Valid String code, HttpServletResponse response)
			throws IOException {
		authService.veriyCode(code, response);
	}

	@GetMapping("/token")
	@Operation(hidden = true)
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<MyApiResponse> getMe() {
		return ResponseEntity.ok(new MyApiResponse("ok"));
	}
}