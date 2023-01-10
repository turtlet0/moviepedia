package com.moviepedia.mapper;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.DoubleSummaryStatistics;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.moviepedia.aop.ExeTimer;
import com.moviepedia.domain.BoxOfficeWithStarDTO;
import com.moviepedia.domain.CntByStarDTO;
import com.moviepedia.domain.CommentInfoDTO;
import com.moviepedia.domain.CommentVO;
import com.moviepedia.domain.DisplayMovieDTO;
import com.moviepedia.domain.DisplayMovieDTO;
import com.moviepedia.domain.FavoriteGenreNationDTO;
import com.moviepedia.domain.FavoritePeopleDTO;
import com.moviepedia.domain.MemberVO;
import com.moviepedia.domain.MovieVO;
import com.moviepedia.domain.MovieWithStarDTO;
import com.moviepedia.domain.PeopleInfoDTO;
import com.moviepedia.domain.PeopleVO;
import com.moviepedia.domain.ReplyVO;
import com.moviepedia.domain.StarRatingAnalysisDTO;
import com.moviepedia.domain.TestFavoriteGenreDTO;
import com.moviepedia.domain.TestMergeVO;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringRunner.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml",
"file:src/main/webapp/WEB-INF/spring/security-context.xml"})
@Log4j
public class MapperTests {

	
	@Setter(onMethod_ = @Autowired)
	private ReplyMapper mapper;
	
	@Setter(onMethod_ = @Autowired)
	private TestMergeMapper mergeMapper;
	
	@Setter(onMethod_ = @Autowired)
	private StarRatingMapper starRatingMapper;
	
	@Setter(onMethod_ = @Autowired)
	private MemberMapper memberMapper;
	
	@Setter(onMethod_ = @Autowired)
	private UserMapper userMapper;
	
	@Setter(onMethod_ = @Autowired)
	private	MovieMapper movieMapper;
	
	@Setter(onMethod_ = @Autowired)
	private BoxOfficeMapper boxOfficeMapper;
	
	@Setter(onMethod_ = @Autowired)
	private PeopleMapper peopleMapper;
	
	
	Long targetReplyCd = 3L;
	Long targetCommentCd = 321L;
	
//	@Test
	public void testMapper() {
		
		log.info(mapper);
	}
	
	// 등록
//	@Test
	public void testCreate() {
		
		ReplyVO vo = new ReplyVO();
		
		vo.setCommentCd(targetCommentCd);
		vo.setMovieCd("20000006");
		vo.setReply("댓글2");
		vo.setUserid("mem2");
		mapper.insert(vo);
	}
	
	// 조회
//	@Test
	public void testRead() {
		
		
//		ReplyVO vo = mapper.read(targetReplyCd);
//		
//		log.info(vo);
	}
	
	
	// 삭제
//	@Test
	public void testDelete() {
		
		mapper.delete(targetReplyCd);
	}
	
