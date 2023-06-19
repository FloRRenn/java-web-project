package cinema.ticket.booking.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class SignUpRequest {
	
	@NotBlank
	@NotNull
	private String fullname;
	
	@NotBlank
	@NotNull
	private String username;
	
	@NotBlank
	@NotNull
	@Email
	private String email;
	
	@NotBlank
	@NotNull
	private String address;
	
	@NotBlank
	@NotNull
	private String phone;
	
	@NotBlank
	@NotNull
	private String password;
	
	public String getUsername() {
		return this.username;
	}
	
	public String getFullname() {
		return this.fullname;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public String getAddress() {
		return this.address;
	}
	
	public String getPhone() {
		return this.phone;
	}
	
	
	public String getPassword() {
		return this.password;
	}
}