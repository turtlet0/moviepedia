package com.moviepedia.domain;

import java.util.List;

import lombok.Data;

@Data
public class StarRatingAnalysisDTO {
	
	private String userid;
	private String movieCd;
	
	private int starRatingCnt;
	private double starRatingAvg;
	
	private String resultMessage;
	
	private List<CntByStarDTO> cntByStarDTOList;
	
}
