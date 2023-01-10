package com.moviepedia.domain;
import java.util.Date;
import java.util.List;

// 유저의 정보(이름 및 사진) 및 코멘트 정보 저장 DTO
import lombok.Data;

@Data
public class BoxOfficeWithStarDTO {

	private Date showDate; // 조회일자 - 별도 저장 yyyymmdd 형식
	private int rank; // 해당일자의 박스오피스 순위
	private String movieCd;
	private String movieNm;
	private Date openDt; //영화의 개봉일 yyyy-MM-dd
	private int audiCnt; // 해당일의 관객수
	private int audiAcc; // 누적관객수

//	private BoxOfficeVO boxOfficeVO;
	
	private String posterUrl;
	private int prdtYear;
	private String repNationNm;
	
	private double avgStarRating;
	
	private double userStarRating;

}
