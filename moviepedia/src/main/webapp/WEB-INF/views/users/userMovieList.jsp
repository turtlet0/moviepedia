<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../includes/header.jsp"%>

<main>
	<div class="section">
		<div class="center">
			<div class="inner">
			
			
				<header>
					<div>
						<button  style="font-size:30px; color:red;"  class="backBtn padding--v5" onclick="history.back(-1)">←</button>
					</div>
					<h2>평가한 영화</h2>
					<div class="padding--bottom10 padding--h5">
						<select id="orderBy">
							<option value="0">최근에 담은 순</option>
							<option value="1">담은지 오래된 순</option>
							
							<c:choose>
								<c:when test="${userInfo.randomString eq randomString }">
									<option value="2">나의 별점 높은 순</option>	
								</c:when>
								<c:when test="${userInfo.randomString ne randomString }">
									<option value="2">이 회원의 별점 높은 순</option>	
								</c:when>
							</c:choose>
							<c:choose>
								<c:when test="${userInfo.randomString eq randomString }">
									<option value="3">나의 별점 낮은 순</option>
								</c:when>
								<c:when test="${userInfo.randomString ne randomString }">
									<option value="3">이 회원의 별점 낮은 순</option>	
								</c:when>
							</c:choose>
						
							<option value="4">평균 별점 높은 순</option>
							<option value="5">신작 순</option>
							<option value="6">상영 시간 짧은 순</option>
							<option value="7">평가 수 많은 순</option>
						</select>
					</div>
				</header>
				
				<!-- 전체 영화 리스트 -->
				<section class="padding--h5">
					<ul class="movieList">

					</ul>
				</section>


				<div id="sentinel">***********</div>



			</div>
		</div>
	</div>
</main>


