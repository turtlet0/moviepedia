package com.moviepedia.domain;
// 검색 시 영화 결과 리스트 저장용 DTO 객체

import java.util.List;

import lombok.Data;

@Data
public class SearchMovieListDTO {

	private List<MovieVO> resultMovieList;
	
	private int returnActorCnt; // mapper.getMovieListForActorNm(query, currentCnt) 출력 행 수
	private int returnMovieNmDirectorCnt; // mapper.getMovieListForMovieNmDirectorNm(query, previousCnt, restCnt) 출력 행 수
}
