package com.moviepedia.controller;

import java.security.Principal;
import java.util.HashMap;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.moviepedia.aop.HttpConnectionUtil;
import com.moviepedia.domain.MemberVO;
import com.moviepedia.service.MemberService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import oracle.jdbc.proxy.annotation.Pre;

@Controller
@Log4j
@AllArgsConstructor
public class PublicController {

	private MemberService service;
	
	
//	@ExceptionHandler(SQLException.class)
//	public void SQLIntegrityConstraintViolationException(Exception e) {
//		
//		log.info("~~~~~~~~~~~~~~~~~~~~~~~~~");
//		log.info(e.getClass());
//		log.info("~~~~~~~~~~~~~~~~~~~~~~~~~");
//		log.info("~~~~~~~~~~~~~~~~~~~~~~~~~");
//	}
	
	
	
	// tip) CustomAccessDeniedHandler를 거쳐 옴
	@GetMapping("/accessError")
	public void accessDenied(Authentication authentication, Model model) {
			// Authentication 객체 파라미터로 받아 이용 가능
		
		log.info("access Denied: " + authentication); // > org.springframework.security.authentication.UsernamePasswordAuthenticationToken@1635ca28: Principal: com.moviepedia.security.domain.CustomUser@a97cb337: Username: lju061@naver.com; Password: [PROTECTED]; Enabled: true; AccountNonExpired: true; credentialsNonExpired: true; AccountNonLocked: true; Granted Authorities: ROLE_MEMBER; Credentials: [PROTECTED]; Authenticated: true; Details: org.springframework.security.web.authentication.WebAuthenticationDetails@43458: RemoteIpAddress: 0:0:0:0:0:0:0:1; SessionId: 77BAC6FC5F19DB54974793B6E22CB86D; Granted Authorities: ROLE_MEMBER
			
		
		model.addAttribute("msg", "Access Denied");
	}
	
	
	/* 로그인 */
	// security_context의 login-page 속성의 URI는 반드시 GET방식으로 접근하는 URI를 지정
	/*
	 * LoginFailureHandler를 통해 /login URL로 forward하는 경우, 로그인 성공 시와 같이
	 * 스프링 시큐리티에서 자동으로 POST로 연결시켜주지 않는다.
	 * -> 직접 /login URL을 POST방식으로 매핑해줘야함
	 * tip) @RequestMapping의 method를 지정하지 않으면 모든 방식을 모두 지원
	 */
	@RequestMapping(value = "/login", method = {RequestMethod.GET ,RequestMethod.POST})
//	@PreAuthorize("isAnonymous()")
	public String Login(HttpServletRequest request, HttpServletResponse response,
//			String error, String logout,
			@RequestParam(value = "error", required = false) boolean error) {
				// required : URI에 붙어오는 파라미터가 필수로 존재해야하는지 여
	
		
		// 참고: 로그인 실패 시 url에 error 파라미터 붙여 전송
			// tip) spring security의 로그인 실패시 default URL : /login?error 
		if(error) {
			log.info("error 넘어옴 : " + error + " : 로그인 실패 후 /login으로 forward되었음");
		} else {
			log.info("error 넘어오지않음 : " + error + " : 다른 페이지에서 /login으로 redirect되었음");
			
		}

		
		
		/* 이전 페이지로 되돌아가기 위한 Referer 헤더값을 세션의 prevPage attribute로 저장 */
		// tip) HTTP Refer : 웹 브라우저로 WWW 서핑시 하이퍼링크 통해 각 사이트 페이지로 방문시 남는 흔적을 의미
		// 여기선 이전 페이지 URI가 Referer 헤더에 저장되어있는 것
		String uri = request.getHeader("Referer");
		if(uri != null && ! uri.contains("/login")) {
			request.getSession().setAttribute("prevPage", uri);
		}
		

		return "member/customLogin";	
	}
	
	
	
	/* 31.6 로그아웃 처리 
	 * 비밀번호 변경/회원탈퇴 등 기능 수행 후 로그아웃 필요한 경우 사용
	 * */
	@GetMapping("/logout")
	public String logoutGET() {
		
		log.info("logout");
		
		return "member/logout";
	}
	
	
	
	/* 회원가입 */
	@GetMapping("/signUp")
	@PreAuthorize("isAnonymous()")
		// 데이터 입력 위한 GET방식 페이지
	public String register() {
		return "member/signUp";
		
	}
	
