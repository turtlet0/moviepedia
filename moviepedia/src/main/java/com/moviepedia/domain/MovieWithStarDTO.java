package com.moviepedia.domain;

import lombok.Data;

@Data
public class MovieWithStarDTO {

//	private MovieVO movieVO;
	private String movieCd;
	private String movieNm;
	private String posterUrl;
	private int prdtYear;
	private String repNationNm; 
	private String repGenreNm;

	
	private double userStarRating;
	private double avgStarRating;

}
