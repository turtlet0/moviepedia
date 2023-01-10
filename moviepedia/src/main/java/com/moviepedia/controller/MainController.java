package com.moviepedia.controller;

import java.security.Principal;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
// Movie 및 People 담당 Controller
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.moviepedia.aop.ExeTimer;
import com.moviepedia.domain.CommentInfoDTO;
import com.moviepedia.domain.CommentVO;
import com.moviepedia.domain.MemberVO;
import com.moviepedia.domain.MovieVO;
import com.moviepedia.domain.PeopleVO;
import com.moviepedia.domain.SearchMovieListDTO;
import com.moviepedia.domain.UserDTO;
import com.moviepedia.exception.signUpFailedException;
import com.moviepedia.mapper.MemberMapper;
import com.moviepedia.service.UserService;
import com.moviepedia.service.CommentService;
import com.moviepedia.service.ManageService;
import com.moviepedia.service.MemberService;
import com.moviepedia.service.MovieService;
import com.moviepedia.service.PeopleService;
import com.moviepedia.service.ReplyService;
import com.moviepedia.service.SearchService;
import com.moviepedia.service.StarRatingService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@AllArgsConstructor
@Log4j
public class MainController {

	private MovieService movieService;
	// MovieController는 MovieService에 대해서 의존적이므로 @AllArgsConstructor를 이용해 생성자 만들고
	// 자동으로 주입하도록함
	// @Setter(onMethod_ = {@Autowired}를 이용할 수도 있음
	// 또는 @inject 이용 가능

	private MemberService memberService;
	
	private PeopleService peopleService;
	
	private CommentService commentService;
	
	private ReplyService replyService;
	
	private StarRatingService starRatingService;
	
	private SearchService searchService;
	
	private UserService userService;
	
	private ManageService manageService;
	

	
//	@ExceptionHandler(SQLIntegrityConstraintViolationException.class)
//	public void except(Exception e) {
//		log.error("error Exception......." + e.getMessage());
//		log.info("info Exception......." + e.getMessage());
//		
//		String userid = "mem6";
//		String randomString = "abcd";
//		log.error(userid + "/" + randomString);
//		memberService.testRandString(userid, randomString);
//		
////		return new ResponseEntity<Integer>(memberService.testRandString(userid, randomString), HttpStatus.OK);
//		
//	}
	

