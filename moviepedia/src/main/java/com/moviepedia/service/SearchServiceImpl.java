package com.moviepedia.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moviepedia.domain.MovieVO;
import com.moviepedia.domain.PeopleVO;
import com.moviepedia.domain.SearchMovieListDTO;
import com.moviepedia.domain.UserDTO;
import com.moviepedia.mapper.PeopleMapper;
import com.moviepedia.mapper.SearchMapper;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Service
@AllArgsConstructor
@Log4j
public class SearchServiceImpl implements SearchService {

	@Setter(onMethod_ = @Autowired)
	private SearchMapper mapper;
	
	@Setter(onMethod_ = @Autowired)
	private PeopleMapper peopleMapper;
	
	
	@Override
	public List<MovieVO> getRelatedQuery(String query) {
		log.info("getRelatedQuery.... " + query);
		return mapper.getRelatedQuery(query);
	}

/*
	@Override
	public SearchMovieListDTO getMovieList(String query, int currentActorCnt, int currentMovieNmDirectorCnt) {
		
		SearchMovieListDTO searchMovieListDTO = new SearchMovieListDTO();
		
		List<MovieVO> resultMovieList = new ArrayList<MovieVO>();
		
		List<MovieVO> movieListForActorNm = mapper.getMovieListForActorNm(query, currentActorCnt);
		
		// DTO에 저장
		searchMovieListDTO.setReturnActorCnt(movieListForActorNm.size());
		
		MovieVO movieVO = new MovieVO();
		
		List<MovieVO> newMovieListForActorNm = new ArrayList<MovieVO>();
			
		for(int i=0; i<movieListForActorNm.size(); i++) {
			movieVO = movieListForActorNm.get(i);
			
			String actorDetail = movieVO.getActorDetail();
			
			String[] arrActorDetail = actorDetail.split(",");
			
			log.info(movieVO.getMovieNm());
			log.info(actorDetail);
//			log.info(arrActorDetail);
			
			for(int j=0; j<arrActorDetail.length; j++) {
				
				if(arrActorDetail[j].contains("|")) { 
					// ,로 split한 String[] 중 배역명에 , 들어가 있어 split된 요소 제거위함
					// ex) 김철수||청원경찰1, 2,박영희|| -> 김철수||청원경찰1, 2, 박영희|| -> 두번째 2같은 요소 제거
					
					String actorNm = arrActorDetail[j].split("\\|", 2)[0]; // split() : 배열 크기 지정가능. 해당 배열 크기되면 split 중단
					
					if(! actorNm.contains(query)) {
						log.info("X: " + arrActorDetail[j]);
						
					} else {
						log.info(actorNm);
						
						newMovieListForActorNm.add(movieVO);
						break;
					}
					
				} else {
					log.info(",,,, : " + arrActorDetail[j]);
				}
			}
			log.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			log.info(movieVO.getActorDetail());
			log.info("----------------------------------------------------------");
		}
		log.info("////////////////////////////////////////");
		log.info("newMovieListForActorNm: " + newMovieListForActorNm);
		log.info("newMovieListForActorNm.size(): " + newMovieListForActorNm.size());
		log.info("////////////////////////////////////////");
		
		int restCnt = 10 - newMovieListForActorNm.size();

		List<MovieVO> movieListForMovieNmDirectorNm = mapper.getMovieListForMovieNmDirectorNm(query, currentMovieNmDirectorCnt, restCnt);
		
		// DTO에 저장
		searchMovieListDTO.setReturnMovieNmDirectorCnt(movieListForMovieNmDirectorNm.size());
		
		log.info("currentMovieNmDirectorCnt: " + currentMovieNmDirectorCnt + "/restCnt: "  + restCnt);
		log.info("movieListForMovieNmDirectorNm: " + movieListForMovieNmDirectorNm);
		log.info("movieListForMovieNmDirectorNm.size(): " + movieListForMovieNmDirectorNm.size());
		log.info("////////////////////////////////////////");
		
		resultMovieList.addAll(newMovieListForActorNm);
		resultMovieList.addAll(movieListForMovieNmDirectorNm);

		// DTO에 저장
		searchMovieListDTO.setResultMovieList(resultMovieList);
		
		log.info("ReturnActorCnt: " + searchMovieListDTO.getReturnActorCnt());
		log.info("ReturnMovieNmDirectorCnt(): " + searchMovieListDTO.getReturnMovieNmDirectorCnt());
		log.info("////////////////////////////////////////");

		return searchMovieListDTO;
	}
*/
	
