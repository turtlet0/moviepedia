package com.moviepedia.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.moviepedia.aop.ExeTimer;
import com.moviepedia.domain.FavoritePeopleDTO;
import com.moviepedia.domain.MemberVO;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Service
@RunWith(SpringJUnit4ClassRunner.class) 
@Log4j
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml",
	"file:src/main/webapp/WEB-INF/spring/security-context.xml"
}) 
public class memberServiceTest {

	@Setter(onMethod_ = @Autowired) 
	private MemberService service;
	

//	@Test
	public void testWithdraw() {
		String userid = "mem8";
		service.withdraw(userid);
	}
	
//	@Test
	public void updateUserNameIntroduction() {
		MemberVO memberVO = new MemberVO();
		
		memberVO.setUserid("mem7");
		memberVO.setUserName("멤멤멤7");
		memberVO.setUserIntroduction("멤멤멤7입니다");
		
		service.modifyUserNameNIntroduction(memberVO);
		
		log.info(memberVO);
	}
	
//	@Test
	public void resetPassword() {
		String userid = "admin90";
		String userpw = "admin90";
		
		service.resetPassword(userid, userpw);
	}
	
	@Test
	public void signUp() {
//		String[] animalNm = new String[] {"바다사자", "바다표범", "바다 코끼리", "바다가재","바다거북","바다뱀","바다악어"
//								, "캥거루", "코알라", "코뿔소", "토끼", "판다", "표범", "하마", "호랑이", "하이에나", "거북이", "악어", "개구리", "이구아나", "두꺼비"
//								, "맹꽁이"};
		String[] animalNm = new String[] {"공작","거위","기러기","까치","까마귀","두루미","독수리","백조","비둘기","부엉이","오리","앵무새","제비"
										,"직박구리","참새","칠면조","타조","펭귄"};
		int length = animalNm.length;
		MemberVO memberVO = null;
		for(int i=0; i<length;i++) {
			memberVO = new MemberVO();
			memberVO.setUserid("mem"+(43+i));
			memberVO.setUserpw("mem"+(43+i));
			memberVO.setUserName(animalNm[i]);
			
			log.info(memberVO);
			
			service.register(memberVO);
		}
	}
}