	// 수정
//	@Test
	public void testUpdate() {

//		ReplyVO vo = mapper.read(targetReplyCd);
//		
//		vo.setReply("댓글 수정 3");
//		
//		int count = mapper.update(vo);
//		
//		log.info("수정 횟수:  " + count);
	}
	
	
	// 전체 조회
//	@Test
	public void testGetList() {
//		List<ReplyVO> replies = mapper.getList(targetCommentCd);
//		
//		replies.forEach(reply -> log.info(reply));
	}
	
//	@Test
	public void testMerge() {
		
		TestMergeVO mergeVO = new TestMergeVO();
		
		mergeVO.setTpk(2);
		mergeVO.setTnum(2);
		mergeVO.setTname("2");
		
		log.info(mergeVO);
//		mapper.testMege(mergeVO);
		log.info(mergeMapper.get());
	}
	
//	@Test
	public void TeststarRatingAnalysis() {
		
		String userid = "mem8";
		
		StarRatingAnalysisDTO  starRatingAnalysisDTO = userMapper.getStarRatingAnalysis(userid);
	
		log.info(starRatingAnalysisDTO);
		
		List<CntByStarDTO> cntByStarDTOList = starRatingAnalysisDTO.getCntByStarDTOList();
		
		log.info(cntByStarDTOList);
		
		String[] star = {"cnt0_5","cnt1_0","cnt1_5","cnt2_0","cnt2_5","cnt3_0","cnt3_5","cnt4_0","cnt4_5","cnt5_0"};
		
		ArrayList<String> starList = new ArrayList<String>();
		for(int i=0; i<star.length;i++) {
			starList.add(star[i]);
		}
		log.info("starList: " + starList);

		
		ArrayList<String> currentStarList = new ArrayList<String>();
		for (CntByStarDTO cntByStarDTO: cntByStarDTOList) {
			currentStarList.add(cntByStarDTO.getStar());
			
		}
		ArrayList<Integer> currentCntList = new ArrayList<>();
		for (CntByStarDTO cntByStarDTO: cntByStarDTOList) {
			currentCntList.add(cntByStarDTO.getStarCnt());
			
		}
		
		
		 // > cntByStarList=[{star=cnt2_0, starCnt=4}, {star=cnt2_5, starCnt=1}, {star=cnt3_0, starCnt=4}, {star=cnt4_0, starCnt=7}, {star=cnt5_0, starCnt=2}])
		log.info("currentStarList: " + currentStarList);
		log.info("currentCntList: " + currentCntList);
		
		// removeAll() 중복 제거 이용
		starList.removeAll(currentStarList);
		log.info(starList);
		log.info(currentStarList);
		

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
		
	}

	
	
//	@Test
	public void Testme() {
		
		String userid = "admin91";
		MemberVO memberVO = memberMapper.read(userid);
		
		log.info(memberVO);
		log.info(memberVO.getAuthList());
		log.info(memberVO.getAuthList().get(0));
		
		// > authList=[AuthVO(userid=admin91, auth=ROLE_ADMIN), AuthVO(userid=admin91, auth=ROLE_MEMBER)])
//		Collection<String> collectionStarList = new ArrayList<String>;
	}
	
