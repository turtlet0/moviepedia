package com.moviepedia.domain;

import java.util.Date;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class PeopleVO {
	// 영진위 영화인 목록 - 
		private String peopleCd; // 인물코드(감독, 배우)
		private String peopleNm; // 인물명(국문)
		private String peopleNmEn; // 인물명(영문)
//		private String filmoNames; // 필모리스트 -> 영진위 상세정보의 filmos로 대신함(각 영화의 movieCd와 moviePatNm만)
		
		// 영진위 영화인 상세정보 - 
		private String sex; // 성별
		private String repRoleNm; // 영화인 분류명(역할)
		private String directorFilmos;	// 감독 필모리스트 : 감독한 필모리스트
		private String actorFilmos;	// 배우 필모리스트 : 출연한 필모리스트
//		private String movieCd;	// 참여 영화코드 -> filmos에 저장
//		private String movieNm; // 참여 영화명 - 저장안함
//		private String moviePartNm; //참여분야 -> 저장안함 (DirectorFilmos와 ActorFilmos로 구분)
//		private String homepages; // 관련 URL -> 저장안함
		
		// 별도로, DB 변경(INSERT, UPDATE) 시각 필드 선언 (SQL default : NOW() )
		private Date updateDate;
		
		// people 대표작
		private String repMovieList;
		private Date repMovieListDate;
		
		// 배우 배역 비중
		private double avgActorRoleImportance;
		private int cntActorRoleImportance;

}
