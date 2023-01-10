package com.moviepedia.domain;

import java.util.Date;

import lombok.Data;

@Data
public class CommentVO {

	private Long commentCd;
	private String movieCd;
	
	private String userid;
	private String contents;
	private Date commentDate;
	private Date updateDate;
	
	private int replyCnt;
	private int likeCnt;
	
}
