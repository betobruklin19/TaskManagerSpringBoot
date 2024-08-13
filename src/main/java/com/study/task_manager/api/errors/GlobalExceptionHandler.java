package com.study.task_manager.api.errors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<CommonResponseBean> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
		List<ErrorBean> errors = new ArrayList<>();

		// Iterate over field errors
		ex.getFieldErrors().forEach(error -> {
			if (error.getDefaultMessage() != null) {
				errors.add(new ErrorBean(HttpStatus.BAD_REQUEST.getReasonPhrase(), error.getField() + ", " + error.getDefaultMessage()));
			}
		});

		CommonResponseBean response = new CommonResponseBean();
		response.setErrors(errors);

		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
}