	@GetMapping("/test/starRating")
	public String testStarRating() {
		return "testStarRating";
	}
	/* ------------- Movie ------------ */
	@GetMapping("/")
	public String main(Model model, Principal principal, RedirectAttributes rttr)  {
		/* tip) 전달된 파라메터 (movieCd) 저장 방법 */
		// @RequestParam("파라미터명") 타입 변수명 꼴 이용 (@ModelParam("파라미터명") 써도됨)
		// = request.getparameter()와 동일 / @ 사용 시 전달되는 데이터 타입에 따른 형변환 가능(문자, 숫자, 날짜)

		/* DB 정보 가져오기 */
		
		
		String userid = null;
		
		if (principal != null) {
		
			log.info("타입정보 : " + principal.getClass());
			log.info("ID정보 : " + principal.getName());
			
			model.addAttribute("userInfo", memberService.get(principal.getName()));
			
			userid = principal.getName();
			
			// only 로그인 유저) 유저 미평가 영화 중 선호 감독 영화 리스트 출력
			model.addAttribute("favoriteDirectorSMovieList", movieService.getFavoriteDirectorSMovieList(userid));
			
			// only 로그인 유저) 유저 미평가 영화 중 선호 배우 영화 리스트 출력
			model.addAttribute("favoriteActorSMovieList", movieService.getFavoriteActorSMovieList(userid));
			
			// only 로그인 유저) 유저 미평가 영화 중 선호 배우 영화 리스트 출력
			model.addAttribute("favoriteGenreMovieList", movieService.getFavoriteGenreMovieList(userid));
			
		} 
		// 박스오피스
		model.addAttribute("boxOfficeWithStarList", manageService.initRegisterMainBoxOfficeList(userid));
					
		// 유저 미평가 영화 중 평균 평점 상위 영화 리스트 출력
		model.addAttribute("highStarRatingMovieList", movieService.getHighStarRatingMovieList(userid));
					
		return "contents/main";

	}
	
	
	
//	public void movieInfo(@RequestParam("movieCd") String movieCd
	// 해당 영화 조회 페이지
	// 조회 일반적으로 GET 방식 이용
	// 해당 movieCd의 Movie 정보와 관련 People 정보 반환
	@GetMapping("/movieInfo/{movieCd}")
	public String movieInfo(@PathVariable("movieCd") String movieCd, Model model, Principal principal)  {
		/* tip) 전달된 파라메터 (movieCd) 저장 방법 */
		// @RequestParam("파라미터명") 타입 변수명 꼴 이용 (@ModelParam("파라미터명") 써도됨)
		// = request.getparameter()와 동일 / @ 사용 시 전달되는 데이터 타입에 따른 형변환 가능(문자, 숫자, 날짜)

		log.info("C. movieCd: " + movieCd);

		
		/* DB 정보 가져오기 */
		
		model.addAttribute("movie", movieService.get(movieCd));

		model.addAttribute("peopleList", peopleService.getList(movieCd));

		model.addAttribute("starAvgCnt", starRatingService.getAvgCnt(movieCd));
		
		model.addAttribute("movieStarRatingAnalysis", starRatingService.getMovieStarRatingAnalysis(movieCd));
		
		if (principal != null) {
			System.out.println("타입정보 : " + principal.getClass());
			System.out.println("ID정보 : " + principal.getName());
			
			model.addAttribute("userInfo", memberService.get(principal.getName()));

//			 model.addAttribute("comment", commentService.getUser(principal.getName(), movieCd));
			
			// 유저 별점 평가 점수 조회
			model.addAttribute("starRating", starRatingService.get(principal.getName(), movieCd));
			

		}

		return "contents/movieInfo";

	}
	
	
	// Ajax_특정 영화의 코멘트 리스트 페이지
		@PostMapping("/ajax/movies/comments")
		public String ajax_movieComments(@RequestParam(name = "movieCd") String movieCd,
				@RequestParam(name = "orderBy") int orderBy,
				@RequestParam(name = "currentCnt") int currentCnt,
				@RequestParam(name = "additionalCnt") int additionalCnt,
				Model model, Principal principal)  {
			
			String userid = null;
			
			if (principal != null) {
				System.out.println("타입정보 : " + principal.getClass());
				System.out.println("ID정보 : " + principal.getName());
				
				userid = principal.getName();
				
				model.addAttribute("userInfo", memberService.get(userid));
			}
			
			model.addAttribute("movieCommentList", commentService.getAdditionalList(movieCd, orderBy, currentCnt, additionalCnt, userid));
			
			return "ajax/ajaxMovieCommentList";
		}
	
	
	

	/* ------------- Movie ------------ */
	
	/* ------------- Review ------------ */
	// 영화 리뷰 페이지
	@GetMapping("/review")
	@PreAuthorize("hasRole('ROLE_MEMBER')")
	public String movieInfo(Model model, Principal principal) {
		/* tip) 전달된 파라메터 (movieCd) 저장 방법 */
		// @RequestParam("파라미터명") 타입 변수명 꼴 이용 (@ModelParam("파라미터명") 써도됨)
		// = request.getparameter()와 동일 / @ 사용 시 전달되는 데이터 타입에 따른 형변환 가능(문자, 숫자, 날짜)


		/* DB 정보 가져오기 */
	
		if (principal != null) {
			System.out.println("타입정보 : " + principal.getClass());
			System.out.println("ID정보 : " + principal.getName());

			String userid = principal.getName();
			
			model.addAttribute("userInfo", memberService.get(userid));
			
			model.addAttribute("totalShowTm", userService.getTotalShowTm(userid));

		}

		return "contents/review";

	}
	
