package com.moviepedia.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.moviepedia.domain.CommentVO;
import com.moviepedia.domain.MemberVO;
import com.moviepedia.domain.MovieVO;
import com.moviepedia.domain.PeopleVO;
import com.moviepedia.service.MemberService;
import com.moviepedia.service.SearchService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@RestController
@AllArgsConstructor
@Log4j
public class MemberController {

	private MemberService service;
	
	// 이메일 중복 검사 URI
	@PostMapping(value = "/rest/member/signUp/emailDupliCheck",
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public int checkEmailDuplicate(String email) {
		log.info("checkEmailDuplicate.......");
		
		return service.checkEmailDuplicate(email);
	}
	
	// 이메일 본인 인증 메일 전송 URI
	@PostMapping(value = "/rest/member/signUp/emailVerification",
			consumes = "application/json",
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public String sendVerificationEmail(@RequestBody Map<String, String> emailMap) {
		String email = emailMap.get("email");
		
		log.info(email.getClass());
		log.info(email.getClass().getName());
		log.info("sendVerificationEmail...... email: " + email);
		
		return service.sendVerificationEmail(email);
	}
	
//	@PostMapping(value = "/rest/member/signUp/emailVerification",
//			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//	public String sendVerificationEmail(String email) {

//		log.info("sendVerificationEmail...... email: " + email);
//		
//		return service.sendVerificationEmail(email);
//	}
	
	
	// 이메일 본인 인증 메일 전송 URI
		@PostMapping(value = "/rest/member/findPassword/passwordReset",
				consumes = "application/json",
				produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
		public String[] sendPasswordResetEmail(@RequestBody Map<String, String> emailMap) {
			String email = emailMap.get("email");
			
			log.info(email.getClass());
			log.info(email.getClass().getName());
			log.info("sendPasswordResetEmail...... email: " + email);
			
			return service.sendPasswordResetEmail(email).split(".");
		}
		
		
		// 프로필 수정
		@RequestMapping(method = {RequestMethod.PUT, RequestMethod.PATCH},
				value = "/rest/member/update/profile",
				consumes = "application/json",
				produces = {
						MediaType.APPLICATION_ATOM_XML_VALUE,
						MediaType.APPLICATION_JSON_UTF8_VALUE
				})
		public ResponseEntity<MemberVO> modify(@RequestBody MemberVO memberVO) throws UnsupportedEncodingException{
			
			log.info("modify before:  " + memberVO);
			
			service.modifyUserNameNIntroduction(memberVO);
			
			log.info("modify after:  " + memberVO.getUserName()+"/"+memberVO.getUserIntroduction());

//			return service.modify(memberVO) == 1 
//				? new ResponseEntity<String>("success", HttpStatus.OK)
//				: new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<MemberVO>(memberVO, HttpStatus.OK);
		}
	
}
