package com.moviepedia.service;


import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.moviepedia.domain.CommentVO;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Service
@RunWith(SpringJUnit4ClassRunner.class) 
@Log4j
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml",
	"file:src/main/webapp/WEB-INF/spring/security-context.xml"
}) 
public class CommentServiceTest {

	@Setter(onMethod_ = @Autowired) 
	private CommentService service;
	
	@Test
	public void commentAdd() {
		
		CommentVO commentVO = new CommentVO();
		commentVO.setMovieCd("20124079");
		commentVO.setUserid("mem29");
		commentVO.setContents("힘을 가졌을 때, 궁안에 갇혀 있던 자와 길 위에서 자유롭게 살던 자, 군림하던 자와 함께 살아가던 자의 세계관이 어떻게 구현되는가에 대한 하나의 대답.");
		service.register(commentVO);
		
		
	}
	


}
