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
	
	// ??????
//	@Test
	public void testCreate() {
		
		ReplyVO vo = new ReplyVO();
		
		vo.setCommentCd(targetCommentCd);
		vo.setMovieCd("20000006");
		vo.setReply("??????2");
		vo.setUserid("mem2");
		mapper.insert(vo);
	}
	
	// ??????
//	@Test
	public void testRead() {
		
		
//		ReplyVO vo = mapper.read(targetReplyCd);
//		
//		log.info(vo);
	}
	
	
	// ??????
//	@Test
	public void testDelete() {
		
		mapper.delete(targetReplyCd);
	}
	
	// ??????
//	@Test
	public void testUpdate() {

//		ReplyVO vo = mapper.read(targetReplyCd);
//		
//		vo.setReply("?????? ?????? 3");
//		
//		int count = mapper.update(vo);
//		
//		log.info("?????? ??????:  " + count);
	}
	
	
	// ?????? ??????
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
		
		// removeAll() ?????? ?????? ??????
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
	
	// ???????????? ?????? ?????? ?????????
//	@Test
	public void testGetFavoritePeople() {
		
		String userid = "mem8";
		String roleNm = "??????";
		
		List<FavoritePeopleDTO> favoriteActorDTOList = userMapper.getFavoritePeopleScore(userid, roleNm);
		
		// ????????? List
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
//	|??????     |10            |0.8557401053692804    |
//	|?????????    |9             |0.6867389437067469    |
//	|?????????    |3             |0.38577625551773026   |
//	|??????(??????) |2             |0.3701597932642931    |
//	|?????????    |2             |0.36824395373291274   |
//	|??????/????????? |1             |0.3                   |
//	|??????     |2             |0.2590319136638189    |
//	|       |1             |0.25                  |
//	|??????     |1             |0.15501864066532836   |
//	|??????     |1             |0.012933753943217666  |
//	|SF     |1             |0.0019173443695192892 |
//	|????????????   |1             |0.0013584726755529622 |
//	|-------|--------------|----------------------|
//	|-------|--------------|---------------------|
//	|genre  |cntstarrating |totalscore           |
//	|-------|--------------|---------------------|
//	|??????     |10            |0.9112100829771185   |
//	|?????????    |9             |0.7096645159765551   |
//	|?????????    |3             |0.40222129266147544  |
//	|??????(??????) |2             |0.39367449914099806  |
//	|?????????    |2             |0.37886178861788616  |
//	|??????/????????? |1             |0.3                  |
//	|??????     |2             |0.2917833324739167   |
//	|??????     |1             |0.18880266075388027  |
//	|??????     |1             |0.1                  |
//	|SF     |1             |0.014824345491161333 |
//	|????????????   |1             |0.010503313125616805 |
//	|-------|--------------|---------------------|
//	|------|--------------|--------------------|
//	|genre |cntstarrating |totalscore          |
//	|------|--------------|--------------------|
//	|??????    |10            |0.9112100829771185  |
//	|?????????   |3             |0.40222129266147544 |
//	|?????????   |2             |0.37886178861788616 |
//	|??????    |2             |0.2917833324739167  |
//	|??????    |1             |0.18880266075388027 |
//	|------|--------------|--------------------|
//	|-------|--------------|-----------|
//	|??????     |10            |91         |
//	|?????????    |9             |71         |
//	|?????????    |3             |40         |
//	|??????(??????) |2             |39         |
//	|?????????    |2             |38         |
//	|??????/????????? |1             |30         |
//	|??????     |2             |29         |
//	|??????     |1             |19         |
//	|??????     |1             |10         |
//	|????????????   |1             |1          |
//	|-------|--------------|-----------|
//	|??????     |10            |91         |
//	|?????????    |9             |71         |
//	|?????????    |3             |40         |
//	|??????(??????) |2             |39         |
//	|?????????    |2             |38         |
//	|??????/????????? |1             |30         |
//	|-------|--------------|-----------|
	
	
	// Oracle REGEXP_SUBSTR() VS Java 
		// : ??? ?????? ???????????? ?????? ????????? ??????????????? ??????
	
//	@Test
	public void testSpeedSubstr() {
		Instant stime = Instant.now();
		
		
		String userid = "mem8";
		
		List<String> genreAltList = userMapper.getGenreAltForFavoriteGenre(userid);
		List<String> genreList = new ArrayList<String>();
		
		for (String genreAlt : genreAltList) {
			genreList.addAll(Arrays.asList(genreAlt.split(","))); // split ??? String[] ????????? ?????? list??? ????????????????????? ???????????? ??????
		}
		log.info(genreList);
		
		// set ??????
			// List ??????) ?????? ??????, ?????? ??????
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
		
		log.info("????????????: " + Duration.between(stime, etime).toMillis() +"ms");
		
		
	}
	
//	@Test
	public void testSpeedSubstr2() {
		Instant stime = Instant.now();
		
		String userid = "mem8";
		List<String> movieCdList = userMapper.getMovieCdForFavoriteGenreNation(userid);
		
		List<TestFavoriteGenreDTO> testFavoriteGenreDTOList = userMapper.getFavoriteGenreScore2(userid, movieCdList);
		
		log.info(testFavoriteGenreDTOList);
		
		Instant etime = Instant.now();
		
		log.info("????????????: " + Duration.between(stime, etime).toMillis() +"ms");
	}
	
	// ?????? : genreAlt ???????????? ??????????????? ????????? ???????????? ???????????? ?????? 0.380s 0.515s ??? java??? ??? ?????????. 
	// ????????? ?????? genre??? ??? ????????? ????????? cntStar, avgStr ?????? ????????? ????????? ????????? ?????? DB ?????? ?????????????????? ???????????? 
	// oracle REGEXP_SUBSTR??? ??????????????? ??????
	
	// + service.getFavoriteActorList(userid); // 4.939s // score2??? java ????????? ????????? ?????? 3.000s(rownum 10???) cf) ????????? 0.807s
		// DB??????  ?????????, ??????, ????????? ??????,????????? ?????? ?????? ?????? ????????? ?????? -> ????????? -> ???????????? ???????????? ?????? ?????? 4-5??? ??????????????? ?????????????????? java Service????????? ?????? ????????? 2-3??? ??? ????????? 
	
	
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
			// DB ???????????? number?????? ???????????? ????????? ??????
			// ?????? Stirng.valueOf()??? String ?????? -> Integer.parseint, Double.parseDboule ????????? java ???????????? ??????
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
        System.out.println("##????????????(???.0f) : " + (endTime2 - startTime2) / 1000.0f + "???");		
//        System.out.println(peopleList.size());
		
	}
	
	
}
