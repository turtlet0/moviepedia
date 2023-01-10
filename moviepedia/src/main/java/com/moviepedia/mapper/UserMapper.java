package com.moviepedia.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.moviepedia.domain.CommentInfoDTO;
import com.moviepedia.domain.FavoriteGenreNationDTO;
import com.moviepedia.domain.FavoritePeopleDTO;
import com.moviepedia.domain.MovieWithStarDTO;
import com.moviepedia.domain.StarRatingAnalysisDTO;
import com.moviepedia.domain.TestFavoriteGenreDTO;
import com.moviepedia.domain.TotalShowTmAnalysisDTO;
import com.moviepedia.domain.UserDTO;

public interface UserMapper {
	
	// 유저 정보 조회 by randomString
	public UserDTO getUserInfoByRandomString(String randomString);
	
	// 유저 별점 평가 분석
	public StarRatingAnalysisDTO getStarRatingAnalysis(String userid);
		
	// 선호 감독
	public List<FavoritePeopleDTO> getFavoritePeopleScore(@Param("userid") String userid, @Param("roleNm") String roleNm);
	
	// 선호 배우 
	public List<FavoritePeopleDTO> getFavoriteActorScore(@Param("userid") String userid);
	
	
		
//	public List<FavoritePeopleDTO> getFavoritePeopleScore2(@Param("userid") String userid, @Param("roleNm") String roleNm);
	
	
	
		
	// 사용x) 배우의 필모리스트 출력 result movieCd
	public List<FavoritePeopleDTO> getActorFilmoList(String peopleCd);
	
		// 사용x) 배우 출역작에서의 비중을 정수값으로 반환
		public Integer getActorRoleImportanceValue(@Param("movieCd") String peopleCd, @Param("peopleNm") String peopleNm);
		
	public Map<String, Double> getActorRoleImportance(String peopleCd);

	
	// 선호 장르/국가 조회 위한 movieCd 반환
	public List<String> getMovieCdForFavoriteGenreNation(String userid);
	
	// Test
	public List<String> getGenreAltForFavoriteGenre(String userid);
	
	// 선호 장르 조회
	public List<FavoriteGenreNationDTO> getFavoriteGenreScore(@Param("userid") String userid, @Param("movieCdList") List<String> movieCdList);
	
	public List<TestFavoriteGenreDTO> getFavoriteGenreScore2(@Param("userid") String userid, @Param("movieCdList") List<String> movieCdList);
	
	// 선호 국가 조회 : getFavoriteGenreScore와 동일 구조
	public List<FavoriteGenreNationDTO> getFavoriteNationScore(@Param("userid") String userid, @Param("movieCdList") List<String> movieCdList);
	
	// 유저 총 감상 시간 조회
	public TotalShowTmAnalysisDTO getTotalShowTm(String userid);
	
	
	// 출력한 선호 감독/배우/장르 리스트 -> 유저 테이블에 저장
	public int updateFavoritePeopleGenre(@Param("userid") String userid, @Param("category") String cateogry, @Param("cateValue") String cateValue);
			
	
/* 유저 페이지 */
	
	// 유저 평가한 코멘트 리스트 
	public List<CommentInfoDTO> getUserCommentList(@Param("randomString") String randomString, @Param("orderBy") int orderBy, 
			@Param("currentSqnc") int currentSqnc, @Param("additionalCnt") int additionalCnt
			, @Param("loginUserid") String loginUserid);
	
	// 유저 평가한 코멘트 리스트 
	public List<MovieWithStarDTO> getUserMovieList(@Param("randomString") String randomString, @Param("orderBy") int orderBy, 
			@Param("currentSqnc") int currentSqnc, @Param("additionalCnt") int additionalCnt);
	

}
//org.apache.ibatis.binding.BindingException: Mapper method 'com.moviepedia.mapper.AnalysisMapper.getActorRoleImportanceValue attempted to return null from a method with a primitive return type (int).
// 	The method getFavoriteGenreScore(List<String>) in the type AnalysisMapper is not applicable for the arguments (String, List<String>)
