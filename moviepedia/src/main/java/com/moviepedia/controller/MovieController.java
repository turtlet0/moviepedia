package com.moviepedia.controller;


import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.moviepedia.domain.CommentVO;
import com.moviepedia.domain.MovieVO;
import com.moviepedia.service.CommentService;
import com.moviepedia.service.MemberService;
import com.moviepedia.service.MovieService;

import lombok.AllArgsConstructor;
import lombok.Delegate;
import lombok.extern.log4j.Log4j;


@RestController //  REST 방식으로 동작
@Log4j
@AllArgsConstructor
public class MovieController {

	private MovieService service;
	

	
	// 사용x) 리뷰 페이지의 영화 리뷰 리스트 조회 (최초, 이후 추가 구분 X)
//	@GetMapping(value = "/rest/review/{userid}/{orderBy}",
//			produces = {
//					MediaType.APPLICATION_XML_VALUE,
//					MediaType.APPLICATION_JSON_UTF8_VALUE
//	})
//	public ResponseEntity<List<MovieVO>> getAdditionalList(
//			@PathVariable("userid") String userid,
//			@PathVariable("orderBy") int orderBy){
//		
//		log.info("getAdditionalList......");
//		
//		return new ResponseEntity<List<MovieVO>>(service.getReviewList(userid, orderBy, currentSqnc, additionalCnt), HttpStatus.OK);
//	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
