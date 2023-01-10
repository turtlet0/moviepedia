package com.moviepedia.service;

import java.util.List;

import com.moviepedia.domain.CommentInfoDTO;
import com.moviepedia.domain.FavoriteGenreAnalysisDTO;
import com.moviepedia.domain.FavoriteGenreNationDTO;
import com.moviepedia.domain.FavoritePeopleDTO;
import com.moviepedia.domain.MovieWithStarDTO;
import com.moviepedia.domain.StarRatingAnalysisDTO;
import com.moviepedia.domain.TotalShowTmAnalysisDTO;
import com.moviepedia.domain.UserDTO;

public interface UserService {
	
/* 취향 분석 페이지 */
	// 별점 평가 분석 결과 조회 
	public StarRatingAnalysisDTO getStarRatingAnalysis(String userid);
		
	// 선호 배우 조회
	public List<FavoritePeopleDTO> getFavoriteActorList_v1(String userid);
	public List<FavoritePeopleDTO> getFavoriteActorList_v2(String userid);
	public List<FavoritePeopleDTO> getFavoriteActorList_new(String userid);
	
	// 선호 감독 조회
	public List<FavoritePeopleDTO> getFavoriteDirectorList(String userid);
	
	// 선호 장르 조회
	public FavoriteGenreAnalysisDTO getFavoriteGenreList(String userid);
	
	public FavoriteGenreAnalysisDTO getFavoriteGenreList2(String userid);
	
	// 선호 국가 조회
	public List<FavoriteGenreNationDTO> getFavoriteNationList(String userid);
	
	// 유저 총 감상 시간 조회
	public TotalShowTmAnalysisDTO getTotalShowTm(String userid);
	
/* 유저 페이지 */
	// 유저 작성한 코멘트 리스트 조회
	public List<CommentInfoDTO> getUserCommentList(String randomString, int orderBy, int currentSqnc, int additionalCnt
								, String loginUserid);
	
	// 유저 평가한 영화 리스트 조회
	public List<MovieWithStarDTO> getUserMovieList(String randomString, int orderBy, int currentSqnc, int additionalCnt);
	
	// 유저 정보 조회
	public UserDTO getUserInfo(String randomString);

}
