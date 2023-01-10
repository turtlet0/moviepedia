package com.moviepedia.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.moviepedia.domain.CommentInfoDTO;
import com.moviepedia.domain.CommentVO;
import com.moviepedia.domain.LikeCntDTO;

public interface CommentMapper {

	// 등록
	public int insert(CommentVO vo);
	
	// 조회
	public CommentInfoDTO read(Long commentCd);
	
	public CommentInfoDTO readUser(@Param("userid") String userid, @Param("movieCd") String movieCd);
		// 	@Param 이용 (두 개 이상의 데이터를 파라미터로 전달하기 위한 방법 중 하나)
	
	// 회원탈퇴) 조회 - 유저의 모든 commentCd 조회
	public Long[] getUserCommentCdList(String userid);
	
	// 삭제
	public int delete (Long commentCd);
	
	// 수정
	public int update(CommentVO vo);
	
	// 전체 조회
	public List<CommentVO> getList(@Param("movieCd") String movieCd , @Param("orderBy") int orderBy, @Param("totalCnt") int totalCnt);
	
	// 추가적인 코멘트 리스트 조회
	public List<CommentInfoDTO> getAdditionalList(@Param("movieCd") String movieCd, @Param("orderBy") int orderBy, @Param("currentCnt") int currentCnt, @Param("additionalCnt") int additionalCnt
				, @Param("loginUserid") String loginUserid);
	
	// 댓글 개수 수정
	public int updateReplyCnt(@Param("commentCd") Long commentCd, @Param("amount") int amount);
	
	// 좋아요 개수 수정
	public int updateLikeCnt(@Param("commentCd") Long commentCd, @Param("amount") int amount);
	
	// 댓글 개수 조회
	public int getReplyCnt(Long commentCd);
	
	// 좋아요 개수 조회
	public int getLikeCnt(Long commentCd);
	
	// test
	public List<CommentInfoDTO> testGetList(String movieCd);
}
