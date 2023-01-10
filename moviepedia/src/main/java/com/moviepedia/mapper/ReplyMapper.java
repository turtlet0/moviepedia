package com.moviepedia.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.moviepedia.domain.ReplyInfoDTO;
import com.moviepedia.domain.ReplyVO;

public interface ReplyMapper {
	// 등록
	public int insert(ReplyVO vo);
	
	// 조회
	public ReplyInfoDTO read(Long replyCd);
	
//	public CommentVO readUser(@Param("userid") String userid, @Param("movieCd") String movieCd);
		// 	@Param 이용 (두 개 이상의 데이터를 파라미터로 전달하기 위한 방법 중 하나)
	
	// 회원탈퇴) 조회 - 유저의 모든 replyCd 조회
	public Long[] getUserReplyCdList(String userid);
		
	// 삭제
	public int delete (Long replyCd);
	
	// 삭제 - commentCd
	public int deleteByCommentCd(Long commentCd);
	
	// 수정
	public int update(ReplyVO vo);
	
	// 전체 조회
	public List<ReplyInfoDTO> getList(Long commentCd);
	public List<ReplyInfoDTO> getList(@Param("commentCd") Long commentCd, @Param("crudIdx") int crudIdx, @Param("replyCd") Long replyCd);
	
	// 특정 코멘트의 댓글 개수 조회
	public int getCountByCommentCd(Long commentCd);
	

}
