package com.moviepedia.service;

import java.util.List;

import com.moviepedia.domain.MovieVO;
import com.moviepedia.domain.PeopleVO;
import com.moviepedia.domain.SearchMovieListDTO;
import com.moviepedia.domain.UserDTO;

public interface SearchService {
	
	public List<MovieVO> getRelatedQuery(String query);
	
//	public SearchMovieListDTO getMovieList(String query, int currentActorCnt, int currentMovieNmDirectorCnt);
	public List<MovieVO> getMovieList(String query, int currentSqnc, int additionalCnt);
	
	public List<PeopleVO> getPeopleList(String query, int currentSqnc, int additionalCnt);
	
	public List<UserDTO> getUserList(String query, int currentSqnc, int additionalCnt);

}
