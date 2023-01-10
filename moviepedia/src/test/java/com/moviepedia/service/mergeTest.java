package com.moviepedia.service;
// open API 호출 관련 service단 Test

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import com.moviepedia.domain.TestMergeVO;


import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Service
@RunWith(SpringJUnit4ClassRunner.class) 
@Log4j
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml",
	"file:src/main/webapp/WEB-INF/spring/security-context.xml"
}) 
public class mergeTest {

	@Setter(onMethod_ = @Autowired) 
	private TestMegeService service;
	
//	@Test
	public void testMerge() {
		
		TestMergeVO mergeVO = new TestMergeVO();
		
		mergeVO.setTpk(2);
		mergeVO.setTnum(2);
//		mergeVO.setTname("2");
		
		log.info(mergeVO);
//		
		service.merge(mergeVO);
	}
	
	@Test
	public void testMergeList() {
		List<TestMergeVO> mergeList = new ArrayList<TestMergeVO>();
		
		TestMergeVO mergeVO;
		for (int i = 3; i < 10; i++) {
			mergeVO = new TestMergeVO();
			mergeVO.setTpk(i);
//			mergeVO.setTnum(i);
			mergeVO.setTname(i + "");
			
			mergeList.add(mergeVO);
		}
		log.info(mergeList);
		service.mergeList(mergeList);
		
		
	}

	

}
