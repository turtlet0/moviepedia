package com.moviepedia.service;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.DoubleSummaryStatistics;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moviepedia.domain.CntByStarDTO;
import com.moviepedia.domain.CommentInfoDTO;
import com.moviepedia.domain.FavoriteGenreAnalysisDTO;
import com.moviepedia.domain.FavoriteGenreNationDTO;
import com.moviepedia.domain.FavoritePeopleDTO;
import com.moviepedia.domain.MovieWithStarDTO;
import com.moviepedia.domain.PeopleVO;
import com.moviepedia.domain.StarRatingAnalysisDTO;
import com.moviepedia.domain.TotalShowTmAnalysisDTO;
import com.moviepedia.domain.UserDTO;
import com.moviepedia.mapper.MemberMapper;
import com.moviepedia.mapper.PeopleMapper;
import com.moviepedia.mapper.UserMapper;


import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Service
@AllArgsConstructor
@Log4j
public class UserServiceImpl implements UserService {

	@Setter(onMethod_ = @Autowired)
	private UserMapper mapper;
	
	@Setter(onMethod_ = @Autowired)
	private PeopleMapper peopleMapper;
	

	

	
	
	
	
	// 별점 평가 분석 결과 조회
	@Override
	public StarRatingAnalysisDTO getStarRatingAnalysis(String userid) {
		
		StarRatingAnalysisDTO  starRatingAnalysisDTO = null;
		
		starRatingAnalysisDTO = mapper.getStarRatingAnalysis(userid);
		
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
		
		
		/* 평점 평균값에 따른 문구 */
		Double avgStarRating = starRatingAnalysisDTO.getStarRatingAvg();
		
		String resultMessage = "";
		
		if(avgStarRating >= 4.7) {
			resultMessage = "5점 뿌리는 '부처님급' 아량의 소유자";
		} else if(avgStarRating >= 4.4){
			resultMessage = "영화면 마냥 다 좋은 '천사급' 착한 사람❤";
		} else if(avgStarRating >= 4.0){
			resultMessage = "남들보다 별점을 조금 후하게 주는 '인심파'";
		} else if(avgStarRating >= 3.9 ){
			resultMessage = "영화를 정말로 즐길 줄 아는 '현명파'";
		} else if(avgStarRating >= 3.8){
			resultMessage = "편식 없이 영화를 골고루 보는 '균형파'";
		} else if(avgStarRating >= 3.7){
			resultMessage = "대중의 평가에 잘 휘둘리지 않는 '지조파' ";
		} else if(avgStarRating >= 3.6){
			resultMessage = "영화 평가에 있어 주관이 뚜렷한 '소나무파'";
		} else if(avgStarRating >= 3.4){
			resultMessage = "대체로 영화를 즐기지만 때론 혹평도 마다치 않는 '이성파'";
		} else if(avgStarRating >= 3.3){
			resultMessage = "영화 평가에 상대적으로 깐깐한 '깐새우파' (3.3)";
		} else if(avgStarRating >= 3.1){
			resultMessage = "영화를 남들보다 진지하고 비판적으로 보는 '지성파'";
		} else if(avgStarRating >= 2.9){
			resultMessage = "영화를 대단히 냉정하게 평가하는 '냉장고파'";
		} else if(avgStarRating >= 2.5){
			resultMessage = "웬만해서는 호평을 하지 않는 매서운 '독수리파'";
		} else if(avgStarRating >= 2.3){
			resultMessage = "별점을 대단히 짜게 주는 한줌의 '소금' 같은 분 :)";
		} else if(avgStarRating >= 1.9){
			resultMessage = "웬만해선 영화에 만족하지 않는 '헝그리파'";
		} else {
			resultMessage = "세상 영화들에 불만이 많으신 '개혁파'";
		}
		
		starRatingAnalysisDTO.setResultMessage(resultMessage);
		
		return starRatingAnalysisDTO;
	}
	
	
	
	
	// 선호 배우 조회
	@Override
	public List<FavoritePeopleDTO> getFavoriteActorList_v1(String userid) {
		
		log.info("getFavoriteActorList.... " + userid);
		
		long startTime1 = System.currentTimeMillis();
		
		String roleNm = "배우";
		
		List<FavoritePeopleDTO> favoriteActorDTOList = mapper.getFavoritePeopleScore(userid, roleNm);
		
		
		
/* --------------------------------------- 기존 ----------------------------------------------------- */	
	
		
		// 저장용 List
		List<Double> avgValueList = new ArrayList<Double>();
		List<Long> cntValueList = new ArrayList<Long>();
		
		// 배우의 영화 역할 비중 값Value의 평균값과 개수(출연작품 개수.(DB에 저장되어있는 영화에 한함))를 
		// 정규화하여 가중평균해 구한 score2와
		// mapper.getFavoriteActorList을 통해 구한 score(1)과 가중평균해 최종 score를 구함
		for(int i=0; i<favoriteActorDTOList.size();i++) {
			FavoritePeopleDTO favoriteActorDTO = favoriteActorDTOList.get(i);
			log.info(favoriteActorDTO);
			
			
			List<FavoritePeopleDTO> actorFilmoList = mapper.getActorFilmoList(favoriteActorDTO.getPeopleCd());
			
			List<Integer> actorRoleImportanceValueList = new ArrayList<Integer>();
			for(FavoritePeopleDTO actorFilmo : actorFilmoList) {
//				log.info(actorFilmo);
				
				int actorRoleImportanceValue = 0;
				if(mapper.getActorRoleImportanceValue(actorFilmo.getMovieCd(), actorFilmo.getPeopleNm()) != null) {
					actorRoleImportanceValue = mapper.getActorRoleImportanceValue(actorFilmo.getMovieCd(), actorFilmo.getPeopleNm());
					
					if(actorRoleImportanceValue != 0) {
						
						actorRoleImportanceValueList.add(actorRoleImportanceValue);
					}
				}
				
				
			}
		
		        
			log.info(actorRoleImportanceValueList);
			
			IntSummaryStatistics valueListStatistics = actorRoleImportanceValueList
					.stream()
					.mapToInt(value -> value)
					.summaryStatistics();
			Double avgValue = valueListStatistics.getAverage();
			Long cntValue = valueListStatistics.getCount();
			
//			log.info(avgValue);
//			log.info(cntValue);
			
			avgValueList.add(avgValue);
			cntValueList.add(cntValue);
//			log.info("====================================");
			
		}
		log.info("avgValueList: " + avgValueList);
		log.info("cntValueList: " + cntValueList);
		
	// 정규화
		
		DoubleSummaryStatistics avgValueListStatistics = avgValueList
				.stream()
				.mapToDouble(avgValue -> avgValue)
				.summaryStatistics();
		
		Double maxAvgValue = avgValueListStatistics.getMax();
		Double minAvgValue = avgValueListStatistics.getMin();
		
		for(int i=0; i<avgValueList.size();i++) {
			
		}
		
		
		LongSummaryStatistics cntValueListStatistics = cntValueList
				.stream()
				.mapToLong(cntValue -> cntValue)
				.summaryStatistics();
		
		Long maxCntValue = cntValueListStatistics.getMax();
		Long minCntValue = cntValueListStatistics.getMin();
//		log.info("maxCntValue: " + maxCntValue);
//		log.info("minCntValue: " + minCntValue);
		
		for(int i=0; i<favoriteActorDTOList.size();i++) {
			
			FavoritePeopleDTO favoriteActorDTO = favoriteActorDTOList.get(i);
			
			log.info("////////////////" + favoriteActorDTO);
			Double scaleAvgValue = (avgValueList.get(i) - minAvgValue) / (maxAvgValue - minAvgValue);
//			log.info("scaleAvgValue: " + scaleAvgValue);
			
			Double scaleCntValue =   ((double) (cntValueList.get(i) - minCntValue) / (maxCntValue - minCntValue));
//			log.info("scaleCntValue: " + scaleCntValue);
			
			Double score2 = scaleAvgValue * 0.7 + scaleCntValue * 0.3;
			
		
			Double score1 = favoriteActorDTO.getScore1();
			
			int finalScore = (int) Math.round((score1 * 0.75 + score2 * 0.25) * 100);
			
			log.info(score1 + "/" + score2 + "/" + finalScore);
			
			favoriteActorDTO.setFinalScore(finalScore);
		}
		log.info(favoriteActorDTOList);
		
		
//		log.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		
					
				

		
		favoriteActorDTOList = favoriteActorDTOList
				.stream()
				.sorted(Comparator.comparing(FavoritePeopleDTO:: getFinalScore).reversed()) /* getFinalScore 기준으로 List 정렬 / 뒤에서부터 삭제하기 위해 reversed() */
				.collect(Collectors.toList());
		
		
		
		log.info(favoriteActorDTOList);
		for(int i=favoriteActorDTOList.size()-1;i>=10;i--) { // 중간 요소 삭제시 인덱스 1씩 당겨지므로 뒤에서부터 삭제해 연산속도 up
			favoriteActorDTOList.remove(i);
		}
		favoriteActorDTOList.forEach(favoriteActorDTOi -> log.info(favoriteActorDTOi.getPeopleNm()+"/"+favoriteActorDTOi.getScaleCntStarRating()+"/"+favoriteActorDTOi.getScaleAvgStarRating()
				+"/"+favoriteActorDTOi.getScaleRatioRatingByCntFilmos()+"/"+favoriteActorDTOi.getScaleAvgValue()+"/"+favoriteActorDTOi.getScaleCntValue()+"/"+favoriteActorDTOi.getFinalScore()));
		
		
		
		
		long endTime1 = System.currentTimeMillis();
		 System.out.println("!!실행시간(초.0f) : " + (endTime1 - startTime1) / 1000.0f + "초");		
		

		 
/* --------------------------------------- 기존 ----------------------------------------------------- */	
		
		// 배우 대표작 조회
//		favoriteActorDTO = null;
//		for(int i=0; i<10; i++) {
//			favoriteActorDTO = favoriteActorDTOList.get(i);
//			String peopleCd = favoriteActorDTO.getPeopleCd();
//			/* 대표작 출력 */
//			// DB 대표작 없는 경우 || 있으나 조회일자가 people updateDate보다 이전일때 재갱신 
//			if(favoriteActorDTO.getRepMovieList() == null || favoriteActorDTO.getRepMovieListDate().before(favoriteActorDTO.getUpdateDate())) {
////			if(true) {
//				peopleMapper.updateActorRepMovieList(peopleCd);			
//				
//				Map<String, Object> repMovieListMap = peopleMapper.getRepMovieList(peopleCd);			
//				favoriteActorDTO.setRepMovieList((String) repMovieListMap.get("repMovieList"));
//				favoriteActorDTO.setRepMovieListDate((Date) repMovieListMap.get("repMovieListDate"));
//				
////				log.info(favoriteActorDTO.getPeopleNm()+"/"+favoriteActorDTO.getRepMovieList()+"/"+favoriteActorDTO.getRepMovieListDate());
//			}
//		
//		}
//		
//		
//	// 선호 배우 리스트 -> Member 테이블에 저장
//		StringBuilder SBFavoriteActorList = new StringBuilder();
//		for (int i=0;i<favoriteActorDTOList.size();i++) {
//
//			SBFavoriteActorList.append(favoriteActorDTOList.get(i).getPeopleCd());
//			if(i != favoriteActorDTOList.size() -1) {
//				SBFavoriteActorList.append(",");
//			}
//		}
// 		String favoriteActorList = SBFavoriteActorList.toString();
//		
// 		mapper.updateFavoritePeopleGenre(userid, roleNm, favoriteActorList);
// 		
// 		
// 		
// 		log.info("===========================");
// 		favoriteActorDTOList.forEach(favoriteActorDTOi -> log.info(favoriteActorDTOi));
// 		log.info("===========================");
 		
		
		return favoriteActorDTOList;
	}
	
	
	// 선호 배우 조회
	@Override
	public List<FavoritePeopleDTO> getFavoriteActorList_v2(String userid) {
		
		log.info("getFavoriteActorList.... " + userid);
		
		long startTime2 = System.currentTimeMillis();
		
		String roleNm = "배우";
		
		List<FavoritePeopleDTO> favoriteActorDTOList = mapper.getFavoritePeopleScore(userid, roleNm);
		
		
		
		
		/* --------------------------------------- 개선 ----------------------------------------------------- */	
		/*
		 * 정리
		 * mybatis array 이용. 원시타입 _
		 * https://blog.naver.com/auheia/80211282884
		 * https://java119.tistory.com/45
		 * 
		 * array 배열 - String[], int[]... 크기 고정 VS list 리스트 : ArrayList... 크기 가변, String, Integer 등 wrapper 객체만 가능
		 * https://wayhome25.github.io/cs/2017/04/17/cs-18-1/
		 * https://seokho-j0308.tistory.com/17
		 * https://m.blog.naver.com/scyan2011/221683417752
		 * 
		 * array 최대최소값
		 * https://apiclass.tistory.com/entry/190627-%EB%B0%B0%EC%97%B4-sumavg-max-min-%EA%B5%AC%ED%95%98%EA%B8%B0
		 * 
		 * for문 vs stream() 성능 : int 등 기본 타입은 for문이 압도적 / Integer 등 객체 ㅌ타입은 stream()도 비슷
		 * https://jypthemiracle.medium.com/java-stream-api%EB%8A%94-%EC%99%9C-for-loop%EB%B3%B4%EB%8B%A4-%EB%8A%90%EB%A6%B4%EA%B9%8C-50dec4b9974b
		 * https://futurecreator.github.io/2018/08/26/java-8-streams/
		 * 
		 * for VS foreach
		 * https://siyoon210.tistory.com/99
		 * https://yangbongsoo.gitbook.io/study/undefined/for
		 * 
		 * 정렬. array.sort vs collection.sort 성능
		 * https://codechacha.com/ko/java-sort-list/
		 * https://sabarada.tistory.com/138
		 * https://laugh4mile.tistory.com/175
		 * 
		 */
		
		/* 기존 2초대 -> 개선 0.2초대 */
		
		
		
		
		int favoriteActorDTOListReturnSize = 20;
		
		int[] cntActorRoleImportanceArr = new int[favoriteActorDTOListReturnSize];
		double[] avgActorRoleImportanceArr = new double[favoriteActorDTOListReturnSize];
		
		/* 사용x-바로 VO에 담음)정규화 결과 담을 배열 */
//		double[] scaleCntValueArr = new double[favoriteActorDTOListReturnSize];
//		double[] scaleAvgValueArr = new double[favoriteActorDTOListReturnSize];
		
		
		int favoriteActorDTOListLength = favoriteActorDTOList.size();
		for (int i = 0; i < favoriteActorDTOListLength; i++) {
			
			Map<String,Double> actorRoleImportance = mapper.getActorRoleImportance(favoriteActorDTOList.get(i).getPeopleCd());
			
			//	Error) java.lang.ClassCastException: java.math.BigDecimal cannot be cast to java.lang.Double
			// https://wogus789789.tistory.com/m/302
			// DB 컬럼타입 number일때 발생하는 형변환 오류
			// 우선 Stirng.valueOf()로 String 변환 -> Integer.parseint, Double.parseDboule 등으로 java 타입으로 변환
			int cntActorRoleImportance =  (int) Double.parseDouble(String.valueOf(actorRoleImportance.get("cntActorRoleImportance")));
			double avgActorRoleImportance =  Double.parseDouble(String.valueOf(actorRoleImportance.get("avgActorRoleImportance")));
			
//			log.info(cntActorRoleImportance);
//			log.info(avgActorRoleImportance);
			
			cntActorRoleImportanceArr[i] = cntActorRoleImportance;
			avgActorRoleImportanceArr[i] = avgActorRoleImportance;
		}	
		
		/* 정규화 */
		// cntValue
		int maxCntValue = cntActorRoleImportanceArr[0];
		int minCntValue = cntActorRoleImportanceArr[0];
		
		// 최대값, 최소값 구하기
		for(int i=1; i<favoriteActorDTOListReturnSize; i++) { // 1부터 시작 : 앞서 max, min값을 0 index의 값으로 초기화했기때문
			// 최대값
			if(cntActorRoleImportanceArr[i] > maxCntValue) {
				maxCntValue = cntActorRoleImportanceArr[i];
			}
			// 최솟값
			else if (cntActorRoleImportanceArr[i] < minCntValue) {
				minCntValue = cntActorRoleImportanceArr[i];
			}
		}
		// avgValue
		double maxAvgValue = avgActorRoleImportanceArr[0];
		double minAvgValue = avgActorRoleImportanceArr[0];
		
		// 최대값, 최소값 구하기
		for(int i=1; i<favoriteActorDTOListReturnSize; i++) { // 1부터 시작 : 앞서 max, min값을 0 index의 값으로 초기화했기때문
			// 최대값
			if(avgActorRoleImportanceArr[i] > maxAvgValue) {
				maxAvgValue = avgActorRoleImportanceArr[i];
			}
			// 최솟값
			else if (avgActorRoleImportanceArr[i] < minAvgValue) {
				minAvgValue = avgActorRoleImportanceArr[i];
			}
		}
		
		
		// 최대-최소 정규화 	
		Double scaleCntValue = null;
		Double scaleAvgValue = null;
		
		// 특정 컬럼의 데이터 개수 1개이거나 모든 데이터 값이 같은 경우 scale 결과값 1 통일 
		// -> 최종 순위는 정규화 결과값의 가중평균한 결과로 정렬되므로 scale값이 얼마든 최종 순위엔 영향 없음
		if(maxCntValue == minCntValue) {
			scaleCntValue = 1.0;
		}
		if(maxAvgValue == minAvgValue) {
			scaleAvgValue = 1.0;
		}
		
		FavoritePeopleDTO favoriteActorDTO = null;
		Double score2 = null;
		Double score1 = null;
		for(int i=0; i<favoriteActorDTOListReturnSize; i++) {
			favoriteActorDTO = favoriteActorDTOList.get(i);
			
			
//			log.info(cntActorRoleImportanceArr[i] - minCntValue);
//			log.info(maxCntValue - minCntValue);
			scaleCntValue = ((double) (cntActorRoleImportanceArr[i] - minCntValue) / (maxCntValue - minCntValue)); // int로 계산되지 않도록 double 형변환 필수
//			log.info(scaleCntValue);
//			scaleAvgValue = (avgActorRoleImportanceArr[i] - minAvgValue) / (maxAvgValue - minAvgValue);
			scaleAvgValue = (maxAvgValue - avgActorRoleImportanceArr[i]) / (maxAvgValue - minAvgValue);  // Avg값은 최소값이 1이 되도록.
			
			/* 가중 평균 */
			double scaleCntStarRating = favoriteActorDTO.getScaleCntStarRating();
			double scaleAvgStarRating = favoriteActorDTO.getScaleAvgStarRating();
			double scaleRatioRatingByCntFilmos = favoriteActorDTO.getScaleRatioRatingByCntFilmos();
			
			favoriteActorDTO.setScaleAvgValue(scaleAvgValue);
			favoriteActorDTO.setScaleCntValue(scaleCntValue);
			
			score2 = scaleAvgValue * 0.7 + scaleCntValue * 0.3;
			
//			score1 = favoriteActorDTO.getScore1();
			
//			int finalScore = (int) Math.round((score1 * 0.75 + score2 * 0.25) * 100);
			int finalScore = (int) Math.round((scaleCntStarRating * 0.35 + scaleAvgStarRating * 0.15 +
					scaleRatioRatingByCntFilmos * 0.10 + scaleAvgValue * 0.35 + scaleCntValue * 0.05) * 100);
			
//			log.info(favoriteActorDTO.getPeopleNm()+"/"+ score1 + "/" + score2 + "/" + finalScore);
//			log.info(favoriteActorDTO.getPeopleNm()+"/"+scaleCntStarRating+"/"+scaleAvgStarRating+"/"+scaleRatioRatingByCntFilmos+"/"+scaleAvgValue+"/"+scaleCntValue+"/"+finalScore);
			
			favoriteActorDTO.setFinalScore(finalScore);
		}
		log.info(favoriteActorDTOList);
		
		
		
		
		
		
		
		
		favoriteActorDTOList = favoriteActorDTOList
				.stream()
				.sorted(Comparator.comparing(FavoritePeopleDTO:: getFinalScore).reversed()) /* getFinalScore 기준으로 List 정렬 / 뒤에서부터 삭제하기 위해 reversed() */
				.collect(Collectors.toList());
		
		
		
		log.info(favoriteActorDTOList);
		for(int i=favoriteActorDTOList.size()-1;i>=10;i--) { // 중간 요소 삭제시 인덱스 1씩 당겨지므로 뒤에서부터 삭제해 연산속도 up
			favoriteActorDTOList.remove(i);
		}
		
		favoriteActorDTOList.forEach(favoriteActorDTOi -> log.info(favoriteActorDTOi.getPeopleNm()+"/"+favoriteActorDTOi.getScaleCntStarRating()+"/"+favoriteActorDTOi.getScaleAvgStarRating()
		+"/"+favoriteActorDTOi.getScaleRatioRatingByCntFilmos()+"/"+favoriteActorDTOi.getScaleAvgValue()+"/"+favoriteActorDTOi.getScaleCntValue()+"/"+favoriteActorDTOi.getFinalScore()));
		
		
		
		long endTime2 = System.currentTimeMillis();
		System.out.println("!!실행시간(초.0f) : " + (endTime2 - startTime2) / 1000.0f + "초");		
		
		
/* --------------------------------------- 개선 ----------------------------------------------------- */						
		
		// 배우 대표작 조회
//		favoriteActorDTO = null;
//		for(int i=0; i<10; i++) {
//			favoriteActorDTO = favoriteActorDTOList.get(i);
//			String peopleCd = favoriteActorDTO.getPeopleCd();
//			/* 대표작 출력 */
//			// DB 대표작 없는 경우 || 있으나 조회일자가 people updateDate보다 이전일때 재갱신 
//			if(favoriteActorDTO.getRepMovieList() == null || favoriteActorDTO.getRepMovieListDate().before(favoriteActorDTO.getUpdateDate())) {
////			if(true) {
//				peopleMapper.updateActorRepMovieList(peopleCd);			
//				
//				Map<String, Object> repMovieListMap = peopleMapper.getRepMovieList(peopleCd);			
//				favoriteActorDTO.setRepMovieList((String) repMovieListMap.get("repMovieList"));
//				favoriteActorDTO.setRepMovieListDate((Date) repMovieListMap.get("repMovieListDate"));
//				
////				log.info(favoriteActorDTO.getPeopleNm()+"/"+favoriteActorDTO.getRepMovieList()+"/"+favoriteActorDTO.getRepMovieListDate());
//			}
//		
//		}
//		
//		
//	// 선호 배우 리스트 -> Member 테이블에 저장
//		StringBuilder SBFavoriteActorList = new StringBuilder();
//		for (int i=0;i<favoriteActorDTOList.size();i++) {
//
//			SBFavoriteActorList.append(favoriteActorDTOList.get(i).getPeopleCd());
//			if(i != favoriteActorDTOList.size() -1) {
//				SBFavoriteActorList.append(",");
//			}
//		}
// 		String favoriteActorList = SBFavoriteActorList.toString();
//		
// 		mapper.updateFavoritePeopleGenre(userid, roleNm, favoriteActorList);
// 		
// 		
// 		
// 		log.info("===========================");
// 		favoriteActorDTOList.forEach(favoriteActorDTOi -> log.info(favoriteActorDTOi));
// 		log.info("===========================");
		
		
		return favoriteActorDTOList;
	}
	
	
	
