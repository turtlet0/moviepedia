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
				
				<!-- 프로필 -->
				<div style="position: relative; margin-top:70px;" class="clearfix margin--bottom20">
					<div class="float--left">
						<div id="userNameDiv" class="margin--bottom10" style="font-size:27px; font-weight:bold;">${userDTO.userName }</div>
						
						<c:choose>
							<c:when test="${userDTO.userIntroduction eq null}">
								<div id="userIntroductionDiv" class="padding--h5">프로필이 없습니다.</div>
							</c:when>
							<c:otherwise>
								<div id="userIntroductionDiv" class="padding--h5">${userDTO.userIntroduction }</div>
							</c:otherwise>
						</c:choose>
				
					</div>
					<div style="position: absolute; right: 10px;" class="float-right">
						<!-- 설정 -->
						<c:if test="${userInfo.randomString eq  userDTO.randomString}">
							<div style="text-align: right; cursor:pointer;" class="settingTrigger margin--bottom10">
								<div class="margin--top5"><img style="width:22px;" alt="설정" src="/resources/img/setting.png"> </div>
							</div>				
							<div class="settingDrop" style="display: none; background-color: white; border: 1px solid black; border-radius: 6px;">
								<div class="settingDropBtn modifyProfile margin--bottom7 margin--top5"><a title="프로필 수정" href="#">프로필 수정</a></div>
								<div class="settingDropBtn margin--bottom7"><a title="비밀번호 변경" href="/user/changePassword">비밀번호 변경</a></div>
								<div class="settingDropBtn margin--bottom5"><a title="회원 탈퇴" href="/user/withdraw">회원 탈퇴</a></div>
							</div>
						</c:if>	
					</div>
				</div>
				
				<div style="text-align: center; height:30px;" class="clearfix border--top border--bottom margin--bottom10">
					<div style="width:49%; text-align: center;  margin-top: 7px;" class="float--left border--right font--bold">
						<a title="평가한 영화" href="/users/${randomString }/movies">
							<span>평가한 영화</span>
							<span class="color--orange">${userDTO.cntStarRating }</span>
						</a>
					</div>
					<div style="width:49%; text-align: center;  margin-top: 7px;" class="float--left font--bold">
						<a title="작성한 코멘트" href="/users/${randomString }/comments">
							<span>작성한 코멘트</span>
							<span class="color--orange">${userDTO.cntComment }</span>
						</a>
					</div>
				</div>
				
				<!-- 취향분석 -->
					 
					<div>
						<p style="font-size:22px; font-weight: bold;" class="margin--bottom10">취향 분석</p>
						<c:choose>
							<c:when test="${starRatingAnalysis ne null }">
								<section>
									<header>
										<p class="margin--bottom20 padding--h5" style="color:red; font-size:20px; font-weight: bold;">별점 분포</p>
									</header>
									<div>
										<p style="text-align: center;" class="font--bold margin--bottom30" >${starRatingAnalysis.resultMessage }</p>
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
										<div style="text-align: center;" class="margin--top30 margin--bottom10">
											<ul class="clearfix">
												<li style="width:33%; text-align: center;" class="float--left border--right">
													<div class=" margin--bottom5 font--bold">${starRatingAnalysis.starRatingAvg }</div>
													<div>별점 평균</div>
												</li>
												<li style="width:33%; text-align: center;" class="float--left border--right">
													<div  class=" margin--bottom5 font--bold">${starRatingAnalysis.starRatingCnt }</div>
													<div>별점 개수</div>
												</li>
												<li style="width:33%; text-align: center;" class="float--left">
													<div id="theMostRatedStar" class=" margin--bottom5 font--bold"></div>
													<div>많이 준 별점</div>
												</li>	
											</ul>
										</div>
										
									</div>
								</section>
		
								<!--    -->
								<!-- 취향 분석 -->
								<div class="border--top"  style="text-align: center; font-size:18px; width:100%; height:23px; background-color: rgb(242,242,242);">
									<a id="analysisMove" target="_blank" title="취향분석"  href="/users/${randomString }/analysis" >
										<span class="font--bold ">모든 분석 보기</span>
									</a>
								</div>
							</c:when>
							<c:otherwise>
								<div class="padding--h5">별점 평가 내역이 없습니다.</div>
							</c:otherwise>
						</c:choose>
					</div>
					 

					
				
						
		

			</div>
		</div>
	</div>