	// Ajax_랜덤 조회 영화 리스트
		@PostMapping("/ajax/review/movies")
		public String ajax_reviewMovies(
				@RequestParam(name = "orderBy") int orderBy,
				@RequestParam(name = "orderBy1") int orderBy1,
				@RequestParam(name = "orderBy2") int orderBy2,
				@RequestParam(name = "currentSqnc") int currentSqnc,
				@RequestParam(name = "additionalCnt") int additionalCnt,
				Model model, Principal principal)  {
			
			
			if (principal != null) {
				System.out.println("타입정보 : " + principal.getClass());
				System.out.println("ID정보 : " + principal.getName());
				
				String userid = principal.getName();
				
				model.addAttribute("userInfo", memberService.get(userid));
		
				model.addAttribute("reviewMovieList", movieService.getReviewList(userid, orderBy, orderBy1, orderBy2, currentSqnc, additionalCnt));
			}
			
	
			
			return "ajax/ajaxReviewMovieList";
		}
		
	/* ------------- Review ------------ */
	
	
	
	/* ------------- People ------------ */
	@GetMapping("/people/{peopleCd}")
	public String peopleInfo(@PathVariable("peopleCd") String peopleCd, Model model, Principal principal)  {
		/* tip) 전달된 파라메터 (movieCd) 저장 방법 */
		// @RequestParam("파라미터명") 타입 변수명 꼴 이용 (@ModelParam("파라미터명") 써도됨)
		// = request.getparameter()와 동일 / @ 사용 시 전달되는 데이터 타입에 따른 형변환 가능(문자, 숫자, 날짜)

		log.info("C. peopleCd: " + peopleCd);

		
		/* DB 정보 가져오기 */
		
//		model.addAttribute("movie", movieService.get(movieCd));
//
//		model.addAttribute("peopleList", peopleService.getList(movieCd));
//
//		model.addAttribute("starAvgCnt", starRatingService.getAvgCnt(movieCd));
		
		String userid = null;
		if (principal != null) {
			System.out.println("타입정보 : " + principal.getClass());
			System.out.println("ID정보 : " + principal.getName());
			
			model.addAttribute("userInfo", memberService.get(principal.getName()));

			userid = principal.getName();
			

		} 
		model.addAttribute("peopleInfoDirectorMovieList", peopleService.getPeopleDirectorFilmoList(userid, peopleCd));
		model.addAttribute("peopleInfoActorMovieList", peopleService.getPeopleActorFilmoList(userid, peopleCd));

		return "contents/peopleInfo";

	}
	
	/* ------------- People ------------ */

	/* ------------- Comment ------------ */

	// 특정 영화 코멘트 리스트 페이지
	@GetMapping("/movieInfo/{movieCd}/comments")
	public String commentsList(@PathVariable("movieCd") String movieCd, Model model, Principal principal) {
		/* tip) 전달된 파라메터 (movieCd) 저장 방법 */
		// @RequestParam("파라미터명") 타입 변수명 꼴 이용 (@ModelParam("파라미터명") 써도됨)
		// = request.getparameter()와 동일 / @ 사용 시 전달되는 데이터 타입에 따른 형변환 가능(문자, 숫자, 날짜)

		log.info("C. movieCd: " + movieCd);

		/* DB 정보 가져오기 */
		
		model.addAttribute("movie", movieService.get(movieCd)); // commentCnt 획득위함

		if (principal != null) {
			System.out.println("타입정보 : " + principal.getClass());
			System.out.println("ID정보 : " + principal.getName());

			model.addAttribute("userInfo", memberService.get(principal.getName()));
		}

		return "comments/commentsList";

	}
	
	// 특정 코멘트 조회 페이지
	 @GetMapping("/comments/{commentCd}") 
	 	// tip) @RestController의 url과 동일할경우 @Controller의 url이 실행 됨 // @Resst는 .json 붙여야 함 
	 public String comments(@PathVariable("commentCd") Long commentCd , Model model, Principal principal) { 
		 // tip) 전달된 파라메터 (movieCd) 저장 방법
	 // @RequestParam("파라미터명") 타입 변수명 꼴 이용 (@ModelParam("파라미터명") 써도됨) 
	// = request.getparameter()와 동일 / @ 사용 시 전달되는 데이터 타입에 따른 형변환 가능(문자, 숫자, 날짜)
		 
		 if (principal != null) {
			System.out.println("타입정보 : " + principal.getClass());
			System.out.println("ID정보 : " + principal.getName());

			model.addAttribute("userInfo", memberService.get(principal.getName()));
		}
		 
		model.addAttribute("commentInfo", commentService.get(commentCd));
		
		// 댓글 리스트는 Ajax 이용해 출력
		 

		 
		 return "comments/commentInfo";
	 
	 }
	 
