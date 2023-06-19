package cinema.ticket.booking.response;

import org.springframework.http.HttpStatus;

public class MyApiResponse {
	private String message;
	private String status;

	public MyApiResponse(String message) {
		this.message = message;
	}
	
	public MyApiResponse(String message, String status) {
		this.message = message;
		this.status = status;
	}
	
	public MyApiResponse(String message, HttpStatus status) {
		this.message = message;
		this.status = status.name();
	}

	public String getMessage() {
		return this.message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}

	public String getStatus() {
		return this.status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
}
