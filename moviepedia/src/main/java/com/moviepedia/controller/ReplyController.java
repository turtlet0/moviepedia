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

import com.moviepedia.domain.ReplyListDTO;
import com.moviepedia.domain.ReplyVO;
import com.moviepedia.service.ReplyService;

import lombok.AllArgsConstructor;
import lombok.Delegate;
import lombok.extern.log4j.Log4j;


@RestController //  REST 방식으로 동작
@Log4j
@AllArgsConstructor
public class ReplyController {

	private ReplyService service;
	
	// 댓글 등록	
	@PostMapping(value = "replies/new",
			consumes = "application/json",
			produces = {
					MediaType.APPLICATION_XML_VALUE,
					MediaType.APPLICATION_JSON_UTF8_VALUE
			})
	public ResponseEntity<Long> create(@RequestBody ReplyVO vo){
		
		log.info("ReplyVO : " + vo);
		
		Long returnReplyCd = service.register(vo);
		
		log.info("Return replyCd : + " + returnReplyCd);
		
		return new ResponseEntity<Long>(returnReplyCd, HttpStatus.OK);
//		return returnReplyCd != 0L
//				? new ResponseEntity<String>("success", HttpStatus.OK)
//				: new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	// 특정 코멘트의 댓글 목록 확인
	@GetMapping(value = "comments/{commentCd}/replies",
			produces = {
					MediaType.APPLICATION_XML_VALUE,
					MediaType.APPLICATION_JSON_UTF8_VALUE
			})
	public ResponseEntity<ReplyListDTO> getList(
			@PathVariable("commentCd") Long commentCd){
		
		log.info("getList......");
		
		return new ResponseEntity<ReplyListDTO>(service.getList(commentCd), HttpStatus.OK);
	}
	
	// 댓글 조회 - replyCd
	@GetMapping(value = "replies/{replyCd}",
			produces = {
					MediaType.APPLICATION_ATOM_XML_VALUE,
					MediaType.APPLICATION_JSON_UTF8_VALUE
			})
	public ResponseEntity<ReplyListDTO> get(@PathVariable("replyCd") Long replyCd){
		
		log.info("get... : " + replyCd);
		
		return new ResponseEntity<ReplyListDTO>(service.get(replyCd), HttpStatus.OK);
		
	}
	

	
	// 코멘트 조회 - userid, moiveCd
//	@GetMapping(value = {"comments/{userid}/{movieCd}"},
//			produces = {
//					MediaType.APPLICATION_ATOM_XML_VALUE,
//					MediaType.APPLICATION_JSON_UTF8_VALUE
//			})
//	public ResponseEntity<CommentVO> getUser(
//			@PathVariable("userid") String userid,
//			@PathVariable("movieCd") String movieCd){
//		
//		log.info("getUser... : " + userid + "/" + movieCd);
//		
//		return new ResponseEntity<CommentVO>(service.getUser(userid, movieCd), HttpStatus.OK);
//		
//		}
	
	// 댓글 삭제
	@DeleteMapping(value = "replies/{replyCd}",
			produces = {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String> remove(@PathVariable("replyCd") Long replyCd){
		
		log.info("remvoe: " + replyCd);
		
		return service.remove(replyCd) == 1
				? new ResponseEntity<String>("success", HttpStatus.OK)
				: new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	// 댓글 수정
	@RequestMapping(method = {RequestMethod.PUT, RequestMethod.PATCH},
			value = "replies/{replyCd}",
			consumes = "application/json",
			produces = {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String> modify(
			@RequestBody ReplyVO vo,
			@PathVariable("replyCd") Long replyCd){
		
		
		log.info("modify:  " + vo);
		
		return service.modify(vo) == 1 
			? new ResponseEntity<String>("success", HttpStatus.OK)
			: new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	// 댓글 개수 조회 - commentCd
		@GetMapping(value = {"replies/count/{commentCd}"},
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
