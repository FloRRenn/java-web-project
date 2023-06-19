package cinema.ticket.booking.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import cinema.ticket.booking.response.ErrorResponse;

import org.springframework.http.HttpStatus;

@ResponseStatus(code =  HttpStatus.INTERNAL_SERVER_ERROR)
public class MyServerErrorException extends RuntimeException {
    
    private ErrorResponse error;
	private String message;
	
	public MyServerErrorException(ErrorResponse error) {
		this.error = error;
	}
	
	public MyServerErrorException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public MyServerErrorException(String message) {
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
