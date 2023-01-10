package com.moviepedia.domain;

import java.util.Date;

import lombok.Data;

@Data
public class StarRatingVO {

	private String movieCd;
	private String userid;
	private double starRating;
	
	private Date starRatingDate; 
}