<script type="text/javascript">
$(document).ready(function(){
	
	
	
	
	
	
	/* 38.5.3 댓글 기능에서의 Ajax -> 보안 처리
	브라우저쪽에선 1. 댓글 등록시 CSRF 토큰 같이 전송 2. 댓글 수정/삭제시 서버쪽에서 사용하기 위한 댓글 작성자 같이 전송*/
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

	var userRandomString = "${randomString}";
	
	console.log("userRandomString: " + userRandomString);
	
	
		
	var csrfHeaderName = "${_csrf.headerName}";
	var csrfTokenValue = "${_csrf.token}"
	console.log("csrfHeaderName: " + csrfHeaderName);
	console.log("csrfTokenValue: " + csrfTokenValue);
	/* 38.5.3 Ajax로 CSRF 토큰 전송 방식
 		: ajaxSend() 이용 시 모든 Ajax 전송 시 CSRF 토큰 같이 전송하도록 되어있음 */
   
	$(document).ajaxSend(function(e, xhr, options){
	
   		console.log("ajaxSend");
   		xhr.setRequestHeader(csrfHeaderName, csrfTokenValue);
   	});
	
	
//////////////// 영화 ////////////////////////////////////////////////



	var randomString = '<c:out value="${randomString}" />';
	
	var movieUL = $(".movieList");
	
	let orderBy = 0;
	let currentSqnc = 0;
	
// 	var currentSqnc = $(".movieLi").length;

	var additionalCntInitValue = 20;
	var additionalCntAddValue = 10;
	
	let additionalCnt = additionalCntInitValue;
		// 무한 스크롤 사용 시 최초 출력 코멘트 리스트 높이가 화면보다 커야함
// 	var totalCntValue = 4; // 최초 조회 시 출력할 총 개수
	let _returnListCnt = 0; // 추가된 코멘트 리스트의 개수
	
	// 영화 리스트 조회 최초 실행
	showUserMovieList(randomString, orderBy, currentSqnc, additionalCnt); 
	
	/* 페이지 접근 시 평가한 영화 개수 0개인 경우 유저 정보 페이지로 이동 */
	if($(".movieLi").length == 0){
		alert("평가한 영화가 없습니다. 유저 페이지로 이동합니다.");
		
// 		history.back(-1);
		location.href = "/users/"+ userRandomString;
// 		location.replace("/users/"+ userRandomString);
			// tip) href는 그대로 페이지 이동을 의미, replace는 현재 페이지에 덮어씌우기 때문에 replace를 사용한 다음에는 이전 페이지로 돌아갈 수 없음.		
	}
	
	// 영화 목록 조회 순서 변경 시 동작
	$("#orderBy").off("change").on("change", function(){
		
		$('#sentinel').show(); // 무한스크롤 감시 요소 show
		
		$('.movieList').html("");
		
		orderBy = $(this).val();
		
		console.log("change orderBy: " + orderBy);

		currentSqnc = 0; // 값 초기화 ROWNUM 1부터 출력되도록
		additionalCnt = additionalCntInitValue;
		
		showUserMovieList(randomString, orderBy, currentSqnc, additionalCnt);
	});
	

/* 영화 리스트 추가 메서드 */
	function showUserMovieList(randomStringValue, orderByValue, currentSqncValue, additionalCntValue){
		// 코멘트 추가 시엔 additionalCnt 파라미터 입력받지 않음
		if(additionalCntValue == null){
			additionalCntValue = additionalCntAddValue;
			additionalCnt = additionalCntAddValue; // 무한스크롤 할지 여부 체크 위해..
		} else {
			additionalCnt = additionalCntInitValue; // 무한스크롤 할지 여부 체크 위해..
		}
		
		console.log("-----------------------");
		console.log("orderByValue: " + orderByValue);
		console.log("currentSqnc: " + currentSqncValue);
		console.log("additionalCnt: " + additionalCntValue);
		console.log("-----------------------");
		
		movieService.ajax_getUserMovieList({randomString: randomStringValue, orderBy: orderByValue, currentSqnc: currentSqncValue, additionalCnt: additionalCntValue}, 
				function(result) {
			
		});

		// 변수 값 갱신
		_returnListCnt = $(".movieLi").length - currentSqnc;
		currentSqnc = $(".movieLi").length;	
		
		
	}
/* 영화 리스트 추가 메서드 */		
	
/*
 * 무한 스크롤
 * JS IntersectionObserver API 이용
 * https://velog.io/@eunoia/%EB%AC%B4%ED%95%9C-%EC%8A%A4%ED%81%AC%EB%A1%A4Infinite-scroll-%EA%B5%AC%ED%98%84%ED%95%98%EA%B8%B0
 */
// 	var intersectionObserver = new IntersectionObserver(function(entries){
// 		// 만약 intersectionRatio 0이면 target이 view 밖에 있는 것
// 		// -> 즉, 이땐 아무 것도 할 필요가 없는 것
// 		if(entries[0].intersectionRatio <= 0) return ;
		
// 		console.log("새 리스트 추가");
// 	});	
	
// 	// oveserving 감시 시작
// 	intersectionObserver.observe(document.querySelector('.scrollerFooter'));
	
	var _scrollchk = false; // 로딩 중인지 여부 체크 변수
	
	const intersectionObserver = new IntersectionObserver((entries, observer) => {
		entries.forEach(entry => {
			if(! entry.isIntersecting) return;
				// entry가 intersecting(교차) 중이 아니라면 함수 실행 X
				
			if(_scrollchk) {
				console.log("_scrollchk: " + _scrollchk);
				return; 
			}
				// page 불러오는 중임을 나타내는 flag 변수 선언해 이를 통해 불러오는 중이면 함수 실행 X
			
			observer.observe(document.getElementById('sentinel'));
				// observer 등록
// 			page._page += 1; // 불러올 페이지 추가
			
			// 코멘트 추가 메서드 호출
			console.log("1 _returnListCnt: " + _returnListCnt);
			showUserMovieList(randomString, orderBy, currentSqnc);
			
			if(_returnListCnt == 0){
				console.log("a0 _returnListCnt: " + _returnListCnt);
			
				$('#sentinel').hide();
				//검색된 아이템이 없을 경우 관찰중인 요소를 숨긴다.
			} else {
				if(_returnListCnt < additionalCnt) {
					console.log("< additionalCnt: " + additionalCnt);
					console.log("< _returnListCnt: " + _returnListCnt);
					$('#sentinel').hide();
					
					
				} else {
					console.log(">= _returnListCnt: " + _returnListCnt);
					$('#sentinel').show();
				}
			}
			
		});
		
	});
	
	intersectionObserver.observe(document.getElementById('sentinel')); // observing 실행
	
/* 무한 스크롤 */	


//////////////// 코멘트 ////////////////////////////////////////////////





	
});
</script>


