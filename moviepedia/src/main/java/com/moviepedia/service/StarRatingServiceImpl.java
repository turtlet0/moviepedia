package com.moviepedia.service;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moviepedia.domain.CntByStarDTO;
import com.moviepedia.domain.StarRatingAnalysisDTO;
import com.moviepedia.domain.StarRatingVO;
import com.moviepedia.mapper.StarRatingMapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class StarRatingServiceImpl implements StarRatingService {
	
	
	@Setter(onMethod_ = @Autowired)
	private StarRatingMapper mapper;
	

	
								
	@Override
	public int register(StarRatingVO vo) {
		
		log.info("register...: " + vo);
		
		return mapper.insert(vo);
	}

	@Override
	public StarRatingVO get(String userid, String movieCd) {
		
		log.info("get.... : " + userid + "/" + movieCd);
		
		return mapper.read(userid, movieCd);
	}
	

	@Override
	public int remove(String userid, String movieCd) {
		
		log.info("remove.... " +  userid + "/" + movieCd);
		
		return mapper.delete(userid, movieCd);
	}

	@Override
	public int modify(StarRatingVO vo) {
		
		log.info("modify.... : " + vo);
		
		return mapper.update(vo);
	}

	@Override
	public Map<String, Double> getAvgCnt(String movieCd) {
		
		log.info("getAvgCnt.... : " + movieCd);
		
		return mapper.getAvgCntByMovieCd(movieCd);
	}

	
	// 회원탈퇴 - 유저의 모든 별점 평가 리스트 삭제
	@Override
	public boolean removeUserStarRatingList(String userid) {

		log.info("removeUserStarRatingList... " + userid);
		
		mapper.deleteUserStarRatingList(userid);
		
		return true;
	}
	
	
	// 특정 영화의 별점 평가 분포 조회 
	@Override
	public StarRatingAnalysisDTO getMovieStarRatingAnalysis(String movieCd) {
		
StarRatingAnalysisDTO  starRatingAnalysisDTO = null;
		
		starRatingAnalysisDTO = mapper.getMovieStarRatingAnalysis(movieCd);
		
		// null Check
		if(starRatingAnalysisDTO == null) {
			return starRatingAnalysisDTO;
		}
		
//			log.info(starRatingAnalysisDTO);
		
		List<CntByStarDTO> cntByStarDTOList = starRatingAnalysisDTO.getCntByStarDTOList();
		
//			log.info(cntByStarDTOList);
		
		String[] star = {"cnt0_5","cnt1_0","cnt1_5","cnt2_0","cnt2_5","cnt3_0","cnt3_5","cnt4_0","cnt4_5","cnt5_0"};
		
		ArrayList<String> starList = new ArrayList<String>();
		for(int i=0; i<star.length;i++) {
			starList.add(star[i]);
		}
//			log.info("starList: " + starList);

		
		ArrayList<String> currentStarList = new ArrayList<String>();
		for (CntByStarDTO cntByStarDTO: cntByStarDTOList) {
			currentStarList.add(cntByStarDTO.getStar());
			
		}
		ArrayList<Integer> currentCntList = new ArrayList<>();
		for (CntByStarDTO cntByStarDTO: cntByStarDTOList) {
			currentCntList.add(cntByStarDTO.getStarCnt());
			
		}
		
		
		 // > cntByStarList=[{star=cnt2_0, starCnt=4}, {star=cnt2_5, starCnt=1}, {star=cnt3_0, starCnt=4}, {star=cnt4_0, starCnt=7}, {star=cnt5_0, starCnt=2}])
//			log.info("currentStarList: " + currentStarList);
//			log.info("currentCntList: " + currentCntList);
		
		// removeAll() 중복 제거 이용
		starList.removeAll(currentStarList);
//			log.info(starList);
//			log.info(currentStarList);
		

		List<CntByStarDTO> newCntByStarDTOList = new ArrayList<CntByStarDTO>();
		CntByStarDTO cntByStarDTO = null;
		for(int i=0; i<star.length;i++) {
			cntByStarDTO = new CntByStarDTO();
			
			cntByStarDTO.setStar(star[i]);
			
			if(starList.contains(star[i])) {
				cntByStarDTO.setStarCnt(0);	
			} else if(currentStarList.contains(star[i])) {
				cntByStarDTO.setStarCnt(currentCntList.get(0));	
				currentCntList.remove(0);
			}
			
			newCntByStarDTOList.add(cntByStarDTO);
		}
		
		log.info(newCntByStarDTOList);
		
		
		starRatingAnalysisDTO.setCntByStarDTOList(newCntByStarDTOList); 
		log.info(starRatingAnalysisDTO);
		
		
		return starRatingAnalysisDTO;
	}

	

	/*
	 * @Override public int getCntByCommentCd(Long commentCd) {
	 * 
	 * log.info("getCntByCommentCd.... : " + commentCd);
	 * 
	 * return mapper.getCountByCommentCd(commentCd); }
	 */

	




	

}
