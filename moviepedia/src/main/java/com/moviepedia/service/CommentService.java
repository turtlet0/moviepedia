package com.moviepedia.service;

import java.util.List;

import com.moviepedia.domain.CommentVO;
import com.moviepedia.domain.CommentInfoDTO;

public interface CommentService {

	public int register(CommentVO commentVO);
	
	public CommentInfoDTO get(Long commentCd);
	
	public CommentInfoDTO getUser(String userid, String movieCd);
	
	public String modify(CommentVO commentVO);
	
	public int remove(Long commentCd);
	
	public List<CommentVO> getList(String movieCd, int orderBy, int totalCnt);
	
	public List<CommentInfoDTO> getAdditionalList(String movieCd, int orderBy, int currentCnt, int additionalCnt
			, String loginUserid);
	
	// 코멘트 좋아요 개수 갱신
	public int getLikeCnt(Long commentCd);
	
	// 코멘트 댓글 개수 갱신
		public int getReplyCnt(Long commentCd);
		
		// 1) ajax post 전송
		// 2) 빈 페이지 만들어 
		
	// 회원탈퇴 - 유저의 모든 코멘트(이하 댓글, 좋아요) 리스트 삭제
	public boolean removeUserCommentList(String userid);	
		
}

