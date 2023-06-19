package cinema.ticket.booking.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class RefreshAccessTokenRequest {
	
	@JsonProperty(value = "refreshToken")
	@NotNull
	@NotBlank
	private String refreshToken;
	
	public String getRefreshToken() {
		return this.refreshToken;
	}
}
