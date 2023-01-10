package com.moviepedia.domain;

import java.util.Date;

import lombok.Data;

@Data
public class ApiInfoDTO {

	private String name;
	private boolean success;
	
	private int curPage;
	private int itemPerPage;
	private int openStartDt;
	private int openEndDt;
	
	private int movieTotalRegisterCnt;
	private int peopleTotalRegisterCnt;
	
	private Date updateDate;
	

}
