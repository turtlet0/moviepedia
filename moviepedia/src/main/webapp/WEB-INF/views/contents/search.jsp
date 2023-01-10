<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

    
<%@ include file="../includes/header.jsp" %>

<main>
	<div class = "section" >
		<div class = "center">
			<div class = "inner">
				
				<!-- 카테고리 메뉴 -->
				<div class="margin--bottom20">			
					<ul id="categoryMenu" class="clearfix">
						<li class="float--left" value="1">영화</li>
						<li class="float--left" value="2">인물</li>
<!-- 						<li class="float--left" value="3">컬렉션</li> -->
						<li class="float--left" value="4">유저</li>
					</ul>
				</div>	
				
				<!-- 출력 결과 리스트 -->
				<div id="resultList">
					<div id="movieList" class="hidden">
						<section>
							<ul></ul>
							<div style="text-align: center;">
								<button class="additionalBtn" id="additionalMovieBtn">더보기</button>
							</div>
						</section>
					</div>
					<div id="peopleList" class="hidden">
						<section>
							<ul></ul>
							<div style="text-align: center;">
								<button class="additionalBtn" id="additionalPeopleBtn">더보기</button>
							</div>
						</section>
					</div>
<!-- 					<div id="deckList" class="hidden"><section><ul></ul></section></div> -->
					<div id="userList" class="hidden">
						<section>
							<ul></ul>
							<div style="text-align: center;">
								<button class="additionalBtn" id="additionalUserBtn">더보기</button>
							</div>
						</section>
					</div>
				</div>
				
<!-- 				<ul id="test"></ul> -->

			</div>
		</div>
	</div>
</main>

