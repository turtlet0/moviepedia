package com.moviepedia.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.moviepedia.domain.MovieVO;
import com.moviepedia.domain.PeopleVO;
import com.moviepedia.domain.UserDTO;

public interface SearchMapper {

	public List<MovieVO> getRelatedQuery(String query);
	
	public List<MovieVO> getMovieListForActorNm(@Param("query") String query, @Param("currentCnt") int currentCnt);
	
	public List<MovieVO> getMovieListForMovieNmDirectorNm(@Param("query") String query, @Param("currentCnt") int currentCnt
															,@Param("restCnt") int restCnt);
	
	
	public List<MovieVO> getMovieList(@Param("query") String query, @Param("currentSqnc") int currentSqnc, @Param("additionalCnt") int additionalCnt);
	
	public List<PeopleVO> getPeopleList(@Param("query") String query, @Param("currentSqnc") int currentSqnc, @Param("additionalCnt") int additionalCnt);
	
	//	public List<MovieVO> getPeopleMasterpiece(@Param("peopleNm") String peopleNm, @Param("repRoleNm") String repRoleNm);
	
	
		
	public List<UserDTO> getUserList(@Param("query") String query, @Param("currentSqnc") int currentSqnc, @Param("additionalCnt") int additionalCnt);
}
