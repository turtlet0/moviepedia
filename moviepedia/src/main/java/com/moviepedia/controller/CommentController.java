package com.moviepedia.controller;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.moviepedia.domain.CommentVO;
import com.moviepedia.domain.CommentInfoDTO;
import com.moviepedia.service.CommentService;
import com.moviepedia.service.MemberService;
import com.moviepedia.service.UserService;

import lombok.AllArgsConstructor;
import lombok.Delegate;
import lombok.extern.log4j.Log4j;


@RestController //  REST 방식으로 동작
@Log4j
@AllArgsConstructor
public class CommentController {

	private CommentService service;
	
	
	// 코멘트 등록
	@PostMapping(value = "comments/new",
			consumes = "application/json",
			produces = {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String> create(@RequestBody CommentVO commentVO){
		
		log.info("CommentVO : " + commentVO);
		
		int insertCount = service.register(commentVO);
		
		log.info("Comment INSERT COUNT : + " + insertCount);
		
		return insertCount == 1 
				? new ResponseEntity<String>("success", HttpStatus.OK)
				: new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	// 특정 영화의 코멘트 목록 조회
		// tip) url 중복 불가(URL 변수명 달라도 중복임)
	@GetMapping(value = "contents/{movieCd}/comments/{orderBy}/{totalCnt}",
			produces = {
					MediaType.APPLICATION_XML_VALUE,
					MediaType.APPLICATION_JSON_UTF8_VALUE
			})
	public ResponseEntity<List<CommentVO>> getList(
			@PathVariable("movieCd") String movieCd,
			@PathVariable("orderBy") int orderBy,
			@PathVariable("totalCnt") int totalCnt){
		
		log.info("getList......");
		
		return new ResponseEntity<List<CommentVO>>(service.getList(movieCd, orderBy, totalCnt), HttpStatus.OK);
	}
	
	// 사용 x. ajax 이용) 특정 영화의 추가적인 코멘트 목록 조회
//	@GetMapping(value = "contents/{movieCd}/{orderBy}/{currentCnt}/{additionalCnt}/comments",
//			produces = {
//					MediaType.APPLICATION_XML_VALUE,
//					MediaType.APPLICATION_JSON_UTF8_VALUE
//	})
//	public ResponseEntity<List<CommentInfoDTO>> getAdditionalList(
//			@PathVariable("movieCd") String movieCd,
//			@PathVariable("orderBy") int orderBy,
//			@PathVariable("currentCnt") int currentCnt,
//			@PathVariable("additionalCnt") int additionalCnt){
//		
//		log.info("getAdditionalList......");
//		
//		return new ResponseEntity<List<CommentInfoDTO>>(service.getAdditionalList(movieCd, orderBy, currentCnt, additionalCnt), HttpStatus.OK);
//	}
	
	// 코멘트 조회 - commentCd
	@GetMapping(value = "/rest/comments/{commentCd}",
			produces = {
					MediaType.APPLICATION_ATOM_XML_VALUE,
					MediaType.APPLICATION_JSON_UTF8_VALUE
			})
	public ResponseEntity<CommentInfoDTO> get(@PathVariable("commentCd") Long commentCd){
		
		log.info("get... : " + commentCd);
		
		return new ResponseEntity<CommentInfoDTO>(service.get(commentCd), HttpStatus.OK);
		
	}
	
	
	// 특정 유저의 특정 영화 코멘트 조회 - userid, moiveCd
	@GetMapping(value = {"/rest/comments/{userid}/{movieCd}"},
			produces = {
					MediaType.APPLICATION_ATOM_XML_VALUE,
					MediaType.APPLICATION_JSON_UTF8_VALUE
			})
	public ResponseEntity<CommentInfoDTO> getUser(
			@PathVariable("userid") String userid,
			@PathVariable("movieCd") String movieCd){
		
		log.info("getUser... : " + userid + "/" + movieCd);
		
		
		
		return new ResponseEntity<CommentInfoDTO>(service.getUser(userid, movieCd), HttpStatus.OK);
		
		}
	
	// 코멘트 삭제
	@DeleteMapping(value = "/rest/comments/{commentCd}",
			produces = {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String> remove(@PathVariable("commentCd") Long commentCd){
		
		log.info("remvoe: " + commentCd);
		
		return service.remove(commentCd) == 1
				? new ResponseEntity<String>("success", HttpStatus.OK)
				: new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	// 코멘트 수정
	@RequestMapping(method = {RequestMethod.PUT, RequestMethod.PATCH},
			value = "/rest/comments/{commentCd}",
			consumes = "application/json",
			produces = {
					MediaType.APPLICATION_ATOM_XML_VALUE,
					MediaType.APPLICATION_JSON_UTF8_VALUE
			})
	public ResponseEntity<CommentVO> modify(
			@RequestBody CommentVO commentVO,
			@PathVariable("commentCd") Long commentCd) throws UnsupportedEncodingException{
		
		log.info("modify before:  " + commentVO);
		
//		commentVO.setCommentCd(commentCd); // {"movieCd":"20000006", "userCd":4, "contents":"코멘트4 수정2"} 와 같이 데이터 전달하므로 commentCd null인상태
		
		log.info("commentCd: " + commentCd);
		service.modify(commentVO);
		log.info("modify after contents:  " + commentVO.getContents());

//		return service.modify(commentVO) == 1 
//			? new ResponseEntity<String>("success", HttpStatus.OK)
//			: new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		return new ResponseEntity<CommentVO>(commentVO, HttpStatus.OK);
	}
	
	// 코멘트 좋아요 개수 갱신
	@GetMapping(value = {"/rest/comments/getLikeCnt/{commentCd}"},
			produces = {
					MediaType.APPLICATION_ATOM_XML_VALUE,
					MediaType.APPLICATION_JSON_UTF8_VALUE
			})
	public ResponseEntity<Integer> getLikeCnt(@PathVariable("commentCd") Long commentCd) {
		
		log.info("getLikeCnt.... " + commentCd);
		
		return new ResponseEntity<Integer>(service.getLikeCnt(commentCd), HttpStatus.OK);
		
	}
	
	// 코멘트 댓글 개수 갱신
	@GetMapping(value = {"/rest/comments/getReplyCnt/{commentCd}"},
			produces = {
					MediaType.APPLICATION_ATOM_XML_VALUE,
					MediaType.APPLICATION_JSON_UTF8_VALUE
	})
	public ResponseEntity<Integer> getReplyCnt(@PathVariable("commentCd") Long commentCd) {
		
		log.info("getReplyCnt.... " + commentCd);
		
		return new ResponseEntity<Integer>(service.getReplyCnt(commentCd), HttpStatus.OK);
		
	}
	
/* ---------- 유저 페이지 ------------------*/
	// 사용x -> Ajax 방식 이용
	// 유저 작성한 코멘트 리스트 조회
//		@GetMapping(value = "rest/users/{randomString}/comments",
//				produces = {
//						MediaType.APPLICATION_XML_VALUE,
//						MediaType.APPLICATION_JSON_UTF8_VALUE
//		})
//		public ResponseEntity<List<CommentInfoDTO>> getUserCommentList(
//				@PathVariable("randomString") String randomString){
//			
//			log.info("getUserCommentList......" + randomString);
//			
//			return new ResponseEntity<List<CommentInfoDTO>>(userService.getUserCommentList(randomString), HttpStatus.OK);
//		}	
//	
/* ---------- 유저 페이지 ------------------*/
	
	
	
	
	
	
	
	
	
	
	
	
}
