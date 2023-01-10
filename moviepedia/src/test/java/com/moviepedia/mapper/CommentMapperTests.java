package com.moviepedia.mapper;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.moviepedia.domain.CommentInfoDTO;
import com.moviepedia.domain.CommentVO;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringRunner.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml",
		"file:src/main/webapp/WEB-INF/spring/security-context.xml"})
@Log4j
public class CommentMapperTests {

	@Setter(onMethod_ = @Autowired)
	private CommentMapper mapper;
	
//	@Test
	public void testMapper() {
		
		log.info(mapper);
	}
	
	// 등록
//	@Test
	public void testCreate() {
		
		CommentVO vo = new CommentVO();
		
		vo.setMovieCd("20000006");
		vo.setContents("코멘트5");
		vo.setUserid("a");
		mapper.insert(vo);
	}
	
	// 조회
//	@Test
//	public void testRead() {
//		
//		Long targetMovieCd = 1L;
//		CommentVO vo = mapper.read(targetMovieCd);
//		
//		log.info(vo);
//	}
//	
//	// 삭제
////	@Test
//	public void testDelete() {
//		Long targetCommentCd = 1L;
//		
//		mapper.delete(targetCommentCd);
//	}
//	
//	// 수정
////	@Test
//	public void testUpdate() {
//		Long targetCommentCd = 2L;
//		CommentVO vo = mapper.read(targetCommentCd);
//		
//		vo.setContents("코멘트 수정");
//		
//		int count = mapper.update(vo);
//		
//		log.info("수정 횟수:  " + count);
//	}
	
	
	// 전체 조회
//	@Test
	public void testGetList() {
//		List<CommentVO> comments = mapper.getList(movieCd, orderBy, totalCnt)
		
//		comments.forEach(comment -> log.info(comment));
	}
	
	// 코멘트 리스트 - 별점 정보 
//	@Test
//	public void testBeginEend() {
//		List<CommentUserDTO> commentUserDTO = mapper.testGetList("20000006");
//		commentUserDTO.
//		commentUserDTO.forEach(comment -> log.info(comment));
//	}
}
