package cinema.ticket.booking.response;

public class AuthenticationResponse {
	private String token;
	private String refreshtoken;
	private String username;
	private String email;
	
	public AuthenticationResponse() {}
	public AuthenticationResponse(String token, String refreshtoken, String username, String email) {
		this.token = token;
		this.refreshtoken = refreshtoken;
		this.username = username;
		this.email = email;
	}
	
	public String getToken() {
		return this.token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
	
	public String getRefreshToken() {
		return this.refreshtoken;
	}
	
	public void setRefreshToken(String token) {
		this.refreshtoken = token;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
}
