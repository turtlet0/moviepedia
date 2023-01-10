<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    

<%@ include file="../includes/header.jsp" %>

<%-- ${movie }
${movie.movieCd }
${peopleList } --%>
<%-- //////////////////////////
<c:forEach var="people" items="${peopleList }">
	<c:out value="${people }" /> ${people.peopleNm }
</c:forEach> --%>
<%-- <c:out value="${comment }" /> --%>
<!-- movieSection -->
<main>
	<div class = "section" >
		<div class = "center">
			<div class = "innerFix">
				
				
				
				

				<!-- 별점 평가 분석 -->
<!-- 				<section> -->
<!-- 					<header> -->
<!-- 						<h2>별점 분포</h2> -->
<!-- 					</header> -->
<!-- 					<div> -->
<%-- 						<h3>${starRatingAnalysis.resultMessage }</h3> --%>
<!-- 						<div> -->
<!-- 							<ul class="graph"> -->
<%-- 								<c:set var="starNum" value="0.5" /> --%>
<%-- 								<c:forEach var="cntByStarDTO" items="${starRatingAnalysis.cntByStarDTOList }" >	 --%>
<%-- 										<c:set var="star" value="${cntByStarDTO.star }"/> --%>
<%-- 										<c:set var="starCnt" value="${cntByStarDTO.starCnt }"/> --%>
										
<%-- 										<li class="graph-bar bar1" graph-val="${starCnt }" graph-color="yellow"> --%>
<%-- 											<c:if test="${starNum % 1 == 0 }"> --%>
<%-- 												<fmt:parseNumber var="starNum"  integerOnly="true" value="${starNum }"/> --%>
<%-- 												<span>${starNum }</span> --%>
<%-- 											</c:if> --%>
<!-- 										</li> -->
										
<%-- 										<c:set var="starNum" value="${starNum+0.5 }" /> --%>
<%-- 								</c:forEach> --%>
<!-- 							</ul> -->
<!-- 						</div> -->
<!-- 						<div> -->
<!-- 							<ul class="clearfix"> -->
<!-- 								<li class="float--left"> -->
<%-- 									<div class="font--bold">${starRatingAnalysis.starRatingAvg }</div> --%>
<!-- 									<div>별점 평균</div> -->
<!-- 								</li> -->
<!-- 								<li class="float--left"> -->
<%-- 									<div class="font--bold">${starRatingAnalysis.starRatingCnt }</div> --%>
<!-- 									<div>별점 개수</div> -->
<!-- 								</li> -->
<!-- 								<li class="float--left"> -->
<!-- 									<div id="theMostRatedStar font--bold"></div> -->
<!-- 									<div>많이 준 별점</div> -->
<!-- 								</li>	 -->
<!-- 							</ul> -->
<!-- 						</div> -->
						
