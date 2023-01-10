package com.moviepedia.domain;

import lombok.Data;

// 선호 장르 또는 선호 국가 정보 담는 DTO
@Data
public class FavoriteGenreNationDTO {
	private String genre; // 선호 장르 담을때만
	private String nation; // 선호 국가 담을때만
	private int cntStarRating;
	private int finalScore;
	
	private double scaleCntStarRating;
	private double scaleAvgStarRating;
	private double scaleratioCntRatingCntGenre;
	
	
	private String resultMessage;

}

