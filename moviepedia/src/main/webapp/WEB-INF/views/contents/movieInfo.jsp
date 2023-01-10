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
			
				<div class="clearfix">
					<div class="poster img-border float--left margin--right">
						<a href='<c:out value="${movie.posterUrl }" />' target="_blank">
							<img  alt="<c:out value="${movie.movieNm }"/> 포스터" src="<c:out value="${movie.posterUrl eq null || movie.posterUrl eq ' ' ? '/resources/img/poster-not-available.jpg' : movie.posterUrl}" />">
								<!-- 삼항연산자 이용해 포스터 없는경우 대비 -->
						</a>
						
					</div>
					<div style="width: 400px;" class="float--left">
						<h2><c:out value="${movie.movieNm }" /></h2>
						<div class="padding--v5"><c:out value="${movie.prdtYear }" /> • <c:out value="${movie.genreAlt }" /> • <c:out value="${movie.nationAlt }" /></div>
						
						<!-- 별점 정보 -->
						<c:choose>
							<c:when test="${starAvgCnt.avg ne null}">
								<fmt:formatNumber var="starAvg"  value="${movieStarRatingAnalysis.starRatingAvg }" pattern=".0" /><!-- 기본이 반올림. 버림은 계산식통해 구현 -->
							</c:when>
							<c:otherwise>
								<c:set var="starAvg" value="0.0"/>
							</c:otherwise>
						</c:choose>			
						<fmt:formatNumber var="starCnt" value="${movieStarRatingAnalysis.starRatingCnt }" pattern="#,###" />
						<div style="color:red;" class="starAvgCntDiv border--top padding--v5">평균 ★${starAvg } (${starCnt }명)</div> <!-- 별점 변경 시 비동기 갱신 안함 -->
						
					<!-- 별점 평가-->							
						<section id="starRatingSection" class=" border--top padding--v5"  data-value="${starRating eq null ? 0 : starRating.starRating }" data-movieCd="${movie.movieCd }">
							<div id="starRatingWords" >
								<span style="font-size: 17px;">평가하기</span>
							</div>
							<div class="starRating">
								<div class="stars">
									<c:forEach var="score" begin="0" step="1" end="4" >
										<input class="stars-input"  type="radio" name="${movie.movieCd }-rating" value="${5.0 - score}" id="${movie.movieCd }-rating-${5.0 - score}" 
										<c:if test="${starRating.starRating eq 5.0-score }">checked</c:if> >
											<!-- tip) 5.0-score -> starRating값은 실수이므로 5가 아닌 5.0으로 지정함-->
										 <label class="stars-view"  for="${movie.movieCd }-rating-${5.0 - score}">
											 <svg class="icon icon-star">
									      		<use xlink:href="#icon-star"></use>
										    </svg>
									    </label>
									    <input class="stars-input" type="radio" name="${movie.movieCd }-rating" value="${4.5 - score}" id="${movie.movieCd }-rating-${4.5 - score}" 
									     <c:if test="${starRating.starRating eq 4.5-score }">checked</c:if> >
										    <label class="stars-view is-half" for="${movie.movieCd }-rating-${4.5 - score}">
										    <svg class="icon icon-star-half">
									      		<use xlink:href="#icon-star-half"></use>
									    	</svg>
								    	</label>
									</c:forEach>
								
								 <%-- <input class="stars-input" type="radio" name="${movie.movieCd }-rating" value="5" id="${movie.movieCd }-rating-5">
								  <label class="stars-view" for="${movie.movieCd }-rating-5"><svg class="icon icon-star">
								      <use xlink:href="#icon-star"></use>
								    </svg></label>
								  <input class="stars-input" type="radio" name="${movie.movieCd }-rating" value="4.5" id="${movie.movieCd }-rating-4.5" > <label class="stars-view is-half" for="${movie.movieCd }-rating-4.5"><svg class="icon icon-star-half">
								      <use xlink:href="#icon-star-half"></use>
								    </svg></label>
								  <input class="stars-input" type="radio" name="${movie.movieCd }-rating" value="4" id="${movie.movieCd }-rating-4"> <label class="stars-view" for="${movie.movieCd }-rating-4"><svg class="icon icon-star">
								      <use xlink:href="#icon-star"></use>
								    </svg></label>
								  <input class="stars-input" type="radio" name="${movie.movieCd }-rating" value="3.5" id="${movie.movieCd }-rating-3.5"> <label class="stars-view is-half" for="${movie.movieCd }-rating-3.5"><svg class="icon icon-star-half">
								      <use xlink:href="#icon-star-half"></use>
								    </svg></label>
								  <input class="stars-input" type="radio" name="${movie.movieCd }-rating" value="3" id="${movie.movieCd }-rating-3"> <label class="stars-view" for="${movie.movieCd }-rating-3"><svg class="icon icon-star">
								      <use xlink:href="#icon-star"></use>
								    </svg></label>
								  <input class="stars-input" type="radio" name="${movie.movieCd }-rating" value="2.5" id="${movie.movieCd }-rating-2.5"> <label class="stars-view is-half" for="${movie.movieCd }-rating-2.5"><svg class="icon icon-star-half">
								      <use xlink:href="#icon-star-half"></use>
								    </svg></label>
								  <input class="stars-input" type="radio" name="${movie.movieCd }-rating" value="2" id="${movie.movieCd }-rating-2"> <label class="stars-view" for="${movie.movieCd }-rating-2"><svg class="icon icon-star">
								      <use xlink:href="#icon-star"></use>
								    </svg></label>
								  <input class="stars-input" type="radio" name="${movie.movieCd }-rating" value="1.5" id="${movie.movieCd }-rating-1.5"> <label class="stars-view is-half" for="${movie.movieCd }-rating-1.5"><svg class="icon icon-star-half">
								      <use xlink:href="#icon-star-half"></use>
								    </svg></label>
								  <input class="stars-input" type="radio" name="${movie.movieCd }-rating" value="1" id="${movie.movieCd }-rating-1"> <label class="stars-view" for="${movie.movieCd }-rating-1"><svg class="icon icon-star">
								      <use xlink:href="#icon-star"></use>
								    </svg></label>
								  <input class="stars-input" type="radio" name="${movie.movieCd }-rating" value="0.5" id="${movie.movieCd }-rating-0.5"> <label class="stars-view is-half" for="${movie.movieCd }-rating-0.5"><svg class="icon icon-star-half">
								      <use xlink:href="#icon-star-half"></use>
								    </svg></label>  --%>
								</div>
							</div>
						</section>
					<!-- 별점 평가-->
					</div>		
				</div>	
						
				<hr>			
			<!-- 로그인유저의 코멘트 -->
				<section class="padding--h5">		
					<div class="commentUser">
					</div>
				</section>
				
			<!-- 로그인유저의 코멘트 -->
				<hr>
				<br>		
					
			<!-- 영화 정보 -->	
				<section class="padding--h5 margin--bottom20">
					<h2>기본 정보</h2>
					<div class="margin--bottom5"><c:out value="${movie.movieNmEn }" /></div>
					<div class="margin--bottom5"><c:out value="${movie.prdtYear}" /> • <c:out value="${movie.nationAlt}" /> • <c:out value="${movie.genreAlt}" /></div>
					<div ><c:set var="showTm" value="${movie.showTm }"/><fmt:parseNumber  integerOnly= "true" value="${showTm / 60}"  />시간 <c:out value="${movie.showTm % 60}" />분 • <c:out value="${movie.watchGradeNm}" /></div>					
					<br>
					<div class=""><c:out value="${movie.storyText}" /></div>
				</section>
			<!-- 영화 정보 -->	
				


				<hr>
				
				<!-- 별점 그래프 -->
				<c:if test="${movieStarRatingAnalysis ne null }">
					<section class="padding--h5 margin--bottom40">
						<div class="clearfix">
							<div class="float--left">
								<h2 style="display:inline-block;">별점 분포</h2>
							</div>
							
							<div class="float--right">
								<div class="starAvgCntDiv padding--v5">평균 ★${starAvg } (${starCnt }명)</div>
							</div>
						
						</div>
						
						<div>
							<ul class="graph">
								<c:set var="starNum" value="0.5" />
								<c:forEach var="cntByStarDTO" items="${movieStarRatingAnalysis.cntByStarDTOList }" >	
										<c:set var="star" value="${cntByStarDTO.star }"/>
										<c:set var="starCnt" value="${cntByStarDTO.starCnt }"/>
										
										<li class="graph-bar bar1" graph-val="${starCnt }" graph-color="yellow">
											<c:if test="${starNum % 1 == 0 }">
												<fmt:parseNumber var="starNum"  integerOnly="true" value="${starNum }"/>
												<span>${starNum }</span>
											</c:if>										
												
										</li>
										
										<c:set var="starNum" value="${starNum+0.5 }" />
								</c:forEach>
							</ul>
						</div>
					</section>
					<hr>
				</c:if>
				<!-- 별점 그래프 -->
				
				
				
			<!-- 전체 코멘트 리스트 -->
				<section class="padding--h5">
					<div class="clearfix">
						<div class="float--left">
							<h2 style="display:inline-block;">코멘트</h2>
							
							<!-- 코멘트 개수 계산 -->
							<c:set var="commentCnt" value="${movie.commentCnt }"/>
							<c:choose>
								<c:when test="${commentCnt >=  100 && commentCnt < 1000}">
									<fmt:parseNumber var="commentCnt" value="${commentCnt /10 }" integerOnly="true"/>
									<c:set var="commentCnt" value="${commentCnt * 10 } +"/>
								</c:when>
								<c:when test="${commentCnt >=  1000 && commentCnt < 10000}">
									<fmt:parseNumber var="commentCnt" value="${commentCnt /100 }" integerOnly="true"/>
									<c:set var="commentCnt" value="${commentCnt * 100 } +"/>
								</c:when>
								
							</c:choose>
							<span style="font-size: 20px; font-weight: bold; color:rgb(160, 160, 160);">${commentCnt }</span> <!-- 총 코멘트 개수는 비동기 갱신안함 -->
						</div>
						<div class="float--right" id="commentPlusBtn">
							<a href="/movieInfo/${movieCd }/comments" style="color:red">더보기</a>
						</div>
					
					</div>
					
					
					<div class="slide" style="overflow: hidden;">
					
						<ul class="commentsList slide-container clearfix">
						</ul>
						
						<div class="slideBtnGroup">
							<div class="slideBtn slidePrevBtn" >
								<img alt="prev" src="/resources/img/prev.png">
							</div>
							<div class="slideBtn slideNextBtn" >	
								<img alt="next" src="/resources/img/next.png">
							</div>
						</div>
					</div>	
					
				</section> 
			<!-- 전체 코멘트 리스트 -->
			
				<hr>
						<%-- <sec:authorize access="isAuthenticated()">
							<c:choose> 
								<c:when test="${empty comment}">
									<section>
										<h3>이 영화에 대한 <sec:authentication property="principal.username"/>님의 평가를 글로 남겨보세요.</h3>
										<div>
											<button id="addCommentBtn" class="commentBtn">코멘트 남기기</button>
										</div>					
									</section>
								</c:when> 
								<c:otherwise>
									<section>
										<div class="commentUser">
											<!-- 정리필요) getUser 메서드 controller에서 한번 view페이지 JS에서 한번 총 두번 실행되고있음  -->
											<div data-commentCd='${comment.commentCd}'>
												<div>
													<Strong>${comment.userid}</Strong>
													<small>${comment.commentDate}</small>
													<p>${comment.contents}</p>
												</div>
												<div>
													<button id='modCommentBtn' class='commentBtn'>수정</button>
													<button id='remCommentBtn' class='commentBtn'>삭제</button>
												</div>
											</div>
										</div>
									</section>
								</c:otherwise> 
							</c:choose>  
						</sec:authorize> --%>
				
			<!-- 영화인 정보 -->
				<section class="padding--h5">
					<h2>출연/제작</h2>
					<div>
						<!-- 감독 -->
						<div class="padding--h5">
							<h3>제작</h3>
							<ul>
								<c:set var="directors" value="${fn:split(movie.directorDetail, ',') }"/>
								<c:forEach var="director" items="${directors }">	
									<c:set var="directorNm" value="${fn:split(director, '|')[0] }"/>
									
									<!-- movie엔 존재하나 people엔 없는 경우 방지 위함 -->
									<c:forEach var="people" items="${peopleList }">
										<c:if test="${people.peopleNm eq directorNm }" >
											<li class="padding--v5">
												<a href="/people/${people.peopleCd }"> 
													<div>
														<h4>${directorNm }</h4>
														<div>감독</div>
													</div>
												</a>
											</li>
	
										</c:if>
									</c:forEach>
								</c:forEach>
							</ul>
						</div>
						<hr class="padding--h5">
						<!-- 배우 -->
						<div class="padding--h5">
							<h3>배우</h3>
							<ul>
								<c:set var="actorList" value="${fn:split(movie.actorDetail, ',') }"/>
							
							<%-- <c:set var="actor" value="${fn:split(actorList, '|') }"/> --%>
							
								<c:forEach var="actor" items="${actorList }">
									<c:if test="${fn:contains(actor, '|') }">
															
										<c:set var="actorNm" value="${fn:split(actor, '|')[0] }"/>
										<!-- 배역 정보 지정 -->
										<c:set var="actorCast" value="배역 정보 없음" />
										<c:if test="${fn:length(fn:split(actor, '|')) == 3 }" > <!-- // ex) 홍길동|hkd| -> [홍길동, hkd] :2 / 홍길동||사장 -> [홍길동, ,사장] : 2 -->
											<c:set var="actorCast" value="${fn:split(actor, '|')[2] }" />
										</c:if>
										
										<!-- movie엔 존재하나 people엔 없는 경우 방지 위함 -->
										<c:forEach var="people" items="${peopleList }">
											<c:if test="${people.peopleNm eq actorNm }" >
												<li class="border--bottom padding--v5">
													<a href="/people/${people.peopleCd }"> 
														<h4>${actorNm }</h4>
														<div>${actorCast }</div>
													</a>
												</li>
											</c:if>
										</c:forEach>
										
									</c:if>
								</c:forEach>
							</ul>
						</div>	
					</div>
				</section>
			<!-- 영화인 정보 -->

			</div>			
		</div>
	</div>