	// 선호 배우 조회
	@Override
	public List<FavoritePeopleDTO> getFavoriteActorList_new(String userid) {
		
		log.info("getFavoriteActorList_new.... " + userid);
		
		long startTime3 = System.currentTimeMillis();
		
		String roleNm = "배우";
		
		
		List<FavoritePeopleDTO> favoriteActorDTOList = mapper.getFavoriteActorScore(userid);
		
		log.info(favoriteActorDTOList);
		
		long endTime3 = System.currentTimeMillis();
		System.out.println("!!실행시간(초.0f) : " + (endTime3 - startTime3) / 1000.0f + "초");		
		
		
		/* --------------------------------------- 개선 ----------------------------------------------------- */				
		
		
		
//		favoriteActorDTOList = favoriteActorDTOList
//				.stream()
//				.sorted(Comparator.comparing(FavoritePeopleDTO:: getFinalScore).reversed()) /* getFinalScore 기준으로 List 정렬 / 뒤에서부터 삭제하기 위해 reversed() */
//				.collect(Collectors.toList());
		
//		log.info(favoriteActorDTOList);
//		for(int i=favoriteActorDTOList.size()-1;i>=10;i--) { // 중간 요소 삭제시 인덱스 1씩 당겨지므로 뒤에서부터 삭제해 연산속도 up
//			favoriteActorDTOList.remove(i);
//		}
		favoriteActorDTOList.forEach(favoriteActorDTOi -> 
			log.info(favoriteActorDTOi.getPeopleNm()+"/"+favoriteActorDTOi.getFinalScore()));
		
		
		
		// 배우 대표작 조회 -> people 테이블 조회/저장
		FavoritePeopleDTO favoriteActorDTO = null;
		for(int i=0; i<10; i++) {
			favoriteActorDTO = favoriteActorDTOList.get(i);
			String peopleCd = favoriteActorDTO.getPeopleCd();
			/* 대표작 출력 */
			// DB 대표작 없는 경우 || 있으나 조회일자가 people updateDate보다 이전일때 재갱신 
			if(favoriteActorDTO.getRepMovieList() == null || favoriteActorDTO.getRepMovieListDate().before(favoriteActorDTO.getUpdateDate())) {
//			if(true) {
				peopleMapper.updateActorRepMovieList(peopleCd);			
				
				Map<String, Object> repMovieListMap = peopleMapper.getRepMovieList(peopleCd);			
				favoriteActorDTO.setRepMovieList((String) repMovieListMap.get("repMovieList"));
				favoriteActorDTO.setRepMovieListDate((Date) repMovieListMap.get("repMovieListDate"));
				
//				log.info(favoriteActorDTO.getPeopleNm()+"/"+favoriteActorDTO.getRepMovieList()+"/"+favoriteActorDTO.getRepMovieListDate());
			}
		
		}
		
		
	// 선호 배우 리스트 -> Member 테이블에 저장
		StringBuilder SBFavoriteActorList = new StringBuilder();
		for (int i=0;i<favoriteActorDTOList.size();i++) {

			SBFavoriteActorList.append(favoriteActorDTOList.get(i).getPeopleCd());
			if(i != favoriteActorDTOList.size() -1) {
				SBFavoriteActorList.append(",");
			}
		}
 		String favoriteActorList = SBFavoriteActorList.toString();
		
 		mapper.updateFavoritePeopleGenre(userid, roleNm, favoriteActorList);
 		
 		
 		
 		log.info("===========================");
 		favoriteActorDTOList.forEach(favoriteActorDTOi -> log.info(favoriteActorDTOi));
 		log.info("===========================");
		
		
		return favoriteActorDTOList;
	}
	
	
	
