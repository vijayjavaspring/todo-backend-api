package com.todos.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

//@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	 //@ExceptionHandler(value 
		    //  = { IllegalArgumentException.class, IllegalStateException.class, ResourceNotFoundException.class })
		    protected ResponseEntity<Object> handleConflict(
		      RuntimeException ex, WebRequest request) {
		 		Map<Integer, String> error= new HashMap<>();
		 		
		        String bodyOfResponse = "This should be application specific";
		        error.put(601, bodyOfResponse);
		        return handleExceptionInternal(ex, error, 
		          new HttpHeaders(), HttpStatus.NOT_FOUND, request);
		    }
}