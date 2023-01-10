<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    

<%@ include file="../includes/header.jsp" %>


<main>
	<div class = "section" >
		<div class = "center">
			<div class = "inner">
			

				<!-- 박스오피스 -->
				<div class="margin--top margin--bottom ">
					<div>
						<h2>박스오피스 순위</h2>
					</div>
					<div>
						<ul class="movieList">
							<div class="clearfix">
								<c:forEach var="boxOfficeWithStar" items="${boxOfficeWithStarList }" > 
			
<%-- 									<c:set var="boxOfficeVO" value="${boxOfficeWithStar.boxOfficeVO }"/> --%>
								
									<li>
										<a title="${boxOfficeWithStar.movieNm } 포스터" href="/movieInfo/${boxOfficeWithStar.movieCd }">
											<div>
												<div class="position--relative img-border">
		<%-- 											<img alt="${boxOfficeWithStar.movieNm }" src="${boxOfficeWithStar.posterUrl }"> --%>
													<img  alt="<c:out value="${boxOfficeWithStar.movieNm }"/> 포스터" src="<c:out value="${boxOfficeWithStar.posterUrl eq null || boxOfficeWithStar.posterUrl eq ' ' ? '/resources/img/poster-not-available.jpg' : boxOfficeWithStar.posterUrl}" />">
													<div class="boxOffice--rank">${boxOfficeWithStar.rank }</div>
												</div>
											</div>
											
											<div>
												<div class="movie-Name font--bold margin--top5 margin--bottom5">${boxOfficeWithStar.movieNm }</div>
												<div class="margin--bottom3">${boxOfficeWithStar.prdtYear } • ${boxOfficeWithStar.repNationNm }</div>
												
											</div>
											<c:if test="${boxOfficeWithStar.openDt != null}">
											
												<!-- 개봉일 
												https://sowon-dev.github.io/2022/11/02/221103JSP-utilDatefmt/ -->
												<jsp:useBean id="now" class="java.util.Date"/>
												<fmt:formatDate var="today" value="${now }" pattern="yyyyMMdd000000"/>	
												<fmt:parseDate var="nowfmt" value="${today }" pattern="yyyyMMddHHmmss"/>
		
												<fmt:parseNumber value="${nowfmt.time}" var="nowfmtTime"/><!-- .time 필수 -->
												<fmt:parseNumber value="${boxOfficeWithStar.openDt.time}" var="openDtfmtTime"/><!-- .time 필수 -->
												<fmt:parseNumber value="${(nowfmtTime - openDtfmtTime) / (1000*60*60*24)}" var="timeDefference"/>
											</c:if>
											
											<!-- 평점 -->
												<!-- 개봉 전 영화는 평점 미출력 -->
											<c:if test="${timeDefference >=0 }">
											
												<c:choose>
													<c:when test="${boxOfficeWithStar.userStarRating eq 0.0 }">	
															<div style="color:red;" class="margin--bottom3">평균 ★${boxOfficeWithStar.avgStarRating}</div>
													</c:when>
													<c:when test="${boxOfficeWithStar.userStarRating ne 0.0 }">
														<div style="color:orange" class="margin--bottom3">평가함 ★${boxOfficeWithStar.userStarRating}</div>
													</c:when>
												</c:choose>
											</c:if>
											
											<c:if test="${boxOfficeWithStar.openDt != null}">
												<c:choose>
													<c:when test="${timeDefference >= 0}">
														<div class="margin--bottom3">개봉 ${timeDefference +1 }일째</div>	
													</c:when>
													<c:otherwise>
														<div class="margin--bottom3">개봉 ${-timeDefference }일전</div>
													</c:otherwise>			
												</c:choose>		
											</c:if>
											
											
											<!-- 관객수 -->
											<div>						
												<c:choose>
													<c:when test="${boxOfficeWithStar.audiCnt ge 10000}">
														<fmt:formatNumber var="audiCntFmt" value="${boxOfficeWithStar.audiCnt / 10000}" pattern="#,###"/> 
														<div class="margin--bottom3">당일 관객 ${audiCntFmt}만명</div>
													</c:when>
													<c:when test="${boxOfficeWithStar.audiCnt lt 10000}">
														<fmt:formatNumber var="audiCntFmt" value="${boxOfficeWithStar.audiCnt}" pattern="#,###"/> 
														<div class="margin--bottom3">당일 관객 ${audiCntFmt}명</div>
													</c:when>
												</c:choose>
											
												<c:choose>
													<c:when test="${boxOfficeWithStar.audiAcc ge 10000}">
														<fmt:formatNumber var="audiAccFmt" value="${boxOfficeWithStar.audiAcc / 10000}" pattern="#,###"/> 
														<div>누적 관객 ${audiAccFmt}만명</div>
													</c:when>
													<c:when test="${boxOfficeWithStar.audiAcc lt 10000}">
														<fmt:formatNumber var="audiAccFmt" value="${boxOfficeWithStar.audiAcc}" pattern="#,###"/> 
														<div>누적 관객 ${audiAccFmt}명</div>
													</c:when>
												</c:choose>
											</div>
											
										</a>
									</li>
								</c:forEach>
							</div>
							
						</ul>
					</div>
				</div>
				<!-- 박스오피스 -->

				<!-- 상위 평점 영화 리스트 -->
				<c:if test="${fn:length(highStarRatingMovieList) ne 0}">
					<div class="margin--bottom">
						<div>
							<h2>상위 평점 영화</h2>
						</div>
						<div class="slide" style="overflow: hidden">
							<ul class="slide-container clearfix">
								<div >
									<c:forEach var="highStarRatingMovie" items="${highStarRatingMovieList }" > 					
										
										<li class="slide-inner">
											<a title="${highStarRatingMovie.movieNm } 포스터" href="/movieInfo/${highStarRatingMovie.movieCd }">
												<div>
													<div class="img-border">
										
														<img  alt="<c:out value="${highStarRatingMovie.movieNm }"/> 포스터" src="<c:out value="${highStarRatingMovie.posterUrl eq null || highStarRatingMovie.posterUrl eq ' ' ? '/resources/img/poster-not-available.jpg' : highStarRatingMovie.posterUrl}" />">
													</div>
												</div>
												
												<div>
													<div class="movie-Name font--bold margin--top5 margin--bottom5">${highStarRatingMovie.movieNm }</div>
													<div class="text--ellipsis margin--bottom3">${highStarRatingMovie.prdtYear } • ${highStarRatingMovie.repGenreNm } • ${highStarRatingMovie.repNationNm }</div>
												</div>
												
												<!-- 평점 -->					
												<div style="color:red">평균★${highStarRatingMovie.avgStarRating}</div>
											</a>
										</li>
									</c:forEach>
								</div>
							</ul>
							
							<!-- 슬라이드 버튼 -->
							<c:if test="${fn:length(highStarRatingMovieList) > 5}">
								<div class="slideBtnGroup">
									<div class="slideBtn slidePrevBtn">
										<img alt="prev" src="/resources/img/prev.png">
									</div>
									<div class="slideBtn slideNextBtn">	
										<img alt="next" src="/resources/img/next.png">
									</div>
								</div>
							</c:if>
						
						</div>	
					</div>
				</c:if>
				
			<!-- 선호 감독 영화 리스트 -->
			<c:if test="${favoriteDirectorSMovieList ne null}">