	// 취향분석 선호 배우 테스트
//	@Test
	public void testGetFavoritePeople() {
		
		String userid = "mem8";
		String roleNm = "배우";
		
		List<FavoritePeopleDTO> favoriteActorDTOList = userMapper.getFavoritePeopleScore(userid, roleNm);
		
		// 저장용 List
		List<Double> avgValueList = new ArrayList<Double>();
		List<Long> cntValueList = new ArrayList<Long>();
		
		List<Double> scaleAvgValueList = new ArrayList<Double>();
		for(int i=0; i<favoriteActorDTOList.size();i++) {
			FavoritePeopleDTO favoriteActorDTO = favoriteActorDTOList.get(i);
			log.info(favoriteActorDTO);
			
			
			List<FavoritePeopleDTO> actorFilmoList = userMapper.getActorFilmoList(favoriteActorDTO.getPeopleCd());
			
			List<Integer> actorRoleImportanceValueList = new ArrayList<Integer>();
			for(FavoritePeopleDTO actorFilmo : actorFilmoList) {
				log.info(actorFilmo);
				
				int actorRoleImportanceValue = 0;
				if(userMapper.getActorRoleImportanceValue(actorFilmo.getMovieCd(), actorFilmo.getPeopleNm()) != null) {
					actorRoleImportanceValue = userMapper.getActorRoleImportanceValue(actorFilmo.getMovieCd(), actorFilmo.getPeopleNm());
					
					actorRoleImportanceValueList.add(actorRoleImportanceValue);
				}
				
				
			}
			log.info(actorRoleImportanceValueList);
			
			IntSummaryStatistics valueListStatistics = actorRoleImportanceValueList
					.stream()
					.mapToInt(value -> value)
					.summaryStatistics();
			Double avgValue = valueListStatistics.getAverage();
			Long cntValue = valueListStatistics.getCount();
			
			log.info(avgValue);
			log.info(cntValue);
			
			avgValueList.add(avgValue);
			cntValueList.add(cntValue);
			log.info("====================================");
			
		}
		log.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		
		log.info("avgValueList: " + avgValueList);
		log.info("cntValueList: " + cntValueList);
		
		
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
		log.info("maxCntValue: " + maxCntValue);
		log.info("minCntValue: " + minCntValue);
		
		for(int i=0; i<favoriteActorDTOList.size();i++) {
			
			FavoritePeopleDTO favoriteActorDTO = favoriteActorDTOList.get(i);
			
			Double scaleAvgValue = (avgValueList.get(i) - minAvgValue) / (maxAvgValue - minAvgValue);
			log.info("scaleAvgValue: " + scaleAvgValue);
			
			Double scaleCntValue =   ((double) (cntValueList.get(i) - minCntValue) / (maxCntValue - minCntValue));
			log.info("scaleCntValue: " + scaleCntValue);
			
			Double score2 = scaleAvgValue * 0.7 + scaleCntValue * 0.3;
			
			Double score1 = favoriteActorDTO.getScore1();
			
			int finalScore = (int) Math.round((score1 * 0.75 + score2 * 0.25) * 100) ;
			
			log.info(score1 + "/" + score2 + "/" + finalScore);
			
			favoriteActorDTO.setFinalScore(finalScore);
		}
		log.info(favoriteActorDTOList);
		
		favoriteActorDTOList = favoriteActorDTOList
				.stream()
				.sorted(Comparator.comparing(FavoritePeopleDTO:: getFinalScore).reversed())
				.collect(Collectors.toList());
		
		log.info(favoriteActorDTOList);
	}
	
//	@Test
	public void testGetFavoriteGenreScore() {
		
		String userid="mem8";
		
		
		
		List<String> movieCdList = userMapper.getMovieCdForFavoriteGenreNation(userid);
		
		
		
		List<FavoriteGenreNationDTO> favoriteGenreList = userMapper.getFavoriteGenreScore(userid, movieCdList);
		favoriteGenreList.forEach(favoriteGenre -> log.info(favoriteGenre));
		log.info("===========================================");
		List<FavoriteGenreNationDTO> favoriteNationList = userMapper.getFavoriteNationScore(userid, movieCdList);
		favoriteNationList.forEach(favoriteNation -> log.info(favoriteNation));
	}
//	|-------|--------------|----------------------|
//	|genre  |cntstarrating |totalscore            |
//	|-------|--------------|----------------------|
//	|액션     |10            |0.8557401053692804    |
//	|드라마    |9             |0.6867389437067469    |
//	|스릴러    |3             |0.38577625551773026   |
//	|공포(호러) |2             |0.3701597932642931    |
//	|코미디    |2             |0.36824395373291274   |
//	|멜로/로맨스 |1             |0.3                   |
//	|범죄     |2             |0.2590319136638189    |
//	|       |1             |0.25                  |
//	|가족     |1             |0.15501864066532836   |
//	|공연     |1             |0.012933753943217666  |
//	|SF     |1             |0.0019173443695192892 |
//	|어드벤처   |1             |0.0013584726755529622 |
//	|-------|--------------|----------------------|
//	|-------|--------------|---------------------|
//	|genre  |cntstarrating |totalscore           |
//	|-------|--------------|---------------------|
//	|액션     |10            |0.9112100829771185   |
//	|드라마    |9             |0.7096645159765551   |
//	|스릴러    |3             |0.40222129266147544  |
//	|공포(호러) |2             |0.39367449914099806  |
//	|코미디    |2             |0.37886178861788616  |
//	|멜로/로맨스 |1             |0.3                  |
//	|범죄     |2             |0.2917833324739167   |
//	|가족     |1             |0.18880266075388027  |
//	|공연     |1             |0.1                  |
//	|SF     |1             |0.014824345491161333 |
//	|어드벤처   |1             |0.010503313125616805 |
//	|-------|--------------|---------------------|
//	|------|--------------|--------------------|
//	|genre |cntstarrating |totalscore          |
//	|------|--------------|--------------------|
//	|액션    |10            |0.9112100829771185  |
//	|스릴러   |3             |0.40222129266147544 |
//	|코미디   |2             |0.37886178861788616 |
//	|범죄    |2             |0.2917833324739167  |
//	|가족    |1             |0.18880266075388027 |
//	|------|--------------|--------------------|
//	|-------|--------------|-----------|
//	|액션     |10            |91         |
//	|드라마    |9             |71         |
//	|스릴러    |3             |40         |
//	|공포(호러) |2             |39         |
//	|코미디    |2             |38         |
//	|멜로/로맨스 |1             |30         |
//	|범죄     |2             |29         |
//	|가족     |1             |19         |
//	|공연     |1             |10         |
//	|어드벤처   |1             |1          |
//	|-------|--------------|-----------|
//	|액션     |10            |91         |
//	|드라마    |9             |71         |
//	|스릴러    |3             |40         |
//	|공포(호러) |2             |39         |
//	|코미디    |2             |38         |
//	|멜로/로맨스 |1             |30         |
//	|-------|--------------|-----------|
	
	
	// Oracle REGEXP_SUBSTR() VS Java 
		// : 한 행의 데이터를 특정 문자로 여러행으로 분리
	
//	@Test
	public void testSpeedSubstr() {
		Instant stime = Instant.now();
		
		
		String userid = "mem8";
		
		List<String> genreAltList = userMapper.getGenreAltForFavoriteGenre(userid);
		List<String> genreList = new ArrayList<String>();
		
		for (String genreAlt : genreAltList) {
			genreList.addAll(Arrays.asList(genreAlt.split(","))); // split 후 String[] 반복문 돌려 list에 추가하는것보다 연산속도 빠름
		}
		log.info(genreList);
		
		// set 이용
			// List 비교) 중복 불허, 순서 없음
		List<TestFavoriteGenreDTO> testFavoriteGenreDTOList = new ArrayList<TestFavoriteGenreDTO>();
		
		
		Set<String> genreSet = new HashSet<String>(genreList);
		TestFavoriteGenreDTO testFavoriteGenreDTO = null;
		for (String genre : genreSet) {
			testFavoriteGenreDTO = new TestFavoriteGenreDTO();
			testFavoriteGenreDTO.setGenre(genre);
			testFavoriteGenreDTO.setTotalCntGnere(Collections.frequency(genreList, genre));
			log.info(testFavoriteGenreDTO);
		}
		log.info(testFavoriteGenreDTOList);
		
		Instant etime = Instant.now();
		
		log.info("소요시간: " + Duration.between(stime, etime).toMillis() +"ms");
		
		
	}
	
//	@Test
	public void testSpeedSubstr2() {
		Instant stime = Instant.now();
		
		String userid = "mem8";
		List<String> movieCdList = userMapper.getMovieCdForFavoriteGenreNation(userid);
		
		List<TestFavoriteGenreDTO> testFavoriteGenreDTOList = userMapper.getFavoriteGenreScore2(userid, movieCdList);
		
		log.info(testFavoriteGenreDTOList);
		
		Instant etime = Instant.now();
		
		log.info("소요시간: " + Duration.between(stime, etime).toMillis() +"ms");
	}
	