	// 선호 감독 조회
	@Override
	public List<FavoritePeopleDTO> getFavoriteDirectorList(String userid) {
		
		log.info("getFavoriteDirectorList... " + userid);
		String roleNm = "감독";
		
		List<FavoritePeopleDTO> favoriteDirectorDTOList = mapper.getFavoritePeopleScore(userid, roleNm);
		
		// 선호 감독 리스트 -> member 테이블에 저장
//		int ranIdx = (int) (Math.random() * 3) + 3;
//		if(favoriteDirectorDTOList.size() -1 < ranIdx) {
//			ranIdx = favoriteDirectorDTOList.size() -1;
//		}
//		log.info("favoriteDirectorDTOList.size(): "+favoriteDirectorDTOList.size()+" / ranIdx : "+ ranIdx );
		
		StringBuilder SBFavoriteDirectorList = new StringBuilder();
		for (int i=0;i<favoriteDirectorDTOList.size();i++) {

			SBFavoriteDirectorList.append(favoriteDirectorDTOList.get(i).getPeopleCd());
			if(i != favoriteDirectorDTOList.size() -1) {
				SBFavoriteDirectorList.append(",");
			}
		}
 		String favoriteDirectorList = SBFavoriteDirectorList.toString();
		
 		mapper.updateFavoritePeopleGenre(userid, roleNm, favoriteDirectorList);
 		
 		
 	// 감독 대표작 조회
 		FavoritePeopleDTO favoriteDirectorDTO = null;
 		int favoriteDirectorDTOListLength = favoriteDirectorDTOList.size();
		for(int i=0; i<favoriteDirectorDTOListLength; i++) {
			favoriteDirectorDTO = favoriteDirectorDTOList.get(i);
			String peopleCd = favoriteDirectorDTO.getPeopleCd();
			/* 대표작 출력 */
			// DB 대표작 없는 경우 || 있으나 조회일자가 people updateDate보다 이전일때 재갱신 
			if(favoriteDirectorDTO.getRepMovieList() == null || favoriteDirectorDTO.getRepMovieListDate().before(favoriteDirectorDTO.getUpdateDate())) {
// 			if(true) {
				peopleMapper.updateDirectorRepMovieList(peopleCd);
				
				Map<String, Object> repMovieListMap = peopleMapper.getRepMovieList(peopleCd);			
				favoriteDirectorDTO.setRepMovieList((String) repMovieListMap.get("repMovieList"));
				favoriteDirectorDTO.setRepMovieListDate((Date) repMovieListMap.get("repMovieListDate"));
				
	// 			log.info(favoriteDirectorDTO.getPeopleNm()+"/"+favoriteDirectorDTO.getRepMovieList()+"/"+favoriteDirectorDTO.getRepMovieListDate());
			}
		
		}
		
		log.info("===========================");
		favoriteDirectorDTOList.forEach(favoriteDirectorDTOi -> log.info(favoriteDirectorDTOi));
 		log.info("===========================");
 		
		return favoriteDirectorDTOList;
	}

	
	
