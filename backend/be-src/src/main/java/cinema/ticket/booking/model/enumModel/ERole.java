package cinema.ticket.booking.model.enumModel;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;

import cinema.ticket.booking.permission.Permission;

import static cinema.ticket.booking.permission.Permission.*;

public enum ERole {
	ROLE_SUPER_ADMIN(
			Set.of(
					ADMIN_READ,
	                ADMIN_UPDATE,
	                ADMIN_DELETE,
	                ADMIN_CREATE,
	                MANAGER_READ,
	                MANAGER_UPDATE,
	                MANAGER_DELETE,
	                MANAGER_CREATE
				  )
			),
	ROLE_ADMIN(
			Set.of(
					ADMIN_READ,
	                ADMIN_UPDATE,
	                ADMIN_DELETE,
	                ADMIN_CREATE,
	                MANAGER_READ,
	                MANAGER_UPDATE,
	                MANAGER_DELETE,
	                MANAGER_CREATE
			  )
			),
	ROLE_USER(Collections.emptySet());

	ERole(Set<Permission> permission) {
		this.permissions = permission;
	}

	private final Set<Permission> permissions;
	
	public Set<Permission> getPermissions() {
		return this.permissions;
	}
	
	public List<SimpleGrantedAuthority> getAuthorities() {
	    var authorities = getPermissions()
	            .stream()
	            .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
	            .collect(Collectors.toList());
	    authorities.add(new SimpleGrantedAuthority(this.name()));
	    return authorities;
	  }
}
