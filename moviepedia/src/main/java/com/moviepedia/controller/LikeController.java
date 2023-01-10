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

import com.moviepedia.domain.LikeVO;
import com.moviepedia.domain.ReplyVO;
import com.moviepedia.service.LikeService;
import com.moviepedia.service.ReplyService;

import lombok.AllArgsConstructor;
import lombok.Delegate;
import lombok.extern.log4j.Log4j;


@RestController //  REST 방식으로 동작
@Log4j
@AllArgsConstructor
public class LikeController {

	private LikeService service;
	
	// 좋아요 등록	
	@PostMapping(value = "/rest/likes/new",
			consumes = "application/json",
			produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
	public ResponseEntity<Integer> create(@RequestBody LikeVO likeVO){
//		public int create(@RequestBody LikeVO likeVO){
		
		log.info("LikeVO : " + likeVO);
		
		Integer likeCnt = service.register(likeVO);
		
		log.info("likeCnt : " + likeCnt);
		
//		return insertCount == 1 
//				? new ResponseEntity<String>("success", HttpStatus.OK)
//				: new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		return new ResponseEntity<Integer>(likeCnt, HttpStatus.OK);
//		return  likeCnt;
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
	
	// 좋아요 조회 - likeCd
	@GetMapping(value = "likes/{likeCd}",
			produces = {
					MediaType.APPLICATION_ATOM_XML_VALUE,
					MediaType.APPLICATION_JSON_UTF8_VALUE
			})
	public ResponseEntity<LikeVO> get(@PathVariable("likeCd") Long likeCd){
		
		log.info("get... : " + likeCd);
		
		return new ResponseEntity<LikeVO>(service.get(likeCd), HttpStatus.OK);
		
	}
	

	
	// 좋아요 조회 - userid, commentCd
	@GetMapping(value = {"likes/{userid}/{commentCd}"},
			produces = {
					MediaType.APPLICATION_ATOM_XML_VALUE,
					MediaType.APPLICATION_JSON_UTF8_VALUE
			})
	public ResponseEntity<LikeVO> getUser(
			@PathVariable("userid") String userid,
			@PathVariable("commentCd") Long commentCd){
		
		log.info("getUser... : " + userid + "/" + commentCd);
		
		return new ResponseEntity<LikeVO>(service.getUser(userid, commentCd), HttpStatus.OK);
		
	}
	
	// 좋아요 삭제
	@DeleteMapping(value = "/rest/likes/delete",
			consumes = "application/json",
//			produces = {MediaType.TEXT_PLAIN_VALUE})
			produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
	public ResponseEntity<Integer> remove(@RequestBody LikeVO likeVO){
//		public int remove(@RequestBody LikeVO likeVO){
		
		log.info("remove likeVO: " + likeVO);
		
		Integer likeCnt = service.remove(likeVO);
		
		log.info("likeCnt : " + likeCnt);
		
//		return  == 1
//				? new ResponseEntity<String>("success", HttpStatus.OK)
//				: new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		return new ResponseEntity<Integer>(likeCnt, HttpStatus.OK);
				
		
	}
	
	
	// 좋아요 개수 조회 - commentCd
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

	
	
	
	
	
	
	
	
	
	
}
