package com.moviepedia.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.moviepedia.domain.MovieWithStarDTO;
import com.moviepedia.domain.PeopleInfoDTO;
import com.moviepedia.domain.PeopleVO;
import com.moviepedia.mapper.PeopleMapper;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Service
// 계층 구조 상 주로 비즈니스 영역을 담당하는 객체임을 표시
// 이 어노테이션은 패키지 읽어들이는 동안 처리됨
// ServiceImpl가 정상적으로 동작하기 위해선 Mapper 객체 필요함
@AllArgsConstructor
@Log4j
public class PeopleServiceImpl implements PeopleService {
	
	private PeopleMapper mapper;
	
	@Override
	public ArrayList<PeopleVO> getList(String movieCd) {
	
		log.info("getList......: " + movieCd);
		
		return mapper.getList(movieCd);
	}
	
	@Override
	public PeopleVO get(String peopleNm) {
		
		log.info("get......: " + peopleNm);
		
		return mapper.readByPeopleNm(peopleNm);
	}

	// 인물 페이지 - 인물 정보 및 감독/출연 영화 리스트 출력
	@Override
	public PeopleInfoDTO getPeopleDirectorFilmoList(String userid, String peopleCd) {
		log.info("getPeopleDirectorFilmoList... " + userid + "/" + peopleCd);
		
		PeopleInfoDTO peopleDirectorFilmoList = mapper.getPeopleDirectorFilmoList(userid, peopleCd);
		
		log.info(peopleDirectorFilmoList);
		
		return peopleDirectorFilmoList;
	}

	@Override
	public PeopleInfoDTO getPeopleActorFilmoList(String userid, String peopleCd) {
		log.info("getPeopleActorFilmoList... " + userid + "/" + peopleCd);
	
		PeopleInfoDTO peopleActorFilmoList = mapper.getPeopleActorFilmoList(userid, peopleCd);
		
		log.info(peopleActorFilmoList);
		
		return peopleActorFilmoList;
	}

	

	

}