	// 선호 장르 조회
	@Override
	public FavoriteGenreAnalysisDTO  getFavoriteGenreList(String userid) {
		log.info("getFavoriteGenreList... " + userid);
		
		// return 변수
		FavoriteGenreAnalysisDTO favoriteGenreAnalysisDTO = new FavoriteGenreAnalysisDTO();
		
		
		List<String> movieCdList = mapper.getMovieCdForFavoriteGenreNation(userid);
		
		List<FavoriteGenreNationDTO> favoriteGenreList = mapper.getFavoriteGenreScore(userid, movieCdList);
		
		favoriteGenreAnalysisDTO.setFavoriteGenreDTOList(favoriteGenreList);
		
		/* 1순위 선호 장르에 따른 출력 문구 */
		String firstGenre = favoriteGenreList.get(0).getGenre();
		
		String resultMessage = "";
		
		// 추후 2,3순위 고려한 확장 대비 case문 대신 if문 사용
			// 1위와 2,3위 점수차 크지 않는 경우 1순위 -> 2,3순위에 따른 추가 메세지 구상
		if(firstGenre.equals("판타지")) {
			resultMessage = "현실과 떨어진 판타지를 좇는 순수한 감성";
		} else if(firstGenre.equals("범죄")) {
			resultMessage = "잊을 수 없는 캐릭터와 스토리는 현실에 기반하죠";
		} else if(firstGenre.equals("멜로/로맨스")) {
			resultMessage = "이 시대의 달콤한 로맨티스트";
		} else if(firstGenre.equals("코미디")) {
			resultMessage = "유쾌한 사람이 코미디를 좋아합니다";
		} else if(firstGenre.equals("액션")) {
			resultMessage = "근사한 액션은 심장을 뛰게 하죠";
		} else if(firstGenre.equals("다큐멘터리")) {
			resultMessage = "현실 속 진실을 밝혀줄 힘이 필요해요";
		} else if(firstGenre.equals("어드벤처")) {
			resultMessage = "상상과 모험, 일탈을 즐기는 낭만파";
		} else if(firstGenre.equals("애니메이션")) {
			resultMessage = "애니메이션을 좋아하지만 덕후는 아니라능";
		} else if(firstGenre.equals("SF")) {
			resultMessage = "로보트와 외계인은 다정한 내 친구";
		} else if(firstGenre.equals("공포(호러)")) {
			resultMessage = "좀비든 귀신이든 무섭지 않아요";
		} else if(firstGenre.equals("전쟁")) {
			resultMessage = "강하고 스펙타클한 영화를 좋아하는 파워 넘치는 영화인";
		} else if(firstGenre.equals("미스터리")) {
			resultMessage = "심장은 쫄깃하게, 반전은 깜놀하게";
		} else if(firstGenre.equals("사극")) {
			resultMessage = "과거를 통해 미래를 봅니다";
		} else if(firstGenre.equals("뮤지컬") || firstGenre.equals("공연")) {
			resultMessage = "눈과 귀가 즐거운 영화가 끌려요";
		} else if(firstGenre.equals("가족")) {
			resultMessage = "영화가 주는 감동과 여운을 즐겨요";
		} else if(firstGenre.equals("서부극(웨스턴)")) {
			resultMessage = "거친 황야를 누비고 싶어요";
		} else if(firstGenre.equals("드라마")) {
			resultMessage = "인생은 역시 한편의 드라마";
		} else if(firstGenre.equals("스릴러")) {
			resultMessage = "역시 손에 땀을 쥐게 하는 영화가 최고죠";
		} else if(firstGenre.equals("기타")) {
			resultMessage = "오직 영화로만 정의내릴 수 있는 것들이 존재하죠";
		} 
		
		favoriteGenreAnalysisDTO.setResultMessage(resultMessage);
		
		
		// 선호 장르 리스트 -> Member 테이블에 저장
		StringBuilder SBFavoriteGenreList = new StringBuilder();
		for (int i=0;i<favoriteGenreList.size();i++) {

			SBFavoriteGenreList.append(favoriteGenreList.get(i).getGenre());
			if(i != favoriteGenreList.size() -1) {
				SBFavoriteGenreList.append(",");
			}
		}
 		String favoriteGenreNmList = SBFavoriteGenreList.toString();
		
 		mapper.updateFavoritePeopleGenre(userid, "장르", favoriteGenreNmList);
		 		
		 		
		
		return favoriteGenreAnalysisDTO;
	}
	
