package com.moviepedia.domain;

import java.util.Date;

import lombok.Data;

@Data
public class LikeVO {

	private Long likeCd;
	
	private Long commentCd;
	private String movieCd;
	private String userid;
	
	private Date likeDate;
}
