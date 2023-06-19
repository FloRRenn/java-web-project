package cinema.ticket.booking.response;

import cinema.ticket.booking.model.JWTToken;

public class AuthenJWTokenResponse {
	
	private JWTToken data;
	private String accessDecrypt;
	private String refreshDecrypt;
	
	public AuthenJWTokenResponse(JWTToken data, String accessDecrypt, String refreshDecrypt) {
		this.data = data;
		this.accessDecrypt = accessDecrypt;
		this.refreshDecrypt = refreshDecrypt;
	}
	
	public JWTToken getData() {
		return this.data;
	}
	
	public String getAccessDecrypt() {
		return this.accessDecrypt;
	}
	
	public String getRefreshDecrypt() {
		return this.refreshDecrypt;
	}
}
