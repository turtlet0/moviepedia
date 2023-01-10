package com.moviepedia.service;


import java.util.List;
import java.util.Map;

import com.moviepedia.domain.StarRatingAnalysisDTO;
import com.moviepedia.domain.StarRatingVO;

public interface StarRatingService {

	public int register(StarRatingVO vo);
	
	public StarRatingVO get(String userid, String movieCd);
	
	public int remove(String userid, String movieCd);
	
	public int modify(StarRatingVO vo);
	
	public Map<String, Double> getAvgCnt(String movieCd);
	
	// 회원탈퇴 - 유저의 모든 별점 평가 리스트 삭제
	public boolean removeUserStarRatingList(String userid);
	
	
	// public int getCntByCommentCd(Long commentCd);
	
	// 특정 영화의 별점 평가 분포 조회 
		public StarRatingAnalysisDTO getMovieStarRatingAnalysis(String movieCd);

	

}
