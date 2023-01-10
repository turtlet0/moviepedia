package com.moviepedia.service;

import java.util.List;

import com.moviepedia.domain.ReplyListDTO;
import com.moviepedia.domain.ReplyVO;

public interface ReplyService {

	public Long register(ReplyVO vo);
	
	public ReplyListDTO get(Long replyCd);
	
//	public ReplyVO getUser(String userid, String movieCd);
	
	public int modify(ReplyVO vo);
	
	public int remove(Long replyCd);
	
	public ReplyListDTO getList(Long commentCd);
	public ReplyListDTO getList(Long commentCd, int crudIdx, Long replyCd);
	
	public int removeByCommentCd(Long commentCd);
	
	public int getCntByCommentCd(Long commentCd);
	
	// 회원탈퇴 - 유저의 모든 댓글 리스트 삭제
	public boolean removeUserReplyList(String userid);	

}
