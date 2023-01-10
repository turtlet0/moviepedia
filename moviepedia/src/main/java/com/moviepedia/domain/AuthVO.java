package com.moviepedia.domain;

import lombok.Data;

// tbl_member_auth 테이블 그대로 반영
@Data
public class AuthVO {
	
	private String userid;
	private String auth;
}
