package com.moviepedia.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.moviepedia.domain.CommentVO;
import com.moviepedia.domain.LikeVO;
import com.moviepedia.domain.StarRatingAnalysisDTO;
import com.moviepedia.domain.StarRatingVO;

public interface StarRatingMapper {

	// 등록
	public int insert(StarRatingVO vo);
	
	// 조회
	public StarRatingVO read (@Param("userid") String userid, @Param("movieCd") String movieCd);
	
	// 삭제 
	public int delete (@Param("userid") String userid, @Param("movieCd") String movieCd);
	
	// 수정
	public int update(StarRatingVO vo);
	
	// 영화 평균 평점 및 평가자 수 조회
	public Map<String, Double> getAvgCntByMovieCd(String movieCd);
	
	public Map<String, Double> getAvgCntByUserid(String userid);
	
	// 영화 별점 평가 분포 조회
		// UserMapper의 getStarRatingAnalysis와 유사
	public StarRatingAnalysisDTO getMovieStarRatingAnalysis(String movieCd);
	
	// 특정 코멘트의 좋아요 개수 조회
	// public int getCountByCommentCd(Long commentCd);
	
	// 회원탈퇴 - 회원의 모든 별점 평가 삭제
	public int deleteUserStarRatingList(String userid);
	
	
}
