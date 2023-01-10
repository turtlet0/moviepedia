package com.moviepedia.security;
// AUthenticationProvier 인터페이스
// 로그인 페이지에서 입력한 로그인 정보와 DB에서 가져온 사용자 정보를 비교해주는 인터페이스
// 이 인터페이스의 authenticate() 메서드는 화면에서 사용자가 입력한 로그인 정보를 담고 있는 
// Authentication 객체를 가지고 있음
// 이때 UserDetailsService 인터페이스의 loadUserByUsername() 메서드를 통해 DB에서 사용자의 정보 가져온다.

import javax.security.auth.login.CredentialExpiredException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountExpiredException;

// AuthenticationProvider 인터페이스는 정보 비교해 인증에 성공하면, 인증된 Authentication 객체를 리턴하므로
// 아이디 일치 여부, 비밀번호 일치 여부, 계정 활성화 여부 등을 확인한 후 리턴하도록 로직 구성

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.rsocket.RSocketSecurity.JwtSpec;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.moviepedia.security.domain.CustomUser;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

//  
/* Authentication(인증) : 'A'라고 주장하는 주체(user, subject, principal)가 'A'가 맞는지 확인하는 것
코드에서 Authentication : 인증 과정에 사용되는 핵심 객체
ID/PASSWORD, JWT, OAuth 등 여러 방식으로 인증에 필요한 값이 전달되는데 이것을 하나의 인터페이스로 받아 수행하도록 추상화 하는 역할의 인터페이스다.
출처: https://jeong-pro.tistory.com/205 [기본기를 쌓는 정아마추어 코딩블로그:티스토리]
 *
 * 로그인 시 UsernamePasswordAuthenticationFilter : username, password를 쓰는 form기반 인증하는 필터
 * 이때 mapper 또는 UserDetailsService를 통해 username을 찾게 되고 해당하는 user정보가 있으면 
 * AuthenticationManager에 Authentication 객체 반환됨(즉, 아이디 존재하므로 인증과정 거치게되는 것)
 * 이때 아이디가 없는 경우 mapper는 null을 반환하고 AuthenticationManager에서  Authentication 객체받지 못해 에러 띄우게되는 것
 * -> AuthenticationFailureHandler 실행됨
 * 
 * =>  AuthenticationManager를 통한 인증 실행
 * 이때 AuthenticationManager 인터페이스는 AuthenticationProvider 를 통해 인증 과정 실제 실행한다.
 * -> 성공 시 Authentication 객체를 SecurityContext에 저장 후 AuthenticationSuccessHandler 실행
 * 
 * UsernamePasswordAuthenticationFilter
 * 
 */
@Log4j
public class CustomAuthenticationProvider implements AuthenticationProvider {

	@Setter(onMethod_ = @Autowired)
	private UserDetailsService userDetailsService;
	
	@Setter(onMethod_ = @Autowired)
	private CustomUserDetailsService customUserDetailsService;
	
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		// > WARN : com.moviepedia.security.CustomUserDetailsService - queried by member mapper: MemberVO(userid=a@b.c, userpw=$2a$10$10.uGgJ55UeL77beQkqdre/LuqhW.eNlT.RimIPGuVcCqc4cJY4ZG, 
		// > userName=aa, enabled=false, randomString=AdGdTL3gSz9fw, regDate=Fri Oct 21 09:48:24 KST 2022, updateDate=Fri Oct 21 09:48:24 KST 2022, authList=[AuthVO(userid=a@b.c, auth=ROLE_MEMBER)])
		
		log.warn("===========================");
		log.warn(authentication.getAuthorities()); // > []
		log.warn(authentication.getPrincipal()); // > a@b.c
		log.warn(authentication.getCredentials()); // > a123456
		log.warn(authentication.getName()); // > a@b.c
		log.warn(authentication.getClass()); // >  class org.springframework.security.authentication.UsernamePasswordAuthenticationToken
		log.warn(authentication.getDetails()); // > org.springframework.security.web.authentication.WebAuthenticationDetails@166c8: RemoteIpAddress: 0:0:0:0:0:0:0:1; SessionId: 41ED1D9F7025E0E690CB2804CB1FE522
		log.warn("===========================");
		
		String username = (String) authentication.getPrincipal(); // getName()가 출력결과 같으나 Object로 return됨 (getName은 String)
//		String username = authentication.getName();
		String password = (String) authentication.getCredentials(); // 입력된 암호화되지않은 비밀번호 출력됨
		
		CustomUser customUser = (CustomUser) userDetailsService.loadUserByUsername(username); // customUserDetailsService을 이용한 것과 getter 통해 얻는 값 똑같음
		CustomUser customUserC = (CustomUser) customUserDetailsService.loadUserByUsername(username);
		
		// log.info 출력안됨
		log.warn(customUser); // > com.moviepedia.security.domain.CustomUser@5757578: Username: a@b.c; Password: [PROTECTED]; 
		// Enabled: true; AccountNonExpired: true; credentialsNonExpired: true; AccountNonLocked: true; Granted Authorities: ROLE_MEMBER
		log.warn("--------------------------");	
		log.warn(customUserC); // > com.moviepedia.security.domain.CustomUser@5757578: Username: a@b.c; Password: [PROTECTED]; 
		// Enabled: true; AccountNonExpired: true; credentialsNonExpired: true; AccountNonLocked: true; Granted Authorities: ROLE_MEMBER
		log.warn("===========================");
		