	// 회원가입
	@PostMapping("/signUp")
	@PreAuthorize("isAnonymous()")
	public String register(MemberVO memberVO, RedirectAttributes rttr) {
		// rttr : addFlashAttr. 이용해 결과값 view로 전달하기 위함
		
		service.register(memberVO);
		
		String resultContent = "님 회원가입이 완료되었습니다. MOVIEPEDIA에 오신 것을 환영합니다.";
		rttr.addFlashAttribute("result", memberVO.getUserid() + resultContent);
		
		return "redirect:/";
			// 다른 페이지로 재전송하는 이유 : register 페이지에서 입력 항목들 전송 후 list와 같이 다른 URL로 재전송하지 않으면 동일한 내용을 새로고침 통해 
		// 계속 반복(도배)하는 문제 발생 -> 등록,수정,삭제 작업은 처리 완료된 후 다시 동일한 내용 전송할 수 없도록 아예 브라우저의 URL 이동하는 방식 이용
		
	}
	
	
	
	/* 비밀번호 찾기 */
	@GetMapping("/user/findPassword")
	@PreAuthorize("isAnonymous()")
	public String findPassword() {
		return "member/findPassword";
	}
	
	@PostMapping("/user/findPassword")
	@PreAuthorize("isAnonymous()")
	public String findPassword(String userid) {
		log.info("userid: " + userid);
		

		return "redirect:/";
	}
	
	
	
	/* 비밀번호 초기화 */
	@GetMapping("user/resetPassword/{jwtToken:.+}")
	@PreAuthorize("isAnonymous()")
		// tip) dot(.)이 포함된 파라미터를 @PathVariable를 통해 받을 때 . 이하가 생략되는 경우 발생 -> url의 파라미터에 :.+ 추가
			// error) io.jsonwebtoken.MalformedJwtException: JWT strings must contain exactly 2 period characters. Found: 1
	public String resetPassword(@PathVariable("jwtToken") String jwtToken, Model model) {
		
		log.info("resetPassword...." + jwtToken);
		
		String userid = service.getUseridFromJwtToken(jwtToken);
		
		
		if(userid == null) {
			// rttr에서 비밀번호 변경 기한이 만료되었습니다. 다시 변경 이메일 변경 받으세요.
			
			return "redirect:user/findPassword";
		}
		
		model.addAttribute("userid", userid);
		
		model.addAttribute("jwtToken", jwtToken);
		
		return "member/resetPassword";	
	}
	
	
	/* 비밀번호 초기화 */
	@PostMapping("user/resetPassword")
	@PreAuthorize("isAnonymous()")
	@Transactional
	public String resetPasswordPost(@RequestParam(value = "jwtToken", required = false) String jwtToken, RedirectAttributes rttr,
			String userid, String userpw) {
		
		log.info("resetPasswordPost....." + userpw);
		
		// 기존 비밀번호와 동일 여부 체크
		if(service.checkUserpwSameness(userid, userpw)) {
			// 기존 비밀번호와 동일
			rttr.addFlashAttribute("result", "기존 비밀번호와 동일합니다.");
			
			return "redirect:/user/resetPassword/" + jwtToken;
		}
		
		// 비밀 번호 변경
		service.resetPassword(userid, userpw);
		
		log.info(service.isAccountNonLocked(userid));
		if(! service.isAccountNonLocked(userid)) {
			// FAILURECNT 0 초기화 / ACCOUNTNONLOCKED 1 초기화
			service.unlockAccountnonlocked(userid);	
		}
		
		
		return "redirect:/login";
		
	}
	
	
	/* 비밀번호 변경 */
	@GetMapping("user/changePassword")
	@PreAuthorize("hasRole('ROLE_MEMBER')")
	public String changePassword(Model model, Principal principal) {
		
		log.info("changePassword....");
		
		/* DB 정보 가져오기 */
		
		if (principal != null) {
			System.out.println("타입정보 : " + principal.getClass());
			System.out.println("ID정보 : " + principal.getName());

			String userid = principal.getName();
			
			model.addAttribute("userInfo", service.get(userid));
			

		}	
		
		return "member/changePassword";	
	}
	
