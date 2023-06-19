package cinema.ticket.booking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import cinema.ticket.booking.model.JWTToken;

public interface JWTokenRepository extends JpaRepository<JWTToken, String> {
	
	Optional<JWTToken> findByUserId(String id);
	Optional<JWTToken> findByRefreshToken(String refresh_token);
}
