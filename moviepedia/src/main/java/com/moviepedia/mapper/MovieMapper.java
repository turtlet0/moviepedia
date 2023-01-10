package com.moviepedia.mapper;
// XML 이용한 SQL문 처리 방식 이용

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.moviepedia.domain.DisplayMovieDTO;
import com.moviepedia.domain.DisplayMovieDTO;
import com.moviepedia.domain.MovieVO;
import com.moviepedia.domain.PeopleVO;
import com.moviepedia.domain.StarRatingVO;

public interface MovieMapper {

	// read: PK movieCd 이용 해당 movie 정보 반환
	public MovieVO read(String movieCd);
	
	public int insertMovieList(List<MovieVO> movieList);
	
	public int insertMovie(MovieVO movieVO);
	
	// 코멘트 개수 수정
	public void updateCommentCnt(@Param("movieCd") String movieCd, @Param("amount") int amount);
	
	
/* 메인 페이지 */
	/** 유저 미평가 영화 중 평균 평점 상위 영화 리스트 반환 
	 * 평가 페이지 평균 평점 TOP 리스트와 유사한 쿼리 사용
	 * : 기준 평가 수 만족하는 영화의 평균별점과 별점 평가 수 8:2 가중 평균한 값 기준으로 정렬 */ 
	public List<DisplayMovieDTO> getHighStarRatingMovieList(String userid);
	
	
	// 유저 선호 감독/배우 리스트 반환 by tbl_member
	public List<PeopleVO> getFavoritePeopleList(@Param("userid") String userid, @Param("roleNm") String roleNm);
	
	// 유저 미평가 영화 중 선호 감독 영화 리스트 반환
	public List<DisplayMovieDTO> getFavoritePeopleSMovieList(@Param("userid") String userid, @Param("peopleCd") String peopleCd, @Param("roleNm") String roleNm);
	
	// 유저 선호 장르 리스트 반환 by tbl_member
	public List<String> getFavoriteGenreList(String userid);
	
	// 유저 미평가 영화 중 선호 장르 영화 리스트 반환
	public List<DisplayMovieDTO> getFavoriteGenreMovieList(@Param("userid") String userid
			, @Param("favoriteGenre1") String favoriteGenre1, @Param("favoriteGenre2") String favoriteGenr2);
	
	
/* 평가하기 페이지 */	
	public List<DisplayMovieDTO> getReviewListByRandom(@Param("userid") String userid, @Param("additionalCnt") int additionalCnt);
	
	public List<DisplayMovieDTO> getReviewListByHighStarRating(@Param("userid") String userid, @Param("repGenreNm") String repGenreNm, @Param("repNationNm") String repNationNm, @Param("currentSqnc") int currentSqnc, @Param("additionalCnt") int additionalCnt);
	
	public List<DisplayMovieDTO> getReviewListByRepGenreNm(@Param("userid") String userid, @Param("repGenreNm") String repGenreNm, @Param("repNationNm") String repNationNm, @Param("currentSqnc") int currentSqnc, @Param("additionalCnt") int additionalCnt);
	
	
	
	
	
	
	
	public int testMerge(List<StarRatingVO> starRatingList);
	
}


