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
public class UserServiceTest {

	@Setter(onMethod_ = @Autowired) 
	private UserService service;
	

//	@Test
	public void getFavoritePeopleTest() {
		
		String userid = "mem8";
		
//		List<FavoritePeopleDTO> favoriteActorList = service.getFavoriteActorList(userid);
		List<FavoritePeopleDTO> favoriteDirectorList = service.getFavoriteDirectorList(userid);
		
//		log.info(favoriteActorList);
//		log.info(favoriteActorList.size());
//		favoriteActorList.forEach(favoriteActor -> log.info(favoriteActor));
//		log.info("//////////////////////////////////////");
//		List<FavoritePeopleDTO> favoriteDirectorList = service.getFavoriteDirectorList(userid);
//		
//		log.info(favoriteDirectorList);
//		log.info(favoriteDirectorList.size());
		
	}
	
	// Oracle REGEXP_SUBSTR() VS Java 
		// : 한 행의 데이터를 특정 문자로 여러행으로 분리
	@Test
	public void testSpeedSubstr() {
		
		String userid = "lju061@naver.com";
//		String userid = "mem7";
		
		long startTime2 = System.currentTimeMillis();
		
//		service.getStarRatingAnalysis(userid); // 0.382s
		
//		service.getFavoriteActorList_v1(userid); // 4.939s // score2만 java 이용해 구하는 코드 3.000s(ROWNUM 10개)
//		service.getFavoriteActorList_v2(userid); // 4.939s // score2만 java 이용해 구하는 코드 3.000s(ROWNUM 10개)
		service.getFavoriteActorList_new(userid); 
//		
//		service.getFavoriteDirectorList(userid);// 0.807s
//		
//		service.getFavoriteGenreList(userid); // 0.500s
//		
//		service.getFavoriteNationList(userid); // 0.500s
//		
//		service.getTotalShowTm(userid); // 0.325s
	
		long endTime2 = System.currentTimeMillis();
        System.out.println("##실행시간(초.0f) : " + (endTime2 - startTime2) / 1000.0f + "초");		
		
		
	}
}
