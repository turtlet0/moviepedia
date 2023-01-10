package com.moviepedia.security;
// 로그인 실패 후의 동작 제어 커스터마이징

import java.io.IOException;

import javax.security.auth.login.CredentialExpiredException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.moviepedia.domain.MemberVO;
import com.moviepedia.mapper.LikeMapper;
import com.moviepedia.mapper.MemberMapper;

import lombok.Data;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@Data // tip) getter/setter 미 선언 시 context.xml에서 bean등록 시 property 태그에 에러 발생함
public class CustomLoginFailureHandler implements AuthenticationFailureHandler {

	@Setter(onMethod_ = @Autowired)
		// Autowired 연결안하면 mapper 제 기능 못함
	private MemberMapper mapper;
	
	private final String DEAULT_FAILURE_URL = "/login?error=true";
	// 필요 정보 담을 변수 선언
		// tip) 굳이 변수를 선언하지 않고 오버라이딩한 메서드 내에 선언한 변수만으로 코드 작성해도 됨(?)(현재 SuccessHandler 그렇게 함)
//	private String loginUsername;
//	private String loginPassword;
//	private String errorMessage;
//	private String defaultFailureUrl;
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		/* HttpServletRequest 객체: 웹에서 넘어온 Request 값을 가지고 있는 객체
		 * 	: 로그인 페이지에서 넘어온 파라미터들을 input name값으로 가져올 수 있음 (getParameter)
		 * HttpServletResponse 객체: 출력을 정의할 수 있는 객체
		 * AuthenticationException 객체: 로그인 실패 정보를 가지고 있는 객체
		 *  : 실패 메시지를 조건별로 구분해 다시 setParameter하면 됨
		 */
		String username = request.getParameter("username");
		
		String errorMsg = null;
		
		log.warn(exception.getMessage());
		
		
		if(exception instanceof UsernameNotFoundException) {
			// 계정 없는 경우
			/*
			 *  UserDetailsService에서 null인 경우 InternalAuthenticationServiceException 발생시키지만
			 *  throw InternalAuthenticationServiceException해도 동일하게 InternalAuthenticationServiceException 에러 내뿜음
			 *  So, UsernameNotFoundException 예외로 throw함
			 */
			errorMsg = username + "은 가입되지 않은 이메일입니다.";
		} else if(exception instanceof BadCredentialsException) {
			// 비밀번호 오류
			
			checkAccountNonLocked(username);
			if(getFailureCnt(username) == 5 && isAccountNonLocked(username) == false) {
				errorMsg = "5회 이상 비밀번호 오류로 계정이 잠겼습니다. <br/>"
						+ "비밀번호 찾기를 통해 다시 로그인해 주세요.";
				
			} else {
				errorMsg = "비밀번호가 일치하지 않습니다. <br/>"
						+ "5회 이상 틀릴 경우 계정이 잠깁니다. (현재 " + getFailureCnt(username)+"/5)";	
			}
			
			
			
			log.warn("getFailureCnt(username): " + getFailureCnt(username));
			
		} else if(exception instanceof DisabledException) {
			// 계정 비활성화
			errorMsg = "계정이 비활성화되었습니다. 관리자에게 문의하세요.";
		} else if(exception instanceof CredentialsExpiredException) {
			// 비밀번호 유효기간 만료
			// tip) CredentialExpiredException 아닌 Credential's'ExpiredException
			errorMsg = "비밀번호 유효기간이 만료되었습니다. 관리자에게 문의하세요.";
		} else if(exception instanceof AccountExpiredException) {
			errorMsg = "만료된 계정입니다.";
		} else if(exception instanceof LockedException) {
			errorMsg = "5회 이상 비밀번호 오류로 계정이 잠겼습니다. <br/>"
					+ "비밀번호 찾기를 통해 다시 로그인해 주세요.";
		} 
		
		
		// TODO Auto-generated method stub
		log.warn("username: " + request.getParameter("username"));
		log.warn("password: " + request.getParameter("password"));
		log.info("=====================================");
		
		request.setAttribute("username", request.getParameter("username"));
		request.setAttribute("password", request.getParameter("password"));
		request.setAttribute("errorMsg", errorMsg);
		
		request.getRequestDispatcher("/login?error=true").forward(request, response);
		/* 
		 * tip) login?error과 같이 url에 붙인 파라미터의 값을 지정하지않으면 String타입 "" 값이 됨 -> @Controller에서 boolean true으로 파라미터받는경우 타입달라 에러 발생함
		 * WARN : org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver - Failed to bind request element: org.springframework.web.method.annotation.MethodArgumentTypeMismatchException: Failed to convert value of type 'java.lang.String' to required type 'boolean'; nested exception is java.lang.IllegalArgumentException: Invalid boolean value []
		 */
			// : view로 forward
			
		
	}
	
	// 비밀번호 오류로 인한 계정 잠금 체크 메서드
	private boolean checkAccountNonLocked(String username) {
		
		boolean accountNonLocked = true;
		
		MemberVO memberVO = mapper.getFailureCntAccountNonLocked(username);
		
		accountNonLocked = memberVO.isAccountNonLocked();
		
	
		if(accountNonLocked) { // true인 경우에만 로직 수행
			
			if(memberVO.getFailureCnt() < 5) {
			
				mapper.updateFailureCnt(username);
				log.warn("getFailureCnt(username): " + getFailureCnt(username));
			} else {
			
				mapper.changeAccountNonLocked(username);
			}
		}
		
		
		return accountNonLocked;
		
	}
	
	// 비밀번호 오류 횟수 반환 메서드
	private int getFailureCnt(String username) {
		MemberVO memberVO = mapper.getFailureCntAccountNonLocked(username);
		return memberVO.getFailureCnt();
	}
	
	private boolean isAccountNonLocked(String username) {
		MemberVO memberVO = mapper.getFailureCntAccountNonLocked(username);
		return memberVO.isAccountNonLocked();
	}

}
