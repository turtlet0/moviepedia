package com.moviepedia.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
//Service - Controller 에게 모든 Data 처리 관련 기능을 제공하는 담당자
//Dao - Service 객체로 인해 실제로 SQL 문을 수행하는 계층
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moviepedia.aop.ExeTimer;
import com.moviepedia.domain.DisplayMovieDTO;
import com.moviepedia.domain.MovieVO;
import com.moviepedia.domain.PeopleVO;
import com.moviepedia.mapper.MovieMapper;
import com.moviepedia.mapper.PeopleMapper;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Service
	// 계층 구조 상 주로 비즈니스 영역을 담당하는 객체임을 표시
	// 이 어노테이션은 패키지 읽어들이는 동안 처리됨
	// ServiceImpl가 정상적으로 동작하기 위해선 Mapper 객체 필요함
@AllArgsConstructor
@Log4j
public class MovieServiceImpl implements MovieService {

	
	private MovieMapper mapper;
	
	// 영화 정보 페이지 반환 메서드
	@ExeTimer
	@Override
	public MovieVO get(String movieCd){
		log.info("get....." + movieCd);
		log.info("~~~~~~~~~~~~");
		
		MovieVO movieVO = mapper.read(movieCd);

		if(movieVO.getActorDetail() != null){
			
			movieVO.setActorDetail(movieVO.getActorDetail().replace("||", "|x|")); // view 페이지에서 split 이용 시 공백 요소 제거됨 방지 위함
		}

		return movieVO;
	}



/* 메인 페이지 */
	//  유저 미평가 영화 중 평균 평점 상위 영화 리스트 반환 
	@Override
	public List<DisplayMovieDTO> getHighStarRatingMovieList(String userid) {

		log.info("getHighStarRatingMovieList... " + userid);
		
		List<DisplayMovieDTO> highStarRatingMovieList = mapper.getHighStarRatingMovieList(userid);
		
		highStarRatingMovieList.forEach(highStarRatingMovie -> log.info(highStarRatingMovie));
		
		return highStarRatingMovieList;
	}

	// 유저 미평가 영화 중 선호 감독 영화 리스트 반환
	@Override
	public List<DisplayMovieDTO> getFavoriteDirectorSMovieList(String userid) {
		
		log.info("getFavoriteDirectorSMovieList... " + userid);
		
		String roleNm = "감독";
		
		List<PeopleVO> favoritePeopleList = mapper.getFavoritePeopleList(userid, roleNm);
		log.info("favoritePeopleList: " + favoritePeopleList);
		
		favoritePeopleList.forEach(favoritePeople -> log.info(favoritePeople));
		
		
		// null check
		int favoritePeopleList_length = favoritePeopleList.size();
		if(favoritePeopleList_length == 0) {
			return null;
		}
		
		List<DisplayMovieDTO> favoriteDirectorSMovieList = null;
		for (int i=0; i<favoritePeopleList_length; i++) {
			favoriteDirectorSMovieList = mapper.getFavoritePeopleSMovieList(userid, favoritePeopleList.get(i).getPeopleCd(), roleNm);
			
			log.info(favoriteDirectorSMovieList);
			if(favoriteDirectorSMovieList.size() >= 3) {
//			if(favoritePeopleList.get(i).getPeopleCd().equals("20070799")) {
				log.info("break");
				break;
			} else {
				favoriteDirectorSMovieList = null;
				log.info("continue");
				continue;
			}
			
		}
		log.info("------------------------");
		if(favoriteDirectorSMovieList != null) {
			favoriteDirectorSMovieList.forEach(favoriteDirectorSMovie -> log.info(favoriteDirectorSMovie));
			
		}
		
		return favoriteDirectorSMovieList;
	}