	// Ajax_특정 코멘트의 댓글 리스트 페이지
	@PostMapping("/ajax/comments/replies")
	public String ajax_userMovies(@RequestParam(name = "commentCd") Long commentCd,
			@RequestParam(name = "crudIdx") int crudIdx,
			@RequestParam(name = "replyCd", required = false) Long replyCd,
			Model model, Principal principal)  {
		
		if (principal != null) {
			System.out.println("타입정보 : " + principal.getClass());
			System.out.println("ID정보 : " + principal.getName());

			model.addAttribute("userInfo", memberService.get(principal.getName()));
		}
		
		model.addAttribute("replyListDTO", replyService.getList(commentCd, crudIdx, replyCd));
		
		return "ajax/ajaxCommentReplyList";
	}

	
	// 사용 x
	 	// movieInfo.jsp 에서 // a태그에 파라미터 추가하기보다, JS이용해 form태그 통해 전달하는 방식 이용*/ 이용 시 사용예정이었음
	@PostMapping("/comments/{commentCd}")
	// tip) @RestController의 url과 동일할 경우 @Controller의 url이 실행 됨
	// @Resst는 .json 붙여야 함
		// 
	public String commentsPost(@PathVariable("commentCd") Long commentCd,  Model model, @ModelAttribute("movie") MovieVO movie, Principal principal) {
		/* tip) 전달된 파라메터 (movieCd) 저장 방법 */
		// @RequestParam("파라미터명") 타입 변수명 꼴 이용 (@ModelParam("파라미터명") 써도됨)
		// = request.getparameter()와 동일 / @ 사용 시 전달되는 데이터 타입에 따른 형변환 가능(문자, 숫자, 날짜)

		model.addAttribute(movie);
		
		return "comments/commentInfo";

	} 

	/* ------------- Comment ------------ */
	
	/* ------------- Search ------------ */
	
	@GetMapping("/search")
	public String search(@RequestParam("query") String query,
					@RequestParam("category") String category,
					Model model, Principal principal) {
		
		// @RequestParam로 받은 파라미터는 Model에 저장해줘야 View에서 사용가능
		
		if (principal != null) {
			System.out.println("타입정보 : " + principal.getClass());
			System.out.println("ID정보 : " + principal.getName());

			model.addAttribute("userInfo", memberService.get(principal.getName()));
		}
		
		
		model.addAttribute("query", query);
		model.addAttribute("category", category);
		
		model.addAttribute("test", "주성치|Stephen Chow,곡덕소|Vincent Kuk");
		// 비동기 이용
		// URL에서 직접 접근하는 경우를 대비해 controller에서 정보 획득
			// search 페이지에서의 category 변경 시엔 비동기/history.pushState 이용한 URL 변경 이용
//		switch (query) {
//		case "movie":
//			model.addAttribute("movieList", searchService.getMovieList(query));
//			break;
//		case "people":
//			model.addAttribute("peopleList", searchService.getPeopleList(query));
//			break;
//		default:
//			break;
//		}
		
		
		return "contents/search";
		
	}
	
	// Ajax_검색_영화 리스트
	@PostMapping("/ajax/search/movie")
//	public String ajaxSearchMovieList(@RequestParam("query") String query, 
//			@RequestParam("currentActorCnt") int currentActorCnt,
//			@RequestParam("currentMovieNmDirectorCnt") int currentMovieNmDirectorCnt, 
//			Model model) {
	public String ajaxSearchMovieList(@RequestParam("query") String query, 
			@RequestParam("currentSqnc") int currentSqnc,
			@RequestParam("additionalCnt") int additionalCnt, 
			Model model) {
		
		log.info("query: " + query);
		
		model.addAttribute("query", query);
	
//		SearchMovieListDTO searchMovieListDTO =  searchService.getMovieList(query, currentActorCnt, currentMovieNmDirectorCnt);
//		model.addAttribute("movieList", searchMovieListDTO.getResultMovieList());
//		
//		model.addAttribute("returnActorCnt", searchMovieListDTO.getReturnActorCnt());
//		
//		model.addAttribute("returnMovieNmDirectorCnt", searchMovieListDTO.getReturnMovieNmDirectorCnt());
		
		model.addAttribute("movieList", searchService.getMovieList(query, currentSqnc, additionalCnt));
		
		return "ajax/ajaxSearchMovieList";
		
	}
	