	@Override
	public List<MovieVO> getMovieList(String query, int currentSqnc, int additionalCnt) {
		
		log.info("getMovieList......... " + query +"/"+ currentSqnc +"/"+ additionalCnt);
		
		List<MovieVO> movieList = mapper.getMovieList(query, currentSqnc, additionalCnt);
		
		log.info(movieList);
		
		return movieList;
	}

	@Override
	public List<PeopleVO> getPeopleList(String query, int currentSqnc, int additionalCnt) {
		log.info("getPeopleList.... " + query);
		
		List<PeopleVO> peopleList = mapper.getPeopleList(query, currentSqnc, additionalCnt);
		
		
		log.info(peopleList);
		log.info("peopleList.size(): " +peopleList.size());
		
		PeopleVO peopleVO = new PeopleVO();
		for(int i=0, len=peopleList.size(); i<len; i++) {
			peopleVO = peopleList.get(i);
			
			/* 대표작 출력 */
			// DB 대표작 없는 경우 || 있으나 조회일자가 people updateDate보다 이전일때 재갱신 
//			if(peopleVO.getRepMovieList() == null || peopleVO.getRepMovieListDate().before(peopleVO.getUpdateDate())) {
			if(true) {
				
				if(peopleVO.getRepRoleNm().equals("배우")) {

					peopleMapper.updateActorRepMovieList(peopleVO.getPeopleCd());
					
				} else { // 감독		
					peopleMapper.updateDirectorRepMovieList(peopleVO.getPeopleCd());			
				}
			}
				
			peopleVO = peopleMapper.read(peopleVO.getPeopleCd());
			log.info(peopleVO.getPeopleNm()+"/"+peopleVO.getRepRoleNm()+"/"+peopleVO.getRepMovieList()+"/"+peopleVO.getRepMovieListDate());
			peopleList.set(i, peopleVO);
			
		}
		
		/* 사용x) 배우 출연 영화 비중 고려 x
		 * mapper.getPeopleMasterpiece 이용
		for(int i=0, len=peopleList.size(); i<len; i++) {
			log.info("~~~~~~~~~~~~~~~~~~~~~~~~~~");
			peopleVO = peopleList.get(i);
			
			List<MovieVO> movieList = mapper.getPeopleMasterpiece(peopleVO.getPeopleNm(), peopleVO.getRepRoleNm());
			

			MovieVO movieVO = new MovieVO();
			
			StringBuilder sbMasterpiece = new StringBuilder();
			
			for(int j=0, len2=movieList.size(); j<len2; j++) {
				movieVO = movieList.get(j);
				sbMasterpiece.append(movieVO.getMovieNm());
				
				if(j != movieList.size() -1) { // 마지막 인덱스 아닌 경우
					sbMasterpiece.append(",");	
				}
			}
			
			if(peopleVO.getRepRoleNm().equals("감독")) {
				peopleVO.setDirectorFilmos(sbMasterpiece.toString());
			} else if(peopleVO.getRepRoleNm().equals("배우")) {
				peopleVO.setActorFilmos(sbMasterpiece.toString());
			}
			
			// 평균 평점과 평가인원(샘플 사이즈)을 1,2,3 줄세워서 단순평균 / 감독은 감독작, 배우는 출연작
		}
		*/
		
		return peopleList;
	}


	@Override
	public List<UserDTO> getUserList(String query, int currentSqnc, int additionalCnt) {
		
		log.info("getUserList......... " + query +"/"+ currentSqnc +"/"+ additionalCnt);
		
		List<UserDTO> userList = mapper.getUserList(query, currentSqnc, additionalCnt);
		
		log.info(userList);
		
		return userList;
	}

	

}
