package com.moviepedia.domain;

import java.util.Date;

import lombok.Data;

@Data
public class ReplyInfoDTO {

	private String userName;
	private String randomString;
	
	
	private Long replyCd;
	
	private Long commentCd;
	private String movieCd;
	private String userid;
	
	private String reply;
	private Date replyDate;
	private Date updateDate;
}
