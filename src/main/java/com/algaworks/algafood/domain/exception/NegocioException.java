package com.algaworks.algafood.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


public class NegocioException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public NegocioException(String message) {
		super(message);
	}
	
	public NegocioException(String message, Throwable cause) {
		super(message, cause);
	}

}
