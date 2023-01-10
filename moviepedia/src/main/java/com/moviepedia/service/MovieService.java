package com.moviepedia.service;
import java.util.List;

import com.moviepedia.domain.DisplayMovieDTO;
//Service : Controller에게 모든 Data 처리 관련 기능을 제공하는 담당자
//DAO : Service 객체로 인해 실제로 SQL문 수행하는 계층
import com.moviepedia.domain.MovieVO;

// 비즈니스 계층 설정
	// 설계 시 각 계층 간 느슨한 연결을 인터페이스 이용해 실현
	// service 인터페이스의 메서드 설계시 이름은 현실적인 로직 이름으로 부여할 것
public interface MovieService {

	public MovieVO get(String movieCd);

		
/* 메인 페이지 */
	// 유저 미평가 영화 중 평균 평점 상위 영화 리스트 반환 
	public List<DisplayMovieDTO> getHighStarRatingMovieList(String userid);
	
	// 유저 미평가 영화 중 선호 감독 영화 리스트 반환
	public List<DisplayMovieDTO> getFavoriteDirectorSMovieList(String userid);
	
	// 유저 미평가 영화 중 선호 배우 영화 리스트 반환
	public List<DisplayMovieDTO> getFavoriteActorSMovieList(String userid);
	
	// 유저 미평가 영화 중 선호 장르 영화 리스트 반환
	List<DisplayMovieDTO> getFavoriteGenreMovieList(String userid);
	

/* 리뷰 페이지 */
	
	/** @see 영화 평가하기 리스트 조회 메인 메서드
	 * @param currentSqnc, additionalCnt
	 * */
	public List<DisplayMovieDTO> getReviewList(String userid, int orderBy, int orderBy1, int orderBy2, int currentSqnc, int additionalCnt);
	
		/** 리뷰 - 랜덤 영화 리스트 조회 메서드 */
		public List<DisplayMovieDTO> getReviewListByRandom(String userid, int additionalCnt);
		
		/** 리뷰 - 평균 평점 상위 리스트 조회 메서드 */
		public List<DisplayMovieDTO> getReviewListByHighStarRating(String userid, int orderBy1, int orderBy2, int currentSqnc, int additionalCnt);
		
		/** 리뷰 - 대표 장르별 리스트 조회 메서드 */
		public List<DisplayMovieDTO> getReviewListByRepGenreNm(String userid, int orderBy, int orderBy2, int currentSqnc, int additionalCnt);

}
