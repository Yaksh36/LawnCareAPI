package patel.yaksh.lawn.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import patel.yaksh.lawn.Model.ServiceNotFoundException;
import patel.yaksh.lawn.Model.UserNotFoundException;


@ControllerAdvice
public class ExceptionHandlerConfiguration extends ResponseEntityExceptionHandler {

	
	@ExceptionHandler(RuntimeException.class)
	protected ResponseEntity<ResponseBody> handleRuntimeException(RuntimeException run, WebRequest request){

		ResponseBody responseBody = new ResponseBody();
		responseBody.setCode("I_AM_A_TEAPOT_RESPONSE");
		responseBody.setMessage("You have hit a runtime exception");
		
		ResponseEntity<ResponseBody> response = new ResponseEntity<>(responseBody, HttpStatus.I_AM_A_TEAPOT);
		return response;
	}

	
	@ExceptionHandler(IllegalArgumentException.class)
	protected ResponseEntity<ResponseBody> handleIllegalArgumentException(IllegalArgumentException iae, WebRequest request){

		ResponseBody responseBody = new ResponseBody();
		responseBody.setCode("BAD_REQUEST_RESPONSE");
		responseBody.setMessage(iae.getMessage());
		
		ResponseEntity<ResponseBody> response = new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
		return response;
	}

	@ExceptionHandler(value = UserNotFoundException.class)
	public ResponseEntity<Object> exception(UserNotFoundException exception) {
		return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = ServiceNotFoundException.class)
	public ResponseEntity<Object> exception(ServiceNotFoundException exception) {
		return new ResponseEntity<>("Service not found", HttpStatus.NOT_FOUND);
	}

	public static class ResponseBody{
		private String code;
		private String message;

		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}

	}

}