<!-- 					</div> -->
<!-- 				</section> -->
				
				<section style="margin-top: 75px;">
					<header>
						<!-- <p class="margin--bottom20 padding--h5" style="color:red; font-size:20px; font-weight: bold;">별점 분포</p> -->
						<h2>별점 분포</h2>
					</header>
					<div>
						<p style="color:red;" class="font--bold margin--bottom30 textAlign--center" >${starRatingAnalysis.resultMessage }</p>
						
						<!-- 별점 그래프 -->
						<div>
							<ul class="graph">
								<c:set var="starNum" value="0.5" />
								<c:forEach var="cntByStarDTO" items="${starRatingAnalysis.cntByStarDTOList }" >	
										<c:set var="star" value="${cntByStarDTO.star }"/>
										<c:set var="starCnt" value="${cntByStarDTO.starCnt }"/>
										
										<li class="graph-bar bar1" graph-val="${starCnt }" graph-color="yellow">
											<c:if test="${starNum % 1 == 0 }">
												<fmt:parseNumber var="starNum"  integerOnly="true" value="${starNum }"/>
												<span>${starNum }</span>
											</c:if>										
												<span style="display: none;" class="topSpan">${starNum +0.0}</span>
										</li>
										
										<c:set var="starNum" value="${starNum+0.5 }" />
								</c:forEach>
							</ul>
						</div>
						<!-- 별점 평가 정보 -->
						<div  class="margin--top30 margin--bottom10 textAlign--center">
							<ul class="clearfix">
								<li style="width:33%;" class="float--left border--right textAlign--center">
									<div class=" margin--bottom5 font--bold">${starRatingAnalysis.starRatingAvg }</div>
									<div>별점 평균</div>
								</li>
								<li style="width:33%;" class="float--left border--right textAlign--center">
									<div  class=" margin--bottom5 font--bold">${starRatingAnalysis.starRatingCnt }</div>
									<div>별점 개수</div>
								</li>
								<li style="width:33%;" class="float--left textAlign--center">
									<div id="theMostRatedStar" class=" margin--bottom5 font--bold"></div>
									<div>많이 준 별점</div>
								</li>	
							</ul>
						</div>
						
					</div>
				</section>
				
				<hr>
				
				<!-- 선호 배우 -->
				<section class="margin--top20">
					<header>
						<h2>선호 배우</h2>
					</header>
					 <ul class="padding--h5">
					 	<c:forEach var="favoriteActor" items="${favoriteActorList }">
					 		<li class="margin--bottom15 padding--bottom5 border--bottom">
					 			<a title="${favoriteActor.peopleNm }" href="/people/${favoriteActor.peopleCd }">
					 				<div class="clearfix margin--bottom5">
					 					<div class="float--left font--bold margin--right20 margin--bottom5">
						 					<span>${favoriteActor.peopleNm }</span>
					 					</div>
					 					<div class="float--left">
						 					<span>${favoriteActor.finalScore }점 • ${favoriteActor.cntStarRating }편</span>
					 					</div>
					 				</div>
					 				<div class="clearfix">
										<c:set var="repMovieList" value="${fn:split(favoriteActor.repMovieList, ',') }"/>
										<c:forEach var="repMovie" items="${repMovieList }" varStatus="status">	
											<c:set var="repMovieNm" value="${fn:split(repMovie, '|')[1] }"/>
											<div class="float--left repNm margin--right5">${repMovieNm }</div>				
										</c:forEach>
									</div>
					 			</a>
					 		</li>
					 		
					 	</c:forEach>
					 </ul>
				 </section>
				 
				<hr>
				<!-- 선호 감독 -->
				<section class="margin--top20">
					<header>
						<h2>선호 감독</h2>
					</header>
					 <ul class="padding--h5">
					 	<c:forEach var="favoriteDirector" items="${favoriteDirectorList }">
					 		<li class="margin--bottom15 padding--bottom5 border--bottom">
					 			<a title="${favoriteDirector.peopleNm }" href="/people/${favoriteDirector.peopleCd }">	
					 				<div class="clearfix margin--bottom5">
					 					<div class="float--left font--bold margin--right20 margin--bottom5">
						 					<span>${favoriteDirector.peopleNm }</span>
					 					</div>
					 					<div class="float--left">
						 					<span>${favoriteDirector.finalScore }점 • ${favoriteDirector.cntStarRating }편</span>
					 					</div>
					 				</div>
					 				<div class="clearfix">
										<c:set var="repMovieList" value="${fn:split(favoriteDirector.repMovieList, ',') }"/>
										<c:forEach var="repMovie" items="${repMovieList }" varStatus="status">	
											<c:set var="repMovieNm" value="${fn:split(repMovie, '|')[1] }"/>
											<div class="float--left repNm margin--right5">${repMovieNm }</div>				
										</c:forEach>
									</div>
					 			</a>
					 		</li>
					 		
					 	</c:forEach>
					 </ul>
				 </section>
				 
				<hr>
				<!-- 선호 장르 -->
				<section class="margin--top20">
					<header>
						<h2>선호 장르</h2>
					</header>
					<div class="padding--h5">
						<p style="color:red;" class="font--bold textAlign--center margin--bottom20">${favoriteGenreAnalysis.resultMessage }</p>
					 	<c:set var="favoriteGenreList" value="${favoriteGenreAnalysis.favoriteGenreDTOList }"/>
					 	<div class="favoriteGenreTop3">
							<ul class="clearfix margin--bottom20">
							 	<c:forEach var="favoriteGenre" items="${favoriteGenreList }" begin="0" end="2">
							 		<li style="width:33%" class="float--left textAlign--center">
							 			<div class="font--bold margin--bottom5">${favoriteGenre.genre }</div>
						 				<div>
						 					${favoriteGenre.finalScore }점 • ${favoriteGenre.cntStarRating }편
						 				</div>	
							 		</li>
							 		
							 	</c:forEach>
							 </ul>
					 	</div>
					 	
						 <ul class="margin--right30">
						 	<c:forEach var="favoriteGenre" items="${favoriteGenreList }" begin="3">
						 		<li>
							 		<div class="clearfix margin--bottom10">
							 			<div class="margin--bottom5 float--left">${favoriteGenre.genre }</div>
						 				<div class="float--right">
						 					${favoriteGenre.finalScore }점 • ${favoriteGenre.cntStarRating }편
						 				</div>	
							 		</div>
						 		</li>
						 		
						 	</c:forEach>
						 </ul>
					</div>
				 </section>
				 
				<hr>
				 <!-- 선호 국가 -->
		 		<section class="margin--top20">
					<header>
						<h2>선호 국가</h2>
					</header>
					<div class="padding--h5">
						 <ul class="clearfix margin--bottom20">
						 	<c:forEach var="favoriteNation" items="${favoriteNationList }" begin="0" end="2">
						 		<li style="width:33%" class="float--left textAlign--center">
						 			<div class="font--bold margin--bottom5">${favoriteNation.nation }</div>
					 				<div>
					 					${favoriteNation.finalScore }점 • ${favoriteNation.cntStarRating }편
					 				</div>	
						 		</li>
						 		
						 	</c:forEach>
						 </ul>
					</div>
					<ul class=" margin--right30">
					 	<c:forEach var="favoriteNation" items="${favoriteNationList }" begin="3">
					 		<li>		
				 				<div class="clearfix margin--bottom10">
						 			<div class="margin--bottom5 float--left">${favoriteNation.nation }</div>
					 				<div class="float--right">
					 					${favoriteNation.finalScore }점 • ${favoriteNation.cntStarRating }편
					 				</div>	
						 		</div>
					 		</li>
					 		
					 	</c:forEach>
					 </ul>
				 </section>
				 
				 <hr>
				 <!-- 감상 시간 -->
				 <section class="margin--top20 margin--bottom30">
					<header>
						<h2>감상 시간</h2>
					</header>
					<div class="padding--h5 textAlign--center">
						<p style="font-size: 22px; font-weight: bold" class="margin--bottom10">${totalShowTmAnalysis.totalShowTm } 시간</p>
						<p  class="color--red font--bold" >${totalShowTmAnalysis.resultMessage }</p>
					</div>
				</section>
				 
				 
			</div> <!-- <div class = "inner"> -->	
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
	
	
	
	var csrfHeaderName = "${_csrf.headerName}";
	var csrfTokenValue = "${_csrf.token}"
	
	/* 38.5.3 Ajax로 CSRF 토큰 전송 방식
 		: ajaxSend() 이용 시 모든 Ajax 전송 시 CSRF 토큰 같이 전송하도록 되어있음 */
   	$(document).ajaxSend(function(e, xhr, options){
   		xhr.setRequestHeader(csrfHeaderName, csrfTokenValue);
   	});
	
	
	/* 그래프 */
	var maxIndex = $("li.graph-bar").length;
	
	var theMostRatedStar = 0;
	var maxVal = 0; // 가장 많은 평가를 받은 별점의 개수
	
	for(var i=0; i<maxIndex; i++){
	  var val = Number($("li.graph-bar").eq(i).attr('graph-val'));
	  if(maxVal < val){
		  maxVal = val;
	  } 
	}
	
	for(var i=0; i<maxIndex; i++){
		  var val = $("li.graph-bar").eq(i).attr('graph-val');
		  if(val == maxVal) {
			  $("li.graph-bar").eq(i).css({
			    "left": (i)*37+"px",
			    "background":"#ffa136",
			    "height":val/maxVal*100+"%"	  
			    
			  });
			  
			  theMostRatedStar = ((i+1)/2).toFixed(1);
			  console.log(((i+1)/2).toFixed(1));

			  $("li.graph-bar[graph-val="+val+"]").find(".topSpan").show();
			  
		  } else {
			  $("li.graph-bar").eq(i).css({
				    "left": (i)*37+"px",
				    "height":val/maxVal*100+"%"
			  });
		  }
	}
	
	$('#theMostRatedStar').html(theMostRatedStar);

});	
</script>	
	
	
	
	<!-- 14.3 a태그 동작 방지 위한 JS 처리 
    	별도의 form 태그 이용해 동작 처리
    	- 페이지 번호 버튼 클릭 시 이동 -->
   	<form id="actionForm" action="/contents" method="post">
   		<input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }">
		<!-- 이 EL의 이름은 실제 브라우저에서 _csrf라는 이름으로 처리됨
			value 값은 임의의값 지정됨
			브라우저 페이지 소스 보기에서 확인 가능 -->   
			
   		<input type="hidden" name="movieNm" value="${movie.movieNm}">
   		<input type="hidden" name="posterUrl" value="${movie.posterUrl}">
   	</form>	
	