	// 결론 : genreAlt 문자열을 특정문자로 나누어 리스트로 출력하는 것은 0.380s 0.515s 로 java가 더 빨랐음. 
	// 그러나 구한 genre와 그 개수를 이용해 cntStar, avgStr 등을 추가로 구하기 위해선 다시 DB 쿼리 수행해야함을 감안하면 
	// oracle REGEXP_SUBSTR의 사용하는것 괜찮
	
	// + service.getFavoriteActorList(userid); // 4.939s // score2만 java 이용해 구하는 코드 3.000s(rownum 10개) cf) 감독은 0.807s
		// DB에서  평균값, 개수, 이들의 최대,최소값 등의 주요 기준 데이터 획득 -> 정규화 -> 가중평균 수행하는 것이 비록 4-5중 서브쿼리를 사용하더라도 java Service단에서 얻는 것보다 2-3배 더 빨랐음 
	
	
//	@Test
	public void testGetMovieOpenDt() {
		
		MovieVO dbMovieVO = movieMapper.read("20217905");
		
		log.info(dbMovieVO.getUpdateDate());
		log.info(dbMovieVO.getUpdateDate().getClass());
		
	
	}
	
//	@Test
	public void testMainBoxOffice() {
		String showDate = "20221113";
		String userid ="mem8";
		boxOfficeMapper.getBoxOfficeWithStarList(showDate, userid);
		
		List<BoxOfficeWithStarDTO> boxOfficeWithStarDTOList = boxOfficeMapper.getBoxOfficeWithStarList(showDate, userid);
		
		log.info(boxOfficeWithStarDTOList);
		boxOfficeWithStarDTOList.forEach(boxOfficeWithStarDTO -> log.info(boxOfficeWithStarDTO));
	}
	
//	@Test
	public void testMainHighStarRatingMovieLIst() {
		String userid = "mem6";
		
		List<DisplayMovieDTO> highStarRatingMovieList = movieMapper.getHighStarRatingMovieList(userid);
		
		highStarRatingMovieList.forEach(highStarRatingMovie -> log.info(highStarRatingMovie));
	}
	
//	@Test
	public void testPeopleInfo() {
		String userid = "mem7";
		String peopleCd = "10019069";
		
		PeopleInfoDTO peopleInfoDTO = peopleMapper.getPeopleDirectorFilmoList(userid, peopleCd);
		log.info(peopleInfoDTO);
		List<MovieWithStarDTO> directorsMovieWithStarDTOList = peopleInfoDTO.getMovieWithStarDTOList();
		directorsMovieWithStarDTOList.forEach(directorsMovieWithStarDTO -> log.info(directorsMovieWithStarDTO));
		
		log.info("====================================");
//		List<MovieWithStarDTO> actorSMovieWithStarDTOList = peopleMapper.getPeopleActorFilmoList(userid, peopleCd);
//		actorSMovieWithStarDTOList.forEach(actorSMovieWithStarDTO -> log.info(actorSMovieWithStarDTO));
		
		
	}
	
//	@Test
	public void testGetUserCommentList() {
		String userid = "mem7";
		
//		List<CommentInfoDTO> userCommentList = userMapper.getUserCommentList(userid);
		
//		userCommentList.forEach(userComment -> log.info(userComment));
	}
//	java.lang.ClassCastException: java.math.BigDecimal cannot be cast to java.lang.Double
	


	
//	@Test
	public void testGetActorImportance() {
		String peopleCd = "10000002";
		
		Map<String,Double> actorRoleImportance = userMapper.getActorRoleImportance(peopleCd);
		
		//	Error) java.lang.ClassCastException: java.math.BigDecimal cannot be cast to java.lang.Double
			// DB 컬럼타입 number일때 발생하는 형변환 오류
			// 우선 Stirng.valueOf()로 String 변환 -> Integer.parseint, Double.parseDboule 등으로 java 타입으로 변환
		int cntActorRoleImportance =  (int) Double.parseDouble(String.valueOf(actorRoleImportance.get("cntActorRoleImportance")));
		double avgActorRoleImportance =  Double.parseDouble(String.valueOf(actorRoleImportance.get("avgActorRoleImportance")));
				
		log.info(cntActorRoleImportance);
		log.info(avgActorRoleImportance);
	}
	
	
	@Test
	public void updateActorRoleImportance() {
		String peopleCd = "10034941";
		peopleMapper.updateActorRoleImportance(peopleCd);
		
		long startTime2 = System.currentTimeMillis();
//		
//		 List<PeopleVO> peopleList = peopleMapper.getPeopleListForUpdateRoleImportance(0); 
//		 
//		 int length = peopleList.size(); 
//		 for(int i=0; i<length;i++) {
//			 peopleMapper.updateActorRoleImportance(peopleList.get(i).getPeopleCd());
//		 }
		 
		 long endTime2 = System.currentTimeMillis();
        System.out.println("##실행시간(초.0f) : " + (endTime2 - startTime2) / 1000.0f + "초");		
//        System.out.println(peopleList.size());
		
	}
	
	
}