<%-- 			<c:if test="${favoriteDirectorSMovieList ne null && fn:length(favoriteDirectorSMovieList) ne 0}"> --%>
				<div class="margin--bottom">
					<div>
						<h2>선호하는 감독 ${favoriteDirectorSMovieList[0].favoritePeopleNm }의 작품</h2>
					</div>
					<div class="slide" style="overflow: hidden">
						<ul class="slide-container clearfix">
							<div >
								<c:forEach var="favoriteDirectorSMovie" items="${favoriteDirectorSMovieList }" > 					
									
									<li class="slide-inner">
										<a title="${favoriteDirectorSMovie.movieNm } 포스터" href="/movieInfo/${favoriteDirectorSMovie.movieCd }">
											<div>
												<div class="img-border">
									
													<img  alt="<c:out value="${favoriteDirectorSMovie.movieNm }"/> 포스터" src="<c:out value="${favoriteDirectorSMovie.posterUrl eq null || favoriteDirectorSMovie.posterUrl eq ' ' ? '/resources/img/poster-not-available.jpg' : favoriteDirectorSMovie.posterUrl}" />">
												</div>
											</div>
											
											<div>
												<div class="movie-Name font--bold margin--top5 margin--bottom5">${favoriteDirectorSMovie.movieNm }</div>
												<div class="text--ellipsis margin--bottom3">${favoriteDirectorSMovie.prdtYear } • ${favoriteDirectorSMovie.repGenreNm }</div>			
											</div>
											
											<!-- 평점 -->
											<div style="color:red">평균★${favoriteDirectorSMovie.avgStarRating}</div>
										</a>
									</li>
								</c:forEach>
							</div>
						</ul>
						
						<!-- 슬라이드 버튼 -->
						<c:if test="${fn:length(favoriteDirectorSMovieList) > 5}">
							<div class="slideBtnGroup">
								<div class="slideBtn slidePrevBtn">
									<img alt="prev" src="/resources/img/prev.png">
								</div>
								<div class="slideBtn slideNextBtn">	
									<img alt="next" src="/resources/img/next.png">
								</div>
							</div>
						</c:if>
						
					</div>	
				</div>		
			</c:if>
			
			
			<!-- 선호 배우 영화 리스트 -->
			<c:if test="${favoriteActorSMovieList ne null }">
				<div class="margin--bottom">
					<div>
						<h2>선호하는 배우 ${favoriteActorSMovieList[0].favoritePeopleNm }의 작품</h2>
					</div>
					<div class="slide" style="overflow: hidden">
						<ul class="slide-container clearfix">
							<div >
								<c:forEach var="favoriteActorSMovie" items="${favoriteActorSMovieList }" > 					
									
									<li class="slide-inner">
										<a title="${favoriteActorSMovie.movieNm } 포스터" href="/movieInfo/${favoriteActorSMovie.movieCd }">
											<div>
												<div class="img-border">
									
													<img  alt="<c:out value="${favoriteActorSMovie.movieNm }"/> 포스터" src="<c:out value="${favoriteActorSMovie.posterUrl eq null || favoriteActorSMovie.posterUrl eq ' ' ? '/resources/img/poster-not-available.jpg' : favoriteActorSMovie.posterUrl}" />">
												</div>
											</div>
											
											<div>
												<div class="movie-Name font--bold margin--top5 margin--bottom5">${favoriteActorSMovie.movieNm }</div>
												<div class="text--ellipsis margin--bottom5">${favoriteActorSMovie.prdtYear } • ${favoriteActorSMovie.repGenreNm }</div>						
											</div>
											
											<!-- 평점 -->
											<div style="color:red">평균★${favoriteActorSMovie.avgStarRating}</div>
										</a>
									</li>
								</c:forEach>
							</div>
						</ul>
						
						<!-- 슬라이드 버튼 -->
						<c:if test="${fn:length(favoriteActorSMovieList) > 5}">
							<div class="slideBtnGroup">
								<div class="slideBtn slidePrevBtn">
									<img alt="prev" src="/resources/img/prev.png">
								</div>
								<div class="slideBtn slideNextBtn">	
									<img alt="next" src="/resources/img/next.png">
								</div>
							</div>
						</c:if>
						
					</div>	
				</div>		
			</c:if>
			
			
			<!-- 선호 장르 영화 리스트 -->
			<c:if test="${favoriteGenreMovieList ne null }">
				<div class="margin--bottom">
					<div>
						<h2>#${favoriteGenreMovieList[0].favoriteGenreNm1 } #${favoriteGenreMovieList[0].favoriteGenreNm2 }</h2>
					</div>
					<div class="slide" style="overflow: hidden">
						<ul class="slide-container clearfix">
							<div >
								<c:forEach var="favoriteGenreMovie" items="${favoriteGenreMovieList }" > 					
									
									<li class="slide-inner">
										<a title="${favoriteGenreMovie.movieNm } 포스터" href="/movieInfo/${favoriteGenreMovie.movieCd }">
											<div>
												<div class="img-border">
									
													<img  alt="<c:out value="${favoriteGenreMovie.movieNm }"/> 포스터" src="<c:out value="${favoriteGenreMovie.posterUrl eq null || favoriteGenreMovie.posterUrl eq ' ' ? '/resources/img/poster-not-available.jpg' : favoriteGenreMovie.posterUrl}" />">
												</div>
											</div>
											
											<div>
												<div class="movie-Name font--bold margin--top5 margin--bottom5">${favoriteGenreMovie.movieNm }</div>
												<div class="margin--bottom3">${favoriteGenreMovie.prdtYear } • ${favoriteGenreMovie.repNationNm }</div>			
												<div class="text--ellipsis margin--bottom3">${favoriteGenreMovie.genreAlt }</div>
											</div>
											
											<!-- 평점 -->
											<div style="color:red">평균★${favoriteGenreMovie.avgStarRating}</div>
										</a>
									</li>
								</c:forEach>
							</div>
						</ul>

						<!-- 슬라이드 버튼 -->
						<c:if test="${fn:length(favoriteGenreMovieList) > 5}">
							<div class="slideBtnGroup">
								<div class="slideBtn slidePrevBtn">
									<img alt="prev" src="/resources/img/prev.png">
								</div>
								<div class="slideBtn slideNextBtn">	
									<img alt="next" src="/resources/img/next.png">
								</div>
							</div>
						</c:if>
					</div>	
				</div>		
			</c:if>
			
			
			
			
			
			
