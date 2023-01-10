package com.moviepedia.domain;


import lombok.Data;

@Data
public class DisplayMovieDTO {

	private String movieCd;
	private String movieNm;
	private int prdtYear;
	private String repGenreNm;
	private String repNationNm;
	private String genreAlt;
	private String posterUrl;
	
	// 선호 감독/배우/장르의 리스트 출력 시 정보
	private String favoritePeopleCd;
	private String favoritePeopleNm;
	private String favoriteGenreNm1;
	private String favoriteGenreNm2;
	
	private double avgStarRating;
	private double userStarRating;

}
