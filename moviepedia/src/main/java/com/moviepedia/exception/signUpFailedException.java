package com.moviepedia.exception;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;

public class signUpFailedException extends DataAccessException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public signUpFailedException(String message) {
		super(message);
	}
}
