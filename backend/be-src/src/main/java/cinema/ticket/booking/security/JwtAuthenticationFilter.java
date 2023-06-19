package cinema.ticket.booking.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import cinema.ticket.booking.service.JwtService;
import io.micrometer.common.lang.NonNull;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private UserDetailsService userDetailService;

	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
			throws ServletException, IOException {
		try {
			final String authHeader = request.getHeader("Authorication");
			final String jwt;
			final String username;
			
			if (authHeader == null || !authHeader.startsWith("Bearer ")) {
				filterChain.doFilter(request, response);
				return;
			}
			
			jwt = authHeader.substring(7);
			username = jwtService.extractUsername(jwt, true);	
			
			if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				UserDetails userDetail = userDetailService.loadUserByUsername(username);
				// System.out.println("=====> " + userDetail.getUsername());
				
				if (jwtService.isValidToken(jwt, userDetail, true)) {
					UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetail, null, userDetail.getAuthorities());
					authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authToken);
				}
				filterChain.doFilter(request, response);
			}
			else 
				sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Token is invalid");
			
		} catch (AccessDeniedException e) {
			sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Token is invalid");
		}
	}
	
	private void sendErrorResponse(HttpServletResponse response, int statusCode, String message) throws IOException {
        response.setStatus(statusCode);
        response.setContentType("application/json");
        response.getWriter().write("{\"error\": \"" + message + "\"}");
    }
	
}