<!-- Modal - Comment -->
 	<div id="commentModal" class="modal" >
 	${comment.commentCd }
		<div class="modal_content" title="클릭하면 창이 닫힙니다.">
			
			
	            <header><c:out value="${movie.movieNm }" /></header>

	            <div>
	            	<div>
	            		<textarea rows="50" cols="50" name="comment" maxlength="1000" placeholder="이 영화에 대한 생각을 자유롭게 표현해주세요."></textarea>
	  
	            	</div>
	            	<div>
	            		<!-- <input name="userid" value=''> -->
	            		
	            	</div>
            	<%-- <sec:authorize access="isAuthenticated()">    	
	            	<div>
	            		<input name="userCd" value='<sec:authentication property="principal.username"/>'>
	            		
	            	</div>
            	</sec:authorize> --%>
	            </div>
	            <div>
	                <button id="modalModBtn" type="button">수정</button>
	                <button id="modalRegisterBtn" type="button">등록</button>

	            </div>
		</div>
	</div>
	<!-- /.modal -->
	
<!-- Modal - Remove -->
	<div id="removeModal" class="modal">
		<div class="modal_content" title="클릭하면 창이 닫힙니다.">
			<div>알림</div>
			<div id="removeModalText"></div>
			<div size="2">
				<button id="modalCloseBtn" type="button">취소</button>
				<button id="modalRemoveBtn" type="button">확인</button>
			</div>
		</div>
	</div>

	
	
	
<!-- Modal - Comment -->