</main>









<!-- 14.3 a태그 동작 방지 위한 JS 처리 
    	별도의 form 태그 이용해 동작 처리
    	- 페이지 번호 버튼 클릭 시 이동 -->
<!--    	<form id="actionForm" action="/contents" method="post"> -->
<%--    		<input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }"> --%>
<!-- 		<!-- 이 EL의 이름은 실제 브라우저에서 _csrf라는 이름으로 처리됨 -->
<!-- 			value 값은 임의의값 지정됨 -->
<!-- 			브라우저 페이지 소스 보기에서 확인 가능 -->
			
<%--    		<input type="hidden" name="movieNm" value="${movie.movieNm}"> --%>
<%--    		<input type="hidden" name="posterUrl" value="${movie.posterUrl}"> --%>
<!--    	</form>	 -->
	
<!-- Modal - profile -->
 	<div style="width:400px;" id="profileModal" class="modal" >
 	${comment.commentCd }
		<div class="modal_content" title="클릭하면 창이 닫힙니다.">
			
				<h3>프로필 수정</h3>
	            <div>
	            	<div class="margin--bottom10">
		            	<label class="font--bold">이름</label>
		            	<input style="width:290px;" id="userNameInput" type="text" value="${userInfo.userName }">            
	            	</div>
	            	<div class="margin--bottom15">
		            	<label class="font--bold">소개</label>
		            	<input style="width:290px;" id="userIntroductionInput" type="text" value="${userInfo.userIntroduction }">
	            	</div>            
	            </div>
	            <div class="float--right">
	                <button style="font-size: 16px; border: 1px solid black; border-radius: 6px;" id="modalModBtn" type="button">수정</button>   
	            </div>
		</div>
	</div>