		log.warn(customUser.getAuthorities()); // > [ROLE_MEMBER]
		log.warn(customUser.getClass()); // > class com.moviepedia.security.domain.CustomUser
		log.warn(customUser.getMemberVO()); // > MemberVO(userid=a@b.c, userpw=$2a$10$10.uGgJ55UeL77beQkqdre/LuqhW.eNlT.RimIPGuVcCqc4cJY4ZG, 
		// userName=aa, enabled=false, randomString=AdGdTL3gSz9fw, regDate=Fri Oct 21 09:48:24 KST 2022, updateDate=Fri Oct 21 09:48:24 KST 2022, authList=[AuthVO(userid=a@b.c, auth=ROLE_MEMBER)])
		log.warn(customUser.getPassword()); // > $2a$10$10.uGgJ55UeL77beQkqdre/LuqhW.eNlT.RimIPGuVcCqc4cJY4ZG
		log.warn(customUser.getUsername()); // > a@b.c
		log.warn("--------------------------");
		log.warn(customUserC.getAuthorities()); // > [ROLE_MEMBER]
		log.warn(customUserC.getClass()); // > class com.moviepedia.security.domain.CustomUser
		log.warn(customUserC.getMemberVO()); // > MemberVO(userid=a@b.c, userpw=$2a$10$10.uGgJ55UeL77beQkqdre/LuqhW.eNlT.RimIPGuVcCqc4cJY4ZG, 
		// userName=aa, enabled=false, randomString=AdGdTL3gSz9fw, regDate=Fri Oct 21 09:48:24 KST 2022, updateDate=Fri Oct 21 09:48:24 KST 2022, authList=[AuthVO(userid=a@b.c, auth=ROLE_MEMBER)])
		log.warn(customUserC.getPassword()); // > $2a$10$10.uGgJ55UeL77beQkqdre/LuqhW.eNlT.RimIPGuVcCqc4cJY4ZG
		log.warn(customUserC.getUsername()); // > a@b.c
		log.warn("===========================");
		log.warn(customUser.isEnabled());
		log.warn(customUser.isCredentialsNonExpired());
		log.warn(customUser.isAccountNonExpired());
		log.warn(customUser.isAccountNonLocked());
		log.warn("===========================");
		
		
		/*
		 *  tip) DB에 사용자계정없는경우 UserDetailsService에서 InternalAuthenticationServiceException 에러를 내고
		 *  AuthenticationProvider을 타지 않고 바로 failureHandler로 넘어감
		 *  이를 방지하기 위해선 UserDetailsService에서 null일 경우 UsernameNotFoundException 예외로 throw해줘야 
		 *  에러 발생하지 않고 안정적으로 failureHandler로 넘어감 
		 */
		
		/* 각 예외 상황에 대한 예외처리 커스터마이징 가능 */
			// tip) 만약 AuthenticationProvider을 커스텀하지 않는다면 아래와 같이 각 예외상황에 맞는 예외를 throw 해줌
			// https://codevang.tistory.com/268
			// L 위 블로그에서 AuthenticationServiceException는 계정존재하지 않을때 발생하는 예외라고 하였으나
			// spring 문서에 따르면 시스템 문제로 인해 인증 요청 처리할 수 없는경우 발생한다함. 
			// -> 커스텀UserDetailsService에서 userNameNotFoundException으로 처리해 Handler로 바로 넘기는게 맞는듯
		
		// 나는 커스텀 했으므로 각 예외 상황에 따른 예외처리하지 모두 해줘야함 그렇지않으면 정상적으로 return되어 SuccessHandler로 넘어가버림
		// throw된 예외는 failureHandler에서 AuthenticationException 객체(로그인 실패 정보를 가지고 있는 객체)에 담기게됨
		if(!matchPassword(password, customUser.getPassword())) {
			log.warn("비밀번호 오류");
			throw new BadCredentialsException(username);		
		} else if(!customUser.isEnabled()) {
			log.warn("계정 비활성화");
			throw new DisabledException(username);
		} else if(!customUser.isCredentialsNonExpired()) {
			log.warn("비밀번호 만료");
			throw new CredentialsExpiredException(username);
		} else if(!customUser.isAccountNonExpired()) {
			log.warn("계정 만료");
			throw new AccountExpiredException(username);
		} else if(!customUser.isAccountNonLocked()) {	
			log.warn("계정 잠김");
			throw new LockedException(username);
		}
		
		
		return new UsernamePasswordAuthenticationToken(username, password, customUser.getAuthorities());
			// UsernamePasswordAuthenticationToken은 추후 인증이 끝나고 SecurityContextHolder.getContext()에 등록될 Authentication 객체
			//	UsernamePasswordAuthenticationToken ->상속 AbstractAuthenticationToken ->상속 Authentication
	}

	// AuthenticationProvider 인터페이스가 지정된 Authentication 객체를 지원하는 경우엔 true를 리턴시킬 것
	// ** 즉, true 필수!!!!!
	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return true;
	}
	

	
	// password 일치 여부 확인 메서드
	private boolean matchPassword(String loginPassword, String password) {
		return loginPassword.equals(password);
	}
}
