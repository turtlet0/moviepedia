<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

    
<%@ include file="../includes/header.jsp" %>

<main>
	<div class = "section" >
		<div class = "center">
			<div class = "innerFix">
			
				<!-- 별점 이미지 -->
				<svg aria-hidden="true" style="position: absolute; width: 0; height: 0; overflow: hidden;" version="1.1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink">
				  <defs>
				    <symbol id="icon-star" viewBox="0 0 26 28">
				      <path d="M26 10.109c0 0.281-0.203 0.547-0.406 0.75l-5.672 5.531 1.344 7.812c0.016 0.109 0.016 0.203 0.016 0.313 0 0.406-0.187 0.781-0.641 0.781-0.219 0-0.438-0.078-0.625-0.187l-7.016-3.687-7.016 3.687c-0.203 0.109-0.406 0.187-0.625 0.187-0.453 0-0.656-0.375-0.656-0.781 0-0.109 0.016-0.203 0.031-0.313l1.344-7.812-5.688-5.531c-0.187-0.203-0.391-0.469-0.391-0.75 0-0.469 0.484-0.656 0.875-0.719l7.844-1.141 3.516-7.109c0.141-0.297 0.406-0.641 0.766-0.641s0.625 0.344 0.766 0.641l3.516 7.109 7.844 1.141c0.375 0.063 0.875 0.25 0.875 0.719z"></path>
				    </symbol>
				    <symbol id="icon-star-half" viewBox="0 0 13 28">
				      <path d="M13 0.5v20.922l-7.016 3.687c-0.203 0.109-0.406 0.187-0.625 0.187-0.453 0-0.656-0.375-0.656-0.781 0-0.109 0.016-0.203 0.031-0.313l1.344-7.812-5.688-5.531c-0.187-0.203-0.391-0.469-0.391-0.75 0-0.469 0.484-0.656 0.875-0.719l7.844-1.141 3.516-7.109c0.141-0.297 0.406-0.641 0.766-0.641v0z"></path>
				    </symbol>
				  </defs>
				</svg>
			
				<div class="border" style="margin-top:90px; border-radius: 10px;">
				
					<header>
						<div class="color-white padding--bottom10">
							<div>
								<h2 class="totalShowTm">평가한 영화 수 ${totalShowTm.cntStarRating }편</h2>
								<p class="resultMessage">${totalShowTm.resultMessage }</p>
							</div>
						</div>
						<div class="border--top border--bottom padding--v5 margin--bottom10">
							<select id="orderBy">
								<optgroup label="영화">
									<option value="0">랜덤 영화</option>
									<option value="1">평균별점 TOP 영화</option>
								</optgroup>
								<optgroup label="장르">
									<c:forEach items="${fn:split('드라마,액션,애니메이션,멜로/로맨스,코미디,공포(호러),다큐멘터리,스릴러,범죄,SF,미스터리,판타지,공연,어드벤처,전쟁,가족,사극,뮤지컬,서부극(웨스턴),기타', ',') }" 
										var="repGenreNm" varStatus="idx">
										
										<option value="${idx.index + 10}">${repGenreNm }</option>
									</c:forEach>
								</optgroup>				
							</select>
							<select id="orderBy1" style="display:none;">
								<optgroup label="장르">
									<c:forEach items="${fn:split('전체,드라마,액션,애니메이션,멜로/로맨스,코미디,공포(호러),다큐멘터리,스릴러,범죄,SF,미스터리,판타지,공연,어드벤처,전쟁,가족,사극,뮤지컬,서부극(웨스턴),기타', ',') }" 
										var="repGenreNm" varStatus="idx">
										
										<option value="${idx.index}">${repGenreNm }</option>
									</c:forEach>
								</optgroup>
							</select>
							<select id="orderBy2" style="display:none;">
								<optgroup label="국가">
								<%-- <c:set var="repNationNmList" value=""/> --%>
									<c:forEach items="${fn:split('전체,미국,한국,일본,프랑스,중국,독일,캐나다,스페인,홍콩,러시아,이탈리아,호주,덴마크,대만,노르웨이,인도,태국,스웨덴,벨기에,아일랜드,헝가리,네덜란드,멕시코,이스라엘,핀란드,브라질,아르헨티나,아이슬란드,폴란드,기타', ',') }" 
										var="repNationNm" varStatus="idx">
										
										<option value="${idx.index}">${repNationNm }</option>
									</c:forEach>
								</optgroup>
							</select>
						</div>
					</header>
					
					<!-- 영화 리뷰 리스트  -->
					<section>
						<ul id="reviewList" class="infinite">
							<li class="clearfix margin--bottom20">
								<div class="float--left">
								
									<div class="reviewPoster img-border">
										<a href='<c:out value="/movieInfo/${movie.movieCd }" />' target="_blank">
											<img  alt="<c:out value="${movie.movieNm }"/> 포스터" src="<c:out value="${movie.posterUrl eq null || movie.posterUrl eq ' ' ? '/resources/img/poster-not-available.jpg' : movie.posterUrl}" />">
										</a>
									</div>
									
									<div>
										<h3><c:out value="${movie.movieNm }" /></h3>
										<div><c:out value="${movie.prdtYear }" /> • <c:out value="${movie.repGenreNm }" /> • <c:out value="${movie.repNationNm }" /></div>
									</div>	
									
									<section class="starRating" data-value="0" data-movieCd="${movie.movieCd }">
										
										<div class="stars">
											<c:forEach var="score" begin="0" step="1" end="4" >
												<input class="stars-input" type="radio" name="${movie.movieCd }-rating" value="${5.0 - score}" id="${movie.movieCd }-rating-${5.0 - score}" >
													<!-- tip) 5.0-score -> starRating값은 실수이므로 5가 아닌 5.0으로 지정함-->
												 <label class="stars-view" for="${movie.movieCd }-rating-${5.0 - score}">
													 <svg class="icon icon-star">
											      		<use xlink:href="#icon-star"></use>
												    </svg>
											    </label>
											    <input class="stars-input" type="radio" name="${movie.movieCd }-rating" value="${4.5 - score}" id="${movie.movieCd }-rating-${4.5 - score}" >
											    <label class="stars-view is-half" for="${movie.movieCd }-rating-${4.5 - score}">
												    <svg class="icon icon-star-half">
											      		<use xlink:href="#icon-star-half"></use>
											    	</svg>
										    	</label>
											</c:forEach>
										</div>
									</section>
								
								</div>
							</li>
						</ul>
					</section>
					
					
					<div id="sentinel"></div>
					
					<!-- 무한 스크롤 -->
				<!-- 	<div class="pagination"></div>
					<div class="loading">
					  <div class="spin"></div>
					</div>	 -->
					
				</div>
				
			
			</div>
		</div>
	</div>
