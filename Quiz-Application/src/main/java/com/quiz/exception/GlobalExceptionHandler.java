package com.quiz.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

//	Exception Handler for Exception class
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetails> exceptionClassHandler(Exception pe, WebRequest req) {

		ErrorDetails err = new ErrorDetails(LocalDateTime.now(), pe.getMessage(), req.getDescription(false));

		return new ResponseEntity<ErrorDetails>(err, HttpStatus.BAD_REQUEST);
	}

//	Data validation exception handler
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorDetails> dataValidationExceptionHandler(MethodArgumentNotValidException ne) {

		ErrorDetails err = new ErrorDetails();
		err.setTimestamp(LocalDateTime.now());
		err.setMessage("Validaton error");
		err.setDetails(ne.getBindingResult().getFieldError().getDefaultMessage());

		return new ResponseEntity<ErrorDetails>(err, HttpStatus.BAD_REQUEST);
	}

//	Exception Handler for No URI found or wrong uri
	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<ErrorDetails> noUriHandlerFoundException(NoHandlerFoundException nfe, WebRequest req) {
		ErrorDetails err = new ErrorDetails(LocalDateTime.now(), nfe.getMessage(), req.getDescription(false));
		return new ResponseEntity<ErrorDetails>(err, HttpStatus.OK);
	}

	@ExceptionHandler(ResultNotAvailableException.class)
	public ResponseEntity<ErrorDetails> ResultNotAvailableException(ResultNotAvailableException nfe, WebRequest req) {
		ErrorDetails err = new ErrorDetails(LocalDateTime.now(), nfe.getMessage(), req.getDescription(false));
		return new ResponseEntity<ErrorDetails>(err, HttpStatus.OK);
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ErrorDetails> UserNotFoundException(UserNotFoundException nfe, WebRequest req) {
		ErrorDetails err = new ErrorDetails(LocalDateTime.now(), nfe.getMessage(), req.getDescription(false));
		return new ResponseEntity<ErrorDetails>(err, HttpStatus.OK);
	}

}
