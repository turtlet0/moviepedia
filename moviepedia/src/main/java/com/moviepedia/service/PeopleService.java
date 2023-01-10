package com.moviepedia.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.moviepedia.domain.MovieVO;
import com.moviepedia.domain.MovieWithStarDTO;
import com.moviepedia.domain.PeopleInfoDTO;
import com.moviepedia.domain.PeopleVO;

public interface PeopleService {
	
	public ArrayList<PeopleVO> getList(String movieCd);
	
	public PeopleVO get(String peopleNm);
	
	// 인물 페이지 - 인물 정보 및 감독/출연 영화 리스트 출력
	public PeopleInfoDTO getPeopleDirectorFilmoList(String userid, String peopleCd);
	public PeopleInfoDTO getPeopleActorFilmoList(String userid, String peopleCd);
	
}
