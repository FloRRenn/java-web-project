package cinema.ticket.booking.response;

import java.util.List;

import org.springframework.http.HttpStatus;

public class ExceptionResponse {
	 private HttpStatus status;
	 private String message;
	 private List<String> errors;

	 public ExceptionResponse(HttpStatus status, String message, List<String> errors) {
	     super();
	     this.status = status;
	     this.message = message;
	     this.errors = errors;
	 }
	 
	 public HttpStatus getStatus() {
		 return this.status;
	 }
	 
	 public String getMessage() {
		 return this.message;
	 }
	 
	 public List<String> getErrors() {
		 return this.errors;
	 }
}

