package com.moviepedia.service;


import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.moviepedia.domain.BoxOfficeWithStarDTO;
import com.moviepedia.domain.PeopleVO;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Service
@RunWith(SpringJUnit4ClassRunner.class) 
@Log4j
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml",
	"file:src/main/webapp/WEB-INF/spring/security-context.xml"
}) 
public class MovieServiceTest {

	@Setter(onMethod_ = @Autowired) 
	private MovieService service;
	

	@Test
	public void getHighStarRatingMovieList() {
		String userid = "mem7";
		service.getHighStarRatingMovieList(userid);
	}
	
	
}