	// Ajax_검색_인물 리스트
	@PostMapping("/ajax/search/people")
	public String ajaxSearchPeopleList(@RequestParam("query") String query
			,@RequestParam("currentSqnc") int currentSqnc
			,@RequestParam("additionalCnt") int additionalCnt
			, Model model) {
		
		log.info("query: " + query);
		
		model.addAttribute("peopleList", searchService.getPeopleList(query, currentSqnc, additionalCnt));
		
		return "ajax/ajaxSearchPeopleList";
		
	}
	
	// Ajax_검색_유저 리스트
		@PostMapping("/ajax/search/user")
		public String ajaxSearchUserList(@RequestParam("query") String query
				,@RequestParam("currentSqnc") int currentSqnc
				,@RequestParam("additionalCnt") int additionalCnt
				, Model model) {
			
			log.info("query: " + query);
			
			model.addAttribute("userList", searchService.getUserList(query, currentSqnc, additionalCnt));
			
			return "ajax/ajaxSearchUserList";
			
		}
	
	/* ------------- Search ------------ */
	
	/* ------------- Users ------------ */
	// 유저 개인 페이지
	@GetMapping("/users/{randomString}")
	public String users(@PathVariable("randomString") String randomString, Model model, Principal principal)  {
		/* tip) 전달된 파라메터 (movieCd) 저장 방법 */
		// @RequestParam("파라미터명") 타입 변수명 꼴 이용 (@ModelParam("파라미터명") 써도됨)
		// = request.getparameter()와 동일 / @ 사용 시 전달되는 데이터 타입에 따른 형변환 가능(문자, 숫자, 날짜)
		
		log.info("C. randomString: " + randomString);
		
//		MemberVO memberVO = memberService.getByRandomString(randomString);
//		log.info("C. randomString-> userid: " + memberVO.getUserid());
//		model.addAttribute("memberVO", memberVO);
		
		UserDTO userDTO = userService.getUserInfo(randomString);
		model.addAttribute("userDTO", userDTO);
		
		/* DB 정보 가져오기 */
		model.addAttribute("starRatingAnalysis", userService.getStarRatingAnalysis(userDTO.getUserid()));
//		model.addAttribute("starRatingAnalysis", null);
		
		if (principal != null) {
			System.out.println("타입정보 : " + principal.getClass());
			System.out.println("ID정보 : " + principal.getName());
			
			model.addAttribute("userInfo", memberService.get(principal.getName()));

		}

		return "users/userInfo";

	}
	
	// 유저 취향분석 페이지
		@GetMapping("/users/{randomString}/analysis")
		public String usersAnalysis(@PathVariable("randomString") String randomString, Model model, Principal principal)  {
			/* tip) 전달된 파라메터 (movieCd) 저장 방법 */
			// @RequestParam("파라미터명") 타입 변수명 꼴 이용 (@ModelParam("파라미터명") 써도됨)
			// = request.getparameter()와 동일 / @ 사용 시 전달되는 데이터 타입에 따른 형변환 가능(문자, 숫자, 날짜)

			log.info("C. randomString: " + randomString);
			
			MemberVO memberVO = memberService.getByRandomString(randomString);
			String userid = memberVO.getUserid();
			log.info("C. randomString-> userid: " + memberVO.getUserid());
			
			model.addAttribute("userInfo", memberVO);
			
			/* DB 정보 가져오기 */
			
		/* 취향 분석 결과는 로그인 유무와 무관없이 출력 */
			
			// 별점 평가 분석 결과 조회
			model.addAttribute("starRatingAnalysis", userService.getStarRatingAnalysis(userid));

			// 선호 배우 조회
//			model.addAttribute("favoriteActorList", userService.getFavoriteActorList(userid));
			model.addAttribute("favoriteActorList", userService.getFavoriteActorList_new(userid));
			
			// 선호 감독 조회
			model.addAttribute("favoriteDirectorList", userService.getFavoriteDirectorList(userid));
			
			// 선호 장르 조회
			model.addAttribute("favoriteGenreAnalysis", userService.getFavoriteGenreList(userid));
			
			// 선호 국가 조회
			model.addAttribute("favoriteNationList", userService.getFavoriteNationList(userid));
			
			// 유저 총 감상 시간 조회
			model.addAttribute("totalShowTmAnalysis", userService.getTotalShowTm(userid));
			
			
			if (principal != null) {
				System.out.println("타입정보 : " + principal.getClass());
				System.out.println("ID정보 : " + principal.getName());
				
				model.addAttribute("userInfo", memberService.get(principal.getName()));

			}

			return "users/analysis";

		}
		
