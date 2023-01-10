package com.moviepedia.domain;

import java.util.List;

import lombok.Data;

@Data
public class PeopleInfoDTO {
//	private PeopleVO peopleVO;
	private String peopleCd;
	private String peopleNm;
	private String peopleNmEn;
	private String repRoleNm;
	
	private List<MovieWithStarDTO> movieWithStarDTOList;

}




