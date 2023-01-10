package com.moviepedia.exception;

import java.sql.SQLIntegrityConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.moviepedia.service.MemberService;

import lombok.extern.log4j.Log4j;

@Log4j
public class CustomExceptionAdvice {

	private MemberService memberService;
	
	@ExceptionHandler(SQLIntegrityConstraintViolationException.class)
	public void except(Exception e) {
		log.error("error Exception......." + e.getMessage());
		log.info("info Exception......." + e.getMessage());
		
		String userid = "mem7";
		String randomString = "ab";
		log.error(userid + "/" + randomString);
//		memberService.testRandString(userid, randomString);
		
//		return new ResponseEntity<Integer>(memberService.testRandString(userid, randomString), HttpStatus.OK);
		
	}
	
	@ExceptionHandler(signUpFailedException.class)
	public void handleSignUpFailedException(Exception e) {
		log.error("handleSignUpFailedException.....");
	}
}