</main>

<script type="text/javascript">
$(document).ready(function(){
	
	
	
	
	
	
	/* 38.5.3 댓글 기능에서의 Ajax -> 보안 처리
	브라우저쪽에선 1. 댓글 등록시 CSRF 토큰 같이 전송 2. 댓글 수정/삭제시 서버쪽에서 사용하기 위한 댓글 작성자 같이 전송 */
	// tip) JS에서도 security 태그 사용 가능
	let userid = null;
	
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

	
/////////////// 영화 리뷰 리스트 //////////////////////////////////////	
	const reviewListUL = $("#reviewList");
	const sentinelDiv = $('#sentinel');
	
	let orderByValue = 0; // 0: 랜덤
	let orderBy1Value = 0; // 메인 order-평점 TOP 리스트 조회 시 장르/국가 조회 show;
	let orderBy2Value = 0; // 메인 order-장르 조회 선택 시 -> 국가 조회 show
	
	reviewListUL.html(""); // 초기화
	
// 	let movieLiCurrentSqnc = 0;
	let movieLiCurrentSqnc = $(".movieLi").length;
	
	let _returnListCnt = 0; // 무한스크롤 - 추가된 영화 리스트의 개수
	
	
	
// 	showReviewList(userid, orderByValue); // 페이지 갱신 시 최초 실행
	showReviewList(); // 페이지 갱신 시 최초 실행
	
	// 리뷰 영화 목록 조회 순서 변경 시 동작
	$("#orderBy").off("change").on("change", function(){

		orderByValue = $(this).val();
		
		reviewListUL.html(""); // 초기화
		
		sentinelDiv.show(); // 무한 스크롤 감시 요소 show
		
		$("#orderBy1").val(0);
		$("#orderBy2").val(0);
		
		$("#orderBy1").hide();
		$("#orderBy2").hide();
		
		// 평균별점 TOP 영화 조회 시 장르/국가 조회 오픈
		if(orderByValue == 1){
			
			$("#orderBy1").show();
			$("#orderBy2").show();
		}
		
		// 장르 조회 시 국가 조회 오픈
		if(orderByValue >= 10){
			
			$("#orderBy2").show();
		}
		
// 		showReviewList(userid, orderByValue);
		showReviewList();
	});
	
	// 장르 조회 순서 변경 시
	$("#orderBy1").off("change").on("change", function(){

		orderBy1Value = $(this).val();
		console.log('orderBy1Value', orderBy2Value);
		
		reviewListUL.html(""); // 초기화
		
		sentinelDiv.show(); // 무한 스크롤 감시 요소 show

		showReviewList();
	});
	
	// 국가 조회 순서 변경 시
	$("#orderBy2").off("change").on("change", function(){

		orderBy2Value = $(this).val();
		console.log('orderBy2Value', orderBy2Value);
		
		reviewListUL.html(""); // 초기화
		
		sentinelDiv.show(); // 무한 스크롤 감시 요소 show

		showReviewList();
	});
	
/* 이벤트 처리 - 리뷰 리스트 추가하기 (10개씩) */
	// 최초 1회도 비동기로 추가함
	

function showReviewList() {
	
	movieLiCurrentSqnc = $(".movieLi").length;
	
	var movieAdditionalCnt = 0;
	
	if(movieLiCurrentSqnc == 0){
		movieAdditionalCnt = 20;
	} else {
		movieAdditionalCnt = 10;
	}
	
	console.log();
	movieService.ajax_getReviewList({orderBy: orderByValue, orderBy1: orderBy1Value, orderBy2: orderBy2Value, currentSqnc: movieLiCurrentSqnc, additionalCnt: movieAdditionalCnt}, 
		function(result){
// 			console.log("ajax_getReviewList result", result);	
	});
	
	
	// 무한 스크롤 - 변수값 갱신
	_returnListCnt = $(".movieLi").length - movieLiCurrentSqnc;
	
	// 조회 결과 없는 경우
	if($("#movieNoResultSpan").length){
		console.log("hide: " + $("#movieNoResultSpan"));
		sentinelDiv.hide();
	}
}	

/*
 * ===================== 무한 스크롤 ==================================
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


			//console.log("function before \n"+ movieCdValue+  "/" + orderByValue + "/" + currentCnt+ "/" + additionalCnt);
			showReviewList();
			
			var movieAdditionalCnt = 0;
			
			if(movieLiCurrentSqnc == 0){
				movieAdditionalCnt = 20;
			} else {
				movieAdditionalCnt = 10;
			}
			//console.log("function after \n"+ movieCdValue+  "/" + orderByValue + "/" + currentCnt+ "/" + additionalCnt);
			
			if(_returnListCnt == 0){
				console.log("0 _returnListCnt: " + _returnListCnt);
			
				sentinelDiv.hide();
				//검색된 아이템이 없을 경우 관찰중인 요소를 숨긴다.
			} else {
				if(_returnListCnt < movieAdditionalCnt) {
					console.log("< movieAdditionalCnt: " + movieAdditionalCnt);
					console.log("< _returnListCnt: " + _returnListCnt);
					sentinelDiv.hide();
				} else {
					console.log(">= _returnListCnt: " + _returnListCnt);
					sentinelDiv.show();
				}
			}
			
		});
		
	});
	
	intersectionObserver.observe(document.getElementById('sentinel')); // observing 실행
	
/* ------------------------ 무한 스크롤 --------------------------- */












// 	function showReviewList(userid, orderByValue){
		
// 		movieService.getReviewList(
// 			{userid:userid, orderBy:orderByValue}, function(list){
				
// 				var str = "";
				
// 				for(let i=0, len=list.length||0; i<len; i++) {

// 					console.log(list[i]);
// 					str += "<li class='clearfix'>";
					
// 					str += "	<div class='float--left'>";
// 					str += "		<div class='reviewPoster'>";
// 					str += "			<a href='/movieInfo/"+list[i].movieCd+"' target='_blank'>";
// 					str += "				<img  alt='"+list[i].movieNm+" 포스터' src='"+list[i].posterUrl+"'/></a></div>";
// 					str += "	</div>";
					
// 					str += "	<div class='float--left'>";
// 					str += "		<div><h3>"+list[i].movieNm+"</h3>";
// 					str += "			<div>"+list[i].prdtYear +" • " + list[i].repGenreNm + " • " + list[i].repNationNm + "</div></div>";
					
// 					str += "		<section class='starRating' data-value='0' data-movieCd='"+list[i].movieCd+"'><div class='stars'>";
// 					str += "			<c:forEach var='score' begin='0' step='1' end='4' >";
// 					str += "				<input class='stars-input' type='radio' name='" + list[i].movieCd + "-rating' value='${5.0 - score}' id='"+list[i].movieCd+"-rating-${5.0 - score}' >";
// 					str += "				<label class='stars-view' for='"+list[i].movieCd+"-rating-${5.0 - score}'><svg class='icon icon-star'><use xlink:href='#icon-star'></use></svg></label>";
// 					str += "				<input class='stars-input' type='radio' name='" + list[i].movieCd + "-rating' value='${4.5 - score}' id='"+list[i].movieCd+"-rating-${4.5 - score}' >";
// 					str += "				<label class='stars-view is-half' for='"+list[i].movieCd+"-rating-${4.5 - score}'><svg class='icon icon-star-half'><use xlink:href='#icon-star-half'></use></svg></label>";
// 					str += "			</c:forEach>";
// 					str += "		</div></section>";
// 					str += "	</div>";
					
					
// 					str += "</li>";
// 				}
				
// 				reviewListUL.append(str); // tip) .html(reviewListUL.html() + str) 사용 시 동적 이벤트인 별점 평가 불가능해짐
// 					// 기본적으로 html()은 내부 요소 비운 뒤 추가하는 형식이라 그런듯함
				
// 			});
// 	} // showAdditionalList(userid, orderByValue)
	

	
	

/////////////// 영화 리뷰 리스트 //////////////////////////////////////	


/////////////// 별점 ///////////////////////////////////////////////////
	
/* 이벤트 - 별점 평가  */
 
	 $(document).off("click").on("click", ".stars-input", function(event){
		// tip) checked 속성 변경해도 개발자 도구에선 확인 불가
		// 동적 생성된 속성값으로 요소 선택 불가.. ex) active
			// -> 이벤트 위임 필요
	//	var currentValue = 3;
	//	console.log("a");
		console.log($(this));
		
		var movieCdValue = $(this).closest("section.starRating").data("moviecd");
			// 랜덤페이지에서의 별점 평가를 위해 movieCd는 이 이벤트 요소를 통해 획득하게함
		
		var starRatingValue = $(this).closest("section.starRating").data("value"); // 별점 점수 변수
		
		console.log("before: data-value: " + $(this).closest("section.starRating").data("value") +"/ movieCd: "+ movieCdValue + "/ checked: "+$(this).prop('checked'));
		
		console.log($(this).val());
		console.log(starRatingValue);
		// 별점 등록/ 수정/ 제거
		if(starRatingValue == 0){
			// 별점 등록
		
			$(this).prop('checked', true); 
			

			starRatingValue = $(this).val();
			
			$(this).closest("section.starRating").data("value", starRatingValue);
			
			var starRating = {
					movieCd: movieCdValue,
					userid : userid,
					starRating : starRatingValue	
			}
			
				
			starRatingService.add(starRating, function(result){
				alert(result);
				console.log(starRating);
			});
			
		} else if(starRatingValue == $(this).val()){ 
			// 별점 제거

			$(this).prop('checked', false);
			
			starRatingValue = 0;
			
			$(this).closest("section.starRating").data("value", starRatingValue);
			
			starRatingService.remove({userid: userid, movieCd: movieCdValue}, function(result){
				alert(result);
			});
			
			
		} else{ // 별점 수정

			$(this).siblings('.stars-input').prop('checked', false);
			$(this).prop('checked', true); 
			
			starRatingValue = $(this).val();
			
			$(this).closest("section.starRating").data("value", starRatingValue);
			
			var starRating = {
					movieCd: movieCdValue,
					userid : userid,
					starRating : starRatingValue	
			}

			starRatingService.update(starRating, function(result){
				alert(result);
				console.log(starRating);
			});
			
			

		}

		console.log("after: data-value: " + $(this).closest("section.starRating").data("value") +"/ movieCd: "+movieCdValue + " / checked: "+$(this).prop('checked'));
	

	}); 
	



/////////////// 별점 ///////////////////////////////////////////////////













	
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