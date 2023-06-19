package cinema.ticket.booking.exception;

import org.springframework.http.HttpStatus;

public class ApiException extends RuntimeException {
	private static final long serialVersionUID = -4378379596886171368L;
	
	private HttpStatus status;
	private String message;

	public ApiException(HttpStatus status, String message) {
		super();
		this.status = status;
		this.message = message;
	}

	public ApiException(HttpStatus status, String message, Throwable exception) {
		super(exception);
		this.status = status;
		this.message = message;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}
}
