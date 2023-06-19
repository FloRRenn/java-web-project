package cinema.ticket.booking.service;

import org.springframework.stereotype.Service;

import cinema.ticket.booking.request.LoginRequest;
import cinema.ticket.booking.request.SignUpRequest;
import cinema.ticket.booking.response.MyApiResponse;
import cinema.ticket.booking.response.AuthenticationResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public interface AuthenticationService {
	public MyApiResponse signup(SignUpRequest request, String ip);
	public AuthenticationResponse login(LoginRequest request, HttpServletRequest servletRequest, boolean adminLogin);
	public AuthenticationResponse refreshAccessToken(String refreshToken, HttpServletRequest servletRequest);
	public void veriyCode(String code, HttpServletResponse response);
}