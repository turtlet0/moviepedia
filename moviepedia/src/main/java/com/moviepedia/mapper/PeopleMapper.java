package com.moviepedia.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.moviepedia.domain.MovieWithStarDTO;
import com.moviepedia.domain.PeopleInfoDTO;
import com.moviepedia.domain.PeopleVO;

public interface PeopleMapper {
	public ArrayList<PeopleVO> getList(String movieCd);
	
	public PeopleVO read(String peopleCd);
	
	public PeopleVO readByPeopleNm(String peopleNm);
	
	public List<PeopleVO> readBypeopleNmMovieCd(@Param("peopleNm") String peopleNm, @Param("movieCd") String movieCd);
		// 인물명으로 검색하기때문에 두개이상의 결과 출력될 수 있어 List로 받음 -> length 2이상인 경우 API 호출
	
	
	public int insertPeopleList(List<PeopleVO> peopleList);
	
	public PeopleInfoDTO getPeopleDirectorFilmoList(@Param("userid") String userid, @Param("peopleCd") String peopleCd);
	public PeopleInfoDTO getPeopleActorFilmoList(@Param("userid") String userid, @Param("peopleCd") String peopleCd);
	
	
// 대표작 조회 - 검색, 취향분석 페이지에서 조회
	public int updateDirectorRepMovieList(@Param("peopleCd") String peopleCd);
//	public int updateDirectorRepMovieList(PeopleVO peopleVO);
	
	public int updateActorRepMovieList(@Param("peopleCd")  String peopleCd);
		// 객체가 아닌 String peopleCd 하나만 파라미터로 지정하면 keyProperty 변수 setter 찾을수없다는 에러 발생 -> @Param 지정 시 정상 실행
//	public int updateActorRepMovieList(PeopleVO peopleVO);
	
	public Map<String, Object> getRepMovieList(String peopleCd);

	
	public int updateActorRoleImportance(@Param("peopleCd")  String peopleCd);
	
	public List<PeopleVO> getPeopleListForUpdateRoleImportance(@Param("currentIdx") int currentIdx);

}
