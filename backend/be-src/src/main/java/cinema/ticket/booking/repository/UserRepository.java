package cinema.ticket.booking.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cinema.ticket.booking.model.Account;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<Account, String> {
	Optional<Account> getByUsername(String username);
	Optional<Account> getByEmail(String email);
	
	Boolean existsByUsername(@NotBlank String username);
	Boolean existsByEmail(@NotBlank String email);
	
	int countByIp(String ip);
	
	//@Query("FROM account WHERE username LIKE '%:username%'")
	List<Account> findByUsernameContaining(String username);
	void deleteByUsername(String name);
};
