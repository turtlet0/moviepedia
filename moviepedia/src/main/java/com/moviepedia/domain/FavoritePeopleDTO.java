package com.moviepedia.domain;

import java.util.Date;

import lombok.Data;

@Data
public class FavoritePeopleDTO {
	// mapper.getFavoritePeopleScore return
	private String peopleCd;
	private String peopleNm;
	private Double score1; 
		// 배우 : (iv2.scaleCntStarRating * 0.55 + iv2.scaleAvgStarRating * 0.30 + iv2.scaleRatioRatingByCntFilmos * 0.15) AS score1
		// 감독 : 최종 점수 : (iv2.scaleCntStarRating * 0.50 + iv2.scaleAvgStarRating * 0.25 + iv2.scaleRatioRatingByCntFilmos * 0.15 + iv2.scaleCntActorFilmos *  0.10) AS score1
	private int cntStarRating;
	
	// mapper.getActorFilmoList return
//	private String peopleNm; // 중복
	private String movieCd;
	private String actorDetail;
	
	private String resultMessage;
	
	private int finalScore; // 연산 결과 최종 점수
	
	
	// people 대표작
	private String repMovieList;
	private Date repMovieListDate;
	
	private Date updateDate;
	
	private double scaleCntStarRating;
	private double scaleAvgStarRating;
	private double scaleRatioRatingByCntFilmos;
	private double scaleCntPeopleFilmos;
	
	private double scaleAvgValue;
	private double scaleCntValue;
	


}