	// 유저 미평가 영화 중 선호 감독 배우 리스트 반환
	@Override
	public List<DisplayMovieDTO> getFavoriteActorSMovieList(String userid) {

		log.info("getFavoriteActorSMovieList... " + userid);
		
		
		
		String roleNm = "배우";
		
		List<PeopleVO> favoritePeopleList = mapper.getFavoritePeopleList(userid, roleNm);
		log.info("favoritePeopleList: " + favoritePeopleList);
		favoritePeopleList.forEach(favoritePeople -> log.info(favoritePeople));
		
		// null check
		int favoritePeopleList_length = favoritePeopleList.size();
		if(favoritePeopleList_length == 0) {
			return null;
		}
				
		
		List<DisplayMovieDTO> favoriteActorSMovieList = null;
		for (int i=0; i<favoritePeopleList_length; i++) {
			favoriteActorSMovieList = mapper.getFavoritePeopleSMovieList(userid, favoritePeopleList.get(i).getPeopleCd(), roleNm);
			
			log.info(favoriteActorSMovieList);
			
			if(favoriteActorSMovieList.size() >= 3) {
//			if(favoritePeopleList.get(i).getPeopleCd().equals("10055626")) {
				log.info("break");
				break;
			} else {
				favoriteActorSMovieList = null;
				log.info("continue");
				continue;
			}
			
		}
		log.info("------------------------");
		
		if(favoriteActorSMovieList != null) {
			favoriteActorSMovieList.forEach(favoriteActorSMovie -> log.info(favoriteActorSMovie));			
		}
		
		return favoriteActorSMovieList;
	}

	// 유저 미평가 영화 중 선호 장르 영화 리스트 반환
	@Override
	public List<DisplayMovieDTO> getFavoriteGenreMovieList(String userid) {
		
		log.info("getFavoriteGenreMovieList... " + userid);
		
		
		List<String> favoriteGenreList = mapper.getFavoriteGenreList(userid);
		
		log.info(favoriteGenreList);
		log.info(favoriteGenreList.size());
		
		// null check
		int favoriteGenreList_length = favoriteGenreList.size();
		if(favoriteGenreList_length <= 4) {
			return null;
		}
		
		
		List<DisplayMovieDTO> favoriteGenreMovieList = null;
		
		int firstIdx = (int) (Math.random() * 3);
		int secondIdx = (int) (Math.random() * (favoriteGenreList_length -3)) + 3;
		
		String favoriteGenreNm1 = favoriteGenreList.get(firstIdx);
		String favoriteGenreNm2 = favoriteGenreList.get(secondIdx);
		
		
		favoriteGenreMovieList = mapper.getFavoriteGenreMovieList(userid
				, favoriteGenreNm1, favoriteGenreNm2);
		
		log.info("genre1: " + favoriteGenreNm1 + " / genre2 : " + favoriteGenreNm2);
		log.info(favoriteGenreMovieList);
			
		// 선호 장르 이름 정보 저장
		favoriteGenreMovieList.get(0).setFavoriteGenreNm1(favoriteGenreNm1);
		favoriteGenreMovieList.get(0).setFavoriteGenreNm2(favoriteGenreNm2);
			
//		Loop1: // 중첩반복문 라벨명 지정해 break에 선언
//			for(int i=0; i<favoriteGenreList_length-1; i++) {
//				Loop2:
//					for(int j=i+1; j<favoriteGenreList.size(); j++) {
////						String favoriteGenreNm1 = favoriteGenreList.get(i);
////						String favoriteGenreNm2 = favoriteGenreList.get(j);
//						String favoriteGenreNm1 = "SF";
//						String favoriteGenreNm2 = "어드벤처";
//						
//						favoriteGenreMovieList = mapper.getFavoriteGenreMovieList(userid
//								, favoriteGenreNm1, favoriteGenreNm2);
//						
//						log.info("genre1: " + favoriteGenreNm1 + " / genre2 : " + favoriteGenreNm2);
//						log.info(favoriteGenreMovieList);
//						
//						
//						if(favoriteGenreMovieList.size() >= 5) {
//							
//							// 선호 장르 이름 정보 저장
//							favoriteGenreMovieList.get(0).setFavoriteGenreNm1(favoriteGenreNm1);
//							favoriteGenreMovieList.get(0).setFavoriteGenreNm2(favoriteGenreNm2);
//							
//							log.info("break");
//							break Loop1;
//						} else {
//							favoriteGenreMovieList = null;
//							log.info("continue");
//							continue;
//						}
//					}
//			}
			
		log.info("-------------------------------");
		if(favoriteGenreMovieList != null) {
			
			favoriteGenreMovieList.forEach(favoriteGenreMovie -> log.info(favoriteGenreMovie));
		}
		
		
		return favoriteGenreMovieList;
	}

	


/* 평가하기 페이지 */	
	
	
	// 영화 평가하기 리스트 조회 메인 메서드
	@Override
	public List<DisplayMovieDTO> getReviewList(String userid, int orderBy, int orderBy1, int orderBy2, int currentSqnc, int additionalCnt) {

		log.info("getReviewList....." + userid + "/" + orderBy + "/" + orderBy1 + "/" + orderBy2 + "/" + currentSqnc +"/"+ additionalCnt);
		
		 List<DisplayMovieDTO> reviewList = null;
		 
		if(orderBy == 0) {
	
			reviewList = getReviewListByRandom(userid, additionalCnt);
		}
		else if(orderBy == 1) {
			
			reviewList = getReviewListByHighStarRating(userid, orderBy1, orderBy2, currentSqnc, additionalCnt);
		}
		else if(orderBy >= 10 && orderBy <= 29) {
			
			reviewList = getReviewListByRepGenreNm(userid, orderBy, orderBy2, currentSqnc, additionalCnt);
		}
		
		return reviewList;
	}
	
	
	
