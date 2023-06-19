package cinema.ticket.booking.exception;

import org.springframework.core.annotation.Order;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import cinema.ticket.booking.response.ErrorResponse;
import io.jsonwebtoken.ExpiredJwtException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class GlobalExceptionController extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler({NullPointerException.class})
	@ResponseBody
    public ResponseEntity<ErrorResponse> nullPointException(Exception e) {
		ErrorResponse error = new ErrorResponse("Got NULL variable in field(s). Please check them again.", HttpStatus.BAD_REQUEST);
		return new ResponseEntity< >(error, HttpStatus.BAD_REQUEST);
    }
	
	@ExceptionHandler(MyAccessDeniedException.class)
	@ResponseBody
	public ResponseEntity<ErrorResponse> accessDenied(MyAccessDeniedException exception) {
		ErrorResponse error = new ErrorResponse(exception.getMessage(), HttpStatus.FORBIDDEN);
		return new ResponseEntity< >(error, HttpStatus.FORBIDDEN);
	}
	
	@ExceptionHandler(MyLockedException.class)
	@ResponseBody
	public ResponseEntity<ErrorResponse> lockedException(MyLockedException exception) {
		ErrorResponse error = new ErrorResponse(exception.getMessage(), HttpStatus.LOCKED);
		return new ResponseEntity< >(error, HttpStatus.LOCKED);
	}
	
	@ExceptionHandler(MyBadRequestException.class)
	@ResponseBody
	public ResponseEntity<ErrorResponse> badRequest(MyBadRequestException exception) {
		ErrorResponse error = new ErrorResponse(exception.getMessage(), HttpStatus.BAD_REQUEST);
		return new ResponseEntity< >(error, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(MyNotFoundException.class)
	@ResponseBody
	public ResponseEntity<ErrorResponse> notFound(MyNotFoundException exception) {
		ErrorResponse error = new ErrorResponse(exception.getMessage(), HttpStatus.NOT_FOUND);
		return new ResponseEntity< >(error, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(MyConflictExecption.class)
	@ResponseBody
	public ResponseEntity<ErrorResponse> conFlict(MyConflictExecption exception) {
		ErrorResponse error = new ErrorResponse(exception.getMessage(), HttpStatus.CONFLICT);
		return new ResponseEntity< >(error, HttpStatus.CONFLICT);
	}

	@ExceptionHandler({MyServerErrorException.class})
	@ResponseBody
    public ResponseEntity<ErrorResponse> internalServerError(Exception exception) {
		ErrorResponse error = new ErrorResponse(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		return new ResponseEntity< >(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
	
	@ExceptionHandler(value = {ExpiredJwtException.class})
	public ResponseEntity<ErrorResponse> handleExpiredJwtException(ExpiredJwtException exception, WebRequest request) {
//		String requestUri = ((ServletWebRequest)request).getRequest().getRequestURI().toString();
		ErrorResponse error = new ErrorResponse(exception.getMessage(), HttpStatus.CONFLICT);
		return new ResponseEntity< >(error, HttpStatus.CONFLICT);
	}
}
