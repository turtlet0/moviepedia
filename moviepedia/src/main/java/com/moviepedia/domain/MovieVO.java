package com.moviepedia.domain;

import java.util.Date;

import lombok.Data;

// DB에서 가져오는 데이터는 VO / 외부와 데이터 통신하는 경우엔 DTO로 임의 구분해 사용

@Data // Lombok 이용해 생성자, getter/setter, toString()등 자동 생성해줌
public class MovieVO {
	private String movieCd; // 영화 코드
	private String movieNm; // 영화 제목(국문)
	private String movieNmEn; // 영화 제목(영문)
	private int prdtYear; // 제작연도(YYYY)
	private Date openDt; // 개봉일(YYYYMMDD)
	private String typeNm; // 영화 유형 (ex) 장편)
	private String prdtStatNm; // 제작 상태 (ex) 개봉)
	private String nationAlt; // 제작국가(전체) 
	private String genreAlt; // 영화장르(전체)
	private String repNationNm; // 대표 제작국가명
	private String repGenreNm; // 대표 장르명
	// private String companys; // 제작사 ex) [{"companyCd":"20100036","companyNm":"㈜인디스토리"}]
	private String companyCd; // 제작사 코드 (현재 저장안함)
	private String companyNm; // 제작사 이름 (현재 저장안함)
	
	// 이하 getMovieDetail(MovieDTO mdto) (영진위 영화 상세정보)에서 값 받는 필드
	private String directorDetail; // 감독 정보 peopleNm | peopleNmEn, 
	private int showTm; // 상영 시간
	private String actorDetail; // 배우 정보 peopleNm | peopleNmEn | cast, 
	private String watchGradeNm; // 관람 등급 명칭
	
	// 이하 getMovieUrlStory(String link, MovieDTO mdto) 에서 값 받는 필드
	private String posterUrl; // 포스터 url
	private String storyText; // 줄거리
	
	// 별도로, DB 변경(INSERT, UPDATE) 시각 필드 선언 (SQL default : NOW() )
	private Date updateDate;
	
	private int commentCnt;
	
	
}
