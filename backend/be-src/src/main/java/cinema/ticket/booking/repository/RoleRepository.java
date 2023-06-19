package cinema.ticket.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cinema.ticket.booking.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
	Role findByName(String name);
}