<!-- 			 <div class="wrapper"> -->
<!-- 		        <form id="todo-form" class="form"> -->
<!-- 		            <input required maxlength="10" type="text" placeholder="SEARCH"> -->
<!-- 		        </form> -->
<!-- 		        <button type="submit" form="todo-form">제출하기</button> -->
<!-- 		        <div class="todo-inner"> -->
<!-- 		            <div class="allDelete off"> -->
<!-- 		                <h2 class="tit">최근 검색어</h2> -->
<!-- 		                <span class="btn">모두 지우기 ❌</span> -->
<!-- 		            </div> -->
<!-- 		            <p class="txt"></p> -->
<!-- 		            <ul id="todo-list"></ul> -->
<!-- 		        </div> -->
<!--    		 	</div> -->
			
		
	
				
			</div>			
		</div>
	</div>
</main>


<script type="text/javascript">
$(document).ready(function(){
	
	var result = '<c:out value="${result }" />'; // rttr.addFlashAttribute 통해 넘긴 파라미터
	console.log("result",result);
	if(result != ''){
		alert(result);
	}
	// 비밀번호 변경 Post 처리 결과 리턴 -> 로그아웃
	var logoutResult = '<c:out value="${logoutResult }" />';
	if(logoutResult == 'logout'){
		alert("비밀번호가 변경되었습니다. 다시 로그인해주세요.");
		$("#logoutForm").submit();
		
	}
	
/* ============================================================= */
 
 /*
   const toDoForm = document.querySelector('#todo-form');
const toDoInput = toDoForm.querySelector('input');
const toDoList = document.querySelector('#todo-list');
const allDelete = document.querySelector('.allDelete');
const txt = document.querySelector('.txt');
const TODOS_KEY = "todos";

let toDos = new Array();

function saveToDos() { //item을 localStorage에 저장합니다.
    typeof(Storage) !== 'undefined' && localStorage.setItem(TODOS_KEY, JSON.stringify(toDos));
};

function allDeleteToDo() { //전체 item을 삭제
    localStorage.clear(toDos);
    toDoList.innerText = '최근검색어 내역이 없습니다.';
};

function deleteToDo(e) { //각각의 item을 삭제
    const li = e.target.parentElement;
    li.remove();
    toDos = toDos.filter((toDo) => toDo.id !== parseInt(li.id));
    toDos.length === 0 && (txt.innerText = '최근검색어 내역이 없습니다.')
    saveToDos();
};

function paintToDo(newTodo) { //화면에 뿌림
    const {id, text} = newTodo;
    const item = document.createElement("li");
    const span = document.createElement("span");
    const button = document.createElement("button");
    item.id = id;
    span.innerText = text;
    button.innerText = '❌';
    button.addEventListener("click", deleteToDo); 
    	// tip) addEventListener 사용해 리스너 추가시 함수에 전달되는 첫번째 인수는 event -> 첫번째 매개변수에 저장됨(이를 e, event 등으로 매개변수명 지정하여 사용하는 것)
    allDelete.addEventListener("click", allDeleteToDo);
    item.appendChild(span);
    item.appendChild(button);
    toDoList.appendChild(item);
    newTodo !== null && allDelete.classList.remove('off');
};

function handleToDoSubmit(event) { //form 전송
    event.preventDefault();
    const newTodoItem = toDoInput.value;
    toDoInput.value = '';
    const newTodoObj = {
        id: Date.now(),
        text: newTodoItem
    };
    toDos.push(newTodoObj);
    paintToDo(newTodoObj);
    saveToDos();
};

toDoForm.addEventListener('submit', handleToDoSubmit);

const savedToDos = JSON.parse(localStorage.getItem(TODOS_KEY));
if(savedToDos !== null) {
    toDos = savedToDos //전에 있던 items들을 계속 가지도 있다록 합니다. 
    savedToDos.forEach(paintToDo);
}
*/
/* ============================================================= */
	
	
// 	transform: translate(-800px);
	
	slideIdx = 0; // 기본값 1
// 	$('.slidePrevBtn').css('visibility','hidden');
	$('.slidePrevBtn').css('display','none');
	
/* 슬라이드 버튼 클릭 이벤트 */
	$('.slideBtn').on("click", function(){
		console.log(slideIdx);
		
		var slideContainerUL = $(this).parents('.slideBtnGroup').siblings('ul.slide-container');
		
		// 다음 버튼 클릭
		if($(this).hasClass("slideNextBtn")){
			
			var slideNextBtn = $(this);
			var slidePrevBtn = $(this).siblings('.slidePrevBtn');
			
			console.log(slideContainerUL.attr("class"));
			console.log(slideNextBtn.attr("class"));
			console.log(slidePrevBtn.attr("class"));
			
			slideIdx += 1;
			slideContainerUL.css('transform', 'translate(calc('+slideIdx +' * -100%))');
			slideNextBtn.css('transform', 'translate(calc('+0 +' * -100%))'); // calc() : css 사칙연산
	
			
	// 		slidePrevBtn.css('visibility','visible');
			slidePrevBtn.css('display','block');
			if(slideIdx == 3){
	// 			slideNextBtn.css('visibility','hidden');
				slideNextBtn.css('display','none');
				
			} else {
	// 			slideNextBtn.css('visibility','visible');
				slideNextBtn.css('display','block');
			}
			
		}
		// 이전 버튼 클릭
		else if($(this).hasClass("slidePrevBtn")){
			
			var slidePrevBtn = $(this);
			var slideNextBtn = $(this).siblings('.slideNextBtn');
			
			slideIdx -= 1;
			
			slideContainerUL.css('transform', 'translate(calc('+slideIdx +' * -100%))');
			slideNextBtn.css('transform', 'translate(calc('+0 +' * -100%))');
			
			if(slideIdx == 0){
	// 			slidePrevBtn.css('visibility','hidden');
				slidePrevBtn.css('display','none');
	// 			slideNextBtn.css('visibility','visible');
				slideNextBtn.css('display','block');
			} else {
	// 			slidePrevBtn.css('visibility','visible');
				slidePrevBtn.css('display','block');
			}
			
		} 
	});
	
	
});
</script>	
	
	

