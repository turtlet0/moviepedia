package com.moviepedia.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import com.moviepedia.mapper.MemberMapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

// 31.5 AuthenticationSuccessHHandler 인터페이스 이용 
	// 로그인 성공 이후 원하는 동작하도록 제어

@Log4j
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {
	// CustomLoginSuccessHandler는 로그인한 사용자에 부여된 권한 Authentication 객체를 이용해서,
	// 사용자가 가진 모든 권한을 문자열로 체크함
	// ex) 사용자가 ROLE_ADMIN 권한 가졌다면 로그인 시 바로 admin 페이지로 이동하도록 하는 것
	
	@Setter(onMethod_ = @Autowired)
	// Autowired 연결안하면 mapper 제 기능 못함
	private MemberMapper mapper;
	
	private final RequestCache requestCache = new HttpSessionRequestCache();
	private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		
		/* 세션에 남아있는 로그인 실패 에러 내용 제거 */
		/// 로그인 실패 한번이라도 발생 후엔 로그인 성공하더라도 로그인 실패 에러가 세션에 저장되어 남아있게됨
		// 이를 로그인 성공 시 삭제하는 작업 수행 필요
		// : 실패 시엔 forward 방식으로 페이지 전환하므로 request 정보 남아있게됨 그래서 여기서 삭제하는것 
		clearAuthenticationAttributes(request);
			
		// 로그인 실패 횟수 초기화
		mapper.resetFailureCnt(authentication.getName()); // Returns the name of this principal. (String)
		
		
		/* 이전 페이지 정보 저장하는 경우 */
		SavedRequest savedRequest = requestCache.getRequest(request, response);
		
		// prevPage가 존재하는 경우 -> 사용자가 직접 로그인 경로로 로그인 요청
		// 이후 기존 Session의 prevPage attribute 제거
		
		String prevPage = (String) request.getSession().getAttribute("prevPage");
		if(prevPage != null) {
			request.getSession().removeAttribute("prevPage");
		}

		
		// 로그인 성공 후 이동 기본 URI
		String uri = "/";
		
		// savedRequest 존재하는 경우 (인증 권한이 없는 페이지 접근)
			// 스프링 시큐리티는 권한이 없는 페이지 접근 시 Security Filter가 작업을 인터셉트하여 
			// 이전 페이지 URI를 savedRequest에 자동으로 저장하고 login page로 redirect함
		if(savedRequest != null) {
			uri = savedRequest.getRedirectUrl();
		} else if(prevPage != null && !prevPage.equals("")) {
			
			if(prevPage.contains("/signUp")) {
				// 회원가입 페이지에서 로그인 페이지로 넘어오는 특수한 경우 대비
				uri = "/";
			} else if(prevPage.contains("/user/resetPassword")){
				// 비밀번호 변경 페이지에서 로그인 페이지로 넘어오는 특수한 경우 대비
				uri = "/";
			} else {
				uri = prevPage;
			}
			
		}
		
		
		
		
		log.warn("Login Success");
		
		List<String> roleNames = new ArrayList<>();
		
		authentication.getAuthorities().forEach(authority -> {
			roleNames.add(authority.getAuthority());
		});
		
		log.warn("ROLE NAMES: " + roleNames);
		
		
//		if(roleNames.contains("ROLE_ADMIN")) {
//			
//			response.sendRedirect("/sample/admin");
//			
//			return;
//		}
//		
//		if(roleNames.contains("ROLE_MEMBER")) {
//			
//			response.sendRedirect("/sample/member");
//			
//			return;
//		}
		
		
		// 로그인 성공 후 Redirect할 정보 및 URL 지정
		redirectStrategy.sendRedirect(request, response, uri);
			// redirect: 새로운 요청 수행 -> request, response 객체 새롭게 생성되는 것
			// tip) 그래서 로그인 실패 시에는 로그인 시의 정보를 받고 이를 가공해 다시 로그인 에러 페이지에 뿌려주기 위해 forward 방식 사용
	
	}
	
	
	
	// 로그인 실패 시 남은 에러 세션 제거 메서드
	protected void clearAuthenticationAttributes(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		
		if(session == null) return;
		
		session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		
	}
	
	

}
