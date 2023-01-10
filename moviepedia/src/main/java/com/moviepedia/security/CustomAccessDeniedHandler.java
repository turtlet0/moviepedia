package com.moviepedia.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import lombok.extern.log4j.Log4j;

/* 31.2.4 직접 AccessDeniedHandler 구현하는 경우 
 * 	기본적으로 단순 error-page 지정하는 경우 URI 변화 없음
 * 	 접근 제한 시 이와 다른 쿠키나 세션에 특정한 작업하거나 HttpServletResponse에 특정한 헤더 정보 추가하는 등
 * 	다른 행위 하고싶은 경우 직접 Handler를 구현하는 것이 권장됨 */

@Log4j
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		
		// 아래 출력 결과는 hasRole에 걸린 경우임. 
		log.warn("Custom Access Denied Handler: " + accessDeniedException.getMessage()); // > Access is denied
		log.warn(accessDeniedException); // > org.springframework.security.access.AccessDeniedException: Access is denied
		log.warn(accessDeniedException.getCause());
		log.warn(accessDeniedException.getClass()); // > class org.springframework.security.access.AccessDeniedException
		log.warn(accessDeniedException.getLocalizedMessage()); // > Access is denied
		log.warn(accessDeniedException.getStackTrace()); // > [Ljava.lang.StackTraceElement;@4ca16303
		
		log.error("Redirect.........");
		
		
		response.sendRedirect("/accessError");
			// 접근 제한 걸리는 경우 해당 URL로 리다이렉트되는 것
	}

}
