package com.moviepedia.service;


import com.moviepedia.domain.LikeVO;

public interface LikeService {

	public int register(LikeVO likeVO);
	
	public LikeVO get(Long likeCd);
	
	public LikeVO getUser(String userid, Long commentCd);
	
	public int remove(Long likeCd);
	
	public int remove(LikeVO likeVO);
	
	public int removeByCommentCd(Long commentCd);
	
	public int getCntByCommentCd(Long commentCd);
	
	// 회원탈퇴 - 유저의 모든 좋아요 리스트 삭제
	public boolean removeUserLikeList(String userid);	
	

	

}
