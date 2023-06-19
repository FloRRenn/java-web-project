package cinema.ticket.booking.response;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorResponse extends MyApiResponse {
	
	@JsonProperty("status")
	private HttpStatus status;

	public ErrorResponse() {
		super("");
	}
	
	public ErrorResponse(String message) {
		super(message);
	}

	public ErrorResponse(String message, HttpStatus httpStatus) {
		super(message);
		this.status = httpStatus;
	}
}
