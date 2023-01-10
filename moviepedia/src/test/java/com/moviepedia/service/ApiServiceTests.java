package com.moviepedia.service;
// open API 호출 관련 service단 Test

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;

import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.moviepedia.domain.BoxOfficeVO;
import com.moviepedia.domain.MovieVO;
import com.moviepedia.domain.StarRatingVO;
import com.moviepedia.mapper.MovieMapper;
import com.moviepedia.mapper.StarRatingMapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Service
@RunWith(SpringJUnit4ClassRunner.class) 
@Log4j
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml",
	"file:src/main/webapp/WEB-INF/spring/security-context.xml"
}) 
public class ApiServiceTests {


	@Setter(onMethod_ = @Autowired) 
	private ManageService service;
	

//	@Test 
	public void testSearchMovieList( ) {
 
		String key = "2a33334b3e7bce3518472e30c1b1cb49";
		
//		movieService.registerMoviePeopleList(key);
		
		

	}
	

	

	
//	@Test 
	public void testMerge( ) {
		
		List<StarRatingVO> starRatingList = new ArrayList<StarRatingVO>();
		
		for(int i=1;i<3;i++) {
			StarRatingVO starVO = new StarRatingVO();
			
			starVO.setMovieCd(""+ i + "");
			starVO.setUserid("mem8");
			starVO.setStarRating(i);

			starRatingList.add(starVO);
			
			
		}
		
		log.info(starRatingList);
		
		try {
			
//			movieMapper.testMerge(starRatingList);
		} catch (Exception e) {
			log.info("....................");
			log.info(e.getMessage());
			log.info(e.getStackTrace());
			
		}
		
		
	}
	
	@Test
	public void testSearchBoxoffice() {
		String key = "2a33334b3e7bce3518472e30c1b1cb49";
		

	}
}
