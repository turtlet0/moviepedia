package com.moviepedia.mapper;

import org.apache.ibatis.annotations.Param;

import com.moviepedia.domain.CommentVO;
import com.moviepedia.domain.LikeVO;

public interface LikeMapper {

	// 등록
	public int insert(LikeVO likeVO);
	
	// 조회
	public LikeVO read (Long likeCd);
	
	// 유저의 특정 코멘트 좋아요 조회 - userid, commentCd
	public LikeVO readUser(@Param("userid") String userid, @Param("commentCd") Long commentCd);
	
	// 회원탈퇴) 조회 - 유저의 모든 likeCd 조회
	public Long[] getUserLikeCdList(String userid);
		
	// 삭제 
	public int delete (Long likeCd);
//	public int delete (LikeVO likeVO);
	
	// 삭제 - commentCd
	public int deleteByCommentCd(Long commentCd);
	
	// 특정 코멘트의 좋아요 개수 조회
	public int getCountByCommentCd(Long commentCd);
}