	// 리뷰 - 랜덤 영화 리스트 조회 메서드
	@Override
	public List<DisplayMovieDTO> getReviewListByRandom(String userid, int additionalCnt) {
		
		log.info("getReviewListByRandom... "+ userid +"/"+ additionalCnt);
		
		return mapper.getReviewListByRandom(userid, additionalCnt);
	}
	
	// 리뷰 - 평균 평점 상위 리스트 조회 메서드
	@Override
	public List<DisplayMovieDTO> getReviewListByHighStarRating(String userid, int orderBy1, int orderBy2, int currentSqnc, int additionalCnt) {

		log.info("getReviewListByHighStarRating... "+ userid + "/" + orderBy1 + "/" + orderBy2 + "/" +currentSqnc +"/"+ additionalCnt);
		
		List<String> repGenreNmList = Arrays.asList("전체","드라마","액션","애니메이션","멜로/로맨스","코미디","공포(호러)","다큐멘터리","스릴러","범죄","SF","미스터리","판타지","공연","어드벤처","전쟁","가족","사극","뮤지컬","서부극(웨스턴)","기타");
		
		String repGenreNm = repGenreNmList.get(orderBy1);
		
		log.info("repGenreNm: " + repGenreNm);
		
		
		List<String> repNationNmList = Arrays.asList("전체", "미국","한국","일본","프랑스","중국","독일","캐나다","스페인","홍콩","러시아","이탈리아","호주","덴마크","대만","노르웨이","인도","태국","스웨덴","벨기에","아일랜드","헝가리","네덜란드","멕시코","이스라엘","핀란드","브라질","아르헨티나","아이슬란드","폴란드","기타");
		
		String repNationNm = repNationNmList.get(orderBy2);
		
		log.info("repNationNm: " + repNationNm);
		
		return mapper.getReviewListByHighStarRating(userid, repGenreNm, repNationNm, currentSqnc, additionalCnt);
	}


	// 대표 장르별 리스트 조회 메서드
	@Override
	public List<DisplayMovieDTO> getReviewListByRepGenreNm(String userid, int orderBy, int orderBy2, int currentSqnc, int additionalCnt) {
		
		log.info("getReviewListByRepGenreNm... "+ userid + "/" + orderBy + "/" + orderBy2 + "/" +currentSqnc +"/"+ additionalCnt);
		
		
		List<String> repGenreNmList = Arrays.asList("드라마","액션","애니메이션","멜로/로맨스","코미디","공포(호러)","다큐멘터리","스릴러","범죄","SF","미스터리","판타지","공연","어드벤처","전쟁","가족","사극","뮤지컬","서부극(웨스턴)","기타");
		
		String repGenreNm = repGenreNmList.get(orderBy-10);
		
		log.info("repGenreNm: " + repGenreNm);
		
		
		List<String> repNationNmList = Arrays.asList("전체", "미국","한국","일본","프랑스","중국","독일","캐나다","스페인","홍콩","러시아","이탈리아","호주","덴마크","대만","노르웨이","인도","태국","스웨덴","벨기에","아일랜드","헝가리","네덜란드","멕시코","이스라엘","핀란드","브라질","아르헨티나","아이슬란드","폴란드","기타");
		
		String repNationNm = repNationNmList.get(orderBy2);
		
		log.info("repNationNm: " + repNationNm);
		
		
		return mapper.getReviewListByRepGenreNm(userid, repGenreNm, repNationNm, currentSqnc, additionalCnt);
	}	
	
	

	

	
	
	

}
