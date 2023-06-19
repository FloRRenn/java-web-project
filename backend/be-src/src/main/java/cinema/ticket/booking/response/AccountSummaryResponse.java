package cinema.ticket.booking.response;

import java.util.Collection;

import cinema.ticket.booking.model.Account;
import cinema.ticket.booking.model.Role;

public class AccountSummaryResponse {
	String id;
	String username;
	String fullname;
	String email;
	String address;
	String status;
	String createAt;
	String updateAt;
	String[] roles;
	
	public AccountSummaryResponse(Account user) {
		this.id = user.getId();
		this.username = user.getUsername();
		this.fullname = user.getFullname();
		this.email = user.getEmail();
		this.address = user.getAddress();
		this.status = user.getStatus();
		this.roles = this.convertCollection(user.getRoles());
		this.createAt = user.getCreateAt().toString();
		this.updateAt = user.getUpdateAt().toString();
	}
	
	public String getId() {
		return this.id;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public String getFullname() {
		return this.fullname;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public String getStatus() {
		return this.status;
	}

	public String getAddress() {
		return this.address;
	}
	
	public String getCreateAt() {
		return this.createAt;
	}
	
	public String getUpdateAt() {
		return this.updateAt;
	}
	
	public String[] getRoles() {
		return this.roles;
	}
	
	private String[] convertCollection(Collection<Role> roles) {
		String[] res = new String[roles.size()];
		
		int i = 0;
        for (Role role : roles) {
        	res[i] = role.getRole();
            i++;
        }
        return res;
	}
}
