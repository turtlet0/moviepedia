package com.moviepedia.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.moviepedia.domain.MemberVO;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Service
@RunWith(SpringJUnit4ClassRunner.class) 
@Log4j
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml",
	"file:src/main/webapp/WEB-INF/spring/security-context.xml"
}) 
public class randomStringTest {

	@Setter(onMethod_ = @Autowired) 
	private MemberService service;
	
	@Setter(onMethod_ = @Autowired) 
	private PasswordEncoder passwordEncoder;
	
//	@Test
	public void testRandomString() {
		
//		String userid = "mem7";
//		String randomString = "a";
//		service.testRandString(userid, randomString);
//		service.testRandString();
//		String randomString = RandomStringUtils.random(13, true, false);
		service.get("mem8");
	}
	
//	@Test
	public void testFileRead() {
		List<String> htmlStrList = null;
		
		// 파일 상대경로 파악
				Path curPath = Paths.get("");

				String cdir = null;
				cdir = curPath.toAbsolutePath().toString();
//				if(curPath.isAbsolute()) {
//					cdir = curPath.toString();
//				} else {
//					
//				}
				
				log.info(curPath.toString());
				log.info(cdir);
				
				String content = service.readTextFile("passwordResetEmailHtml.txt");
				content.replace("resetPassword/", "aa");
				log.info(content.indexOf("resetPassword/"));
				log.info(content.indexOf("resetPassword/aa"));
				log.info(content);
				String test = "abfvsfvsdvscv sdgsdkflsjdlk "
						+ "dfssfsdfsd cc";
				test = test.replace("sdf", "/안녕");
				log.info(test);
			
				
		
		
//		try {
//			htmlStrList = Files.readAllLines(Paths.get("src/main/webapp/resources/codeFile/passwordResetEmailHtml.txt"));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		htmlStrList.forEach(string -> log.info(string));
//		
//		StringBuilder sbHtmlStrList = new StringBuilder();
//		
//		for(int i=0;i<htmlStrList.size();i++) { 
//				// 판단과 저장을 한번에 하는 코드
//			sbHtmlStrList.append(htmlStrList.get(i));
//		}			
//
//		String result = sbHtmlStrList.toString(); // toString() : StringBuilder -> String
//		
//		log.info(result);

	}
	
	@Test
	public void testJwtToken() {
		String jwtToken = service.createJwtToken("lju061@naver.com");
		
		service.getUseridFromJwtToken(jwtToken);
	}
}