	/* POST_비밀번호 변경 */
	@PostMapping("user/changePassword")
	@PreAuthorize("hasRole('ROLE_MEMBER')")
	@Transactional
	public String changePasswordPost(Principal principal, RedirectAttributes rttr,
			String userOldPw, String userNewPw) {
		
		log.info("userOldPw: "+userOldPw);
		log.info("userNewPw: " + userNewPw);
//		 rttr : addFlashAttr. 이용해 결과값 view로 전달하기 위함
		
		log.info("changePasswordPost....." + userNewPw);
		
		System.out.println("타입정보 : " + principal.getClass());
		System.out.println("ID정보 : " + principal.getName());

		String userid = principal.getName();
		
		// 기존 비밀번호와 유저 DB 저장 비밀번호 동일 여부 체크
		if(! service.checkUserpwSameness(userid, userOldPw)) {
//			 유저 DB 저장 비밀번호와 동일
			rttr.addFlashAttribute("result", "입력하신 기존 비밀번호가 틀렸습니다. 정확히 입력바랍니다.");
			
			return "redirect:/user/changePassword";
		}
		
		// 변경할 비밀번호와 기존 비밀번호와 동일 여부 체크
		if(service.checkUserpwSameness(userid, userNewPw)) {
//			 기존 비밀번호와 동일
			rttr.addFlashAttribute("result", "기존 비밀번호와 동일합니다. 다른 비밀번호로 변경바랍니다.");
			
			return "redirect:/user/changePassword";
		}
		
		// 비밀 번호 변경
		service.resetPassword(userid, userNewPw);
		
		
//		rttr.addFlashAttribute("logoutResult", "logout");		
//		return "redirect:/";
			// -> Main 페이지 이동 후 Main JSP 페이지에서 logout form post 전송하는걸로 구현..
		
		
		return "redirect:/logout";
		// 다른 페이지로 재전송하는 이유 : register 페이지에서 입력 항목들 전송 후 list와 같이 다른 URL로 재전송하지 않으면 동일한 내용을 새로고침 통해 
		// 계속 반복(도배)하는 문제 발생 -> 등록,수정,삭제 작업은 처리 완료된 후 다시 동일한 내용 전송할 수 없도록 아예 브라우저의 URL 이동하는 방식 이용
		
	}
	
	
	/* 회원 탈퇴 */
	@GetMapping("user/withdraw")
	@PreAuthorize("hasRole('ROLE_MEMBER')")
	public String withdraw(Model model, Principal principal) {
		
		log.info("withdraw....");
		
		/* DB 정보 가져오기 */
		
		if (principal != null) {
			System.out.println("타입정보 : " + principal.getClass());
			System.out.println("ID정보 : " + principal.getName());

			String userid = principal.getName();
			
			model.addAttribute("userInfo", service.get(userid));
			

		}	
		
		return "member/withdraw";	
	}
	
	
	/* POST_회원 탈퇴 */
	@PostMapping("user/withdraw")
	@PreAuthorize("hasRole('ROLE_MEMBER')")
	@Transactional
	public String withdrawPost(Principal principal, RedirectAttributes rttr,
			String userpw) {
		
		log.info("userpw: "+userpw);
//		 rttr : addFlashAttr. 이용해 결과값 view로 전달하기 위함
		
		log.info("withdrawPost....." + userpw);
		
		System.out.println("타입정보 : " + principal.getClass());
		System.out.println("ID정보 : " + principal.getName());

		String userid = principal.getName();
		
		// 기존 비밀번호와 유저 DB 저장 비밀번호 동일 여부 체크
		if(! service.checkUserpwSameness(userid, userpw)) {
//			 유저 DB 저장 비밀번호와 동일
			rttr.addFlashAttribute("result", "입력하신 비밀번호가 틀렸습니다. 정확히 입력바랍니다.");
			
			return "redirect:/user/withdraw";
		}
		
	
		// 회원 탈퇴
		if(! service.withdraw(userid)) {
//			 유저 DB 저장 비밀번호와 동일
			rttr.addFlashAttribute("result", "회원 탈퇴가 정상적으로 처리되지 않았습니다. 다시 시도해 주세요.");
			
			return "redirect:/user/withdraw";
		}
		
		return "redirect:/logout";
		// 다른 페이지로 재전송하는 이유 : register 페이지에서 입력 항목들 전송 후 list와 같이 다른 URL로 재전송하지 않으면 동일한 내용을 새로고침 통해 
		// 계속 반복(도배)하는 문제 발생 -> 등록,수정,삭제 작업은 처리 완료된 후 다시 동일한 내용 전송할 수 없도록 아예 브라우저의 URL 이동하는 방식 이용
		
	}
	
	
	
	
}