<script type="text/javascript">
$(document).ready(function(){
	
	
	
	
	/* 38.5.3 댓글 기능에서의 Ajax -> 보안 처리
	브라우저쪽에선 1. 댓글 등록시 CSRF 토큰 같이 전송 2. 댓글 수정/삭제시 서버쪽에서 사용하기 위한 댓글 작성자 같이 전송 */
	// tip) JS에서도 security 태그 사용 가능
	var userid = null;
	
	<sec:authorize access="isAuthenticated()">
		userid = '<sec:authentication property="principal.username"/>';
		// 특수 문자 치환
			// @ . 과 같은 특수문자 Java -> JS 넘어오면서 변환되어 메서드 에러 발생시킴
		userid = userid.replace("&#64;", "@");
		userid = userid.replace(/\&\#46\;/gi, "."); // /gi: JS엔 replaceAll 없음 -> 정규식 gi 이용해 구현
		console.log("userid",userid);
	</sec:authorize>


		
	var csrfHeaderName = "${_csrf.headerName}";
	var csrfTokenValue = "${_csrf.token}"
	
	/* 38.5.3 Ajax로 CSRF 토큰 전송 방식
 		: ajaxSend() 이용 시 모든 Ajax 전송 시 CSRF 토큰 같이 전송하도록 되어있음 */
   	$(document).ajaxSend(function(e, xhr, options){
   		xhr.setRequestHeader(csrfHeaderName, csrfTokenValue);
   	});

	
/////////////// 검색 결과 리스트 //////////////////////////////////////	

	// URL 파라미터
 	var query = '${query}';
 	var category = '${category}';
 	
	console.log(query);
	console.log(category);
	
	// 검색창에 검색어 표시
	$('#search').val(query);
	
 	
	
// 	console.log(window.location.origin); // http://localhost:8088
// 	console.log(window.location.pathname); // /search
// 	console.log(window.location.href); // http://localhost:8088/search?query=%E3%84%B4&category=movie
// 	console.log(window.location.search); // ?query=%E3%84%B4&category=movie
// 	history.pushState(null, null, window.location.origin + window.location.pathname + "?query="+query+"&category=people");


/* 사용x - mapper 쿼리 개선 */	
// 이벤트 - 검색 결과 추가 버튼 클릭
// 	var returnActorCnt = 0;
// 	var returnMovieNmDirectorCnt = 0;
	
// 	// test
// 	var page = 0;
// 	var request = 0;
// 	var session = 0;
	
// 	var inputReturnActorCnt = 0;
// 	var inputReturnMovieNmDirectorCnt = 0;
	
// 	var movieLiCurrentCnt = $(".movieLi").length;
// // 	var commentCnt = '<c:out value="${movie.commentCnt}" />';
// 	var moviePreviousCnt = 0; // mapper.getMovieListForMovieNmDirectorNm 이전 출력 행 수
	
// /* 영화 검색 결과 출력 메인 메서드 */
// 	function showMovieList(){
		
// 		searchService.ajax_getMovieList({query: query,
// 			currentActorCnt: returnActorCnt,
// 			currentMovieNmDirectorCnt: returnMovieNmDirectorCnt}, function(){
				
// 			});
		
// 		if($("#movieNoResultSpan").length){
// 			console.log("hide: " + $("#movieNoResultSpan"));
// 			$("#additionalMovieBtn").hide();
// 		}
		
// //			session += Number('<c:out value="${session}"/>');
// //			request += Number('<c:out value="${request}"/>');
// //			page += Number('<c:out value="${page}"/>');
	
// //			console.log("session: " + session);
// //			console.log("request: " + request);
// //			console.log("page: " + page);
		
// 		console.log("inputReturnActorCnt: " + $("input[name=inputReturnActorCnt]").val()); // > undefined 출력 
// 			// id로 요소 선택, name으로 요소 선택할때의 출력 결과 달라짐 (값이 없는 것은 아님)
// 		console.log("inputReturnMovieNmDirectorCnt: " + $("input[name=inputReturnMovieNmDirectorCnt]").val());
	
// 		returnActorCnt += Number($("input[name=returnActorCnt]").val());
// 		returnMovieNmDirectorCnt += Number($("input[name=returnMovieNmDirectorCnt]").val());
		
// 		$("input[name=returnActorCnt]").remove();
// 		$("input[name=returnMovieNmDirectorCnt]").remove();
	
// 		console.log(returnActorCnt);
// 		console.log(returnMovieNmDirectorCnt);
// 	}
	
/* 영화 검색 결과 출력 메인 메서드 */
let movieLiCurrentSqnc = $(".movieLi").length;

function showMovieList(){
	
	movieLiCurrentSqnc = $(".movieLi").length;
	
	var movieAdditionalCnt = 0;
	
	if(movieLiCurrentSqnc == 0){
		movieAdditionalCnt = 20;
	} else {
		movieAdditionalCnt = 10;
	}
	
	searchService.ajax_getMovieList({query: query, currentSqnc: movieLiCurrentSqnc, additionalCnt: movieAdditionalCnt},
			function(result){
 				console.log("ajax_getMovieList result", result);
		
	});
	
	if($("#movieNoResultSpan").length){
		console.log("hide: " + $("#movieNoResultSpan"));
		$("#additionalMovieBtn").hide();
	}
}
	
/* 인물 검색 결과 출력 메인 메서드 */

let peopleLiCurrentSqnc = $(".peopleLi").length;

function showPeopleList(){
	
	peopleLiCurrentSqnc = $(".peopleLi").length;
	
	var peopleAdditionalCnt = 0;
	
	if(peopleLiCurrentSqnc == 0){
		peopleAdditionalCnt = 20;
	} else {
		peopleAdditionalCnt = 10;
	}
	
	searchService.ajax_getPeopleList({query: query, currentSqnc: peopleLiCurrentSqnc, additionalCnt: peopleAdditionalCnt},
			function(result){
// 				console.log("ajax_getPeopleList result", result);
		
	});
	
	if($("#peopleNoResultSpan").length){
		console.log("hide: " + $("#peopleNoResultSpan"));
		$("#additionalPeopleBtn").hide();
	}
}
	
	
/* 유저 검색 결과 출력 메인 메서드 */

let userLiCurrentSqnc = $(".userLi").length;

function showUserList(){
	
	userLiCurrentSqnc = $(".userLi").length;
	
	var userAdditionalCnt = 0;
	
	if(userLiCurrentSqnc == 0){
		userAdditionalCnt = 20;
	} else {
		userAdditionalCnt = 10;
	}
	
	console.log(query, userLiCurrentSqnc, userAdditionalCnt);
	searchService.ajax_getUserList({query: query, currentSqnc: userLiCurrentSqnc, additionalCnt: userAdditionalCnt},
			function(result){
// 				console.log("ajax_getUserList result", result);
		
	});
	
	// 함수 내부에 작성하면, result 태그들이 요소에 추가되기 전이라 원하는 결과 얻지 못함
	if($("#userNoResultSpan").length){
		console.log("hide: " + $("#userNoResultSpan"));
		$("#additionalUserBtn").hide();
	}
}	
	
	
/* 더보기 버튼 */
	$(".additionalBtn").off("click").on("click", function(){
		
		if($(this).attr("id") == "additionalMovieBtn"){
			
			showMovieList();
			
		}
		else if($(this).attr("id") == "additionalPeopleBtn"){
			
			showPeopleList();
			
		}
		else if($(this).attr("id") == "additionalUserBtn"){
			
			showUserList();
			
		}
		
	});
	
	
// 최초 실행 시 비동기 조회
	switch (category) {
	case 'movie':
		$('#categoryMenu').children('li[value="1"]').addClass('categorySelected');
		
		$('#resultList').children().addClass('hidden');
		$('#movieList').removeClass('hidden');
		
		showMovieList();
		
		$('#movieList').addClass('already'); // ajax JSP 페이지엔 #movieList 요소 없으므로 메서드 실행 결과 내부에서 추가 불가

		break;
		
	case 'people':
		$('#categoryMenu').children('li[value="2"]').addClass('categorySelected');
		
		$('#resultList').children().addClass('hidden');
		$('#peopleList').removeClass('hidden');
			
		showPeopleList();
		
		$('#peopleList').addClass('already');
		
		break;
	
	case 'users':
		$('#categoryMenu').children('li[value="4"]').addClass('categorySelected');
		
		$('#resultList').children().addClass('hidden');
		$('#userList').removeClass('hidden');
			
		showUserList();
		
		$('#userList').addClass('already');
		
		break;
		
	default:
		break;
	}
	
// 메뉴바 클릭 시 비동기 조회
	$('#categoryMenu').on('click', 'li', function(e){
		console.log($(this).val());
		var cateogryVal = $(this).val();
		
		$(this).addClass('categorySelected');
		$(this).siblings().removeClass('categorySelected');
		

		switch (cateogryVal) {
		// 1은 숫자 '1'은 문자 / li의 val값은 숫자만 가능
		case 1:
			history.pushState({query:query, category:'movie'}, null, window.location.origin + window.location.pathname + "?query="+query+"&category=movie");
			$('#resultList').children().addClass('hidden');
			$('#movieList').removeClass('hidden');
			
			
			// 중복 조회 방지
			if(! $('#movieList').hasClass('already')){
				
				showMovieList();
				
				$('#movieList').addClass('already'); // ajax JSP 페이지엔 #movieList 요소 없으므로 메서드 실행 결과 내부에서 추가 불가
			}
			
			break;
			
		case 2:		
			history.pushState({query:query, category:'people'}, null, window.location.origin + window.location.pathname + "?query="+query+"&category=people");

			$('#resultList').children().addClass('hidden');
			$('#peopleList').removeClass('hidden');
				
			// 중복 조회 방지
			if(! $('#peopleList').hasClass('already')){
				
				showPeopleList();
				
// 				searchService.ajax_getPeopleList({query:query});
				
				$('#peopleList').addClass('already');
				
			}
			
			break;
		case 3:
			history.pushState(null, null, window.location.origin + window.location.pathname + "?query="+query+"&category=decks");
			$('#resultList').children().addClass('hidden');
			
			
			break;
		case 4:
			history.pushState(null, null, window.location.origin + window.location.pathname + "?query="+query+"&category=users");
			$('#resultList').children().addClass('hidden');
			$('#userList').removeClass('hidden');
			
			// 중복 조회 방지
			if(! $('#userList').hasClass('already')){
				
				showUserList();
// 				searchService.ajax_getUserList({query:query});
				
				$('#userList').addClass('already');
				
			}
			
			break;
		default:
			break;
		}
	});
	
	
	


/////////////// 검색 결과 리스트 //////////////////////////////////////	


















	
/* 사용 X ////////// 코멘트 페이지 이동 ///////// */
	// a태그에 파라미터 추가하기보다, JS이용해 form태그 통해 전달하는 방식 이용*/
/*	
	var actionForm = $("#actionForm");
	
	$(document).on("click", ".commentMove", function(e){
		
		//alert(e.currentTarget);
		// alert($(this).attr("href"));
		e.preventDefault();
		actionForm.append("<input type='hidden' name='commentCd' value='"+$(this).attr("href")+"'>"); // tip) JS 코드 추가:  "+ JS 코드 +"
		// actionForm.attr("action", e.currentTarget);
		actionForm.attr("action", $(this).attr("href"));
		actionForm.submit();
		// 게시물 제목 클릭 시 form 태그에 추가로 bno 값을 전송하기 위해 input 태그 만들어 추가.
		// form 태그의 action은 /board/get으로 변경
		// -> 정상 처리 시 actionForm의 input에 담긴 pageNum 및 amount 파라미터 전달됨
	})
	
*/
	
});
</script>