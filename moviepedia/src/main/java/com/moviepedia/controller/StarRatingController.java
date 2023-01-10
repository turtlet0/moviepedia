package com.moviepedia.controller;


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
import com.moviepedia.domain.StarRatingVO;
import com.moviepedia.service.StarRatingService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;


@RestController //  REST 방식으로 동작
@Log4j
@AllArgsConstructor
public class StarRatingController {

	private StarRatingService service;
	
	// 별점 등록	
	@PostMapping(value = "/starRating/new",
			consumes = "application/json",
			produces = {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String> create(@RequestBody StarRatingVO vo){
		
		log.info("StarRatingVO : " + vo);
		
		int insertCount = service.register(vo);
		
		log.info("StarRating INSERT COUNT : + " + insertCount);
		
		return insertCount == 1 
				? new ResponseEntity<String>("success", HttpStatus.OK)
				: new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	// 특정 코멘트의 댓글 목록 확인
//	@GetMapping(value = "comments/{commentCd}/replies",
//			produces = {
//					MediaType.APPLICATION_XML_VALUE,
//					MediaType.APPLICATION_JSON_UTF8_VALUE
//			})
//	public ResponseEntity<List<ReplyVO>> getList(
//			@PathVariable("commentCd") Long commentCd){
//		
//		log.info("getList......");
//		
//		return new ResponseEntity<List<ReplyVO>>(service.getList(commentCd), HttpStatus.OK);
//	}
	
	// 별점 조회 - userid, movieCd
	@GetMapping(value = "starRating/{userid}/{movieCd}",
			produces = {
					MediaType.APPLICATION_ATOM_XML_VALUE,
					MediaType.APPLICATION_JSON_UTF8_VALUE
			})
	public ResponseEntity<StarRatingVO> get(@PathVariable("userid") String userid,
			@PathVariable("movieCd") String movieCd){
		
		log.info("get... : " + userid +"/" + movieCd);
		
		return new ResponseEntity<StarRatingVO>(service.get(userid, movieCd), HttpStatus.OK);
		
	}

	
	// 별점 삭제
	@DeleteMapping(value = "starRating/{userid}/{movieCd}",
			produces = {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String> remove(@PathVariable("userid") String userid,
			@PathVariable("movieCd") String movieCd){
		
		log.info("remove: " + userid +"/" + movieCd);
		
		return service.remove(userid, movieCd) == 1
				? new ResponseEntity<String>("success", HttpStatus.OK)
				: new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	// 별점 수정
		@RequestMapping(method = {RequestMethod.PUT, RequestMethod.PATCH},
				value = "starRating/{userid}/{movieCd}",
				consumes = "application/json",
				produces = {MediaType.TEXT_PLAIN_VALUE})
		public ResponseEntity<String> modify(
				@RequestBody StarRatingVO vo,
				@PathVariable("userid") String userid,
				@PathVariable("movieCd") String movieCd){
			
			log.info("modify:  " + vo);
			
			return service.modify(vo) == 1 
				? new ResponseEntity<String>("success", HttpStatus.OK)
				: new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	// 좋아요 개수 조회 - commentCd
	/*
	@GetMapping(value = {"likes/count/{commentCd}"},
			produces = {
					MediaType.APPLICATION_ATOM_XML_VALUE,
					MediaType.APPLICATION_JSON_UTF8_VALUE
			})
	public ResponseEntity<Integer> getCntByCommentCd(
			@PathVariable("commentCd") Long commentCd){
			// error) ResponseEntity<int -> Integer>
		log.info("getCntByCommentCd... : " + commentCd);
		
		return new ResponseEntity<Integer>(service.getCntByCommentCd(commentCd), HttpStatus.OK);
	}		
	*/

	
	
	
	
	
	
	
	
	
	
}
