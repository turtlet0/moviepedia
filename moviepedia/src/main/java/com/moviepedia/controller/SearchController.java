package com.moviepedia.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.moviepedia.domain.MovieVO;
import com.moviepedia.domain.PeopleVO;
import com.moviepedia.service.SearchService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@RestController
@AllArgsConstructor
@Log4j
public class SearchController {

	private SearchService service;
	
	// 검색창 연관검색어 출력
	@GetMapping(value = "/rest/search/related/{query}",
			produces = {MediaType.APPLICATION_XML_VALUE,
						MediaType.APPLICATION_JSON_UTF8_VALUE
				})
	public ResponseEntity<List<MovieVO>> getRelatedQuery(
				@PathVariable("query") String query){
		
		log.info("getRelatedQuery....");
		
		return new ResponseEntity<List<MovieVO>>(service.getRelatedQuery(query), HttpStatus.OK);
	}
	
	// 현재 사용X) Ajax 방식 이용 중
	// 검색_영화 리스트
//	@GetMapping(value = "/rest/search/movie/{query}/{currentCnt}/{previousCnt}",
//			produces = {MediaType.APPLICATION_XML_VALUE,
//					MediaType.APPLICATION_JSON_UTF8_VALUE})
//	public ResponseEntity<List<MovieVO> > getMovieList(@PathVariable("query") String query,
//													@PathVariable("currentActorCnt") int currentActorCnt,
//													@PathVariable("currentMovieNmDirectorCnt") int currentMovieNmDirectorCnt){
//		
//		log.info("getMovieList.......");
//		
//		return new ResponseEntity<List<MovieVO>>(service.getMovieList(query, currentActorCnt, currentMovieNmDirectorCnt), HttpStatus.OK);
//	}
	
	// 현재 사용X) Ajax 방식 이용 중
	// 검색_영화인 리스트
//	@GetMapping(value = "/rest/search/people/{query}",
//			produces = {MediaType.APPLICATION_XML_VALUE,
//					MediaType.APPLICATION_JSON_UTF8_VALUE})
//	public ResponseEntity<List<PeopleVO> > getPeopleList(@PathVariable("query") String query){
//		
//		log.info("getPeopleList....... " + query);
		
//		List<PeopleVO> peopleList = service.getPeopleList(query);
		
//		PeopleVO peopleVO = new PeopleVO();
//		
//		log.info(peopleList);
//		for(int i=0, len=peopleList.size(); i<len; i++) {
//			peopleVO = peopleList.get(i);
//			
//			String directorFilmos = peopleVO.getDirectorFilmos();
//			
//			String actorFilmos = peopleVO.getActorFilmos();
//			// 평균 평점과 평가인원(샘플 사이즈)을 1,2,3 줄세워서 단순평균 / 감독은 감독작, 배우는 출연작
//		}
		
//		return new ResponseEntity<List<PeopleVO>>(peopleList, HttpStatus.OK);
//	}
	
	
}
