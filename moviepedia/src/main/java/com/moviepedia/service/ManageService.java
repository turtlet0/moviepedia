package com.moviepedia.service;

import java.util.List;

import com.moviepedia.domain.ApiInfoDTO;
import com.moviepedia.domain.BoxOfficeVO;
import com.moviepedia.domain.BoxOfficeWithStarDTO;
import com.moviepedia.domain.MovieVO;
import com.moviepedia.domain.PeopleVO;

public interface ManageService {

	public void testPost();
	
	
	
	// 영화 & 인물 정보 API 호출 및 DB 저장 메서드
	public List<ApiInfoDTO> registerMainMoviePeopleList(String key, 
			int curPageStart,
			int curPageFinish,
			int itemPerPage,		
			int openStartDt,
			int openEndDt);
	
		// 영화 정보 API 호출 메인 메서드
		public List<MovieVO> searchMovieList(String key,
				int curPage,
				int itemPerPage,		
				int openStartDt,
				int openEndDt);
			
			// 영화 상세 정보 API 호출 메서드
			public MovieVO searchMovieDetail(String key, MovieVO movieVO);
			
			// 네이버 영화 검색 API 호출 메서드
			public String searchNaverMovieLink(MovieVO movieVO);
			
			// 네이버 영화 포스터 및 줄거리 크롤링 메서드
			public MovieVO crawlingNaverMoviePosterStory(String link, MovieVO movieVO);
		
		// 영화 API 호출 결과 리스트 DB 저장 메서드
		public int registerMovieList(List<MovieVO> movieList);
		
		// 인물 정보 API 호출 메인 메서드
		public List<PeopleVO> searchPeopleList(String key, List<MovieVO> movieList);
			
			// 인물 상세정보 API 호출 메서드
			public PeopleVO searchPeopleDetail(String key, PeopleVO peopleVO);
		
		// 인물 API 호출 결과 리스트 DB 저장 메서드
		public int registerPeopleList(List<PeopleVO> peopleList);
		
	
	
	
	// ** 박스오피스 호출 최초 메서드 - 메인페이지 
	public List<BoxOfficeWithStarDTO> initRegisterMainBoxOfficeList(String userid);
	
	// 박스오피스 정보 API 호출 & DB 저장 메인 메서드
	public List<BoxOfficeWithStarDTO> registerMainBoxOfficeList(String key, String inquiryDate, String userid);
	
		// 금일 날짜 반환 메서드 - 박스오피스 API
		public String getTodayDate();
		
		// 해당 일자의 DB 저장된 박스오피스 리스트 반환 메서드 - 없는 경우 API 호출 진행 
		public List<BoxOfficeWithStarDTO> getDailyBoxOfficeList(String inquiryDate, String userid);
		
		// 박스오피스 정보 API 호출 메인 메서드
		public List<BoxOfficeVO> searchDailyBoxOfficeList(String key, String inquiryDate);
		
			// 박스오피스 API 호출 중 영화 정보 API 호출 메서드 - 영화명, 영화코드
			public MovieVO searchMovie(String key,
					String movieNm,	
					String movieCd);
			
			// 영화 API 호출 결과 DB 저장 메서드
			public int registerMovie(MovieVO movieVO);
			
			// 오버라이딩 - 인물 정보 API 호출 메인 메서드
			public List<PeopleVO> searchPeopleList(String key, MovieVO movieVO);
			
		// 박스오피스 API 호출 결과 리스트 DB 저장 메서드
		public int registerBoxOfficeList(List<BoxOfficeVO> boxOfficeList);
			
}