</main>
	
	
	
	
	<!-- 사용x) 14.3 a태그 동작 방지 위한 JS 처리 
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

		<div class="modal_content" title="클릭하면 창이 닫힙니다.">
	
            <header class="margin--bottom10 font--bold"><c:out value="${movie.movieNm }" /></header>

            <div>
            	<div >
            		<textarea rows="20" cols="60" name="comment" maxlength="1000" placeholder="이 영화에 대한 생각을 자유롭게 표현해주세요."></textarea>
  
            	</div>
            </div>
            <div class="float--right">
                <button id="modalModBtn" type="button">수정</button>
                <button id="modalRegisterBtn" type="button">등록</button>

            </div>
		</div>
	</div>
	<!-- /.modal -->
	
	
	
<!-- Modal - Remove -->
	<div id="removeModal" class="modal">
		<div class="modal_content" title="클릭하면 창이 닫힙니다.">
			<div class="margin--bottom10 font--bold">알림</div>
			<div id="removeModalText"  class="margin--bottom10"></div>
			<div size="2" class="float--right">
				<button id="modalCloseBtn" type="button">취소</button>
				<button id="modalRemoveBtn" type="button">확인</button>
			</div>
		</div>
	</div>



<!-- //////////////////////////////////////////////////////////////////////////////////////////////////////////////////// -->
	
	
	
	
<!-- Modal - Comment -->
<script type="text/javascript">
$(document).ready(function(){
	
	/* 38.5.3 댓글 기능에서의 Ajax -> 보안 처리
	브라우저쪽에선 1. 댓글 등록시 CSRF 토큰 같이 전송 2. 댓글 수정/삭제시 서버쪽에서 사용하기 위한 댓글 작성자 같이 전송*/
	// tip) JS에서도 security 태그 사용 가능
	var userid = null;
	
	<sec:authorize access="isAuthenticated()">
		userid = '<sec:authentication property="principal.username"/>';
		console.log("userid",userid);
		// 특수 문자 치환
			// @ . 과 같은 특수문자 Java -> JS 넘어오면서 변환되어 메서드 에러 발생시킴
		userid = userid.replace("&#64;", "@");
		userid = userid.replace(/\&\#46\;/gi, "."); // /gi: JS엔 replaceAll 없음 -> 정규식 gi 이용해 구현
		console.log("userid",userid);
	</sec:authorize>
		
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

//////////// 별점 그래프 //////////////////////////////////////////	
   	/* 그래프 */
	var graphBarLength = $("li.graph-bar").length;
	console.log("graphBarLength",graphBarLength);
	var theMostRatedStar = 0;
	var maxVal = 0; // 가장 많은 평가를 받은 별점의 개수
	
	for(var i=0; i<graphBarLength; i++){
	  var val = Number($("li.graph-bar").eq(i).attr('graph-val'));
	  console.log("val",val);
	  if(maxVal < val){
		  maxVal = val;
	  } else {
		  console.log(maxVal + val);
	  }
	}
	console.log("maxVal", maxVal);
	
	for(var i=0; i<graphBarLength; i++){
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
//////////// 별점 그래프 //////////////////////////////////////////	
	
//////////////// 코멘트 ////////////////////////////////////////////////



/* 이벤트 처리 - 영화 코멘트 목록 */
		// tip) Ajax 사용해 이벤트 발생 시 기본저그올 return 기다리지 않고
		// 다음 명령 수행 ex) for문은 비동기 함수의 결과를 기다려주지않음
		// -> 즉시 실행함수로 감싸거나 async 설정 false로 변경(return받을때까지 브라우저 정지)
	var movieCdValue = '<c:out value="${movie.movieCd}" />';
	var commentUL = $(".commentsList");
	
	var orderByValue = 0; // 영화 페이지에서의 코멘트 목록은 기본 좋아요 순으로 출력
	var currentCnt = $(".commentLi").length;
	
	var additionalCntInitValue = 10;
	var additionalCntAddValue = 4;
	let additionalCnt = additionalCntAddValue; // 추가

	let _returnListCnt = 0; // 추가된 코멘트 리스트의 개수
	
// 	showList(movieCdValue, orderByValue, totalCntValue); // 최초 실행 시 / 코멘트 등록,수정,삭제 시
// 	showAdditionalList(movieCdValue, orderByValue, currentCnt, additionalCnt);
	showMovieCommentList(movieCdValue, orderByValue, currentCnt);
	
	console.log("1 additionalCnt:" + additionalCnt);
	
	// ================================================================//
	function showMovieCommentList(param_movieCd, param_orderBy, param_currentCnt, param_additionalCnt){
		
		// 코멘트 최초 조회 시엔 additionalCnt 파라미터 전달안함
		if(param_additionalCnt == null){
			param_additionalCnt = additionalCntInitValue;
			
		}
		console.log(param_movieCd+  "/" + param_orderBy + "/" + param_currentCnt + "/" + param_additionalCnt);
		
		commentService.ajax_getMovieCommentList({movieCd: param_movieCd, orderBy: param_orderBy, currentCnt: param_currentCnt, additionalCnt: param_additionalCnt}, 
				function(result){
			
		
			console.log("result: " + result);
		});
		
		// 영화 페이지의 코멘트 리스트는 가로 정렬
		$(".commentLi").addClass("slide-inner"); // tip) 추가할 클래스 기존재 시 추가x

	
		// 변수 값 갱신
		console.log("commentLi.length: " + $(".commentLi").length);
		_returnListCnt = $(".commentLi").length - param_currentCnt;
		currentCnt = $(".commentLi").length;
		console.log("param_currentCnt: " + param_currentCnt +"/currentCnt: " + currentCnt);
	}
	
	
	
	
	// ================================================================//
	
	// 사용 x) showAdditionalList로 모두 수행
	// 메서드- 최초 코멘트 리스트 조회 
// 	function showList(movieCdValue, orderByValue, totalCntValue){
		
// 		commentService.getList(
// 		{movieCd:movieCdValue, orderBy:orderByValue, totalCnt:totalCntValue}, function(list){
		
// 			var str = "";
			
// 			if(list == null || list.length == 0){
// 				commentUL.html("");
				
// 				return;
// 			}
			

			
// 			for(let i=0, len=list.length||0; i<len; i++) {

// 				str += "<li class='commentLi' data-commentCd='"+list[i].commentCd+"'>";
// 				str += "	<div>"+list[i].userid+"</div>";
// 				str += "	<div class='commentContents'><a class='commentMove' href='/comments/"+list[i].commentCd+"'>";
// 				str += "		<div>" + list[i].contents +"</div></a></div>";
// 				str += "	<div class='commentCnt'><span class='likeCnt'>좋아요 "+list[i].likeCnt +"</span>";
// 				str += "		<span class='replyCnt'>댓글 "+list[i].replyCnt +" </span></div>";

// 				// 유저 해당 코멘트 좋아요 유무 확인
// 				likeService.getUser({userid: userid, commentCd:list[i].commentCd}, function(like){

					
// 					if(like == null){
					
// 						str += "	<div><button class='likeBtn'>좋아요</button></div>";
						
					
						
// 					} else { // likeActive 클래스 추가
					
// 						str += "	<div><button class='likeBtn likeActive'>좋아요</button></div>";
					
// 					}
					
// 				}); 
				
// 				str += "</li>";
				
// 			}
			

// 			commentUL.html(str);
// 		}); // commentService.getList
		
// 	} // showList(movieCdValue, orderByValue, totalCntValue);
	
	
/* 메서드 - 코멘트 리스트 가져오기 (최초, 추가 시 메서드 통일) */
// 	function showAdditionalList(movieCd, orderByValue, currentCnt, additionalCnt){
		
// 		// 코멘트 추가 시엔 additionalCnt 파라미터 전달안함
// 		if(additionalCnt == null){
// 			additionalCnt = 3;
			
// 		}
// 		currentCnt = $(".commentLi").length;
		
// 		commentService.getAdditionalList(
// 		{movieCd:movieCd, orderBy: orderByValue, currentCnt:currentCnt, additionalCnt:additionalCnt}, function(commentUserDTOList){
		
// 			var str = "";
			
// 			for(let i=0, len=commentUserDTOList.length||0; i<len; i++) {
				
// 				var commentVO = commentUserDTOList[i].commentVO;
				
// 				str += '<li class="commentLi" data-commentCd="'+commentVO.commentCd+'">' +
// 				'	<div><div><a title="'+ commentUserDTOList[i].userName +'" href="/users/'+ commentUserDTOList[i].randomString +'"><div>'+ commentUserDTOList[i].userName +'</div></a></div>';
				
// 				str += '<div class="commentStarRatingDiv">';
				
// 				// 별점 평가 정보
// 				if(commentUserDTOList[i].starRating != 0){
					
// 					console.log(commentUserDTOList[i].starRating);
// 					console.log(Number.isInteger(commentUserDTOList[i].starRating));
			
// 					str += '<sapn>평가 '+commentUserDTOList[i].starRatingCnt+'</sapn><span>별점평균 '+commentUserDTOList[i].starRatingAvg+'</span>'+
// 						'	<svg class="icon icon-star" style="width:16px; height:16px;"><use xlink:href="#icon-star"></use></svg>' +
// 					'	<span>'+ commentUserDTOList[i].starRating.toFixed(1) +'</span>';
// 				}
				
// 				str += '</div>';
				
				
// 				str += '	</div>' +
// 				'	<div class="commentContents"><a class="commentMove" href="/comments/'+commentVO.commentCd+'">' +
// 				'		<div>' + commentVO.contents +'</div></a></div>' +
// 				'	<div class="commentCnt"><span class="likeCnt">좋아요 '+commentVO.likeCnt +'</span>' +
// 				'		<span class="replyCnt">댓글 '+commentVO.replyCnt +' </span></div>';

// 				// 유저 해당 코멘트 좋아요 유무 확인
// 				likeService.getUser({userid: userid, commentCd:commentVO.commentCd}, function(like){

					
// 					if(like == null){
					
// 						str += "	<div><button class='likeBtn'>좋아요</button></div>";
						
					
						
// 					} else { // likeActive 클래스 추가
					
// 						str += "	<div><button class='likeBtn likeActive'>좋아요</button></div>";
					
// 					}
					
// 				}); 
				
// 				str += "</li>";
				
// 			}
			

// 			commentUL.append(str) ;
			
			
// 		}); // commentService.getAddtionalList
		
// 	} // showAdditionalList(movieCd, orderByValue, currentCnt)
	
	
// 	이벤트 - 코멘트 추가 조회 버튼 클릭
// 	var commentLiCurrentCnt = $(".commentLi").length;
// 	var commentTotalCnt = '<c:out value="${movie.commentCnt}" />';
	
// 	$("#additionalCommentBtn").off("click").on("click", function(){
		
		
// // 		showAdditionalList(movieCdValue, orderByValue, currentCnt);
// 		showMovieCommentList(movieCdValue, orderByValue, currentCnt) // 코멘트 리스트 추가 조회
		
// 		commentLiCurrentCnt = $(".commentLi").length;
// 		console.log(commentLiCurrentCnt);
		
// 		// 영화의 코멘트 총 개수 commentCnt = 현재 개수 currentCnt 인 경우 코멘트 추가 조회 버튼 숨김
// 		if(commentLiCurrentCnt >= commentTotalCnt){
// 			$("#additionalCommentBtn").hide();
// 		}
		
// 	});

	
	

	
/* 이벤트 처리 - 해당 유저 코멘트 */
	
	var commentUserDiv = $(".commentUser");
	
	var userName = null;
	if(userid != null){
		
		userName = '${userInfo.userName}';
		
		showUserComment();

	}

	function showUserComment(){
		
		console.log(userid);
		console.log(movieCdValue);
		
		commentService.getUser(
			{userid:userid, movieCd:movieCdValue}, 
			function(commentUserDTO){
			
				
				console.log('commentUserDTOb: ' + commentUserDTO);
				
				
				var userCommentHtml = "";
				
				commentUserDiv.html("");
				
				if(commentUserDTO == null){
					
					userCommentHtml += '<h3>이 영화에 대한 ' + userName +'님의 평가를 글로 남겨보세요.</h3>' +
										'<div><button style="font-size:18px; font-weight:bold; background-color:rgb(237, 237, 237);" id="addCommentBtn" class="commentBtn">코멘트 남기기</button></div>	';
								
				} else {
					var commentVO = commentUserDTO.commentVO;
				
					userCommentHtml += '<div class="commentCdDiv" data-commentCd="'+commentVO.commentCd+'">' +
					 '<div class="padding--v5"><a class="commentMove" href="/comments/'+commentVO.commentCd+'">' +
					 '<p  class="text padding--h5">' + commentVO.contents +'</p></a></div>' +
					 '<div><button id="modCommentBtn" class="commentBtn border--right">수정</button>' +
					 '<button id="remCommentBtn" class="commentBtn">삭제</button></div>';
					
				}
							
				commentUserDiv.html(userCommentHtml);
			});
		
	}
/* 이벤트 처리 - 해당 유저 코멘트 */	


/* 코멘트 모달창 오픈 처리 */
	var modal = $("#commentModal"); 
	var removeModal = $("#removeModal");
	//console.log(modal);
	//console.log($(".modal"));
	//console.log($(".modal[id=commentModal]"));
	
	var modalTextareaComment = modal.find("textarea[name='comment']");
	console.log(modalTextareaComment);
	var modalInputUserid = modal.find("input[name='userid']");
	var modalInputCommentDate = modal.find("input[name='commentDate']");
	
	var modalRegisterBtn = $("#modalRegisterBtn");
	var modalModBtn = $("#modalModBtn");
	var modalRemoveBtn = $("#modalRemoveBtn");
	
	

	/* 유저 코멘트 모달창 오픈 버튼 클릭 */
	$(document.body).off("click").on("click", ".commentBtn", function(event){
		// tip) 동적요소 이벤트 바인딩 위해 doucment에 이번트 걸고 원하는 요소에 위임

		commentContentsValue = commentUserDiv.find("p").html();
			// tip) children()은 자식요소만 / find()는 자식 요소 포함 하위 요소 모두
		var commentCdValue = $(".commentCdDiv").data("commentcd");
			// - 는 카멜표기법으로 변환됨 참고 / 카멜표기법은 소문자로 변환됨 참고
		console.log(commentCdValue);
		var commentBtnId = event.currentTarget.getAttribute('id');
		var BtnClass = event.currentTarget.getAttribute('class');
			// tip) event.currentTarget : 이벤트 걸린 요소 / event.target  : 이벤트 위임된 요소
		
			//modal.find("input").val("");
		modalInputCommentDate.closest("div").hide();
		modal.find("button").hide(); // 종료버튼 제외 전체 숨김
		
		console.log(commentBtnId);
		
		// 등록 처리
		if(commentBtnId == 'addCommentBtn'){

			modalRegisterBtn.show();
			
			// 모달창 오픈
				// 옵션은 등록 function 내에 모달창 오픈 시 지정 
			modal.modal({
				 escapeClose: true,      // Allows the user to close the modal by pressing `ESC`
				  clickClose: true,	// Allows the user to close the modal by clicking the overlay
				  showClose: true	// Shows a (X) icon/link in the top-right corner
			});
		// 수정 처리
		} else if (commentBtnId == 'modCommentBtn'){

			modalModBtn.show();
			
			
			modal.find("textarea").val(commentContentsValue);
			
			// 모달창 오픈
				// 옵션은 등록 function 내에 모달창 오픈 시 지정 
			modal.modal({
				 escapeClose: true,      // Allows the user to close the modal by pressing `ESC`
				  clickClose: true,	// Allows the user to close the modal by clicking the overlay
				  showClose: true	// Shows a (X) icon/link in the top-right corner
			});
		// 삭제 처리
		} else if (commentBtnId == 'remCommentBtn'){
			
			// 코멘트 || 댓글 삭제 선택에 따른 텍스트 변경
			if(BtnClass == "commentBtn"){
				$("#removeModalText").html("");
				$("#removeModalText").html("코멘트를 삭제하시겠어요?");
			} else if(BtnClass == "replyBtn"){
				$("#removeModalText").html("");
				$("#removeModalText").html("댓글을 삭제하시겠어요?");
			} 
			
			removeModal.modal({
				escapeClose: false,      // Allows the user to close the modal by pressing `ESC`
				  clickClose: true,	// Allows the user to close the modal by clicking the overlay
				  showClose: false	// Shows a (X) icon/link in the top-right corner
			});
			
		}
		
		
		
		/* 코멘트 등록 처리 */
		modalRegisterBtn.off("click").on("click", function(e){
			var comment = {
				contents : modalTextareaComment.val(),
				userid : userid,
				movieCd: movieCdValue
			};
			
			console.log(comment);
			commentService.add(comment, function(commentVO){
				
				// alert(commentVO);
				alert("코멘트를 등록하였습니다.");
				
				
	
				modal.find("textarea").val("");				
				
				$.modal.close();
				
				showUserComment(); 
				
				/* 코멘트 등록/수정/삭제 시 리스트 갱신 X. 삭제 시에만 해당  */
// 				additionalCnt = 2;
// 				commentUL.html("");
// 				showAdditionalList(movieCdValue, orderByValue, currentCnt, additionalCnt);
// 				showList(movieCdValue, orderByValue, totalCntValue);
				// 수정필요) 왓챠피디아는 코멘트 삭제 시에만 해당 코멘트만 골라 삭제함
				// 등록/수정/삭제 모두 코멘트 리스트 갱신은 없음
				// 결정 필요
				
			});
			
		});
		
		/* 17.5.4 코멘트 수정 처리 */
   		modalModBtn.off("click").on("click",  function(e){
   			// tip) 클릭 이벤트 변경하기 위해 다시 정의하면 이벤트 대체되는것이 아니라 중복 되는 경우 발생
   			// -> 누적된 이벤트가 다 실행되는 불상사 발생 => off() 전체이벤트 제거 또는 off("click") 특정 이벤트 제거
   		//	modal.data("commentCd", commentCdValue); // modal에 data-commentCd 추가
		//	alert("m : " + modal.data('commentCd'));
   			//var comment = {commentCd:modal.data("commentCd"), contents : modalTextareaComment.val()};
   			var comment = {commentCd: commentCdValue, contents : modalTextareaComment.val()};
   			
   			console.log("update comment: " + comment.contents);
   			
   			commentService.update(comment, function(commentVO){
	   				
       			// alert(commentVO);
       			alert("코멘트를 수정하였습니다.");
       			
       			console.log(commentVO);
				console.log("commentVO.contents: " + commentVO.contents);
				
       			modal.find("textarea").val("")
       			
       			$.modal.close();
       			
       			showUserComment(); 
	       			
	       		// 댓글 리스트에서 해당 댓글 내용 수정
		  		$('.commentLi[data-commentCd='+commentVO.commentCd+']').find('.commentContents').html(commentVO.contents);
     
       				
   			}); 

   		});

		
		/* 코멘트 삭제 처리 */
	 	$("#modalRemoveBtn").off("click").on("click", function(e){
	
 			//removeModal.data("commentCd", commentCdValue);
 			//console.log("c2:"+removeModal.data("commentCd"));
			//commentService.remove(removeModal.data("commentCd"), function(result){
			commentService.remove(commentCdValue, function(result){
				
	  			//alert(result);
	  			alert("코멘트를 삭제하였습니다.");
				
	  			modal.find("textarea").val("")
	  			
	  			$.modal.close(); // 이때 modal은 변수명 아님
	  			
				showUserComment(); 
	  			
				// 코멘트 리스트에서 해당 코멘트 삭제
					// 코멘트 개수는 갱신 안함
	  			$('.commentLi[data-commentCd='+commentCdValue+']').remove();
	     		

	
  			});
  		});
			
			
	 	/* 모달창 종료 버튼 클릭 처리 */
		$("#modalCloseBtn").off("click").on("click", function(e){	
			$.modal.close();
		});
			
		
	}); // $(document).on("click", ".commentBtn", function(event)
/* 코멘트 모달창 오픈 버튼 클릭 */


/* 코멘트 리스트 슬라이드 */

// 	transform: translate(-800px);
	
	slideIdx = 0; // 기본값 1
// 	$('.slidePrevBtn').css('visibility','hidden');
	$('.slidePrevBtn').css('display','none');
	
	if($('.commentLi').length <= 2){
		$('.slideNextBtn').css('display','none');
	}
	
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
			
			// 코멘트 리스트 추가
			console.log("slideIdx%2 == 1: " + slideIdx%2);
			if(slideIdx%2 == 1){
				console.log("function before \n"+ movieCdValue+  "/" + orderByValue + "/" + currentCnt+ "/" + additionalCnt);
				showMovieCommentList(movieCdValue, orderByValue, currentCnt, additionalCnt);
				
				console.log("function after \n"+ movieCdValue+  "/" + orderByValue + "/" + currentCnt+ "/" + additionalCnt);
				
				if(_returnListCnt == 0){
					console.log("0 _returnListCnt: " + _returnListCnt);
				
					$('#sentinel').hide();
					//검색된 아이템이 없을 경우 관찰중인 요소를 숨긴다.
				} else {
					if(_returnListCnt < additionalCnt) {
						console.log("< additionalCnt: " + additionalCnt);
						console.log("< _returnListCnt: " + _returnListCnt);
						
						slideNextBtn.css('display','none');
					} else {
						console.log(">= _returnListCnt: " + _returnListCnt);
						slideNextBtn.css('display','block');
					}
				}
			}
			
			
			slideIdx += 1;
			slideContainerUL.css('transform', 'translate(calc('+slideIdx +' * -100%))');
			slideNextBtn.css('transform', 'translate(calc('+0 +' * -100%))'); // calc() : css 사칙연산
	
			
	// 		slidePrevBtn.css('visibility','visible');
			slidePrevBtn.css('display','block');
// 			if(slideIdx == 3){
// 	// 			slideNextBtn.css('visibility','hidden');
// 				slideNextBtn.css('display','none');
				
// 			} else {
// 	// 			slideNextBtn.css('visibility','visible');
// 				slideNextBtn.css('display','block');
// 			}
			
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
/* 코멘트 리스트 슬라이드 */


//////////////// 코멘트 ////////////////////////////////////////////////


//////////////// 좋아요 ////////////////////////////////////////////////

/* 이벤트 - 좋아요 버튼 클릭 시 좋아요 등록/제거 */

	$(document).off("click").on("click", ".likeBtn", function(event){
		
		// 로그인한 사용자만 좋아요 가능
		if(userid != null){
			
			console.log("like userid",userid);
			
			var commentLi = $(this).closest("li.commentLi");

			var commentCdValue = commentLi.data("commentcd");
			
			console.log("commentCdValue: " + commentCdValue);
		
			var commentCntDiv = $(this).parent().siblings("div.commentCnt");
				// 동적 생성된 요소라도 무관함.
// 				<div class="commentCnt"><span class="likeCnt">좋아요 '+commentVO.likeCnt +'</span>'
			var commentLikeCntSpan = commentCntDiv.children("span.likeCnt");
			
			console.log("commentLikeCntSpan : " + commentLikeCntSpan.val());
			// 좋아요 활성화 클래스 포함 여부 확인
				// tip) jQuery 클래스 관련 메서드 hasClass, addClass, removeClass
			if($(this).hasClass("likeActive") == true){
				$(this).removeClass('likeActive');
				
				// 유저의 특정 코멘트 좋아요 조회 메서드 -> 해당 좋아요 삭제
				likeService.getUser({userid:userid, commentCd:commentCdValue}, function(likeVO){
					// likeVO 객체 반환
					
					console.log("likeVO: " + likeVO);
					
					likeService.remove(likeVO, function(result){
					
						//alert(result);
						// alert("좋아요를 취소하였습니다.");
						
						// 좋아요 개수 갱신
						commentService.getLikeCnt(commentCdValue, function(likeCnt){
							console.log(likeCnt);
							commentLikeCntSpan.html("좋아요 " + likeCnt);
						});
					});
					
				});
				
				
			} else {
				
				// 좋아요 등록
				$(this).addClass('likeActive');
				
				var like = {
						commentCd : commentCdValue,
						movieCd: movieCdValue,
						userid : userid,
						csrfHeaderName: csrfHeaderName,
						csrfTokenValue: csrfTokenValue
					}
				console.log("like: " + like.commentCd);
				console.log("like: " + like.movieCd);
				console.log("like: " + like.userid);
				
				likeService.add(like, function(result){
				
					//alert('add result: ' + result);
					
					// 좋아요 개수 갱신
					commentService.getLikeCnt(commentCdValue, function(likeCnt){
						console.log(likeCnt);
						commentLikeCntSpan.html("좋아요 " + likeCnt);
					});
					

					
				});		
				
			}
			
			console.log($(this));
			
		} else {
			// 미로그인 사용자
			var confirmResult = confirm("로그인 후 좋아요 기능을 이용할 수 있습니다. \n 로그인 페이지로 이동하시겠습니까?");
			if(confirmResult){
				location.href= '/login';
			}
			
			
		}
		

	
	});


//////////////// 좋아요 ////////////////////////////////////////////////



/////////////// 별점 ///////////////////////////////////////////////////

/* 최초 별점 조회 */
	
	
	/* showUserStarRating(userid, movieCdValue);
	
	function showUserStarRating(userid, movieCd){
		
		starRatingService.get({userid: userid, movieCd: movieCd}, function(starRating){
			console.log(starRating);
			
			starRatingValue = starRating.starRating;
			
			$("#starRatingSection").data("value", starRatingValue);
			console.log($("#starRatingSection").data("value"));
			$(".stars-input[value='"+starRatingValue+"']").prop('checked', true); 
		})
	
	}  */

/* 별점 평가 문구 입력 메서드 */
function enterStarRatingWords(starRatingValue){
		starRatingValue = String(Number(starRatingValue).toFixed(1));
	
		var starRatingWordsSpan = $('#starRatingWords').children('span');
		var starRatingWords = '';
		
		switch(starRatingValue){
		
			case '0.5':
				starRatingWords= '최악이에요';
				break;
			case '1.0':
				starRatingWords= '싫어요';
				break;
			case '1.5':
				starRatingWords= '재미없어요';
				break;
			case '2.0':
				starRatingWords = '별로예요';
				break;
			case '2.5':
				starRatingWords= '부족해요';
				break;
			case '3.0':
				starRatingWords= '보통이에요';
				break;
			case '3.5':
				starRatingWords= '볼만해요';
				break;
			case '4.0':
				starRatingWords= '재미있어요';
				break;
			case '4.5':
				starRatingWords= '훌륭해요';
				break;
			case '5.0':
				starRatingWords= '최고예요!';
				break;
			default:
				starRatingWords= '평가하기';
				break;
		}
		
		starRatingWordsSpan.html(starRatingWords);
	}

/* 이벤트 - 별점 평가  */
 	if(userid != null){
 		var starRatingValue = starRatingValue = $("#starRatingSection").data("value"); // 별점 점수 변수
 		
 		enterStarRatingWords(starRatingValue);
 	}
 	
 	 $(".stars-view").off("mouseover").on("mouseover", function(event){
 		 console.log($(this));
//  		 console.log($(this).siblings(".stars-input"));
 	 
 		$(this).attr('title', '취소하기');
		 console.log($(this).attr('title'));
		 
		 var starViewFor = $(this).attr('for');
		 console.log(starViewFor);
		 var starInput = $(".star-input[id='"+starViewFor+"']");
		 console.log(starInput);
//  		if(starRatingValue == $(this).siblings(".star-input[value="++"]").val()){ 
 			
//  		}
 	 });
 	// 별점 평가 클릭 이벤트
	 $(".stars-input").off("click").on("click", function(event){
		// tip) checked 속성 변경해도 개발자 도구에선 확인 불가
		// 동적 생성된 속성값으로 요소 선택 불가.. ex) active
			// -> 이벤트 위임 필요
	//	var currentValue = 3;
	//	console.log("a");
	
		if(userid != null){
			
			console.log($(this));
			
			var movieCdValue = $(this).closest("#starRatingSection").data("moviecd");
				// 랜덤페이지에서의 별점 평가를 위해 movieCd는 이 이벤트 요소를 통해 획득하게함
			
			
			
			
			console.log("before: data-value: " + $(this).closest("#starRatingSection").data("value") +"/ movieCd: "+ movieCdValue + "/ checked: "+$(this).prop('checked'));
			
			console.log($(this).val());
			console.log(starRatingValue);
			// 별점 등록/ 수정/ 제거
			if(starRatingValue == 0){
				// 별점 등록
			
				$(this).prop('checked', true); 
				

				starRatingValue = $(this).val();
				
				$(this).closest("#starRatingSection").data("value", starRatingValue);
				
				var starRating = {
						movieCd: movieCdValue,
						userid : userid,
						starRating : starRatingValue	
				}
				
					
				starRatingService.add(starRating, function(result){
					//alert(result);
					console.log(starRating);
					
					// 별점 평가 문구 수정
					enterStarRatingWords(starRating.starRating);
					
					// 코멘트 리스트의 유저 코멘트 별점 내역 수정
					if($('.commentCdDiv').length){
						var commentCdValue = $(".commentCdDiv").data("commentcd");
						
						if($('.commentLi[data-commentCd='+commentCdValue+']').length){
							
							var starRatingHtml = '<svg class="icon icon-star" style="width:16px; height:16px;"><use xlink:href="#icon-star"></use></svg>' +
							'	<span>'+ starRatingValue +'</span>';
							$('.commentLi[data-commentCd='+commentCdValue+']').find('.commentStarRatingDiv').html(starRatingHtml);
						
						}
					}
					
				});
				
				
				
				
			} else if(starRatingValue == $(this).val()){ 
				// 별점 제거

				$(this).prop('checked', false);
				
				starRatingValue = 0;
				
				$(this).closest("#starRatingSection").data("value", starRatingValue);
				
				// 별점 평가 문구 수정
				enterStarRatingWords(starRatingValue);
				
				starRatingService.remove({userid: userid, movieCd: movieCdValue}, function(result){
					
					//alert(result);
					
					
					
					// 코멘트 리스트의 유저 코멘트 별점 내역 수정
					if($('.commentCdDiv').length){
						var commentCdValue = $(".commentCdDiv").data("commentcd");
											
						if($('.commentLi[data-commentCd='+commentCdValue+']').length){
						
							$('.commentLi[data-commentCd='+commentCdValue+']').find('.commentStarRatingDiv').html('');
						
						}
					}
					
					
				});
				
				
			} else{ // 별점 수정

				$(this).siblings('.stars-input').prop('checked', false);
				$(this).prop('checked', true); 
				
				starRatingValue = $(this).val();
				
				$(this).closest("#starRatingSection").data("value", starRatingValue);
				
				var starRating = {
						movieCd: movieCdValue,
						userid : userid,
						starRating : starRatingValue	
				}

				starRatingService.update(starRating, function(result){
					//alert(result);
					console.log(starRating);
					
					// 별점 평가 문구 수정
					enterStarRatingWords(starRating.starRating);
					
					
					if($('.commentCdDiv').length){
						var commentCdValue = $(".commentCdDiv").data("commentcd");
						
						if($('.commentLi[data-commentCd='+commentCdValue+']').length){

							$('.commentLi[data-commentCd='+commentCdValue+']').find('.commentStarRatingDiv').children('span').html(starRatingValue);
						
						}
					}
				});
				
				

			}

			console.log("after: data-value: " + $(this).closest("#starRatingSection").data("value") +"/ movieCd: "+movieCdValue + " / checked: "+$(this).prop('checked'));
		
			
		} else {
			// 미로그인 사용자
			$(this).prop('checked', false);
			
			var confirmResult = confirm("로그인 후 별점 평가 기능을 이용할 수 있습니다. \n 로그인 페이지로 이동하시겠습니까?");
			if(confirmResult){
				location.href= '/login';
			}
		}
		
		

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
<!-- / Modal - Comment -->

<!-- Modal - Login -->
<!-- / Modal - Login -->
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
<!-- 코멘트 테스트 -->
	<script>
	
	/* 38.5.3 댓글 기능에서의 Ajax -> 보안 처리
	브라우저쪽에선 1. 댓글 등록시 CSRF 토큰 같이 전송 2. 댓글 수정/삭제시 서버쪽에서 사용하기 위한 댓글 작성자 같이 전송*/
	// tip) JS에서도 security 태그 사용 가능
// 	var userid = null;
	
// 	<sec:authorize access="isAuthenticated()">
// 		userid = '<sec:authentication property="principal.username"/>';
// 	</sec:authorize>
		
// 	var csrfHeaderName = "${_csrf.headerName}";
// 	var csrfTokenValue = "${_csrf.token}"
	
// 	/* 38.5.3 Ajax로 CSRF 토큰 전송 방식
// 		: ajaxSend() 이용 시 모든 Ajax 전송 시 CSRF 토큰 같이 전송하도록 되어있음 */
// 	$(document).ajaxSend(function(e, xhr, options){
// 		xhr.setRequestHeader(csrfHeaderName, csrfTokenValue);
// 	});
	
		console.log("===========")
		console.log("JS TEST");
		
		var movieCdValue = '<c:out value="${movie.movieCd}"/>';
		
		// commentService add 메서드 테스트
		/* commentService.add(
			{movieCd:movieCdValue, userCd:8, contents:"코멘트8"}
			,
			function(result){
				alert("RESULT: " + result);
			}
		); */
		
		// commentService getList 메서드 테스트
		/* commentService.getList(
			{movieCd:movieCdValue}, function(list){
			
			for(var i=0, len=list.length||0; i<len; i++) {
				
				console.log(list[i]);
			}
		}); */
		
		// 코멘트 삭제 테스트
		/* commentService.remove(21, function(count){ // function(count) : callback, function(error) : error
			
			console.log(count);
			
			if(count === "success") {
				alert("REMOVED");
			}
		}, function(err) {
			alert('ERROR...');
		}
		); */
		
		// 코멘트 수정 테스트
		/* commentService.update({
			commentCd : 23,
			movieCd : movieCdValue,
			contents : "코멘트5 수정"
		}, function(result) {
			alert("수정 완료");
			
		}
		); */
		
		// 코멘트 조회 테스트
// 		 commentService.get(321, function(data){
// 			console.log(data);
// 		}); 
		
		

		
		
		
		
		
		
	</script>
	
	
<!-- 댓글 테스트 -->
	<!-- tip) 각 script 태그는 서로 영향을 줄 수 없음. 한 script 태크 내에서 저으이한 변수 메서드는 다른 script 태그 안에서 사용 불가 -->
	<script>
		
		/* 38.5.3 댓글 기능에서의 Ajax -> 보안 처리
		브라우저쪽에선 1. 댓글 등록시 CSRF 토큰 같이 전송 2. 댓글 수정/삭제시 서버쪽에서 사용하기 위한 댓글 작성자 같이 전송*/
		// tip) JS에서도 security 태그 사용 가능
// 		var userid = null;
		
// 		<sec:authorize access="isAuthenticated()">
// 			userid = '<sec:authentication property="principal.username"/>';
// 		</sec:authorize>
			
// 		var csrfHeaderName = "${_csrf.headerName}";
// 		var csrfTokenValue = "${_csrf.token}"
		
// 		/* 38.5.3 Ajax로 CSRF 토큰 전송 방식
// 			: ajaxSend() 이용 시 모든 Ajax 전송 시 CSRF 토큰 같이 전송하도록 되어있음 */
// 		$(document).ajaxSend(function(e, xhr, options){
// 			xhr.setRequestHeader(csrfHeaderName, csrfTokenValue);
// 		});
	
	
		console.log("===========")
		console.log("JS TEST");
		
		// var commentCdValue = '321';
		
		// replyService add 메서드 테스트
/* 		 replyService.add(
			{commentCd:commentCdValue, movieCd:'20000006', userid:'mem3', reply:"댓글 추가2"}
			,
			function(result){
				alert("RESULT: " + result);
			}
		);  */
		
		// replyService getList 메서드 테스트
		/*  replyService.getList(
			{commentCd:commentCdValue}, function(list){
			
			for(var i=0, len=list.length||0; i<len; i++) {
				
				console.log(list[i]);
			}
		});  */
		
		// 댓글 삭제 테스트
		/*  replyService.remove(21, function(count){ // function(count) : callback, function(error) : error
			
			console.log(count);
			
			if(count === "success") {
				alert("REMOVED");
			}
		}, function(err) {
			alert('ERROR...');
		}
		);  */
		
		// 댓글 수정 테스트
		/*  replyService.update({
			replyCd : 22,
			commentCd : commentCdValue,
			movieCd : '20000006',
			reply : "댓글 수정"
		}, function(result) {
			alert("수정 완료");
			
		}
		);  */
		
		// 댓글 조회 테스트
		/*  replyService.get(22, function(data){
			console.log(data);
		}); */ 
		
	</script>
