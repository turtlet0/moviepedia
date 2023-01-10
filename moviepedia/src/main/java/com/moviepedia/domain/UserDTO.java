package com.moviepedia.domain;

import lombok.Data;

@Data
public class UserDTO {

//	private MemberVO memberVO;
	private String userid;
	private String userName;
	private String randomString;
	
	private String userIntroduction;
	
	private int cntStarRating;
	private int cntComment;
}
