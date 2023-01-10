package com.moviepedia.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.moviepedia.domain.ApiInfoDTO;
import com.moviepedia.domain.BoxOfficeVO;
import com.moviepedia.domain.BoxOfficeWithStarDTO;
import com.moviepedia.domain.MovieVO;
import com.moviepedia.domain.PeopleVO;
import com.moviepedia.mapper.BoxOfficeMapper;
import com.moviepedia.mapper.MovieMapper;
import com.moviepedia.mapper.PeopleMapper;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;


@Service
	//계층 구조 상 주로 비즈니스 영역을 담당하는 객체임을 표시
	// 이 어노테이션은 패키지 읽어들이는 동안 처리됨
	// ServiceImpl가 정상적으로 동작하기 위해선 Mapper 객체 필요함
@AllArgsConstructor
@Log4j
public class ManageServiceImpl implements ManageService {
	
	private MovieMapper movieMapper;
	
	private PeopleMapper peopleMapper;
	
	private BoxOfficeMapper boxOfficeMapper;
	
	

	
	
	
	
// 영화&인물 정보 API 통합 메서드	
		@Transactional
		@Override
		public List<ApiInfoDTO> registerMainMoviePeopleList(String key, 
				int curPageStart,
				int curPageFinish,
				int itemPerPage,
				int openStartDt,
				int openEndDt) {
			
			// 반환할 API 호출 결과 리스트
			List<ApiInfoDTO> resultList = new ArrayList<ApiInfoDTO>();
			
//			<!-- currentPage <= 20
//					for(int i=1; i<=1; i++) > 1
//					0 -> 정상실행되나 반복문 돌지않음
//					2 -> 1, 2
			
			ApiInfoDTO apiInfoDTO;
			
			for(int curPage=curPageStart; curPage<=curPageFinish; curPage++) {
				
				List<MovieVO> movieList = searchMovieList(key, curPage, itemPerPage, openStartDt, openEndDt);
				
				log.info(movieList);
				
				
				log.info("-----------------------------------------");
				movieList.forEach(movie -> log.info(movie)); // forEach(): 주어진 함수를 배열 요소 각각에 대해 실행
				log.info(movieList.size());
				
				int movieTotalRegisterCnt = 0;
				if(movieList.size() != 0) {
					
					movieTotalRegisterCnt = registerMovieList(movieList);
//				movieMapper.insertMovieList(movieList);
					
				}
				
				
				log.info("-----------------------------------------");
				log.info("-----------------------------------------");
				
				
				  List<PeopleVO> peopleList = searchPeopleList(key, movieList);
				  
				  
				  
				 peopleList.forEach(people -> log.info(people)); log.info(peopleList.size());
				  
				  int peopleTotalRegisterCnt = 0; 
				  
				  if(peopleList.size() != 0) {
				  
					  peopleTotalRegisterCnt = registerPeopleList(peopleList); //
	//				  peopleMapper.insertPeopleList(peopleList);
					  
				  /* 배우 배역 비중 저장! */
					int peopleListLength = peopleList.size();
					for(int j=0; j<peopleListLength; j++) {
						PeopleVO peopleVO = peopleList.get(j);
						
						
						if(peopleVO.getRepRoleNm().equals("배우") && ! peopleVO.getActorFilmos().replace(" ", "").equals("")) {
							peopleMapper.updateActorRoleImportance(peopleVO.getPeopleCd());
						}
					}
					  
					
					
					  log.info("================ 최종 ================"); log.info(movieList.size());
					  movieList.forEach(movie -> log.info(movie)); // forEach(): 주어진 함수를 배열 요소 각각에 대해 실행 
					  log.info("================ 최종 ================");
					  log.info("================ 최종 ================");
					  log.info(peopleList.size()); peopleList.forEach(people -> log.info(people));
					  log.info("================ 최종 ================"); 
				  }
				  
				  
				  
				  // API 호출 결과 저장 
				  apiInfoDTO = new ApiInfoDTO();
				  
				  apiInfoDTO.setName("영화&인물"); apiInfoDTO.setCurPage(curPage);
				  apiInfoDTO.setItemPerPage(itemPerPage);
				  apiInfoDTO.setOpenStartDt(openStartDt); apiInfoDTO.setOpenEndDt(openEndDt);
				  apiInfoDTO.setMovieTotalRegisterCnt(movieTotalRegisterCnt);
				  apiInfoDTO.setPeopleTotalRegisterCnt(peopleTotalRegisterCnt);
				  
				  resultList.add(apiInfoDTO);
				 
			}
					
			
			return resultList;
			 
		
		} // registerMoviePeopleList(String key)


// 영화 정보 API 호출 메서드
		// JSON Parsing: json-simple 라이브러리 이용
		@Override
		public List<MovieVO> searchMovieList(String key,
				int curPage,
				int itemPerPage,		
				int openStartDt,
				int openEndDt) {
			
			ArrayList<MovieVO> movieList = new ArrayList<MovieVO>();
			
			String apiUrl = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieList.json";
			
			// 선택 요청 정보 지정
			String curPageVal = Integer.toString(curPage); // 현재 페이지를 지정 default : “1”
			String itemPerPageVal = Integer.toString(itemPerPage); // 출력 결과 Row의 개수 / default : 10 / Max : 100
			String movieNm = null; // 영화명 조회 (UTF-8 인코딩)
			String directorNm = "이병헌"; // 감독명 조희 (UTF-8 인코딩)
			String openStartDtVal = Integer.toString(openStartDt); // YYYY형식의 조회시작 개봉연도
			String openEndDtVal = Integer.toString(openEndDt); // YYYY형식의 조회종료 개봉연도
			String prdtStartYear = null; // YYYY형식의 조회시작 제작연도
			String prdtEndYear = null; // YYYY형식의 조회종료 제작연도
			String repNationCd = null; // 영화 국적. 국적코드는 공통코드 조회 서비스에서 “2204” 로서 조회된 국적코드입니다. (default : 전체)
			String movieTypeCd = null; // 영화 유형 코드. 공통코드 조회 서비스에서 “2201”로서 조회된 영화유형코드입니다.(default: 전체)
			
			// StringBuilder : 일반적으로 String객체 연산 시 새로운 String 객체 생성됨. 
			// StringBuilder 사용 시 새로운 객체 생성되지 않고 기존 데이터에 더하는 방식. 성능 개선
			StringBuilder urlBuilder = new StringBuilder(apiUrl);
			
			try {
				urlBuilder.append("?" + URLEncoder.encode("key", "UTF-8") + "=" + key);
				urlBuilder.append("&" + URLEncoder.encode("curPage","UTF-8") + "=" + URLEncoder.encode(curPageVal, "UTF-8"));  // 현재 페이지를 지정 default : “1”
				urlBuilder.append("&" + URLEncoder.encode("itemPerPage","UTF-8") + "=" + URLEncoder.encode(itemPerPageVal, "UTF-8"));  // 출력 결과 Row의 개수 / default : 10
//	        urlBuilder.append("&" + URLEncoder.encode("movieNm","UTF-8") + "=" + URLEncoder.encode(movieNm, "UTF-8"));  // 영화명 조회 (UTF-8 인코딩)
	        urlBuilder.append("&" + URLEncoder.encode("directorNm","UTF-8") + "=" + URLEncoder.encode(directorNm, "UTF-8")); // 감독명 조희 (UTF-8 인코딩)
				urlBuilder.append("&" + URLEncoder.encode("openStartDt","UTF-8") + "=" + URLEncoder.encode(openStartDtVal, "UTF-8"));  // YYYY형식의 조회시작 개봉연도
				urlBuilder.append("&" + URLEncoder.encode("openEndDt","UTF-8") + "=" + URLEncoder.encode(openEndDtVal, "UTF-8")); // YYYY형식의 조회종료 개봉연도
//	        urlBuilder.append("&" + URLEncoder.encode("prdtStartYear","UTF-8") + "=" + URLEncoder.encode(prdtStartYear, "UTF-8")); // YYYY형식의 조회시작 제작연도
//	        urlBuilder.append("&" + URLEncoder.encode("prdtEndYear","UTF-8") + "=" + URLEncoder.encode(prdtEndYear, "UTF-8")); // YYYY형식의 조회종료 제작연도
//	        urlBuilder.append("&" + URLEncoder.encode("repNationCd","UTF-8") + "=" + URLEncoder.encode(repNationCd, "UTF-8")); // 영화 국적. 국적코드는 공통코드 조회 서비스에서 “2204” 로서 조회된 국적코드입니다. (default : 전체)
//	        urlBuilder.append("&" + URLEncoder.encode("movieTypeCd","UTF-8") + "=" + URLEncoder.encode(movieTypeCd, "UTF-8")); // 영화 유형 코드. 공통코드 조회 서비스에서 “2201”로서 조회된 영화유형코드입니다.(default: 전체)
			
				
			//GET방식(URL에 데이터 붙여 보냄)으로 전송해서 파라미터 받아오기
				// urlBuilder.toString() : String으로 변환해 반환해주는 매서드
				URL url = new URL(urlBuilder.toString());
				
				// URLConnection 클래스 : 사용자 인증이나 보안이 설정되어있지 않은 웹서버에 접속하여 파일 등 다운로드하는데 많이 사용됨
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				// 요청 방법 및 속성 지정
				conn.setRequestMethod("GET"); // GET방식으로 전달
				conn.setRequestProperty("Content-type", "application/json"); // json 타입으로 전달
				//HttpURLConnection.getResponseCode() : HTTP 상태(응답) 코드 반환 (200, 404, 500 등)
				
				
			//Buffer 보조 스트림 연결
				BufferedReader br;
				if(conn.getResponseCode() == 200) { //HttpURLConnection.getResponseCode() : HTTP 상태(응답) 코드 반환 (200, 404, 500 등)
					br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				} else {
					br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
				}
				StringBuilder sb = new StringBuilder();
				String line;
				while((line = br.readLine()) != null) { // Buffer 보조 스트림의 readLine 이용. 더이상 읽어올게 없을때 까지
						// 판단과 저장을 한번에 하는 코드
					sb.append(line);
				}			
				br.close();
				conn.disconnect();
				

				String result = sb.toString(); // toString() : StringBuilder -> String
				
				log.info(result);
				
			///////////////////////////////
			/*JSON 데이터 파싱*/
				JSONParser parser = new JSONParser();
				
				////////////에러발생 코드 - 톰캣 lib에 json simple jar 파일 넣으니 해결됨
				JSONObject jsonObj = (JSONObject) parser.parse(result);
				// 분해
				JSONObject parse_movieListResult = (JSONObject) jsonObj.get("movieListResult"); // key name
				// 검색조건에 해당되는 총 영화 수
				//int totCnt = Integer.parseInt((String) parse_movieListResult.get("totCnt")); // Long.intValue() : Long -> int
				int totCnt = ((Long) parse_movieListResult.get("totCnt")).intValue(); // Long.intValue() : Long -> int
				log.info("검색 조건 해당하는 총 영화 수: " + totCnt);
				
				// 영화 리스트 배열 형식
				JSONArray parse_movieList = (JSONArray) parse_movieListResult.get("movieList"); // key name
				
				// 하나의 영화 정보 저장할 변수
				JSONObject JObj_movie = null;
				log.info("화면 출력한 영화 수: " + parse_movieList.size());
				log.info("--------------------------------");
				
				// 
				for(int i=0; i<parse_movieList.size(); i++) {
					
					// 영화 정보 저장할 movieVO 객체 선언
					MovieVO movieVO = new MovieVO();
		
					
					JObj_movie = (JSONObject) parse_movieList.get(i);
					
					// 영화명은 화면 출력을 위해 별도 변수로 선언
					String parse_movieNm = (String) JObj_movie.get("movieNm");
					
		//			log.info("==========================");
		//			log.info("curPage: " + curPageStr);
		//			log.info("==========================");
					
					log.info("---------------------------------");
					log.info(i+1+ "번째 영화 - " + parse_movieNm);
					
					
					movieVO.setMovieCd((String) JObj_movie.get("movieCd"));
					movieVO.setMovieNm(parse_movieNm);
					movieVO.setMovieNmEn((String) JObj_movie.get("movieNmEn"));
					
					// 제작연도
					if(JObj_movie.get("prdtYear").equals("")) {
						log.info("prdtYear 없음 modto: "+JObj_movie );
					} else {
						movieVO.setPrdtYear(Integer.parseInt((String) JObj_movie.get("prdtYear")));
					}
					
					
					// 개봉일자 yyyyMMdd String -> yyyy-MM-dd Date 변환
					// String -> util.Date -> String -> sql.Date
					//JSONObject parse_openDt = (JSONObject) JObj_movie.get("openDt");
					//if(! JObj_movie.get("openDt").equals("")) {
					if(JObj_movie.containsKey("openDt")) { // "prdtStatNm":"개봉예정" 인경우 openDt key 없음
//						SimpleDateFormat beforeOpenDtForm = new SimpleDateFormat("yyyyMMdd");
//						SimpleDateFormat afterOpenDtForm = new SimpleDateFormat("yyyy-MM-dd");
//						java.util.Date tempDateOpenDt  = beforeOpenDtForm.parse((String) JObj_movie.get("openDt"));
//						String transOpenDt = afterOpenDtForm.format(tempDateOpenDt);
//						Date openDt = Date.valueOf(transOpenDt);
						
						Date openDt = new SimpleDateFormat("yyyyMMdd").parse((String) JObj_movie.get("openDt")); // String -> util.Date는 (Date) 로 직접 형변환 불가  
						movieVO.setOpenDt(openDt);
					} 
					
					
					movieVO.setGenreAlt((String) JObj_movie.get("genreAlt"));
					// 장르 - 성인물 제외
						// genreAlt
					if(movieVO.getGenreAlt().contains("성인물(에로)")) {
						
						log.info("Result) 0_1. 성인물 -> 제외");
						log.info("");
						continue;
					}
					
					movieVO.setRepNationNm((String) JObj_movie.get("repNationNm"));
					// 한국/일본 영화 중 영화명에 성인물 관련 키워드 포함 시 제외
					// repNationNm, movieNm
					String[] adultKeywords = {"섹스", "음란", "불륜", "마사지", "맛사지", "가슴", "욕망", "음란", "능욕", "정사", "형수", "처형", "애원", "유부녀", "19금", "은밀한 유혹", "욕정", "숙모", "스와핑", 
							"새댁", "노리개", "젖은", "장모", "엄마친구", "남자사냥", "며느리", "제부", "맛있는", "시아버지", "노출", "화끈", "여직원", "새엄마", "누나 친구", "하던 날", "그녀와 하던", "몸 로비", "새누나",
							"엄마친구", "변태", "치한", "연상녀", "오피스걸", "오피스녀", "오르가즘", "섹시", "방문판매", "밀애","아주버님","탐하","온천","과부","사모님","명기","몸으로","출장","미망인","거칠게","굶주린",
							"창녀","신음","거유","야한","에로","여사원","러브호텔","바람난","여주인","기둥서방","해주는","음탕","성노예","대물","원나잇","드릴게요","부장님","잘하는","C컵","D컵","E컵","F컵","G컵","H컵","J컵","K컵","도련님",
							"그라비아", "육체 세일즈", "수리기사","거근", "관음", "부인","부부","교환","쾌락","애집","당하","당한","양기","음기","밝히는","간호사","몰래","벌려","물건","젊은 엄마","전철","동거","착한","남편","야릇","엄마",
							"AV스타", "은밀한", "방석집"};
					int adultKeywordResult = 0;
					for(String adultKeyword : adultKeywords) {
						if(movieVO.getRepNationNm().equals("한국") || movieVO.getRepNationNm().equals("일본")) {
							if(movieVO.getMovieNm().contains(adultKeyword)) {
								adultKeywordResult += 1;
								break;
							}
						}
						
					}
					if(adultKeywordResult > 0) {
						log.info("Result) "+movieVO.getRepNationNm()+" - 영화명 성인물 키워드 포함 -> 제외");
						log.info("");
						continue;
					}
					
					movieVO.setTypeNm((String) JObj_movie.get("typeNm"));
					movieVO.setPrdtStatNm((String) JObj_movie.get("prdtStatNm"));
					movieVO.setNationAlt((String) JObj_movie.get("nationAlt"));
					movieVO.setRepGenreNm((String) JObj_movie.get("repGenreNm"));
					movieVO.setCompanyCd((String) JObj_movie.get("companyCd"));
					movieVO.setCompanyNm((String) JObj_movie.get("companyNm"));
					
				// 영화 영문명 없음 -> 제외
					// 왜 제외?
		//			if(movieVO.getMovieNmEn().equals("")) {
		//				log.info("Result) 영화 영문명 없음 -> 제외");
		//				log.info();
		//				continue;
		//			}
					
					
					

				// 영화 상세 정보 API 메서드 호출
				/* searchMovieDetail(MovieVO movieVO) 호출*/
					// DB에 해당 영화 이미 존재 시 생략 -> continue;
					MovieVO dbMovieVO = movieMapper.read(movieVO.getMovieCd());
//					MovieVO dbMovieVO = null;
					if(dbMovieVO == null || dbMovieVO.getStoryText() == null || dbMovieVO.getActorDetail() == null 
							|| dbMovieVO.getPrdtStatNm() == null || dbMovieVO.getPrdtStatNm().equals("개봉예정")) {
//					if(dbMovieVO == null || dbMovieVO.getPrdtStatNm().equals("개봉예정") ) {
						
						movieVO = searchMovieDetail(key, movieVO);
					} 
					// !!!!!!!!!!!!!!!
					
					 else { log.info("Result) 이미 DB에 저장 -> 영화 상세정보 API 호출X"); log.info("");
					 	continue; 
					 }
					 
					
//					movieVO = searchMovieDetail(key, movieVO);
					
					
				// 네이버 영화 검색 api 및 네이버 크롤링 함수 호출
					/* getNaverMovieLink(String movieNm, String peopleNm) 호출 - String link 반환 */
					String link = "";
					link = searchNaverMovieLink(movieVO);
				
					// 네이버 영화 link 정상 저장 확인 -> 저장된 경우만 movieList 저장
					if(link.equals("")) {
						log.info("Result) 2. 네이버 영화 link 없음 -> 제외");
						log.info("");
						continue;
					}
					
					/* crawlingNaverMoviePosterStory(link, movieVO) 호출*/
				//	log.info("2. 네이버 영화 link 저장 완료: " + link);
					movieVO = crawlingNaverMoviePosterStory(link, movieVO);
				//	log.info("2) getMovieUrlStory(link, movieVO) 실행 완료");
					
					// 네이버 영화 장르 : 에로 인 경우 null 반환받음 -> 제외
					if(movieVO != null) {
						log.info("Result) movieVO: " + movieVO);
						log.info("");					
						
						// 최종 저장 : movieList에 추가
						movieList.add(movieVO);
						
					}
					
					
					
				
				} // for(int i=0; i<parse_movieList.size(); i++)
				
				

				
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (java.text.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
			log.info("최종 검색된 영화 수 : " + movieList.size());
			movieList.forEach(movie -> log.info(movie)); // forEach(): 주어진 함수를 배열 요소 각각에 대해 실행
			
			return movieList;
	      
		} // searchMovieList()
		
		
// 영화 상세정보 API 호출 메서드
		@Override
		public MovieVO searchMovieDetail(String key, MovieVO movieVO) {
			
			String watchGradeNm = ""; // 심의 정보
			int showTm = 0; // 상영 시간
			
			/*	필수 요청 정보 지정*/
			// ApiUrl 
			String apiUrl = " http://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieInfo.json";
			
			String movieCd = movieVO.getMovieCd(); // 영화 코드

			// StringBuilder : 일반적으로 String객체 연산 시 새로운 String 객체 생성됨. 
			// StringBuilder 사용 시 새로운 객체 생성되지 않고 기존 데이터에 더하는 방식. 성능 개선
			StringBuilder urlBuilder = new StringBuilder(apiUrl);		
			
			try {
				urlBuilder.append("?" + URLEncoder.encode("key", "UTF-8") + "=" + key);
				urlBuilder.append("&" + URLEncoder.encode("movieCd","UTF-8") + "=" + URLEncoder.encode(movieCd, "UTF-8"));  // 출력 결과 Row의 개수 / default : 10
				
				
				/*GET방식(URL에 데이터 붙여 보냄)으로 전송해서 파라미터 받아오기*/
				// urlBuilder.toString() : String으로 변환해 반환해주는 매서드
				URL url = new URL(urlBuilder.toString());
				
				// URLConnection 클래스 : 사용자 인증이나 보안이 설정되어있지 않은 웹서버에 접속하여 파일 등 다운로드하는데 많이 사용됨
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				// 요청 방법 및 속성 지정
				conn.setRequestMethod("GET"); // GET방식으로 전달
				conn.setRequestProperty("Content-type", "application/json"); // json 타입으로 전달
				

				
				//Buffer 보조 스트림 연결
				BufferedReader br;
				if(conn.getResponseCode() == 200) { //HttpURLConnection.getResponseCode() : HTTP 상태(응답) 코드 반환 (200, 404, 500 등)
					br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				} else {
					br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
				}
				StringBuilder sb = new StringBuilder();
				String line;
				while((line = br.readLine()) != null) { // Buffer 보조 스트림의 readLine 이용. 더이상 읽어올게 없을때 까지
						// 판단과 저장을 한번에 하는 코드
					sb.append(line);
				}
				br.close();
				conn.disconnect();
				
				
				String result = sb.toString(); // toString() : StringBuilder -> String	
				////////////////////////////////
				/*JSON 데이터 파싱*/
				JSONParser parser = new JSONParser();
				
				JSONObject jsonObj = (JSONObject) parser.parse(result);
				
				// 분해
				JSONObject parse_movieInfoResult = (JSONObject) jsonObj.get("movieInfoResult");
				
				// 영화 리스트 배열 형식
				JSONObject parse_movieInfo = (JSONObject) parse_movieInfoResult.get("movieInfo");
				
				
				// 감독 배열 ex) "peopleNm" "peopleNmEn"
				JSONArray parse_directors = (JSONArray) parse_movieInfo.get("directors");
				
				/// 감독 정보 변수
				StringBuilder  sbDirectorDetail = new StringBuilder();
				for(int i=0; i<parse_directors.size(); i++) {
					// 감독 정보
					JSONObject parse_director = (JSONObject) parse_directors.get(i); // 
					
					// 감독 이름(국문)
					String peopleNm = (String) parse_director.get("peopleNm");
					// 감독 이름(영문)
					String peopleNmEn = (String) parse_director.get("peopleNmEn");
					
					sbDirectorDetail.append(peopleNm);
					sbDirectorDetail.append("|");
					sbDirectorDetail.append(peopleNmEn);
					
					if(i != parse_directors.size() -1) { // 마지막 인덱스 아닌 경우
						sbDirectorDetail.append(",");
					}
					
				
					
				}
				String directorDetail = sbDirectorDetail.toString();
				
				// 콤마(,) 정리 
				// 문자,,문자 -> 문자,문자
				// 문자, -> 문자
				if(directorDetail.contains(",,")) {
					directorDetail = directorDetail.replace(",,", ","); // 기존 변수에 저장해야함
				}
				
				if(directorDetail.endsWith(",")) {
					directorDetail = directorDetail.replaceAll(",$", ""); // ,$ : 문자열 끝에 , 존재
				}
				
				/// 상영시간 showTm
				if(! ((String) parse_movieInfo.get("showTm")).isEmpty()) {
//				if(! parse_movieInfo.get("showTm").equals("")) {
					showTm =  Integer.parseInt((String) parse_movieInfo.get("showTm"));
				}
				
				// 배우 배열 ex) "peopleNm" "peopleNmEn" "cast" "castEn"
				JSONArray parse_actors = (JSONArray) parse_movieInfo.get("actors");
				
				/// 배우 정보 변수
				StringBuilder sbActorDetail = new StringBuilder();
				
			// error) ORA-01461 발생 -> varchar2 최대 크기인 4000char 초과
				// -> actorSize 조절해 4000char 넘지않도록함
				int maxActorSize = 20;
				int parse_actorsSize = parse_actors.size();
				if(parse_actorsSize > maxActorSize) {
					parse_actorsSize = maxActorSize;
				}
				for(int i=0; i<parse_actorsSize; i++) {
					// 배우 정보
					JSONObject parse_actor = (JSONObject) parse_actors.get(i); 
					
					// 배우 이름(국문)
					String peopleNm = (String) parse_actor.get("peopleNm");
					// 배우 이름(영문)
					String peopleNmEn = (String) parse_actor.get("peopleNmEn");
					// 캐스팅(국문)
					String cast = (String) parse_actor.get("cast");
					
					sbActorDetail.append(peopleNm);
					sbActorDetail.append("|");
					sbActorDetail.append(peopleNmEn);
					sbActorDetail.append("|");
					sbActorDetail.append(cast);
					
					if(i != parse_actors.size() -1) { // 마지막 인덱스 아닌 경우
						sbActorDetail.append(",");	
					}
		
				}
				String actorDetail = sbActorDetail.toString();
				
				// 콤마(,) 정리 
				// 문자,,문자 -> 문자,문자
				// 문자, -> 문자
				if(actorDetail.contains(",,")) {
					actorDetail = actorDetail.replace(",,", ","); // 기존 변수에 저장해야함
				}
				
				if(actorDetail.endsWith(",")) {
					actorDetail = actorDetail.replaceAll(",$", ""); // ,$ : 문자열 끝에 , 존재
				}
				
				log.info("0_2. getMovieDetail - parse_movieInfo: " + parse_movieInfo);
				
				// 심의 정보 watchGradeNm
				JSONArray  parse_audits = (JSONArray) parse_movieInfo.get("audits");
				
				if(parse_audits.isEmpty()) {
					log.info("심의정보 없음");
				} else {
					JSONObject parse_audit = (JSONObject) parse_audits.get(0);
					watchGradeNm = (String) parse_audit.get("watchGradeNm");
				}
				/// 관람 등급 명칭
				
				// movieVO에 저장
				movieVO.setDirectorDetail(directorDetail);
				movieVO.setShowTm(showTm);
				
				
				// actorDetetail 크기 제한
				int actorDetailLengh = actorDetail.length();
				log.info(movieVO.getMovieCd()+"/"+movieVO.getMovieNm()+"/"+actorDetailLengh);
	
				// Oracle Varchar2 4000 BYTES가 최대. UTF-8 한글 3BYtes, 1Bytes
				// -> length, substring 등은 bytes 기준. 문자열 모두 한글로 구성되어있다는 가혹조건으로 1330 지정함
				if(actorDetailLengh < 1330) {
					
					movieVO.setActorDetail(actorDetail);
				} else {
					log.info("!!!!!!actorDetail!!!!!!-"+movieVO.getMovieCd()+"/"+movieVO.getMovieNm()+"/"+actorDetailLengh+"/"+actorDetail);
					actorDetail = actorDetail.substring(0, 1330);
					int lastCommaIdx = actorDetail.lastIndexOf(",");
					if(lastCommaIdx != -1) {
						actorDetail = actorDetail.substring(0, lastCommaIdx);
						log.info("!!!!actorDetailLengh: "+ actorDetail.length()+"/actorDetail: "+actorDetail);
						movieVO.setActorDetail(actorDetail);
					}
				}
				
				
				
				movieVO.setWatchGradeNm(watchGradeNm);
				
							
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return movieVO;
		
			
		} // searchMovieDetail(MovieVO movieVO)
		
		
// 네이버 영화 검색 API 호출 메서드
		// 특정 영화 제목과 영화국가코드 입력 받아 네이버 영화 검색 API 호출해 해당 영화 페이지 링크 반환
		// API 호출 후 링크 반환 조건 : 제작연도, 감독명(없는 경우 배우명)
		@Override
		public String searchNaverMovieLink(MovieVO movieVO) {
			// 리턴 변수 link : 네이버 영화 페이지
			String link = "";
			
		
			String clientId = "JD8k4P6UkpBcXqsucMRW";
			String clientSecret = "Wy5O6e506N";
			
			
			// 검색어 요청 변수 : 영화명(국문)
			// String searchStr = movieNm.replace(" ", ""); // 공백 제거
//			String searchStr = movieVO.getMovieNm().replace(" ", "");
			String searchStr = movieVO.getMovieNm();
			
			// 영화국가코드 요청 변수
			// 영진위 영화국가 -> 네이버 영화국가코드로 변환 : 한국 (KR), 일본 (JP), 미국 (US), 홍콩 (HK), 영국 (GB), 프랑스 (FR), 기타 (ETC)
			// 영화제목과 더불어 영화국가코드를 함께 검색해 검색결과 수 줄이고 정확성 높임
			String naverNationNm = "";
			switch (movieVO.getRepNationNm()) {
			case "한국":
				naverNationNm = "KR";
				break;
			case "일본":
				naverNationNm = "JP";
				break;
			case "미국":
				naverNationNm = "US";
				break;
			case "홍콩":
				naverNationNm = "HK";
				break;
			case "영국":
				naverNationNm = " GB";
				break;
			case "프랑스":
				naverNationNm = "FR";
				break;
			default:
				naverNationNm = "ETC"; // 기타
				break;
			}
			
			//  String 검색어 변수를 API 요청하기 위한 URL로 사용하기 위해 Encoding 및 URL 객체로 생성
			try {
				String text = URLEncoder.encode(searchStr, "UTF-8");
				String country = URLEncoder.encode(naverNationNm, "UTF-8");
				//String yearfrom = Integer.pa (movieVO.getPrdtYear());
				//int yearto = Integer.parseInt(movieVO.getPrdtYear());
		
				// APIUrl
				String apiURL = "https://openapi.naver.com/v1/search/movie.json?query=" + text
								+ "&country=" + country;
		//											+ "&yearfrom=" + yearfrom
		//											+ "&yearto=" + yearto;
				URL url = new URL(apiURL);
				
				// GET 방식 이용해 데이터 받기
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.setRequestProperty("X-Naver-Client-Id", clientId);
				conn.setRequestProperty("X-Naver-Client-Secret", clientSecret);
				
				// getResponseCode() 메서드 이용해 연결 여부 확인
				int responseCode = conn.getResponseCode();
				BufferedReader br; // 버퍼 입력 스트림 연결
				if(responseCode == 200) {
					br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				} else {
					br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
				}
				
				// 데이터 받기
				String inputLine;
				StringBuffer response = new StringBuffer();
				while((inputLine = br.readLine()) != null) {
					response.append(inputLine);
				}
				br.close();
				conn.disconnect();
				
				
				String result = response.toString();
							
				// String 값(형태는 JSON데이터)을 JSON Data로 파싱
				JSONParser parser = new JSONParser();
				
				JSONObject jsonObj = (JSONObject) parser.parse(result);
				
				// 네이버 영화 정보 콘솔 출력
				System.out.println("1. 네이버 영화: " + jsonObj);
				
				
				JSONArray parse_items = (JSONArray) jsonObj.get("items");
				
				/*제작연도 및 감독명(및 배우) 조건 일치하는 영화 찾기 */
				for(int i=0; i<parse_items.size(); i++) { // 네이버 영화 정보 리스트
					JSONObject parse_item = (JSONObject) parse_items.get(i);
					
					// 제작연도 조건
					String pubDate = (String) parse_item.get("pubDate");
					
					// 사용x) 해당 영화 네이버에서 찾았으나 제작연도 달라 무작정 continue 시켜버리는 경우 발생..
//					if(! Integer.toString(movieVO.getPrdtYear()).equals(pubDate)) {
//
//						System.out.println(i+"."+movieVO.getMovieNm()+"/"+movieVO.getPrdtYear()+"/"+pubDate+" : 제작연도 불일치");
//						continue;
//					}
					
					// 감독명 조건 (movieVO 감독 정보 null인 경우 배우 정보 검색)
					if(movieVO.getDirectorDetail() != null && ! movieVO.getDirectorDetail().isEmpty()) { // 영진위 감독 정보 존재 시
						System.out.println("movieVO 감독정보: " + movieVO.getDirectorDetail());
						String parse_director = (String) parse_item.get("director");
						System.out.println("1. parse_director: " + parse_director);
						
						
						String[] directors = parse_director.split("\\|");
						for(String director : directors) {
							System.out.println("1. director: " + director);
							if(movieVO.getDirectorDetail().contains(director)) {
								link = (String) parse_item.get("link");
								//System.out.println("영진위 제작연도: " + movieVO.getPrdtYear());
								//System.out.println(director);
								//System.out.println(pubDate);
								break;
							} else {
								continue;
							} 
						}
					} else { // 영진위 감독정보 미존재 시
						// 영진위 배우 정보와 네이버 영화 배우 정보 비교
						System.out.println("1. 영진위 감독 정보 미존재 -> 배우 정보를 조건으로.");
						String parse_actor = (String) parse_item.get("actor");
						
						if(parse_actor.equals("")) { // 네이버 영화 배우 정보 없는 경우
							System.out.println("1. 네이버 영화 배우 정보 없음");
							continue;
						}
						
						System.out.println("movieVO 배우정보: " + movieVO.getActorDetail());
						System.out.println("1. parse_actor: " + parse_actor);
						String[] actors = parse_actor.split("\\|");
						for(String actor : actors) {
							System.out.println("1. actor: " + actor);
							if(movieVO.getActorDetail() != null && movieVO.getActorDetail().contains(actor)) {
								link = (String) parse_item.get("link");
								break;
							} else {
								continue;
							} 
						}
					}
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return link;
		} // searchNaverMovieLink(MovieVO movieVO)
		
		
// 네이버 영화 포스터 및 줄거리 크롤링 메서드
		// 네이버 영화 페이지의 장르 크롤링해 제외 처리 수행
		// jsoup 라이브러리
		@Override
		public MovieVO crawlingNaverMoviePosterStory(String link, MovieVO movieVO) {
			// 반환할 포스터url 및 스토리 변수 선언
			String posterUrl = "";
			String storyText = "";
			String genre = "";
			
			// 크롤링할 url 지정
			final String url = link;
			
			// Jsoup이용해 url 연결
			Connection conn = Jsoup.connect(url);
			try {
				// 페이지의 전체 소스 저장할 변수 Document
				Document document = conn.get(); // Connection.get() : Connection -> Document 타입으로 변환해 반환
					// org.jsoup.nodes 클래스
				
				
				// 장르 정보 가져오기
				Element genreElement = document.select("dl.info_spec > dd > p > span > a").first();
				if(genreElement == null) {
					System.out.println("2. genre 없음");
				} else {
					genre = genreElement.text();
				}
	
				
			// 네이버 영화 장르 에로인 경우 제외 - null 반환
				if(genre.contains("에로")) {
					
					log.info("result) 네이버 장르: 에로 -> 제외 :  " + genre);
					return null;
					
				}
					
				/*포스터 url 가져오기*/
				Element posterUrlElement = document.select("div.poster > a > img").first(); // 섬네일 이미지 요소(class="swiper-lazy")
				
				if(posterUrlElement == null) {
					System.out.println("2. 포스터 URL 없음");
				} else {
					posterUrl = posterUrlElement.attr("src");
					int lastIdx = posterUrl.lastIndexOf("?type=");
					posterUrl = posterUrl.substring(0, lastIdx);
				}
				// movieVO 에 담기
				movieVO.setPosterUrl(posterUrl);
				
				/* 줄거리 text 가져오기*/
				Element storyTextElement = document.select("div.story_area > p.con_tx").first();
				if(storyTextElement == null) {
					System.out.println("2. 줄거리 text 없음");
				} else {
					storyText = storyTextElement.text();
				}
				int storyTextLengh = storyText.length();
				log.info(movieVO.getMovieCd()+"/"+movieVO.getMovieNm()+"/"+storyTextLengh);
						
				// Oracle Varchar2 4000 BYTES가 최대. UTF-8 한글 3BYtes, 1Bytes
					// -> length, substring 등은 bytes 기준. 문자열 모두 한글로 구성되어있다는 가혹조건으로 1330 지정함
				if(storyTextLengh < 1330) {
					
					movieVO.setStoryText(storyText);
				} else {
					log.info("!!!!!!storyText!!!!!!-"+movieVO.getMovieCd()+"/"+movieVO.getMovieNm()+"/"+storyTextLengh+"/"+storyText);
					storyText = storyText.substring(0, 1330);
					movieVO.setStoryText(storyText);
				}
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return movieVO;
		
		
		} // crawlingNaverMoviePosterStory(String link, MovieVO movieVO)
			
		
// 영화 API 호출 결과 리스트 DB 저장 메서드
		@Override
		public int registerMovieList(List<MovieVO> movieList) {
			
			log.info("registerMovieList.....");
			
			return movieMapper.insertMovieList(movieList);
		}
		
		
		
// 인물 정보 API 호출 메인 메서드		
		@Override
		public List<PeopleVO> searchPeopleList(String key, List<MovieVO> movieList) {
			// 리턴 배열 선언
			List<PeopleVO> peopleList = new ArrayList<>();
		
			/* movieList 가져와 반복문 이용 분해 */
			MovieVO movieVO = null;
			
			for(int i=0; i<movieList.size(); i++) {
				movieVO = movieList.get(i);
				
				
				log.info("-------------------------------------------");
				log.info("배우 검색할 영화명: " +(i+1)+". " + movieVO.getMovieNm() +"/"+movieVO.getMovieCd());
				
				
				
				String movieNm =  movieVO.getMovieNm();
				
				String directorDetail = movieVO.getDirectorDetail(); // 국문|영문,국문|영문,... 꼴
				String actorDetail = movieVO.getActorDetail();
				
				// 감독명 국문만 저장
				StringBuilder SB_directors = new StringBuilder();
				String[] directors = directorDetail.split(",");
				for(int j=0; j<directors.length; j++) {
					String directorNm = directors[j].split("\\|")[0];
					SB_directors.append(directorNm);
					
					if(j != directors.length -1) {
						SB_directors.append(",");
					}
				}
				
				// 배우명 국문만 저장
				StringBuilder SB_actors = new StringBuilder();
				String[] actors = actorDetail.split(",");
				
				
				for(int j=0; j<actors.length; j++) {
					String actorNm = actors[j].split("\\|")[0];
					SB_actors.append(actorNm);
					
					if(j != actors.length -1) {
						SB_actors.append(",");
					}
				}
				
				log.info("SB_directors: " + SB_directors);
				log.info("SB_actors: " + SB_actors);
				
				StringBuilder peopleNmSb = new StringBuilder();
				peopleNmSb.append(SB_directors);
				peopleNmSb.append(",");
				peopleNmSb.append(SB_actors);
				String[] directorActorNms = peopleNmSb.toString().split(",");
				
				// String[] 중복 제거
				// Arrays.Stream 이용 : 배열 -> stream -> distinct() 중복제거 -> 배열
				String[] resultRirectorActorNms = Arrays.stream(directorActorNms).distinct().toArray(String[]::new);
				log.info("resultRirectorActorNms: " + Arrays.toString(resultRirectorActorNms));
				log.info("");
				
				// 리턴 PeopleVO 선언
				PeopleVO peopleVO = null;
				
				
	
			// 배우 API 호출
				/* (X 모두 호출) 해당 directorActorNm와 movieCd에 해당하는 영화인 people DB에 존재하지 않는 경우만 API 호출*/
				String directorActorNm = "";
				for(int k=0; k<resultRirectorActorNms.length;k++) {
					directorActorNm = resultRirectorActorNms[k];
					
				// DB 저장 여부 판단
					log.info(" DB 저장 여부 판단. directorActorNm: " + directorActorNm +"/"+movieVO.getMovieCd());
					 List<PeopleVO> dbPeopleList = peopleMapper.readBypeopleNmMovieCd(directorActorNm, movieVO.getMovieCd());
						// 인물명으로 검색하기때문에 두개이상의 결과 출력될 수 있어 List로 받음 -> length 1이 아닌경우에 API 호출
					
					if(dbPeopleList.size() == 1) {
						log.info("people DB 이미 존재 -> API 미호출, DB 새로 저장 안함");
						
						peopleVO = dbPeopleList.get(0);
						log.info("dbPeopleVO: " + peopleVO);
						log.info("");
						
						continue;
					} else if(dbPeopleList.size() > 1) {
						log.info("!!!! people DB 2개 이상 출력 -> API 호출");
						log.info(dbPeopleList);
					}
				
					
					
					
					
//					peopleVO = new PeopleVO();
					
					// UPSERT 이용 : DB 기존재 시 UPDATE, 미존재 시 ISNERT 
						// API 호출 횟수 소진하더라도 배우 필모 업데이트 필요하다 판단	
//					if(mapper.get(directorActorNm) == null) {
//						log.info("null: " + directorActorNm );
//						log.info("");
//					} else {
//						log.info("get: " + directorActorNm);
//						log.info("");
//					}
					
					
					
				///////////////////////////////////////////////////
				/* 영화인 정보 API 호출 */
					
					/*	필수 요청 정보 지정*/
					// ApiUrl 
					String apiUrl = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/people/searchPeopleList.json";
					
					
					/*선택 요청 정보 지정*/
//					String curPage = "1"; // 현재 페이지를 지정 default : “1”
//					String itemPerPage = "10"; // 출력 결과 Row의 개수 / default : 10 / Max : 100
					String peopleNm = directorActorNm; // 영화인명
					String filmoNames = movieNm; // 필모리스트 (영화명)
					
					
					// StringBuilder : 일반적으로 String객체 연산 시 새로운 String 객체 생성됨. 
					// StringBuilder 사용 시 새로운 객체 생성되지 않고 기존 데이터에 더하는 방식. 성능 개선
					StringBuilder urlBuilder = new StringBuilder(apiUrl);
					
					
					try {
						urlBuilder.append("?" + URLEncoder.encode("key", "UTF-8") + "=" + key);
						//						urlBuilder.append("&" + URLEncoder.encode("curPage","UTF-8") + "=" + URLEncoder.encode(curPage, "UTF-8"));  // 현재 페이지를 지정 default : “1”
						//						urlBuilder.append("&" + URLEncoder.encode("itemPerPage","UTF-8") + "=" + URLEncoder.encode(itemPerPage, "UTF-8"));  // 출력 결과 Row의 개수 / default : 10
						urlBuilder.append("&" + URLEncoder.encode("peopleNm","UTF-8") + "=" + URLEncoder.encode(peopleNm, "UTF-8"));  // 영화명 조회 (UTF-8 인코딩)
						urlBuilder.append("&" + URLEncoder.encode("filmoNames","UTF-8") + "=" + URLEncoder.encode(filmoNames, "UTF-8")); // 감독명 조희 (UTF-8 인코딩)
						
						/////////////////////////////////////////////////////////////////
						/*GET방식(URL에 데이터 붙여 보냄)으로 전송해서 파라미터 받아오기*/
						// urlBuilder.toString() : String으로 변환해 반환해주는 매서드
						URL url = new URL(urlBuilder.toString());
						
						// URLConnection 클래스 : 사용자 인증이나 보안이 설정되어있지 않은 웹서버에 접속하여 파일 등 다운로드하는데 많이 사용됨
						HttpURLConnection conn = (HttpURLConnection) url.openConnection();
						// 요청 방법 및 속성 지정
						conn.setRequestMethod("GET"); // GET방식으로 전달
						conn.setRequestProperty("Content-type", "application/json"); // json 타입으로 전달
						//HttpURLConnection.getResponseCode() : HTTP 상태(응답) 코드 반환 (200, 404, 500 등)
						
						
						//Buffer 보조 스트림 연결
						BufferedReader br;
						if(conn.getResponseCode() == 200) { //HttpURLConnection.getResponseCode() : HTTP 상태(응답) 코드 반환 (200, 404, 500 등)
							br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
						} else {
							br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
						}
						StringBuilder sb = new StringBuilder();
						String line;
						while((line = br.readLine()) != null) { // Buffer 보조 스트림의 readLine 이용. 더이상 읽어올게 없을때 까지
							// 판단과 저장을 한번에 하는 코드
							sb.append(line);
						}			
						br.close();
						conn.disconnect();
						
						
						String result = sb.toString(); // toString() : StringBuilder -> String
						
						
					////////////////////////////////////
					/// key 일일한도 초과 에러 발생 시 null 리턴해 메서드 종료시킴
						log.info("영진위 영화인 결과 출력 : " + result);
						if(result.contains("320011")) {
							return null;
						}
						
					///////////////////////////
					/* JSON 데이터 파싱 */
						JSONParser parser = new JSONParser();
						
						JSONObject jsonObj = (JSONObject) parser.parse(result);
						// 분해
						JSONObject parse_peopleListResult = (JSONObject) jsonObj.get("peopleListResult");
						// 검색조건에 해당되는 총 영화인 수
						int totCnt = ((Long) parse_peopleListResult.get("totCnt")).intValue();
						//						log.info("검색조건에 해당되는 총 영화인 수: " + totCnt);
						
						////////////////////////
						// 영화인 API 호출 결과 존재하는 경우만 이하 코드 실행
							// 영화인명과 영화명을 조건으로 API 호출했으나 검색조건 해당하는 영화인 출력되지 않은 경우
							// => 영진위 영화 정보에는 영화인 이름 저장되어 있으나 영진위 영화인 정보에는 없는 경우
						if(totCnt != 0) {
							// 영화인 리스트 배열 형식
							JSONArray parse_peopleList = (JSONArray) parse_peopleListResult.get("peopleList");
							//							log.info("parse_peopleList: " + parse_peopleList);
							//							log.info("화면 출력한 영화인 수: " + parse_peopleList.size());
							// 하나의 영화인 정보 저장할 변수 - 반복문
							for(int m=0; m<parse_peopleList.size();m++) {
								
								peopleVO = new PeopleVO();
								
								JSONObject people = (JSONObject) parse_peopleList.get(m);
								
								log.info((i+1)+"/" +movieList.size()+ "_" + (k+1)+"/"+resultRirectorActorNms.length +". " + (String) people.get("peopleNm") + " - " + movieNm);
								log.info((String) people.get("peopleCd"));
								
								peopleVO.setPeopleCd((String) people.get("peopleCd"));
								peopleVO.setPeopleNm((String) people.get("peopleNm"));
								peopleVO.setPeopleNmEn((String) people.get("peopleNmEn"));
								
								//////////////////////////////////////////
								/*	filmoNames 2개 이상인 영화인만 API 호출 */
								String[] filmoNamesArr = null;
								if(people.get("filmoNames") != null) {
									filmoNamesArr = ((String) people.get("filmoNames")).split("\\|");
									log.info(filmoNamesArr.length);
									
								}
								
								// 필모 null인 경우에도 상제 정보 api 호출
								if(filmoNamesArr == null || filmoNamesArr.length >= 2) {
									
									///////////////////////////////////////
									// 영화인 상제 정보 API 호출
									/*	searchPeopleDetail(key, peopleVO) 호출	*/
									// 감독/배우인 경우에만 peopleVO 반환받음, 아닌 경우 null 반환받음
									peopleVO = searchPeopleDetail(key, peopleVO);
									
									if(peopleVO == null) {
										log.info("감독/배우 아님 -> 제외");
										log.info("");
									} else {
										log.info("Result) peopleVO: " + peopleVO);
										
										
								
										
										// 최종 저장
										peopleList.add(peopleVO);
										//									log.info("peopleVO 저장 : " + peopleList);
										log.info("");
									}
								} else {
									log.info("Result) 필모 2개 미만 -> 상세 영화인 API 호출X");
									log.info("");
								}
								
							}
						} else {
							log.info("Result) "+directorActorNm+" - "+movieNm+" -> 영진위 영화인 API 목록에 존재X");
							log.info("");
						}
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
					
				}
			}
			
			log.info("===================================");
			log.info("최종 검색된 영화인 수 : " + peopleList.size());
			peopleList.forEach(people -> log.info(people)); // forEach(): 주어진 함수를 배열 요소 각각에 대해 실행
			
			return peopleList;
		}

		
// 인물 상세정보 API 호출 메서드
		@Override
		public PeopleVO searchPeopleDetail(String key, PeopleVO peopleVO) {
			// 변수 선언
			String repRoleNm = "";
			
			/*	필수 요청 정보 지정*/
			// ApiUrl 
			String apiUrl = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/people/searchPeopleInfo.json";
			
			/*선택 요청 정보 지정*/
			String peopleCd = peopleVO.getPeopleCd(); // 영화인코드
			
			
			
			// StringBuilder : 일반적으로 String객체 연산 시 새로운 String 객체 생성됨. 
			// StringBuilder 사용 시 새로운 객체 생성되지 않고 기존 데이터에 더하는 방식. 성능 개선
			StringBuilder urlBuilder = new StringBuilder(apiUrl);
			
			try {
				urlBuilder.append("?" + URLEncoder.encode("key", "UTF-8") + "=" + key);
				urlBuilder.append("&" + URLEncoder.encode("peopleCd","UTF-8") + "=" + URLEncoder.encode(peopleCd, "UTF-8"));  // 현재 페이지를 지정 default : “1”
		       
				/////////////////////////////////////////////////////////////////
				/*GET방식(URL에 데이터 붙여 보냄)으로 전송해서 파라미터 받아오기*/
				// urlBuilder.toString() : String으로 변환해 반환해주는 매서드
				URL url = new URL(urlBuilder.toString());
				
				// URLConnection 클래스 : 사용자 인증이나 보안이 설정되어있지 않은 웹서버에 접속하여 파일 등 다운로드하는데 많이 사용됨
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				// 요청 방법 및 속성 지정
				conn.setRequestMethod("GET"); // GET방식으로 전달
				conn.setRequestProperty("Content-type", "application/json"); // json 타입으로 전달
				//HttpURLConnection.getResponseCode() : HTTP 상태(응답) 코드 반환 (200, 404, 500 등)
				
				
				//Buffer 보조 스트림 연결
				BufferedReader br;
				if(conn.getResponseCode() == 200) { //HttpURLConnection.getResponseCode() : HTTP 상태(응답) 코드 반환 (200, 404, 500 등)
					br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				} else {
					br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
				}
				StringBuilder sb = new StringBuilder();
				String line;
				while((line = br.readLine()) != null) { // Buffer 보조 스트림의 readLine 이용. 더이상 읽어올게 없을때 까지
						// 판단과 저장을 한번에 하는 코드
					sb.append(line);
				}			
				br.close();
				conn.disconnect();
				
		
				String result = sb.toString(); // toString() : StringBuilder -> String
				
				///////////////////////////
				/*JSON 데이터 파싱*/
				JSONParser parser = new JSONParser();
				
				JSONObject jsonObj = (JSONObject) parser.parse(result);
				// 분해
				JSONObject parse_peopleInfoResult = (JSONObject) jsonObj.get("peopleInfoResult");
				
				JSONObject parse_peopleInfo = (JSONObject) parse_peopleInfoResult.get("peopleInfo");
				
				// filmos - 배열 ex) [{"movieCd":"20212741","moviePartNm":"감독","movieNm":"안테벨룸"}]
				JSONArray parse_filmos = (JSONArray) parse_peopleInfo.get("filmos");
				
			// repRoleNm - 감독 또는 배우인 경우에만 영화인정보 저장
				// 아닌 경우 null return
				if(parse_peopleInfo.get("repRoleNm") != null) {
					repRoleNm = (String) parse_peopleInfo.get("repRoleNm");
				}
				System.out.println("repRoleNm: " + repRoleNm);
				if(repRoleNm.equals("감독") || repRoleNm.equals("배우")) {
					
					// filmos 참여 영화정보 저장할 변수 - StringBuilder 이용
					StringBuilder directorFilmosSb = null;
					StringBuilder actorFilmosSb = null;
					String directorFilmos;
					String actorFilmos;
					
					// 하나의 영화정보 저장할 변수 - 반복문
					JSONObject filmo;
					directorFilmosSb = new StringBuilder();
					actorFilmosSb = new StringBuilder();
					for(int i=0; i<parse_filmos.size(); i++) {
						
						
						filmo = (JSONObject) parse_filmos.get(i);
		//							System.out.println((i+1)+"번째 필모: "+ filmo);
						
						// 하나의 filmo에 담긴 정보는 movieCd|moviePartNm 로 저장 및 filmo는 ,로 구분
						// 이때 moviePartNm이 감독 / 배우 인 경우만 저장
						String movieCd = (String) filmo.get("movieCd");
						String moviePartNm = (String) filmo.get("moviePartNm");
						
						// 감독 필모리스트 저장
						if(moviePartNm.equals("감독")) {
							if(i != parse_filmos.size() -1) {
								directorFilmosSb.append(movieCd);
								directorFilmosSb.append(","); // tip) 마지막에 , 존재하여도, split 통해 배열 생성 시 , 이후 공백은 무시됨
							} else {
								directorFilmosSb.append(movieCd);
							}
						} else if(moviePartNm.equals("배우")) { // 배우 필모리스트 저장
							if(i != parse_filmos.size() -1) {
								actorFilmosSb.append(movieCd);
								actorFilmosSb.append(",");
							} else {
								actorFilmosSb.append(movieCd);
							}
						} else {
							System.out.println("상세 - 감독/배우 아님: " + moviePartNm);
							continue;
						}
					}
					directorFilmos = directorFilmosSb.toString();
					actorFilmos = actorFilmosSb.toString();
					
					// 콤마(,) 정리 
						// 문자,,문자 -> 문자,문자
						// 문자, -> 문자
					if(directorFilmos.contains(",,")) {
						directorFilmos = directorFilmos.replace(",,", ","); // 기존 변수에 저장해야함
					}
					
					if(directorFilmos.endsWith(",")) {
						directorFilmos = directorFilmos.replaceAll(",$", ""); // ,$ : 문자열 끝에 , 존재
					}
					if(actorFilmos.contains(",,")) {
						actorFilmos = actorFilmos.replace(",,", ","); // 기존 변수에 저장해야함
					}
					
					if(actorFilmos.endsWith(",")) {
						actorFilmos = actorFilmos.replaceAll(",$", ""); // ,$ : 문자열 끝에 , 존재
					}
					
					System.out.println("directorFilmos: " + directorFilmos);
					System.out.println("actorFilmos: " + actorFilmos);
					
					// pdto에 저장
					peopleVO.setSex((String) parse_peopleInfo.get("sex"));
					peopleVO.setRepRoleNm(repRoleNm);
					peopleVO.setRepRoleNm((String) parse_peopleInfo.get("repRoleNm"));
					peopleVO.setDirectorFilmos(directorFilmos);
					peopleVO.setActorFilmos(actorFilmos);
				} else {
					return null;
				}
			} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return peopleVO;
		}
		
		
// 인물 API 호출 결과 리스트 DB 저장 메서드
		@Override
		public int registerPeopleList(List<PeopleVO> peopleList) {
			
			log.info("registerPeopleList.....");
			
			return peopleMapper.insertPeopleList(peopleList);
		}
		
/* -------------- BoxOffice -------------------------------------------- */

// ** 박스오피스 호출 최초 메서드 - 메인페이지 
	@Transactional
	@Override
	public List<BoxOfficeWithStarDTO> initRegisterMainBoxOfficeList(String userid) {
		
		log.info("=============================");
		log.info("initRegisterMainBoxOfficeList............");
		log.info("=============================");
		
		String key = "2a33334b3e7bce3518472e30c1b1cb49";
		
		String inquiryDate = getTodayDate();
		
		
		List<BoxOfficeWithStarDTO> boxOfficeWithStarDTOList = registerMainBoxOfficeList(key, inquiryDate, userid);
		
		log.info("boxOfficeWithStarDTOList: " + boxOfficeWithStarDTOList);
		
		return boxOfficeWithStarDTOList;
		
	}
	
// ** 박스오피스 정보 API 호출 & DB 저장 메인 메서드
	@Transactional
	@Override
	public List<BoxOfficeWithStarDTO> registerMainBoxOfficeList(String key, String inquiryDate, String userid) {
		
		if(key == null) {
			key = "2a33334b3e7bce3518472e30c1b1cb49";
		}
		if(inquiryDate == null) {
			inquiryDate = getTodayDate();
		}
		
		// DB 반환
		List<BoxOfficeWithStarDTO> boxOfficeWithStarDTOList = getDailyBoxOfficeList(inquiryDate, userid);
		log.info("boxOfficeWithStarDTOList: " + boxOfficeWithStarDTOList);
		// 여기서 유저 평가 평점과 평균 평점 모두 들고오기
		
		if(boxOfficeWithStarDTOList.size() != 10) {
			log.info("DB : boxOfficeList.size() != 10 => API 호출");
			
			// API 호출
			List<BoxOfficeVO> boxOfficeList = searchDailyBoxOfficeList(key, inquiryDate);
			
			if(boxOfficeList.size() != 0) {
				log.info("boxOfficeList.size() != 0");
				registerBoxOfficeList(boxOfficeList);
				
				boxOfficeWithStarDTOList = getDailyBoxOfficeList(inquiryDate, userid);
				log.info("boxOfficeWithStarDTOList: " + boxOfficeWithStarDTOList);
			}
		}
		
		log.info("boxOfficeWithStarDTOList: " + boxOfficeWithStarDTOList);
		
		
		return boxOfficeWithStarDTOList;
		
	}		
// 금일 날짜 반환 메서드 - 박스오피스 API
		@Override
		public String getTodayDate() {
			String inquiryDate = "";
			
			Date now = new Date();
			
			Calendar cal = Calendar.getInstance(); // 날짜 계산 위해 Calendar 사용
			cal.setTime(now);
			
			cal.add(Calendar.DATE, -1); // 어제 날짜(오늘 날짜 -1) 을 조회날짜로 사용
			
			SimpleDateFormat sdformat = new SimpleDateFormat("yyyyMMdd");
			
			inquiryDate = sdformat.format(cal.getTime());
			
			log.info("조회날짜: " + inquiryDate);
			
			return inquiryDate;
		}
		
		
	// 해당 일자의 DB 저장된 박스오피스 리스트 반환 메서드 - 없는 경우 API 호출 진행 
		@Override
		public List<BoxOfficeWithStarDTO> getDailyBoxOfficeList(String inquiryDate, String userid) {
			log.info("getDailyBoxOfficeList...." + inquiryDate + "/" + userid);
			
			List<BoxOfficeWithStarDTO> boxOfficeWithStarDTOList = boxOfficeMapper.getBoxOfficeWithStarList(inquiryDate, userid);
			log.info("a: " + boxOfficeWithStarDTOList);
			return boxOfficeWithStarDTOList;
		}


// 박스오피스 정보 API 호출 메인 메서드
		@Transactional
		@Override
		public List<BoxOfficeVO> searchDailyBoxOfficeList(String key, String inquiryDate) {
			
			List<BoxOfficeVO> boxOfficeList = new ArrayList<BoxOfficeVO>();
			
			
			try {
				// 조회일자 yyyyMMdd String -> yyyy-MM-dd Date 변환
				// String -> util.Date -> String -> sql.Date
//				SimpleDateFormat beforeOpenDtForm = new SimpleDateFormat("yyyyMMdd");
//				SimpleDateFormat afterOpenDtForm = new SimpleDateFormat("yyyy-MM-dd");
//				java.util.Date tempDateOpenDt  = beforeOpenDtForm.parse(inquiryDate);
//				String transOpenDt = afterOpenDtForm.format(tempDateOpenDt);
//				Date showDate = Date.valueOf(transOpenDt);
				
				SimpleDateFormat formatDate = new SimpleDateFormat("yyyyMMdd");
				Date showDate = formatDate.parse(inquiryDate);
				
				
				/*	필수 요청 정보 지정*/
				// ApiUrl 
				String apiUrl = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json";
				
				/*선택 요청 정보 지정*/
				String targetDt = inquiryDate; // 조회 일자 yyyymmdd 형식
				String itemPerPage = null; // 결과 ROW 의 개수(default : “10”, 최대 : “10“)
				String multiMovieYn = null; // 다양성 영화/상업영화를 구분 조회/ “Y” : 다양성 영화 “N” : 상업영화 (default : 전체)
				String repNationCd = null; // 한국/외국 영화별로 조회 /  “K: : 한국영화 “F” : 외국영화 (default : 전체)
				String wideAreaCd = null; // 상영지역별로 조회 / 지역코드는 공통코드 조회 서비스에서 “0105000000” 로서 조회된 지역코드. (default : 전체)
				
				StringBuilder urlBuilder = new StringBuilder(apiUrl);
				
				urlBuilder.append("?" + URLEncoder.encode("key", "UTF-8") + "=" + key);
				urlBuilder.append("&" + URLEncoder.encode("targetDt","UTF-8") + "=" + URLEncoder.encode(targetDt, "UTF-8"));  // 조회 날짜 yyyymmdd 형식
//				urlBuilder.append("&" + URLEncoder.encode("itemPerPage","UTF-8") + "=" + URLEncoder.encode(itemPerPage, "UTF-8"));  // 결과 ROW 의 개수(default : “10”, 최대 : “10“)
//				urlBuilder.append("&" + URLEncoder.encode("multiMovieYn","UTF-8") + "=" + URLEncoder.encode(multiMovieYn, "UTF-8"));  // 다양성 영화/상업영화를 구분 조회/ “Y” : 다양성 영화 “N” : 상업영화 (default : 전체)
//				urlBuilder.append("&" + URLEncoder.encode("repNationCd","UTF-8") + "=" + URLEncoder.encode(repNationCd, "UTF-8"));  // 한국/외국 영화별로 조회 /  “K: : 한국영화 “F” : 외국영화 (default : 전체)
//				urlBuilder.append("&" + URLEncoder.encode("wideAreaCd","UTF-8") + "=" + URLEncoder.encode(wideAreaCd, "UTF-8"));  // 상영지역별로 조회 / 지역코드는 공통코드 조회 서비스에서 “0105000000” 로서 조회된 지역코드. (default : 전체
				
				/*GET방식(URL에 데이터 붙여 보냄)으로 전송해서 파라미터 받아오기*/
				URL url = new URL(urlBuilder.toString());
				
				// URLConnection 클래스 : 사용자 인증이나 보안이 설정되어있지 않은 웹서버에 접속하여 파일 등 다운로드하는데 많이 사용됨
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				// 요청 방법 및 속성 지정
				conn.setRequestMethod("GET"); // GET방식으로 전달
				conn.setRequestProperty("Content-type", "application/json"); // json 타입으로 전달
				//HttpURLConnection.getResponseCode() : HTTP 상태(응답) 코드 반환 (200, 404, 500 등)
				
				
				//Buffer 보조 스트림 연결
				BufferedReader br;
				if(conn.getResponseCode() == 200) { //HttpURLConnection.getResponseCode() : HTTP 상태(응답) 코드 반환 (200, 404, 500 등)
					br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				} else {
					br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
				}
				StringBuilder sb = new StringBuilder();
				String line;
				while((line = br.readLine()) != null) { // Buffer 보조 스트림의 readLine 이용. 더이상 읽어올게 없을때 까지
						// 판단과 저장을 한번에 하는 코드
					sb.append(line);
				}			
				br.close();
				conn.disconnect();
				

				String result = sb.toString(); // toString() : StringBuilder -> String
				
				log.info(inquiryDate + "일자 박스오피스 호출 결과: " + result);
				
				////////////////////////////
				/*JSON 데이터 파싱*/
				JSONParser parser = new JSONParser();
				
				JSONObject jsonObj = (JSONObject) parser.parse(result);
				
				JSONObject parse_boxOfficeResult = (JSONObject) jsonObj.get("boxOfficeResult");
				JSONArray parse_dailyBoxOfficeList = (JSONArray) parse_boxOfficeResult.get("dailyBoxOfficeList");
				
				JSONObject boxOffice = null;
				for(int i=0; i<parse_dailyBoxOfficeList.size(); i++) {
					BoxOfficeVO boxOfficeVO = new BoxOfficeVO();
					
					boxOffice = (JSONObject) parse_dailyBoxOfficeList.get(i);
//					log.info("boxoffice: " + boxOffice);
					
					int rank = Integer.parseInt((String) boxOffice.get("rank")); 
					String movieCd = (String) boxOffice.get("movieCd");
					String movieNm = (String) boxOffice.get("movieNm");
					
					
					int audiCnt = Integer.parseInt((String) boxOffice.get("audiCnt"));
					int audiAcc = Integer.parseInt((String) boxOffice.get("audiAcc"));
					
					boxOfficeVO.setShowDate(showDate);
					boxOfficeVO.setRank(rank);
					boxOfficeVO.setMovieCd(movieCd);
					boxOfficeVO.setMovieNm(movieNm);
					boxOfficeVO.setAudiCnt(audiCnt);
					boxOfficeVO.setAudiAcc(audiAcc);
					
					Date openDt = null;
					if(! boxOffice.get("openDt").equals(" ")){ // API 출 데이터 공백 문자 입력되어있음..
						log.info(movieNm  + " openDt null X : %" + boxOffice.get("openDt")+"%" );
						
						//참고)  박스오피스 "openDt":"2022-11-23" / 영화 "openDt":"20221222" 
						openDt = new SimpleDateFormat("yyyy-MM-dd").parse((String) boxOffice.get("openDt")); // String -> util.Date는 (Date) 로 직접 형변환 불가  
						log.info("openDt 2:" + openDt);
						boxOfficeVO.setOpenDt(openDt);
						
						
					} else {
						log.info(movieNm  + " openDt null O : %" + boxOffice.get("openDt")+"%" );
						
						
					}
					
				////////////////////////////////////////////////////////////////
				// 박스오피스 영화 -> 영화 API 호출해 DB 저장
				// SQL 구문) INSERT IGNORE 통해 중복 검사
				
				// 개봉일자 openDt  yyyy-MM-dd Date -> yyyy String 변환
				// sql.Date -> String -> util.Date -> String 
				
//				SimpleDateFormat movieBeforeOpenDtForm = new SimpleDateFormat("yyyy-MM-dd");
//				SimpleDateFormat movieAfterOpenDtForm = new SimpleDateFormat("yyyy");
////				String oepnDtStr = openDt.toString();
//				java.util.Date OpenDtUtilDate  = movieBeforeOpenDtForm.parse(oepnDtStr);
//				String OpenDtStr = movieAfterOpenDtForm.format(OpenDtUtilDate);
				
//				log.info("OpenDtStr: " + OpenDtStr);
				log.info("api openDt: " + openDt);
				

			/* 영화 API 호출 */
				MovieVO movieVO = searchMovie(key, movieNm, movieCd);
				log.info("movieVO: " + movieVO);
				
			
				
				log.info("////////movieVO: " + movieVO);
//				if(movieVO != null) 
				if(movieVO != null) {
					if(movieVO.getMovieCd() != null) {
						
						// 사용x:  new SimpleDateFormat("yyyy-MM-dd") 포맷을 잘못 입력해 부정확한 값 발생한 것)
						// boxOffice openDt 업데이트 : boxOffice API 오픈일 부정확함. -> 영화 api openDt 정확함
//						log.info("api openDt: " + openDt +"/movieVO.getOpenDt(): " + movieVO.getOpenDt());
//						boxOfficeVO.setOpenDt(movieVO.getOpenDt());
						
						log.info("////////// null X");
						registerMovie(movieVO);
						if(movieVO.getDirectorDetail() != null && movieVO.getActorDetail() != null) {
							
							List<PeopleVO> peopleList = searchPeopleList(key, movieVO);
							
							log.info("---------------------------------");
							log.info("박스오피스 movieVO: " + movieVO);
							log.info("---------------------------------");
							
							log.info("peopleList 크기: " + peopleList.size());
							for(PeopleVO peopleVO : peopleList) {
								log.info((peopleList.indexOf(peopleVO)+1) +". " + peopleVO);
							} 
							
							log.info("---------------------------------");
							
							log.info("DB 저장 시작");
							
							if(peopleList.size() != 0) {
								
								registerPeopleList(peopleList);
								
								/* 배우 배역 비중 저장! */
								int peopleListLength = peopleList.size();
								for(int j=0; j<peopleListLength; j++) {
									PeopleVO peopleVO = peopleList.get(j);
									
									
									if(peopleVO.getRepRoleNm().equals("배우") && ! peopleVO.getActorFilmos().replace(" ", "").equals("")) {
										peopleMapper.updateActorRoleImportance(peopleVO.getPeopleCd());
									}
								}
							}
							
							log.info("DB 저장 완료");
						}
						
						
					}
				} else {
					// 사용x:  new SimpleDateFormat("yyyy-MM-dd") 포맷을 잘못 입력해 부정확한 값 발생한 것)
					// boxOffice openDt 업데이트 : boxOffice API 오픈일 부정확함. -> 영화 api openDt 정확함
					// DB에서 저장되어 있는 movieVO 가져와서 업데이트
//					MovieVO dbMovieVO = movieMapper.read(movieCd);
//					log.info("dbMovieVO.getOpenDt(): " + dbMovieVO.getOpenDt());
//					boxOfficeVO.setOpenDt(dbMovieVO.getOpenDt());
				}
				
				
				
				////////////////////////////////////////////////////////////////
				
				//log.info(boxOfficeVO);
				
				boxOfficeList.add(boxOfficeVO);
					
					
					
				}
				
				/*DB 저장*/
//				InsertBoxOffice(boxOfficeList);
				
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (java.text.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			log.info("----------------------------------------");
			log.info("----------------------------------------");
			
			boxOfficeList.forEach(boxOffice -> log.info(boxOffice));
			
			return boxOfficeList;
			
		}	
		
		
// 박스오피스 API 호출 중 영화 정보 API 호출 메서드 - 영화명, 영화코드		
		@Override
		public MovieVO searchMovie(String key, String movieNm, String movieCd) {
			
			
			MovieVO movieVO = null;
			
			String apiUrl = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieList.json";
			
			// 선택 요청 정보 지정
//			String curPageVal = "1"; // 현재 페이지를 지정 default : “1”
			String itemPerPageVal = null; // 출력 결과 Row의 개수 / default : 10 / Max : 100
			String movieNmStr = movieNm; // 영화명 조회 (UTF-8 인코딩)
			String directorNm = null; // 감독명 조희 (UTF-8 인코딩)
			String openStartDtVal = null; // YYYY형식의 조회시작 개봉연도
			String openEndDtVal = null; // YYYY형식의 조회종료 개봉연도
			String prdtStartYear = null; // YYYY형식의 조회시작 제작연도
			String prdtEndYear = null; // YYYY형식의 조회종료 제작연도
			String repNationCd = null; // 영화 국적. 국적코드는 공통코드 조회 서비스에서 “2204” 로서 조회된 국적코드입니다. (default : 전체)
			String movieTypeCd = null; // 영화 유형 코드. 공통코드 조회 서비스에서 “2201”로서 조회된 영화유형코드입니다.(default: 전체)
			
			// StringBuilder : 일반적으로 String객체 연산 시 새로운 String 객체 생성됨. 
			// StringBuilder 사용 시 새로운 객체 생성되지 않고 기존 데이터에 더하는 방식. 성능 개선
			StringBuilder urlBuilder = new StringBuilder(apiUrl);
			
			try {
				urlBuilder.append("?" + URLEncoder.encode("key", "UTF-8") + "=" + key);
//				urlBuilder.append("&" + URLEncoder.encode("curPage","UTF-8") + "=" + URLEncoder.encode(curPageVal, "UTF-8"));  // 현재 페이지를 지정 default : “1”
//				urlBuilder.append("&" + URLEncoder.encode("itemPerPage","UTF-8") + "=" + URLEncoder.encode(itemPerPageVal, "UTF-8"));  // 출력 결과 Row의 개수 / default : 10
	        urlBuilder.append("&" + URLEncoder.encode("movieNm","UTF-8") + "=" + URLEncoder.encode(movieNmStr, "UTF-8"));  // 영화명 조회 (UTF-8 인코딩)
//	        urlBuilder.append("&" + URLEncoder.encode("directorNm","UTF-8") + "=" + URLEncoder.encode(directorNm, "UTF-8")); // 감독명 조희 (UTF-8 인코딩)
//				urlBuilder.append("&" + URLEncoder.encode("openStartDt","UTF-8") + "=" + URLEncoder.encode(openStartDtVal, "UTF-8"));  // YYYY형식의 조회시작 개봉연도
//				urlBuilder.append("&" + URLEncoder.encode("openEndDt","UTF-8") + "=" + URLEncoder.encode(openEndDtVal, "UTF-8")); // YYYY형식의 조회종료 개봉연도
//	        urlBuilder.append("&" + URLEncoder.encode("prdtStartYear","UTF-8") + "=" + URLEncoder.encode(prdtStartYear, "UTF-8")); // YYYY형식의 조회시작 제작연도
//	        urlBuilder.append("&" + URLEncoder.encode("prdtEndYear","UTF-8") + "=" + URLEncoder.encode(prdtEndYear, "UTF-8")); // YYYY형식의 조회종료 제작연도
//	        urlBuilder.append("&" + URLEncoder.encode("repNationCd","UTF-8") + "=" + URLEncoder.encode(repNationCd, "UTF-8")); // 영화 국적. 국적코드는 공통코드 조회 서비스에서 “2204” 로서 조회된 국적코드입니다. (default : 전체)
//	        urlBuilder.append("&" + URLEncoder.encode("movieTypeCd","UTF-8") + "=" + URLEncoder.encode(movieTypeCd, "UTF-8")); // 영화 유형 코드. 공통코드 조회 서비스에서 “2201”로서 조회된 영화유형코드입니다.(default: 전체)
			
				
			//GET방식(URL에 데이터 붙여 보냄)으로 전송해서 파라미터 받아오기
				// urlBuilder.toString() : String으로 변환해 반환해주는 매서드
				URL url = new URL(urlBuilder.toString());
				
				// URLConnection 클래스 : 사용자 인증이나 보안이 설정되어있지 않은 웹서버에 접속하여 파일 등 다운로드하는데 많이 사용됨
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				// 요청 방법 및 속성 지정
				conn.setRequestMethod("GET"); // GET방식으로 전달
				conn.setRequestProperty("Content-type", "application/json"); // json 타입으로 전달
				//HttpURLConnection.getResponseCode() : HTTP 상태(응답) 코드 반환 (200, 404, 500 등)
				
				
			//Buffer 보조 스트림 연결
				BufferedReader br;
				if(conn.getResponseCode() == 200) { //HttpURLConnection.getResponseCode() : HTTP 상태(응답) 코드 반환 (200, 404, 500 등)
					br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				} else {
					br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
				}
				StringBuilder sb = new StringBuilder();
				String line;
				while((line = br.readLine()) != null) { // Buffer 보조 스트림의 readLine 이용. 더이상 읽어올게 없을때 까지
						// 판단과 저장을 한번에 하는 코드
					sb.append(line);
				}			
				br.close();
				conn.disconnect();
				

				String result = sb.toString(); // toString() : StringBuilder -> String
				
				log.info(result);
				
			////////////////////////////////
			/*JSON 데이터 파싱*/
				JSONParser parser = new JSONParser();
				
				////////////에러발생 코드 - 톰캣 lib에 json simple jar 파일 넣으니 해결됨
				JSONObject jsonObj = (JSONObject) parser.parse(result);
				// 분해
				JSONObject parse_movieListResult = (JSONObject) jsonObj.get("movieListResult"); // key name
				// 검색조건에 해당되는 총 영화 수
				//int totCnt = Integer.parseInt((String) parse_movieListResult.get("totCnt")); // Long.intValue() : Long -> int
				int totCnt = ((Long) parse_movieListResult.get("totCnt")).intValue(); // Long.intValue() : Long -> int
				log.info("검색 조건 해당하는 총 영화 수: " + totCnt);
				
				// 영화 리스트 배열 형식
				JSONArray parse_movieList = (JSONArray) parse_movieListResult.get("movieList"); // key name
				
				// 하나의 영화 정보 저장할 변수
				JSONObject JObj_movie = null;
				log.info("화면 출력한 영화 수: " + parse_movieList.size());
				log.info("--------------------------------");
				
			
				for(int i=0; i<parse_movieList.size(); i++) {
					
					// 영화 정보 저장할 movieVO 객체 선언
					movieVO = new MovieVO();
		
					
					JObj_movie = (JSONObject) parse_movieList.get(i);
					
					// 영화명은 화면 출력을 위해 별도 변수로 선언
					String parse_movieNm = (String) JObj_movie.get("movieNm");
					String parse_movieCd = (String) JObj_movie.get("movieCd");
					
				// 파라미터로 받은 영화명과 영화코드 모두 일치하는 영화 찾기
					if(parse_movieCd.equals(movieCd)) {
						
						log.info("---------------------------------");
						log.info(i+1+ "번째 영화 - " + parse_movieNm);
						
						
						movieVO.setMovieCd(parse_movieCd);
						movieVO.setMovieNm(parse_movieNm);
						movieVO.setMovieNmEn((String) JObj_movie.get("movieNmEn"));
						
						// 제작연도
						if(JObj_movie.get("prdtYear").equals("")) {
							log.info("prdtYear 없음 modto: "+JObj_movie );
						} else {
							movieVO.setPrdtYear(Integer.parseInt((String) JObj_movie.get("prdtYear")));
						}
						
						
						// 개봉일자 yyyyMMdd String -> yyyy-MM-dd Date 변환
						// String -> util.Date -> String -> sql.Date
						//JSONObject parse_openDt = (JSONObject) JObj_movie.get("openDt");
						//if(! JObj_movie.get("openDt").equals("")) {
						if(JObj_movie.containsKey("openDt")) { // "prdtStatNm":"개봉예정" 인경우 openDt key 없음
//							SimpleDateFormat beforeOpenDtForm = new SimpleDateFormat("yyyyMMdd");
//							SimpleDateFormat afterOpenDtForm = new SimpleDateFormat("yyyy-MM-dd");
//							java.util.Date tempDateOpenDt  = beforeOpenDtForm.parse((String) JObj_movie.get("openDt"));
//							String transOpenDt = afterOpenDtForm.format(tempDateOpenDt);
//							Date openDt = Date.valueOf(transOpenDt);
							
							Date openDt = new SimpleDateFormat("yyyyMMdd").parse((String) JObj_movie.get("openDt")); // String -> util.Date는 (Date) 로 직접 형변환 불가
						
							movieVO.setOpenDt(openDt);
						} 
						
						
						movieVO.setGenreAlt((String) JObj_movie.get("genreAlt"));
						// 장르 - 성인물 제외
							// genreAlt
						if(movieVO.getGenreAlt().contains("성인물(에로)")) {
							
							log.info("Result) 0_1. 성인물 -> 제외");
							log.info("");
							continue;
						}
						
						movieVO.setRepNationNm((String) JObj_movie.get("repNationNm"));
						// 한국/일본 영화 중 영화명에 성인물 관련 키워드 포함 시 제외
						// repNationNm, movieNm
						String[] adultKeywords = {"섹스", "음란", "불륜", "마사지", "맛사지", "가슴", "욕망", "음란", "능욕", "정사", "형수", "처형", "애원", "유부녀", "19금", "은밀한 유혹", "욕정", "숙모", "스와핑", 
								"새댁", "노리개", "젖은", "장모", "엄마친구", "남자사냥", "며느리", "제부", "맛있는", "시아버지", "노출", "화끈", "여직원", "새엄마", "누나 친구", "하던 날", "그녀와 하던", "몸 로비", "새누나",
								"엄마친구", "변태", "치한", "연상녀", "오피스걸", "오피스녀", "오르가즘", "섹시", "방문판매", "밀애","아주버님","탐하","온천","과부","사모님","명기","몸으로","출장","미망인","거칠게","굶주린",
								"창녀","신음","거유","야한","에로","여사원","러브호텔","바람난","여주인","기둥서방","해주는","음탕","성노예","대물","원나잇","드릴게요","부장님","잘하는","C컵","D컵","E컵","F컵","G컵","H컵","J컵","K컵","도련님",
								"그라비아", "육체 세일즈", "수리기사","거근", "관음", "부인","부부","교환","쾌락","애집","당하","당한","양기","음기","밝히는","간호사","몰래","벌려","물건","젊은 엄마","전철","동거","착한","남편","야릇","엄마",
								"AV스타", "은밀한", "방석집"};
						int adultKeywordResult = 0;
						for(String adultKeyword : adultKeywords) {
							if(movieVO.getRepNationNm().equals("한국") || movieVO.getRepNationNm().equals("일본")) {
								if(movieVO.getMovieNm().contains(adultKeyword)) {
									adultKeywordResult += 1;
									break;
								}
							}
							
						}
						if(adultKeywordResult > 0) {
							log.info("Result) "+movieVO.getRepNationNm()+" - 영화명 성인물 키워드 포함 -> 제외");
							log.info("");
							continue;
						}
						
						movieVO.setTypeNm((String) JObj_movie.get("typeNm"));
						movieVO.setPrdtStatNm((String) JObj_movie.get("prdtStatNm"));
						movieVO.setNationAlt((String) JObj_movie.get("nationAlt"));
						movieVO.setRepGenreNm((String) JObj_movie.get("repGenreNm"));
						movieVO.setCompanyCd((String) JObj_movie.get("companyCd"));
						movieVO.setCompanyNm((String) JObj_movie.get("companyNm"));
						
					// 영화 영문명 없음 -> 제외
						// 왜 제외?
			//			if(movieVO.getMovieNmEn().equals("")) {
			//				log.info("Result) 영화 영문명 없음 -> 제외");
			//				log.info();
			//				continue;
			//			}
						
						
						

					// 영화 상세 정보 API 메서드 호출
					/* searchMovieDetail(MovieVO movieVO) 호출*/
						// DB에 해당 영화 이미 존재 시 생략 -> continue;
						// 개봉예정 인경우 -> 상세정보까지 호출 후 DB 재저장
						
						MovieVO dbMovieVO = movieMapper.read(movieVO.getMovieCd());
						if(dbMovieVO == null || dbMovieVO.getPrdtStatNm().equals("개봉예정")) {
							movieVO = searchMovieDetail(key, movieVO);
						} else {
							log.info("Result) 이미 DB에 저장 -> 영화 상세정보 API 호출X");
							log.info("");
							
							movieVO = null; // null일 경우 DB 저장 메서드 수행X
							
							continue;
						}
						
//						movieVO = searchMovieDetail(key, movieVO);
						
						
					// 네이버 영화 검색 api 및 네이버 크롤링 함수 호출
						/* getNaverMovieLink(String movieNm, String peopleNm) 호출 - String link 반환 */
						String link = "";
						link = searchNaverMovieLink(movieVO);
					
						// 네이버 영화 link 정상 저장 확인 -> 저장된 경우만 movieList 저장
						if(link.equals("")) {
							log.info("Result) 2. 네이버 영화 link 없음 -> 제외");
							log.info("");
							continue;
						}
						
						/* crawlingNaverMoviePosterStory(link, movieVO) 호출*/
					//	log.info("2. 네이버 영화 link 저장 완료: " + link);
						movieVO = crawlingNaverMoviePosterStory(link, movieVO);
					//	log.info("2) getMovieUrlStory(link, movieVO) 실행 완료");
						
						// 네이버 영화 장르 : 에로 인 경우 null 반환받음 -> 제외
						if(movieVO != null) {
							log.info("Result) movieVO: " + movieVO);
							log.info("");	
												
							
						}
						
						break;
					} 
					
					
		
				} // for(int i=0; i<parse_movieList.size(); i++)
				
				

				
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (java.text.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
			
			return movieVO;
		}
		
// 영화 API 호출 결과 DB 저장 메서드
		@Override
		public int registerMovie(MovieVO movieVO) {
			
			log.info("registerMovie.....");
			
			return movieMapper.insertMovie(movieVO);
		}
		
		
// 오버라이딩 - 인물 정보 API 호출 메인 메서드	
		@Override
		public List<PeopleVO> searchPeopleList(String key, MovieVO movieVO) {
			// 리턴 배열 선언
			List<PeopleVO> peopleList = new ArrayList<>();
		
			log.info("-------------------------------------------");
			log.info("배우 검색할 영화명: " + movieVO.getMovieNm());
			
			
			
			String movieNm =  movieVO.getMovieNm();
			
			String directorDetail = movieVO.getDirectorDetail(); // 국문|영문,국문|영문,... 꼴
			String actorDetail = movieVO.getActorDetail();
			
			// 감독명 국문만 저장
			StringBuilder SB_directors = new StringBuilder();
			String[] directors = directorDetail.split(",");
			
			for(int i=0; i<directors.length; i++) {
				String directorNm = directors[i].split("\\|")[0];
				SB_directors.append(directorNm);
				
				if(i != directors.length -1) {
					SB_directors.append(",");
				}
			}
			
			// 배우명 국문만 저장
			StringBuilder SB_actors = new StringBuilder();
			String[] actors = actorDetail.split(",");
			
			
			for(int i=0; i<actors.length; i++) {
				String actorNm = actors[i].split("\\|")[0];
				SB_actors.append(actorNm);
				
				if(i != actors.length -1) {
					SB_actors.append(",");
				}
			}
			
			log.info("SB_directors: " + SB_directors);
			log.info("SB_actors: " + SB_actors);
			
			StringBuilder peopleNmSb = new StringBuilder();
			peopleNmSb.append(SB_directors);
			peopleNmSb.append(",");
			peopleNmSb.append(SB_actors);
			String[] directorActorNms = peopleNmSb.toString().split(",");
			
			// String[] 중복 제거
			// Arrays.Stream 이용 : 배열 -> stream -> distinct() 중복제거 -> 배열
			String[] resultRirectorActorNms = Arrays.stream(directorActorNms).distinct().toArray(String[]::new);
			log.info("resultRirectorActorNms: " + Arrays.toString(resultRirectorActorNms));
			log.info("");
			
			// 리턴 PeopleVO 선언
			PeopleVO peopleVO = null;
			
			/* (X 모두 호출) 해당 directorActorNm와 movieCd에 해당하는 영화인 people DB에 존재하지 않는 경우만 API 호출*/
			String directorActorNm = "";
			for(int j=0; j<resultRirectorActorNms.length;j++) {
				directorActorNm = resultRirectorActorNms[j];
				
				peopleVO = new PeopleVO();
				
				// UPSERT 이용 : DB 기존재 시 UPDATE, 미존재 시 ISNERT 
					// API 호출 횟수 소진하더라도 배우 필모 업데이트 필요하다 판단	
//						if(mapper.get(directorActorNm) == null) {
//							log.info("null: " + directorActorNm );
//							log.info("");
//						} else {
//							log.info("get: " + directorActorNm);
//							log.info("");
//						}
				
				
				
			///////////////////////////////////////////////////
			/* 영화인 정보 API 호출 */
				
				/*	필수 요청 정보 지정*/
				// ApiUrl 
				String apiUrl = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/people/searchPeopleList.json";
				
				
				/*선택 요청 정보 지정*/
//						String curPage = "1"; // 현재 페이지를 지정 default : “1”
//						String itemPerPage = "10"; // 출력 결과 Row의 개수 / default : 10 / Max : 100
				String peopleNm = directorActorNm; // 영화인명
				String filmoNames = movieNm; // 필모리스트 (영화명)
				
				
				// StringBuilder : 일반적으로 String객체 연산 시 새로운 String 객체 생성됨. 
				// StringBuilder 사용 시 새로운 객체 생성되지 않고 기존 데이터에 더하는 방식. 성능 개선
				StringBuilder urlBuilder = new StringBuilder(apiUrl);
				
				
				try {
					urlBuilder.append("?" + URLEncoder.encode("key", "UTF-8") + "=" + key);
					//						urlBuilder.append("&" + URLEncoder.encode("curPage","UTF-8") + "=" + URLEncoder.encode(curPage, "UTF-8"));  // 현재 페이지를 지정 default : “1”
					//						urlBuilder.append("&" + URLEncoder.encode("itemPerPage","UTF-8") + "=" + URLEncoder.encode(itemPerPage, "UTF-8"));  // 출력 결과 Row의 개수 / default : 10
					urlBuilder.append("&" + URLEncoder.encode("peopleNm","UTF-8") + "=" + URLEncoder.encode(peopleNm, "UTF-8"));  // 영화인명 조회 (UTF-8 인코딩)
					urlBuilder.append("&" + URLEncoder.encode("filmoNames","UTF-8") + "=" + URLEncoder.encode(filmoNames, "UTF-8")); // 필모리스트 (영화명) 조회 (UTF-8 인코딩)
					
					/////////////////////////////////////////////////////////////////
					/*GET방식(URL에 데이터 붙여 보냄)으로 전송해서 파라미터 받아오기*/
					// urlBuilder.toString() : String으로 변환해 반환해주는 매서드
					URL url = new URL(urlBuilder.toString());
					
					// URLConnection 클래스 : 사용자 인증이나 보안이 설정되어있지 않은 웹서버에 접속하여 파일 등 다운로드하는데 많이 사용됨
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					// 요청 방법 및 속성 지정
					conn.setRequestMethod("GET"); // GET방식으로 전달
					conn.setRequestProperty("Content-type", "application/json"); // json 타입으로 전달
					//HttpURLConnection.getResponseCode() : HTTP 상태(응답) 코드 반환 (200, 404, 500 등)
					
					
					//Buffer 보조 스트림 연결
					BufferedReader br;
					if(conn.getResponseCode() == 200) { //HttpURLConnection.getResponseCode() : HTTP 상태(응답) 코드 반환 (200, 404, 500 등)
						br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
					} else {
						br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
					}
					StringBuilder sb = new StringBuilder();
					String line;
					while((line = br.readLine()) != null) { // Buffer 보조 스트림의 readLine 이용. 더이상 읽어올게 없을때 까지
						// 판단과 저장을 한번에 하는 코드
						sb.append(line);
					}			
					br.close();
					conn.disconnect();
					
					
					String result = sb.toString(); // toString() : StringBuilder -> String
					
					
				////////////////////////////////////
				/// key 일일한도 초과 에러 발생 시 null 리턴해 메서드 종료시킴
					log.info("영진위 영화인 결과 출력 : " + result);
					if(result.contains("320011")) {
						return null;
					}
					
				///////////////////////////
				/* JSON 데이터 파싱 */
					JSONParser parser = new JSONParser();
					
					JSONObject jsonObj = (JSONObject) parser.parse(result);
					// 분해
					JSONObject parse_peopleListResult = (JSONObject) jsonObj.get("peopleListResult");
					// 검색조건에 해당되는 총 영화인 수
					int totCnt = ((Long) parse_peopleListResult.get("totCnt")).intValue();
					//						log.info("검색조건에 해당되는 총 영화인 수: " + totCnt);
					
					////////////////////////
					// 영화인 API 호출 결과 존재하는 경우만 이하 코드 실행
						// 영화인명과 영화명을 조건으로 API 호출했으나 검색조건 해당하는 영화인 출력되지 않은 경우
						// => 영진위 영화 정보에는 영화인 이름 저장되어 있으나 영진위 영화인 정보에는 없는 경우
					if(totCnt != 0) {
						// 영화인 리스트 배열 형식
						JSONArray parse_peopleList = (JSONArray) parse_peopleListResult.get("peopleList");
						//							log.info("parse_peopleList: " + parse_peopleList);
						//							log.info("화면 출력한 영화인 수: " + parse_peopleList.size());
						// 하나의 영화인 정보 저장할 변수 - 반복문
						JSONObject people = (JSONObject) parse_peopleList.get(0);
						
						log.info((j+1)+"/"+resultRirectorActorNms.length +". " + (String) people.get("peopleNm") + " - " + movieNm);
						log.info((String) people.get("peopleCd"));
						
						peopleVO.setPeopleCd((String) people.get("peopleCd"));
						peopleVO.setPeopleNm((String) people.get("peopleNm"));
						peopleVO.setPeopleNmEn((String) people.get("peopleNmEn"));
						
					//////////////////////////////////////////
					/*	filmoNames 2개 이상인 영화인만 API 호출 */
						String[] filmoNamesArr = ((String) people.get("filmoNames")).split("\\|");
						log.info(filmoNamesArr.length);
						if(filmoNamesArr.length >= 2) {
							
						///////////////////////////////////////
						// 영화인 상제 정보 API 호출
						/*	searchPeopleDetail(key, peopleVO) 호출	*/
							// 감독/배우인 경우에만 peopleVO 반환받음, 아닌 경우 null 반환받음
							peopleVO = searchPeopleDetail(key, peopleVO);
							
							if(peopleVO == null) {
								log.info("감독/배우 아님 -> 제외");
								log.info("");
							} else {
								log.info("Result) peopleVO: " + peopleVO);
								
							
								
								// 최종 저장
								peopleList.add(peopleVO);
								//									log.info("peopleVO 저장 : " + peopleList);
								log.info("");
							}
						} else {
							log.info("Result) 필모 2개 미만 -> 상세 영화인 API 호출X");
							log.info("");
						}
					} else {
						log.info("Result) "+directorActorNm+" - "+movieNm+" -> 영진위 영화인 API 목록에 존재X");
						log.info("");
					}
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				
			}
			
			log.info("===================================");
			log.info("최종 검색된 영화인 수 : " + peopleList.size());
			peopleList.forEach(people -> log.info(people)); // forEach(): 주어진 함수를 배열 요소 각각에 대해 실행
			
		
			return peopleList;
		}

		
// 박스오피스 API 호출 결과 리스트 DB 저장 메서드
		@Override
		public int registerBoxOfficeList(List<BoxOfficeVO> boxOfficeList) {
		
			log.info("registerBoxOfficeList....");
			
			return boxOfficeMapper.insertBoxOfficeList(boxOfficeList);
		}


		@PostConstruct
		@Override
		public void testPost() {
			log.info("초기화 메서드");
			System.out.println("초기화 메서드2");
			
		}


		

	


		


		

}
