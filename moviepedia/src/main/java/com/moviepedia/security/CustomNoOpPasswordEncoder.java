package com.moviepedia.security;

import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.extern.log4j.Log4j;

/* 32.1.1 PasswordEncoder 문제 해결
 * 기본적으로 로그인 시 패스워드가 평문으로 처리되어 예외발생
 * 스프링 시큐리티 5부터는 기본적으로 PasswordEncoder를 지정해야함
 * : PasswordEncoder 인터페이스 존재
 * -> 예제는 암호화가 없는 PasswordEncoder를 직접 구현해 사용할 것
 */

@Log4j
public class CustomNoOpPasswordEncoder implements PasswordEncoder {

	@Override
	public String encode(CharSequence rawPassword) {
		
		log.warn("before encoder: " + rawPassword);
		return rawPassword.toString();
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		
		log.warn("matches: " + rawPassword + ":  " + encodedPassword);
		
		return rawPassword.toString().equals(encodedPassword);
	}

}
