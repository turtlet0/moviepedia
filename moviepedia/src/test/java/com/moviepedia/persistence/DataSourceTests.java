package com.moviepedia.persistence;


import static org.junit.Assert.fail;

import java.sql.Connection;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.Setter;
import lombok.extern.log4j.Log4j;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class DataSourceTests {
	@Setter(onMethod_ = {@Autowired})
	private DataSource dataSource;
	
	
	
	// 3.3.1 JDBC 테스트 코드
		// 스프링에 Bean으로 등록된 DataSource 이요해 Connection을 제대로 처리할 수 있는지 확인하기 위한 테스트
		// > 내부적으로 HikariCP가 시작, 종료되는 로그 확인 가능
	@Test
	public void testConnection() {
		try (Connection con = dataSource.getConnection()){
			log.info(con);
			
		} catch (Exception e) {
			// TODO: handle exception
			fail(e.getMessage());
		}
	}
	
	
}
