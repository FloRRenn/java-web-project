package cinema.ticket.booking.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import cinema.ticket.booking.response.ErrorResponse;

import org.springframework.http.HttpStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class MyAccessDeniedException extends RuntimeException {
	private static final long serialVersionUID = 6091194842293941195L;
	
	private ErrorResponse error;
	private String message;
	
	public MyAccessDeniedException(ErrorResponse error) {
		this.error = error;
	}
	
	public MyAccessDeniedException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public MyAccessDeniedException(String message) {
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
