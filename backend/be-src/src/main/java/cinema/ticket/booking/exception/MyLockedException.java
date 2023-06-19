package cinema.ticket.booking.exception;

import cinema.ticket.booking.response.ErrorResponse;

public class MyLockedException extends RuntimeException {

	private static final long serialVersionUID = -2022048252065686500L;
	
	private ErrorResponse error;
	private String message;
	
	public MyLockedException(ErrorResponse error) {
		this.error = error;
	}
	
	public MyLockedException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public MyLockedException(String message) {
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