	@Override
	public FavoriteGenreAnalysisDTO  getFavoriteGenreList2(String userid) {
		return null;
		
	}

	// 선호 국가 조회
	@Override
	public List<FavoriteGenreNationDTO> getFavoriteNationList(String userid) {
		log.info("getFavoriteNationList... " + userid);
		
		List<String> movieCdList = mapper.getMovieCdForFavoriteGenreNation(userid);
		
		List<FavoriteGenreNationDTO> favoriteNationList = mapper.getFavoriteNationScore(userid, movieCdList);
		
		favoriteNationList.forEach(favoriteNation -> log.info(favoriteNation));
		
		return favoriteNationList;

	}

	// 유저 총 감상 시간 조회
	@Override
	public TotalShowTmAnalysisDTO getTotalShowTm(String userid) {
		
		log.info("getTotalShowTm....." + userid);
		
		// return 변수
		TotalShowTmAnalysisDTO totalShowTmAnalysisDTO = mapper.getTotalShowTm(userid);

		
		int totalShowTm = totalShowTmAnalysisDTO.getTotalShowTm();
		
		String resultMessage = "";
		
		if(totalShowTm < 50) {
			resultMessage = "평가하는거 나름 되게 재밌는데 어서 더 평가를...";
		} else if(totalShowTm < 150) {
			resultMessage = "영화 본 시간으로 아직 평균에 못 미쳐요..";
		} else if(totalShowTm < 280) {
			resultMessage = "상위 30%만큼 영화를 보셨어요. 그래도 상위권!";
		} else if(totalShowTm < 330) {
			resultMessage = "극장에 50만원쯤 쓰셨겠어요. 영화에 쓰는 돈은 사랑입니다.";
		} else if(totalShowTm < 400) {
			resultMessage = "이제 자기만의 영화보는 관점이 생기셨을 거예요.";
		} else if(totalShowTm < 500) {
			resultMessage = "인생의 3주는 순수하게 영화 본 시간. 대단합니다.";
		} else if(totalShowTm < 720) {
			resultMessage = "일주일에 두 편씩 1년이면 상위 5% 매니아예요.";
		} else if(totalShowTm < 810) {
			resultMessage = "단언컨대 이 정도면 어디 가서 영화로 꿀리진 않을겁니다.";
		} else if(totalShowTm < 1000) {
			resultMessage = " 상위 5% 진입! 공식적인 영화인입니다.";
		} else if(totalShowTm < 1100) {
			resultMessage = "대..대단합니다. 순수 영화 본 시간 1000시간 돌파!";
		} else if(totalShowTm < 1200) {
			resultMessage = "영화 본 시간으로 상위 3%! 왓챠가 보증하는 영화 내공인!";
		} else if(totalShowTm < 1300) {
			resultMessage = "이제 당신에게 영화는 문화 그 이상";
		} else if(totalShowTm < 1470) {
			resultMessage = "살면서 순수하게 영화 본 시간 50일 돌파! 상상이 되세요?";
		} else if(totalShowTm < 1580) {
			resultMessage = "이 정도면 영화에서 인생을 통째로 배웠을 수준.";
		} else if(totalShowTm < 1640) {
			resultMessage = "영화를 공부하는 영화학도가 보통 이 정도 본답니다.";
		} else if(totalShowTm < 1750) {
			resultMessage = "상위 0.1%의 고지가 저 앞에 보여요.";
		} else if(totalShowTm < 1850) {
			resultMessage = "상위 0.1%의 무비피디아 보증 '1등급 영화 내공인'";
		} else if(totalShowTm < 2400) {
			resultMessage = "영화 1000작을 넘게 본 영화 '아마추어 영화인'";
		} else if(totalShowTm < 2750) {
			resultMessage = "100일 동안 영화 본 '웅녀급 영화인'";
		} else if(totalShowTm < 3000) {
			resultMessage = "영화 감독을 꿈꿀지 모를 '영화 프로페셔널'";
		} else if(totalShowTm < 4000) {
			resultMessage = "3000시간을 영화 본 '상위 0.03%의 매니아'";
		} else if(totalShowTm < 4500) {
			resultMessage = "상위 0.01%에 꼽히는 '베테랑 사회인'";
		} else if(totalShowTm < 4800) {
			resultMessage = "국내에 몇 안되는 '영화 Expert'";
		} else if(totalShowTm < 5000) {
			resultMessage = "영화가 즉 삶 그 자체인 '영화 장인'";
		} else {
			resultMessage = "경지에 도달한 'Film Master'";
		}
		
		totalShowTmAnalysisDTO.setResultMessage(resultMessage);
		
		log.info("totalShowTmAnalysisDTO: " + totalShowTmAnalysisDTO);
		
		return totalShowTmAnalysisDTO;
	}

	
	// 유저 작성한 코멘트 리스트 조회
	@Override
	public List<CommentInfoDTO> getUserCommentList(String randomString, int orderBy, int currentSqnc, int additionalCnt, String loginUserid) {
		
		log.info("getUserCommentList..." + randomString+  "/" + orderBy + "/" + currentSqnc + "/" + additionalCnt +"/" + loginUserid);

		List<CommentInfoDTO> userCommentList = mapper.getUserCommentList(randomString, orderBy, currentSqnc, additionalCnt, loginUserid);
		
		userCommentList.forEach(userComment -> log.info(userComment));
		
		return userCommentList;
	}


	@Override
	public List<MovieWithStarDTO> getUserMovieList(String randomString, int orderBy, int currentSqnc, int additionalCnt) {
		
		log.info("getUserMovieList..." + randomString);

		List<MovieWithStarDTO> userMovieList = mapper.getUserMovieList(randomString, orderBy, currentSqnc, additionalCnt);
		
		userMovieList.forEach(userMovie -> log.info(userMovie));
		
		return userMovieList;
	}


	// 유저 정보 조회
	@Override
	public UserDTO getUserInfo(String randomString) {
		log.info("getUserInfo... " + randomString);
		
		UserDTO userDTO = mapper.getUserInfoByRandomString(randomString);
		
		log.info(userDTO);
		
		return userDTO;
	}
	
	
	

}
