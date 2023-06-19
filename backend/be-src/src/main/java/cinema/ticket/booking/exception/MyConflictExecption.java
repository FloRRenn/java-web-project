package cinema.ticket.booking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import cinema.ticket.booking.response.ErrorResponse;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class MyConflictExecption extends RuntimeException {

	private static final long serialVersionUID = 8635916168362817287L;
	
	private ErrorResponse error;
	private String message;
	
	public MyConflictExecption(ErrorResponse error) {
		this.error = error;
	}
	
	public MyConflictExecption(String message, Throwable cause) {
		super(message, cause);
	}
	
	public MyConflictExecption(String message) {
		super(message);
		this.message = message;
	}
	
	public ErrorResponse getErrorResponse() {
		return this.error;
	}
	
	public void setErrorResponse(ErrorResponse error) {
		this.error = error;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
