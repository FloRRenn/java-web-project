package cinema.ticket.booking.model;

import cinema.ticket.booking.model.enumModel.ERole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "Role",
		uniqueConstraints = { @UniqueConstraint(columnNames = { "name" })
	})
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO )
	@Column(name = "id")
	private Long id;
	
	@Column(name = "name")
	private String name;
	
	public Role() {}
	
	public Role(Long id, ERole role) {
		this.id = id;
		this.name = role.name();
	}
	
	public Role(ERole role) {
		this.name = role.name();
	}
	
	public Long getId() {
		return this.id;
	}
	
	public String getRole() {
		return this.name;
	}
	
	public void setRole(ERole role) {
		this.name = role.name();
	}
	
}
