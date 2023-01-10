package com.moviepedia.security;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.moviepedia.service.MemberService;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Service
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
	"file:src/main/webapp/WEB-INF/spring/root-context.xml",
	"file:src/main/webapp/WEB-INF/spring/security-context.xml"
})

@Log4j

public class MemberTests {
	@Setter(onMethod_ = @Autowired) 
	private PasswordEncoder passwordEncoder;
	
	@Setter(onMethod_ = @Autowired) 
	private MemberService service;
	
	@Setter(onMethod_ = @Autowired)
	private JavaMailSenderImpl mailSender;
	
	
//	@Test
	public void changePassword() {
		
		String userid = "lju061@naver.com";
		String newUserpw = "a12345";
		
		boolean matchReturn = passwordEncoder.matches(newUserpw, service.get(userid).getUserpw());
		
		log.info("///////////"+matchReturn);
		
	}
	
	@Test
	public void smtpImaage() {
		// 회원가입 이메일 본인인증 메일 전송
	
		
			String email = "lju061@naver.com";
			String validationCode = RandomStringUtils.random(6, false, true);
			
			// 이메일 전송 양식
			String fromEmail = mailSender.getUsername();
			String toMail = email;
			String title = "MOVIEPEDIA 회원 가입 인증 이메일입니다.";
			String htmlContent = 
					"MOVIEPEDIA에 회원 가입해주셔서 감사합니다." +
			"<br><br>" +
			"인증 번호는 <p>" + validationCode + "</p> 입니다." +
			"<br>" +
			"<img src='cid:logo'/>" +
			"해당 인증 번호를 회원가입D 페이지 인증번호 확인란에 입력하여 주세요.";
	
			
			// 이메일 전송
			MimeMessage message = mailSender.createMimeMessage();
			
			try {
				MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
					// true 매개값 전달 시 multipart 형식의 메세지 전달이 가능해짐(파일 첨부 가능). 그리고 문자 인코딩 설정도 가능
				
				helper.setFrom(fromEmail);
				helper.setTo(toMail);
				helper.setSubject(title);
				helper.setText(htmlContent, true); 
					// true 매개값 전달 시 html 형식으로 전송(작성 x-> 단순 텍스트로 전달)
		
				// 파일 첨부
				File file = new File("");
				log.info(file.getAbsolutePath() + "/src/main/webapp/resources/img/logo.png"); // 상대경로
				helper.addInline("logo", new FileDataSource(file.getAbsolutePath() + "/src/main/webapp/resources/img/logo.png")); 
//				helper.addInline(contentId, dataSource); : 자원 첨부 메서드. FileDataSource 클래스 함께 이용
			
				mailSender.send(message);
				
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
//		for(String classPath: System.getProperty("java.class.path").split(";") ) {
//			log.info(classPath);
//				
//		}
	
	}
}
