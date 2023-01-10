package com.moviepedia.persistence;


// 4.2.2 Mapper 테스트


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.moviepedia.mapper.TimeMapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml" )
@Log4j
public class TimeMapperTests {
	
	@Setter(onMethod_ = @Autowired)
	private TimeMapper timeMapper;
	
	@Test
	public void testGetTime() {
		log.info(timeMapper.getClass().getName()); // getClass().getName() : 실제 동작하는 클래스의 이름 리턴
		log.info(timeMapper.getTime());
	}
	
	// 정상 동작 -> 스프링 내부에 TimeMapper 타입으로 만들어진 스프링 객체(Bean)이 존재한다는 뜻
	// > 인터페이스만 만들었는데 내부적으로 임의의 적당한 클래스가 만들어진 것 확인 가능 -> AOP 연관
	
	// 4.2.3 XML 매퍼와 같이 쓰기
	//@Test
	public void testGetTime2() {
		log.info("getTime2");
		log.info(timeMapper.getTime2());
	}
}