<!-- Modal - profile -->



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
	
	
	/* 설정 드롭 다운 */
 	$(".settingTrigger").on("click", function(event){
//		console.log(event.currentTarget); // <div class="replyTrigger">..</div>
//		console.log($(this)); // r.fn.init [div.replyTriger]
//		console.log(this); // <div class="replyTrigger">..</div>
			// tip) 즉 this = event.currentTarget
			
//		event.stopPropagation();
		$(this).siblings(".settingDrop").toggle(); // 숨겨진 요소 보여지게 - 보여진 요소 숨기기
			// tip) find() 사용하기 위해 event.currentTarget 대신 $(this) 사용 
			// https://velog.io/@thyoondev/this-event.currentTarget-event.target-%EC%B0%A8%EC%9D%B4%EC%A0%90
	}); 
	
	
	
	/* 그래프 */
	var maxIndex = $("li.graph-bar").length;
	
	var theMostRatedStar = 0;
	var maxVal = 0; // 가장 많은 평가를 받은 별점의 개수
	
	for(var i=0; i<maxIndex; i++){
	  var val = Number($("li.graph-bar").eq(i).attr('graph-val'));
	  console.log("val`",val);
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
	
	
	
// 취향 분석 조건
		// 15편 이상 평가 시 제공
	let starCntValue = 0;
	if(${starRatingAnalysis.starRatingCnt != null}){
		starCntValue = parseInt('${starRatingAnalysis.starRatingCnt }');
	}
	
	console.log("starCntValue",starCntValue);

	// 취향분석 페이지 이동
	$("#analysisMove").on("click", function(e) {
	
		if(starCntValue <15){
			alert("영화를 15편 이상 평가하셔야 취향 분석 결과를 확인할 수 있습니다.");
			
			return false;
		}

		return true;
		
	});
	
	
	
	
	
/* ------------- 프로필 수정 모달창 ------------------------------------*/
	
	const userNameDiv = $("#userNameDiv");
	const userIntroductionDiv = $("#userIntroductionDiv");
	
	
	
	/* 코멘트 모달창 오픈 처리 */
	const modal = $("#profileModal"); 
	const modalModBtn = $("#modalModBtn");
	
	const modalUserNameInput = modal.find("#userNameInput");
	const modalUserIntroductionInput = modal.find("#userIntroductionInput");
	
	
	
// 	var modalTextareaComment = modal.find("textarea[name='comment']");
// 	console.log(modalTextareaComment);
// 	var modalInputUserid = modal.find("input[name='userid']");
// 	var modalInputCommentDate = modal.find("input[name='commentDate']");
	
// 	var modalRegisterBtn = $("#modalRegisterBtn");
// 	var modalModBtn = $("#modalModBtn");
// 	var modalRemoveBtn = $("#modalRemoveBtn");
	
	

	/* 유저 프로필 수정 모달창 오픈 버튼 클릭 */
	$(".modifyProfile").off("click").on("click", function(event){

		let modalUserNameValue = $("#userNameDiv").html();
		let modalUserIntroductionValue = '';
		
		console.log('userInfo.userIntroduction','${userInfo.userIntroduction}');
		if('${userInfo.userIntroduction}' != ''){
			console.log('"" x');
			modalUserIntroductionValue = $("#userIntroductionDiv").html();
			
		}
		else if(modalUserIntroductionValue == '프로필이 없습니다.'){
			modalUserIntroductionValue = '';
		}
		
		console.log("a");
// 		commentContentsValue = commentUserDiv.find("p").html();
// 			// tip) children()은 자식요소만 / find()는 자식 요소 포함 하위 요소 모두
// 		var commentCdValue = $(".commentCdDiv").data("commentcd");
// 			// - 는 카멜표기법으로 변환됨 참고 / 카멜표기법은 소문자로 변환됨 참고
// 		console.log(commentCdValue);
// 		var commentBtnId = event.currentTarget.getAttribute('id');
// 		var BtnClass = event.currentTarget.getAttribute('class');
			// tip) event.currentTarget : 이벤트 걸린 요소 / event.target  : 이벤트 위임된 요소
		
// 			//modal.find("input").val("");
// 		modalInputCommentDate.closest("div").hide();
// 		modal.find("button").hide(); // 종료버튼 제외 전체 숨김
		
// 		console.log(commentBtnId);
		
		// settingDrop 닫기
      	$(".settingDrop").toggle();
			
		// 수정 처리

			modalUserNameInput.val(modalUserNameValue);
			modalUserIntroductionInput.val(modalUserIntroductionValue);
			
			// 모달창 오픈
				// 옵션은 등록 function 내에 모달창 오픈 시 지정 
			modal.modal({
				 escapeClose: true,      // Allows the user to close the modal by pressing `ESC`
				  clickClose: true,	// Allows the user to close the modal by clicking the overlay
				  showClose: true	// Shows a (X) icon/link in the top-right corner
			});


		/* 17.5.4 프로필 수정 처리 */
   		$("#modalModBtn").off("click").on("click",  function(e){
   			// tip) 클릭 이벤트 변경하기 위해 다시 정의하면 이벤트 대체되는것이 아니라 중복 되는 경우 발생
   			// -> 누적된 이벤트가 다 실행되는 불상사 발생 => off() 전체이벤트 제거 또는 off("click") 특정 이벤트 제거
   		//	modal.data("commentCd", commentCdValue); // modal에 data-commentCd 추가
		//	alert("m : " + modal.data('commentCd'));
   			//var comment = {commentCd:modal.data("commentCd"), contents : modalTextareaComment.val()};
   			var member = {userid: userid, userName : modalUserNameInput.val(), userIntroduction: modalUserIntroductionInput.val()};
   			
   			console.log("update profile member",member);
   			
   			memberService.update(member, function(memberVO){
	   				
       			alert(memberVO);
       			
       			console.log(memberVO);
				console.log("memberVO",memberVO);
				
				modalUserNameInput.val('');
				modalUserIntroductionInput.val('');
       			
       			$.modal.close();
       			
       			console.log(memberVO.userIntroduction);
       			userNameDiv.html(memberVO.userName);
       					
       			if(memberVO.userIntroduction != null){
       				console.log("null x");
       				userIntroductionDiv.html(memberVO.userIntroduction);	
       			} else {
       				userIntroductionDiv.html('프로필이 없습니다.');	
       			}
       			
       		
 				
   			}); 
      			

   		});

	
			
			
	 	/* 모달창 종료 버튼 클릭 처리 */
		$("#modalCloseBtn").off("click").on("click", function(e){	
			$.modal.close();
		});
			
		
	}); // $(document).on("click", ".commentBtn", function(event)
/* 코멘트 모달창 오픈 버튼 클릭 */
 
 /* ------------- 프로필 수정 모달창 ------------------------------------*/
	

 

});	
</script>	
	
	
	
	

