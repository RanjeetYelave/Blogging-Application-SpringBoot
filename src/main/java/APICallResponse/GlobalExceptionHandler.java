package APICallResponse;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	ResponseEntity<Map<String, String>> MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex) {
		Map<String, String> errorMap = new HashMap<>();

		ex.getBindingResult().getAllErrors().forEach(error -> {

			String field = ((FieldError) error).getField();
			String defaultMessage = error.getDefaultMessage();
			errorMap.put(field, defaultMessage);
		});
		return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	ResponseEntity<ApiResponse> ResourceNotFoundExceptionHandler(ResourceNotFoundException ex) {
		String message = ex.getMessage();

		return new ResponseEntity<>(new ApiResponse(message, false, "caused by ResourceNotFoundException"),
				HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(InvalidDataAccessApiUsageException.class)
	ResponseEntity<ApiResponse> InvalidDataAccessApiUsageExceptionHandler(InvalidDataAccessApiUsageException ex) {
		String message = ex.getMessage();

		return new ResponseEntity<>(new ApiResponse(message, false, "caused by InvalidDataAccessApiUsageException"),
				HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(IOException.class)
	ResponseEntity<ApiResponse> IOExceptionHandler(IOException ex) {
		String message = ex.getMessage();
		ApiResponse apiResponse = new ApiResponse();
		apiResponse.setMessage(message);
		apiResponse.setHttpStatus("IO Exception");
		apiResponse.setSuccess(false);
		return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(FileNotFoundException.class)
	ResponseEntity<ApiResponse> FileNotFoundExceptionHandler(FileNotFoundException ex) {
		ApiResponse apiResponse = new ApiResponse("File Not Found", false, "No Such File Exists");
		return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(UsernameNotFoundException.class)
	ResponseEntity<ApiResponse> UsernameNotFoundExceptionHandler(UsernameNotFoundException ex) {
		ApiResponse apiResponse = new ApiResponse("No Such User Exists", false, "Bad Request");
		return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);

	}
}