	// 유저 평가 영화 리스트 페이지
		@GetMapping("/users/{randomString}/movies")
		public String userMovies(@PathVariable("randomString") String randomString, Model model, Principal principal)  {
			
			String userid = null;
			
			if (principal != null) {
				System.out.println("타입정보 : " + principal.getClass());
				System.out.println("ID정보 : " + principal.getName());

				userid = principal.getName();
				
				model.addAttribute("userInfo", memberService.get(userid));
			}
			
			return "users/userMovieList";
		}
		
		// 유저 작성 코멘트 리스트 페이지
		@GetMapping("/users/{randomString}/comments")
		public String userComments(@PathVariable("randomString") String randomString, Model model, Principal principal)  {
			
			String userid = null;
			
			if (principal != null) {
				System.out.println("타입정보 : " + principal.getClass());
				System.out.println("ID정보 : " + principal.getName());

				userid = principal.getName();
				
				model.addAttribute("userInfo", memberService.get(userid));
			}
			
			
			return "users/userCommentList";
		}
		
		
		// Ajax_유저 작성 코멘트 리스트 페이지
		@PostMapping("/ajax/users/comments")
		public String ajax_userComments(@RequestParam("randomString") String randomString,
				@RequestParam("orderBy") int orderBy,
				@RequestParam("currentSqnc") int currentSqnc,
				@RequestParam("additionalCnt") int additionalCnt
				, Model model, Principal principal)  {
			
			String userid = null;
			
			if (principal != null) {
				System.out.println("타입정보 : " + principal.getClass());
				System.out.println("ID정보 : " + principal.getName());

				userid = principal.getName();
				
				model.addAttribute("userInfo", memberService.get(userid));
			}
			
			model.addAttribute("userCommentList", userService.getUserCommentList(randomString, orderBy, currentSqnc, additionalCnt, userid));
		
			
			return "ajax/ajaxUserCommentList";
		}
		
		// Ajax_유저 평가 영화 리스트 페이지
		@PostMapping("/ajax/users/movies")
		public String ajax_userMovies(@RequestParam("randomString") String randomString,
				@RequestParam("orderBy") int orderBy,
				@RequestParam("currentSqnc") int currentSqnc,
				@RequestParam("additionalCnt") int additionalCnt
				, Model model, Principal principal)  {
			
			String userid = null;
			
			if (principal != null) {
				System.out.println("타입정보 : " + principal.getClass());
				System.out.println("ID정보 : " + principal.getName());

				userid = principal.getName();
				
				model.addAttribute("userInfo", memberService.get(userid));
			}
			
			model.addAttribute("userMovieList", userService.getUserMovieList(randomString, orderBy, currentSqnc, additionalCnt));
			
			return "ajax/ajaxUserMovieList";
		}
		
		
	/* ------------- Users ------------ */
	
	
	
	/* ------------- Manage ------------ */
	
	// 영화&인물 정보 API 호출 페이지
	@GetMapping("/manage/callApi")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String callApiMoviePeople(Model model, Principal principal) throws Exception {
		
		if (principal != null) {
			System.out.println("타입정보 : " + principal.getClass());
			System.out.println("ID정보 : " + principal.getName());

			model.addAttribute("userInfo", memberService.get(principal.getName()));
		}
		
		
		
		return "manage/callApi";
	}
	
	/* ------------- Manage ------------ */
	

}
