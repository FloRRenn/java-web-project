package cinema.ticket.booking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cinema.ticket.booking.model.AccountTemp;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface UserTempRepository extends JpaRepository<AccountTemp, Long> {
	void deleteByUsername(String name);
	
	int countByIp(String ip);
	
	Optional<AccountTemp> findByCode(String code);
	
	Boolean existsByUsername(String username);
	Boolean existsByEmail(String email);
}
