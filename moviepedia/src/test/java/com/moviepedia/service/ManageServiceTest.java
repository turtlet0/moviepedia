package com.moviepedia.service;


import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.moviepedia.domain.BoxOfficeWithStarDTO;
import com.moviepedia.domain.MovieVO;
import com.moviepedia.domain.PeopleVO;
import com.moviepedia.mapper.PeopleMapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Service
@RunWith(SpringJUnit4ClassRunner.class) 
@Log4j
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml",
	"file:src/main/webapp/WEB-INF/spring/security-context.xml"
}) 
public class ManageServiceTest {

	@Setter(onMethod_ = @Autowired) 
	private ManageService service;
	
	@Setter(onMethod_ = @Autowired) 
	private MovieService movieService;
	
	@Setter(onMethod_ = @Autowired) 
	private PeopleMapper peopleMapper;
	

//	@Test
	public void testMainBoxOffice() {
		String userid = null;
		List<BoxOfficeWithStarDTO> boxOfficeWithStarDTOList = service.initRegisterMainBoxOfficeList(userid);
		
		boxOfficeWithStarDTOList.forEach(boxOfficeWithStarDTO -> log.info(boxOfficeWithStarDTO));
	}
	
//	@Test
	public void searchNaverMovieLink() {
		MovieVO movieVO = new MovieVO();
		movieVO.setMovieNm("올빼미");
		movieVO.setRepNationNm("한국");
		
		service.searchNaverMovieLink(movieVO);
		
	}
	
	@Test
	public void searchPeopleList() {
		String key = "0c3d249406e4ae7e4b9cb5059633cb54";
		List<MovieVO> movieList = new ArrayList<MovieVO>();
		
		MovieVO movieVO = movieService.get("20182530");
		log.info(movieVO);
		movieList.add(movieVO);
		
		 List<PeopleVO> peopleList = service.searchPeopleList(key, movieList);
		 
		 peopleList.forEach(people -> log.info(people)); log.info(peopleList.size());
		  
		  int peopleTotalRegisterCnt = 0; 
		  
		  if(peopleList.size() != 0) {
		  
			  peopleTotalRegisterCnt = service.registerPeopleList(peopleList); //
//				  peopleMapper.insertPeopleList(peopleList);
			  
		  /* 배우 배역 비중 저장! */
			int peopleListLength = peopleList.size();
			for(int j=0; j<peopleListLength; j++) {
				PeopleVO peopleVO = peopleList.get(j);
				
				
				if(peopleVO.getRepRoleNm().equals("배우") && ! peopleVO.getActorFilmos().replace(" ", "").equals("")) {
					peopleMapper.updateActorRoleImportance(peopleVO.getPeopleCd());
				}
			}
			  
			
			
			  log.info("================ 최종 ================"); log.info(movieList.size());
			  movieList.forEach(movie -> log.info(movie)); // forEach(): 주어진 함수를 배열 요소 각각에 대해 실행 
			  log.info("================ 최종 ================");
			  log.info("================ 최종 ================");
			  log.info(peopleList.size()); peopleList.forEach(people -> log.info(people));
			  log.info("================ 최종 ================"); 
		  }
	}
}
