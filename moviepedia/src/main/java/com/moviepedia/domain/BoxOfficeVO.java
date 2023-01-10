package com.moviepedia.domain;


import java.util.Date;

import lombok.Data;

@Data // Lombok 이용해 생성자, getter/setter, toString()등 자동 생성해줌
public class BoxOfficeVO {
	
	private Date showDate; // 조회일자 - 별도 저장 yyyymmdd 형식
	private int rank; // 해당일자의 박스오피스 순위
	private String movieCd;
	private String movieNm;
	private Date openDt; //영화의 개봉일 yyyy-MM-dd
	private int audiCnt; // 해당일의 관객수
	private int audiAcc; // 누적관객수
	
	private Date updateDate;
}
