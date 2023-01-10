<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ include file="../includes/header.jsp" %>

<main>
	<div class = "section" >
		<div class = "center">
			<div class = "innerFix">
			
			
				<c:set var="commentVO" value="${commentInfo.commentVO }"/>
							
				<section class="margin--bottom20">
					
					<div  class="commentInfo" data-commentCd="${commentVO.commentCd}" data-movieCd="${commentVO.movieCd}">
						<div style="margin-bottom:10px;" class="clearfix">
							<div style="width:80%;" class="float--left">
								<div class="clearfix ">
									<div class="float--left">
										<a title="${commentInfo.userName}" href="/users/${commentInfo.randomString}">
											<div class="clearfix">
												<div class="float--left margin--right10 font--bold">${commentInfo.userName}</div>	
												<!-- 작성 일자 
													https://sowon-dev.github.io/2022/08/18/220818JSTL-date/ -->
												<div class="float--left">
													<jsp:useBean id="now" class="java.util.Date"/>
													<fmt:parseNumber value="${now.time / (1000*60)}" var="nowfmtTime"/><!-- .time 필수 -->
													<fmt:parseNumber value="${commentVO.commentDate.time / (1000*60)}" var="commentDatefmtTime"/><!-- .time 필수 -->
													<fmt:parseNumber value="${nowfmtTime - commentDatefmtTime}" var="timeDefference"/>
													<c:choose>
														<c:when test="${timeDefference <= 10}"><!-- 10분 이하 -->
															방금 전
														</c:when>
														<c:when test="${timeDefference > 10 && timeDefference <= 60}"><!-- 1시간 이하 -->
															<fmt:parseNumber value="${timeDefference}" integerOnly="true" var="timeDefference"/>	
															${timeDefference }분 전
														</c:when>
														<c:when test="${timeDefference > 60 && timeDefference <= 60*24}"><!-- 24시간 이하 -->
															<fmt:parseNumber value="${timeDefference / 60}" integerOnly="true" var="timeDefference"/>
															${timeDefference }시간 전
														</c:when>
														<c:when test="${timeDefference > 60*24 && timeDefference <= 60*24*30}"><!-- 30일 이하 -->
															<fmt:parseNumber value="${timeDefference / (60*24)}" integerOnly="true" var="timeDefference"/>
															${timeDefference }일 전
														</c:when>
														<c:when test="${timeDefference > 60*24*30 && timeDefference <= 60*24*365}"><!-- 1년 이하 -->
															<fmt:parseNumber value="${timeDefference / (60*24*30)}" integerOnly="true" var="timeDefference"/>
															${timeDefference }개월 전
														</c:when>
														<c:when test="${timeDefference > 60*24*365}">
															<fmt:parseNumber value="${timeDefference / (60*24*365)}" integerOnly="true" var="timeDefference"/>
															${timeDefference }년 전
														</c:when>
													</c:choose>							
												</div>						
											</div>
										</a>	
									</div>
									
								</div>
								
								<!-- 영화 정보 -->
								<div class="peopleSMovie clearfix  padding--h5">							
									<div class="peopleSMovieInfo float--left">									
											<div class="font--bold margin--bottom5">${commentInfo.movieNm }</div>
											<div><c:out value="${commentInfo.prdtYear }" /> • <c:out value="${commentInfo.directorDetail }" /></div>		
									</div>		
								</div>
								
								<!-- 별점 정보 -->
								<c:if test="${commentInfo.userStarRating ne null }">
									<div class="commentStarRatingDiv  padding--h5">
										<svg class="icon icon-star" style="width:16px; height:16px;"><use xlink:href="#icon-star"></use></svg>
										<span>${commentInfo.userStarRating}</span>
										<span>(평가 ${commentInfo.cntUserStarRating }</span>
										<span>별점평균 ${commentInfo.avgUserStarRating })</span>
									</div>
								</c:if>
							</div>	
							
							<!-- 영화 포스터 -->
							<div class="commentMoviePoster img-border float--right">
								<a href="/movieInfo/${commentVO.movieCd }">
									<img  alt="<c:out value="${commentInfo.movieNm }"/> 포스터" src="<c:out value="${commentInfo.posterUrl eq null || commentInfo.posterUrl eq ' ' ? '/resources/img/poster-not-available.jpg' : commentInfo.posterUrl}" />">
								</a>
							</div>
						</div>
						
						<div class="commentMainGroup margin--top7">
							<div style="margin-bottom: 10px;">
								<div  class="commentContents text padding--h5">${commentVO.contents}</div>
							</div>
							<div class="commentCntNmodRemBtn clearfix">
								<div class="commentCnt float--left">
									<span class="likeCnt margin--right10">좋아요 ${commentVO.likeCnt}</span>
									<span class="replyCnt">댓글 ${commentVO.replyCnt}</span>
								</div>
								
								<c:if test="${userInfo.userid eq commentVO.userid}">
									<div class="commentModRemBtn float--right">
										<button id="modCommentBtn" class="commentBtn border--right">수정</button>
										<button id="remCommentBtn" class="commentBtn">삭제</button>
									</div>
								
								</c:if>
							</div>
						</div>
						
						<div class="commentBtnGroup border--top border--bottom">
							<div class="clearfix">
									<button class="likeBtn">좋아요</button>
									<button id="addReplyBtn" class="replyBtn">댓글</button>	

							</div>	
						</div>
					</div>
				</section>
				<!-- 댓글 -->
				<section>
					<ul class="replyList">
						
					</ul>
				</section>
			
				
				
				
				
<!-- 				<div class="clearfix">	 -->
				
	<!-- 로그인유저의 코멘트 -->
			<%-- 	<section>
<!-- 					<div> -->
						<a href='<c:out value="/movieInfo/${movie.movieCd }" />' >
							<img  alt="<c:out value="${movie.movieNm }"/> 포스터" src="<c:out value="${movie.posterUrl }" />" style="width:100px">
							<div><c:out value="${movie.movieNm }" /></div>
							<div><c:out value="${movie.prdtYear}" /> • <c:out value="${movie.repNationNm}" /> • <c:out value="${movie.repGenreNm}" /></div>
<!-- 						</a> -->
<!-- 					</div> -->
<!-- 					<div class="commentUser"> -->
						
<!-- 					</div> -->
<!-- 					<hr> -->
<!-- 					<div class="commentCnt"> -->
<!-- 						<span class="likeCnt">좋아요 </span> -->
<!-- 						<span class="replyCnt">댓글 </span> -->
<!-- 					</div> -->
					
					
					
					
					
					<div class="commentInfo clearfix">
						<div class="commentUserMovieInfo float--left" >
							<a class="commentUserInfoRegDate" >
							
							</a>
							<a href='<c:out value="/movieInfo/${movie.movieCd }" />' >	
								<div><c:out value="${movie.movieNm }" /></div>
								<div><c:out value="${movie.prdtYear}" /> • <c:out value="${movie.repNationNm}" /> • <c:out value="${movie.repGenreNm}" /></div>
							</a>
						
						</div>
						<a class="float--left" href='<c:out value="/movieInfo/${movie.movieCd }" />' >
							<img  alt="<c:out value="${movie.movieNm }"/> 포스터" src="<c:out value="${movie.posterUrl }" />" style="width:100px">
						</a>
					</div>
					<hr>
					<div class="commentContents">
					
					</div>
					<hr>
					<div class="commentCntNmodRemBtn">
						<div class="commentCnt">
							<span class="likeCnt">좋아요 0</span>
							<span class="replyCnt">댓글 0</span>
						</div>
					
					</div>
					<hr>
					<div>
						<button id='likeBtn' class='likeBtn'>좋아요
							<svg viewBox="0 0 20 20" class="css-vkoibk"><path class="fillTarget" fill-rule="evenodd" clip-rule="evenodd" d="M5.6252 7.9043H3.1252C2.6652 7.9043 2.29187 8.27763 2.29187 8.73763V17.071C2.29187 17.531 2.6652 17.9043 3.1252 17.9043H5.6252C6.08604 17.9043 6.45854 17.531 6.45854 17.071V8.73763C6.45854 8.27763 6.08604 7.9043 5.6252 7.9043Z" fill="#87898B"></path><path class="fillTarget" fill-rule="evenodd" clip-rule="evenodd" d="M11.71 4.34525L11.7017 3.99359L11.6825 3.14525L11.6809 3.09692L11.6759 3.04942C11.6684 2.96942 11.6792 2.93442 11.6775 2.93275C11.6917 2.92442 11.7534 2.90442 11.8725 2.90442C12.115 2.90442 13.3225 2.97609 13.3225 4.38692C13.3225 4.93359 13.2775 5.35859 13.1809 5.72692L12.8375 7.03275C12.8034 7.16525 12.9025 7.29442 13.0392 7.29442H14.3892H15.7317C16.0575 7.29442 16.3684 7.43692 16.585 7.68442C16.7975 7.93025 16.9009 8.25609 16.87 8.58275L15.6717 14.7703L15.6634 14.8119L15.6584 14.8536C15.59 15.3961 15.0959 15.8211 14.5334 15.8211H8.54169V8.19942C8.54169 7.89109 8.71169 7.56275 9.04835 7.22359L11.3417 4.90025L11.5775 4.66109C11.71 4.52359 11.71 4.34525 11.71 4.34525ZM17.5275 6.86525C17.0734 6.34275 16.4184 6.04442 15.7317 6.04442H14.3892C14.5167 5.56025 14.5725 5.02942 14.5725 4.38692C14.5725 2.50942 13.1734 1.65442 11.8725 1.65442C11.3825 1.65442 11 1.80775 10.7367 2.11025C10.5667 2.30359 10.3792 2.64442 10.4325 3.17359L10.4517 4.02192L8.15835 6.34525C7.58335 6.92692 7.29169 7.55109 7.29169 8.19942V16.2378C7.29169 16.6978 7.66502 17.0711 8.12502 17.0711H14.5334C15.7342 17.0711 16.7559 16.1603 16.8992 15.0078L18.1067 8.77192C18.1925 8.08109 17.9809 7.38692 17.5275 6.86525Z" fill="#87898B"></path></svg>
						</button>
						<button id='addReplyBtn' class='replyBtn'>댓글</button>
					</div>
				</section>
				
				<hr>
				
	<!-- 전체 댓글 리스트 -->
				<section>
					<ul class="replyList">
						<li data-commentCd='23'>
							<div>
								<Strong>닉네임</Strong>
								<p>콘텐츠 내용</p>
							</div>
						</li>
					</ul>
				</section> --%>
					
<!-- 				</div> -->

			</div>
		</div>			
	</div>
</main>	
<!-- ///////////////////////////////////////////////////////////////////////////////////// -->



<!-- Modal - Comment -->
 	<div id="commentModal" class="modal"  >
		<div class="modal_content" title="클릭하면 창이 닫힙니다.">
			
			
	            <header class="margin--bottom10 font--bold">코멘트 수정</header>
			
	            <div>
	            	<div class="margin--bottom10"> 
	            		<textarea rows="20" cols="60" name="comment" maxlength="1000" placeholder="이 영화에 대한 생각을 자유롭게 표현해주세요."></textarea>
	  
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
	        
	            <div class="float--right">
	                <button id="modalModBtn" type="button" >수정</button>
	                <button id="modalRegisterBtn" type="button">등록</button>
	            </div>
		</div>
	</div>
<!-- Modal - Comment -->

<!-- Modal - Reply -->
 	<div id="replyModal" class="modal" style="height:300px;" >
		<div class="modal_content" title="클릭하면 창이 닫힙니다.">
			
			
	            <header class="font--bold margin--bottom10">댓글</header>

	            <div class="margin--bottom10">
	            	<div>
	            		<textarea rows="14" cols="60" name="reply" maxlength="1000" placeholder="이 코멘트에 대한 의견을 작성해주세요."></textarea>  
	            	</div>
	            </div>
	            <div class="float--right">
	                <button id="replyModalModBtn" type="button">수정</button>
	                <button id="replyModalRegisterBtn" type="button">등록</button>
	            </div>
		</div>
	</div>
<!-- Modal - Reply -->
	
<!-- Modal - Remove -->
	<div id="removeModal" class="modal">
		<div class="modal_content" title="클릭하면 창이 닫힙니다.">
			<div class="margin--bottom10 font--bold">알림</div>
			<div id="removeModalText" class="margin--bottom10"></div>
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
		console.log(userid);
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

/*////////// 코멘트 ///////////////////////////////////////// */


/* 이벤트 처리 - 해당 유저의 코멘트 */
 
 	var commentInfo = $(".commentInfo");
 	console.log(commentInfo.attr('class'));
 	
 	var movieCdValue = '<c:out value="${commentVO.movieCd}" />';
 	
	var commentCdValue = '<c:out value="${commentCd}" />'; // URL 통해 전달된 파라미터
	
	var commentCntDiv = $(".commentCnt");
	
	
// 	showUserComment(); // 유저 코멘트 조회
	
	// 코멘트 조회 메서드 - commentCd
	function showUserComment(){
		
		commentService.get(commentCdValue, function(commentUserDTO){
			console.log(commentUserDTO);
			
			var commentVO = commentUserDTO.commentVO;
				
// 			<div class="commentInfo">
// 				<div class="commentUserMovieInfo">
// 					<a class="commentUserInfoRegDate" title="str += 이지우" href="str+= /ko-KR/users/RE9528djgqQ72">
// 						str += <div class="css-a7gqjg">이지우</div>
// 						str += <div class="css-1hy7aba">4분 전</div>
// 					</a>
// 					<a href='<c:out value="/movieInfo/${movie.movieCd }" />' >	
// 						<div><c:out value="${movie.movieNm }" /></div>
// 						<div><c:out value="${movie.prdtYear}" /> • <c:out value="${movie.repNationNm}" /> • <c:out value="${movie.repGenreNm}" /></div>
// 					</a>
// 					str += <div><svg class="icon icon-star" style="width:16px; height:16px;"><use xlink:href="#icon-star"></use></svg>' +
// 					str+= '	<span>'+ commentUserDTOList[i].starRating +'</span></div>
// 				</div>
// 				<a href='<c:out value="/movieInfo/${movie.movieCd }" />' >
// 					<img  alt="<c:out value="${movie.movieNm }"/> 포스터" src="<c:out value="${movie.posterUrl }" />" style="width:100px">
// 				</a>
// 			</div>
// 			<hr>
// 			<div class="commentContents">
// 				str += "<span>" + comment.contents +"</span>";
// 			</div>
// 			<hr>
// 			<div class="commentCntNmodRemBtn">
// 				<div class="commentCnt">
// 					<span class="likeCnt">좋아요 </span>
// 					<span class="replyCnt">댓글 </span>
// 				</div>
// 				str += <div class="commentModRemBtn">
// 					str += "<button id='modCommentBtn' class='commentBtn'>수정</button>";
// 					str += "<button id='remCommentBtn' class='commentBtn'>삭제</button>";	
// 				str+= </div>
// 			</div>
			
			// commentCd 
			$('.commentInfo').attr('data-commentCd', commentVO.commentCd);
			
			// 유저 정보 및 코멘트 날짜
			$('.commentUserInfoRegDate').attr('title', commentUserDTO.userName);
			$('.commentUserInfoRegDate').attr('href', 'users/'+ commentUserDTO.randomString);
			var commentUserInfoRegDateHtml = '';
			commentUserInfoRegDateHtml += '<div>'+ commentUserDTO.userName +'</div>' +
			 	'<div>'+ commentVO.commentDate +'</div>'
			 	/* 10분전 : 방금전 /분, 시간, 일, 월, 년 */
		 	$('.commentUserInfoRegDate').html(commentUserInfoRegDateHtml);
			 	
			// 유저 별점
			if(commentUserDTO.starRating != 0){
				var commentStarRatingHtml = '';
				commentStarRatingHtml += '<div><sapn>평가 '+commentUserDTO.starRatingCnt+'</sapn><span>별점평균 '+commentUserDTO.starRatingAvg+'</span>' +
				'<svg class="icon icon-star" style="width:16px; height:16px;"><use xlink:href="#icon-star"></use></svg>' +
										'<span>'+ commentUserDTO.starRating +'</span></div>';
				$('.commentUserMovieInfo').append(commentStarRatingHtml);			
			} 	
			
			// 코멘트 내용
			var commentContentsHtml = '<span>' + commentVO.contents +'</span>';
			$('.commentContents').html(commentContentsHtml);
			
			// 코멘트 좋아요/댓글 개수
			commentCntDiv.html('');
			
			var commentCntHtml = '';
			commentCntHtml += '<span class="likeCnt">좋아요 '+commentVO.likeCnt +' </span>' +
								'<span class="replyCnt">댓글 '+commentVO.replyCnt +' </span>';
			commentCntDiv.html(commentCntHtml);
			
			// 코멘트 수정/삭제 버튼
			if(userid == commentVO.userid){
				var commentModRemBtnHtml = '';
				commentModRemBtnHtml += '<div class="commentModRemBtn">' +
						'<button id="modCommentBtn" class="commentBtn">수정</button>'+
						'<button id="remCommentBtn" class="commentBtn">삭제</button></div>';			
				$('.commentCntNmodRemBtn').html(commentModRemBtnHtml);
			}
			
			
// 			str += "<div class='commentInfo' data-commentCd='"+commentVO.commentCd+"'>";
			
			
// 			str += "	<div><Strong>" + comment.userid + "</Strong>";
			
			
// 			str += "<small>"+ comment.commentDate + "</small>";
// 			str += "<p>" + comment.contents +"</p></div>";
// 			str += "<div><button id='modCommentBtn' class='commentBtn'>수정</button>";
// 			str += "<button id='remCommentBtn' class='commentBtn'>삭제</button></div></div>";	
			
			
			
			
			
			// 유저 해당 코멘트 좋아요 유무 확인
			likeService.getUser({userid: commentVO.userid, commentCd:commentVO.commentCd}, function(like){
				// console.log("like: " + like);
				
				if(like == null){
					
				} else {
					$(".likeBtn").addClass('likeActive');
				}
			});
			
		});
		
		
		
		
	}	
	
	

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
	
	

	/* 코멘트 수정/삭제 버튼 클릭 */
	$(".commentBtn").off("click").on("click", function(event){
		// tip) 동적요소 이벤트 바인딩 위해 document에 이번트 걸고 원하는 요소에 위임
		console.log(".commentBtn");
		
		var commentContentsDiv = $(".commentContents");
// 		commentContentsValue = commentContentsDiv.find("span").html();
			// tip) children()은 자식요소만 / find()는 자식 요소 포함 하위 요소 모두
		commentContentsValue = commentContentsDiv.html();

		var commentCdValue = $(".commentInfo").data("commentcd");
			// - 는 카멜표기법으로 변환됨 참고 / 카멜표기법은 소문자로 변환됨 참고
		console.log(commentCdValue);
		var commentBtnId = event.currentTarget.getAttribute('id');
		var BtnClass = event.currentTarget.getAttribute('class');
			// tip) event.currentTarget : 이벤트 걸린 요소 / event.target  : 이벤트 위임된 요소
		
			//modal.find("input").val("");
		modalInputCommentDate.closest("div").hide();
		modal.find("button").hide(); // 종료버튼 제외 전체 숨김
		
		console.log(commentBtnId);
		
		// 수정 처리
		if (commentBtnId == 'modCommentBtn'){

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
		
		
		/* 17.5.4 코멘트 수정 처리 */
   		modalModBtn.off("click").on("click",  function(e){
   			// tip) 클릭 이벤트 변경하기 위해 다시 정의하면 이벤트 대체되는것이 아니라 중복 되는 경우 발생
   			// -> 누적된 이벤트가 다 실행되는 불상사 발생 => off() 전체이벤트 제거 또는 off("click") 특정 이벤트 제거
   		//	modal.data("commentCd", commentCdValue); // modal에 data-commentCd 추가
		//	alert("m : " + modal.data('commentCd'));
   			//var comment = {commentCd:modal.data("commentCd"), contents : modalTextareaComment.val()};
   			var comment = {commentCd:commentCdValue, contents : modalTextareaComment.val()};
   			
   			commentService.update(comment, function(commentVO){
	   				
       			console.log(commentVO);
       			
       			$.modal.close();
       			
       			// 수정 내용 반영
       			$(".commentContents").html(commentVO.contents);
  				
   			}); 

   		});

		
		/* 코멘트 삭제 처리 */
	 	$("#modalRemoveBtn").off("click").on("click", function(e){
	 		
	
 			//removeModal.data("commentCd", commentCdValue);
 			//console.log("c2:"+removeModal.data("commentCd"));
			//commentService.remove(removeModal.data("commentCd"), function(result){
			commentService.remove(commentCdValue, function(result){
				
	  			//alert(result);
			
	  			$.modal.close(); // 이때 modal은 변수명 아님
	  			
	  			location.replace("/movieInfo/"+movieCdValue);
	  				// tip) href는 그대로 페이지 이동을 의미, replace는 현재 페이지에 덮어씌우기 때문에 replace를 사용한 다음에는 이전 페이지로 돌아갈 수 없음.
  			});
  		});
		
		/* 모달창 종료 버튼 클릭 처리 */
		$("#modalCloseBtn").off("click").on("click", function(e){	
			$.modal.close();
		});
			
		
	}); // $(document).on("click", ".commentBtn", function(event)
/*/ 코멘트 모달창 오픈 버튼 클릭 */
	
/*////////// 코멘트 ///////////////////////////////////////// */

//////////댓글 ///////////////////////////////////////// 
	
	
	/* 이벤트 처리 - 해당 코멘트의 댓글 목록 */

	var replyUL = $(".replyList");
	
	var commentReplyCntSpan = $("span.replyCnt");
	
	// ================================================================ //
	showCommentReplyList(1, commentCdValue); // 최초 조회
	
	// 댓글 리스트 조회 (최초 조회/등록/수정/삭제 모두 하나의 메서드로 통일)
		// crudIdx) 등록: 0, 조회: 1 (수정/삭제는 댓글 DB에서 받지않고 댓글 개수만 별도로 받아 JS로 수정)
	function showCommentReplyList(crudIdx, commentCd, replyCd){
		
		
		replyService.ajax_getCommentReplyList({crudIdx: crudIdx, commentCd: commentCd, replyCd: replyCd}, function(result) {
// 			console.log("result: " + result);

			// 댓글 갯수 갱신
			var replyCntValue = $("input[name=replyCnt]").last().val();
			commentReplyCntSpan.html("댓글 " + replyCntValue);
			
			$("input[name=replyCnt]").remove(); // input hidden 제거
		
			
		});
		
	}
	// ================================================================ //
// 	showReplyList();
	
// 	// 댓글 목록 조회 메서드
// 	function showReplyList(){
		
// 		replyService.getList(
// 		{commentCd:commentCdValue}, function(replyList, replyCnt){
		
// 			var replyListHtml = '';
			
// 			if(replyList == null || replyList.length == 0){
// 				replyUL.html('');
				
// 				return;
// 			}
			
// 			for(var i=0, len=replyList.length||0; i<len; i++) {
				
// 				replyListHtml += '<li class="reply" data-replyCd="'+replyList[i].replyCd+'">' +
// 				'<div><Strong>'+replyList[i].userid+'</Strong>' +
// 				'<small>'+replyList[i].replyDate+'</small>' +
// 				'<p>'+replyList[i].reply+'</p></div>';
				
// 				if(replyList[i].userid == userid){
					
// 					replyListHtml += '<div class="replyTrigger"><svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 20 20" fill="none" class="injected-svg" data-src="data:image/svg+xml +base64,PHN2ZyB3aWR0aD0iMjAiIGhlaWdodD0iMjAiIHZpZXdCb3g9IjAgMCAyMCAyMCIgZmlsbD0ibm9uZSIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPHBhdGggZmlsbC1ydWxlPSJldmVub2RkIiBjbGlwLXJ1bGU9ImV2ZW5vZGQiIGQ9Ik0xMS4yNTEgNS40MjY3NkMxMS4yNTEgNi4xMTc1OSAxMC42OTEgNi42NzY3NiAxMC4wMDEgNi42NzY3NkM5LjMxMDE0IDYuNjc2NzYgOC43NTA5OCA2LjExNzU5IDguNzUwOTggNS40MjY3NkM4Ljc1MDk4IDQuNzM2NzYgOS4zMTAxNCA0LjE3Njc2IDEwLjAwMSA0LjE3Njc2QzEwLjY5MSA0LjE3Njc2IDExLjI1MSA0LjczNjc2IDExLjI1MSA1LjQyNjc2Wk0xMC4wMDEgOC43NDk5M0M5LjMxMDE0IDguNzQ5OTMgOC43NTA5OCA5LjMwOTkzIDguNzUwOTggOS45OTk5M0M4Ljc1MDk4IDEwLjY5MDggOS4zMTAxNCAxMS4yNDk5IDEwLjAwMSAxMS4yNDk5QzEwLjY5MSAxMS4yNDk5IDExLjI1MSAxMC42OTA4IDExLjI1MSA5Ljk5OTkzQzExLjI1MSA5LjMwOTkzIDEwLjY5MSA4Ljc0OTkzIDEwLjAwMSA4Ljc0OTkzWk0xMC4wMDEgMTMuMzIzMUM5LjMxMDE0IDEzLjMyMzEgOC43NTA5OCAxMy44ODIzIDguNzUwOTggMTQuNTczMUM4Ljc1MDk4IDE1LjI2MzkgOS4zMTAxNCAxNS44MjMxIDEwLjAwMSAxNS44MjMxQzEwLjY5MSAxNS44MjMxIDExLjI1MSAxNS4yNjM5IDExLjI1MSAxNC41NzMxQzExLjI1MSAxMy44ODIzIDEwLjY5MSAxMy4zMjMxIDEwLjAwMSAxMy4zMjMxWiIgZmlsbD0iI0EwQTBBMCIvPgo8L3N2Zz4K" xmlns:xlink="http://www.w3.org/1999/xlink">' +
// 					'<path fill-rule="evenodd" clip-rule="evenodd" d="M11.251 5.42676C11.251 6.11759 10.691 6.67676 10.001 6.67676C9.31014 6.67676 8.75098 6.11759 8.75098 5.42676C8.75098 4.73676 9.31014 4.17676 10.001 4.17676C10.691 4.17676 11.251 4.73676 11.251 5.42676ZM10.001 8.74993C9.31014 8.74993 8.75098 9.30993 8.75098 9.99993C8.75098 10.6908 9.31014 11.2499 10.001 11.2499C10.691 11.2499 11.251 10.6908 11.251 9.99993C11.251 9.30993 10.691 8.74993 10.001 8.74993ZM10.001 13.3231C9.31014 13.3231 8.75098 13.8823 8.75098 14.5731C8.75098 15.2639 9.31014 15.8231 10.001 15.8231C10.691 15.8231 11.251 15.2639 11.251 14.5731C11.251 13.8823 10.691 13.3231 10.001 13.3231Z" fill="#A0A0A0"></path></svg></div>' +				
// 					'<div class="replyDrop"><button id="modReplyBtn" class="replyBtn">수정</button>' +
// 					'<button id="remReplyBtn" class="replyBtn">삭제</button></div>';
					
// 				}
				
// 				replyListHtml += '</li>';
// 			}
			
// 			replyUL.html(replyListHtml);
			
// 			// 댓글 개수 갱신
// 			$('span.replyCnt').html('댓글 ' + replyCnt);
// 				// 삭제 버튼 눌러 삭제 처리 후엔 삭제 버튼 사라지므로 $(this) 사용할 수 없게됨
// 				// -> 카운트 요소 직접 찾아야함
			
// 			$('.replyDrop').css('display', 'none'); // 수정/삭제 버튼 drop 요소 숨기기
// 		}); // replyService.getList
		
// 	} // showReplyList()
	
	
	
// 	// 댓글 등록 후 등록한 댓글 호출해 리스트에 추가하는 메서드
// 	function appendReply(replyCd){
		
// 		replyService.get(
// 		replyCd, function(replyList, replyCnt){
		
// 			var str = "";
			
// 			if(replyList == null || replyList.length == 0){
// 				replyUL.html("");
				
// 				return;
// 			}
			
// 			var reply = replyList[0]; // List에 하나의 reply만 존재
			
				
// 			str += "<li class='reply'  data-replyCd='"+reply.replyCd+"'>";
// 			str += "	<div><Strong>"+reply.userid+"</Strong>";
// 			str += "<small>"+reply.replyDate+"</small>";
// 			str += "<p>"+reply.reply+"</p></div>";
// 			str += "<div class='replyTrigger'><svg xmlns='http://www.w3.org/2000/svg' width='20' height='20' viewBox='0 0 20 20' fill='none' class='injected-svg' data-src='data:image/svg+xml;base64,PHN2ZyB3aWR0aD0iMjAiIGhlaWdodD0iMjAiIHZpZXdCb3g9IjAgMCAyMCAyMCIgZmlsbD0ibm9uZSIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPHBhdGggZmlsbC1ydWxlPSJldmVub2RkIiBjbGlwLXJ1bGU9ImV2ZW5vZGQiIGQ9Ik0xMS4yNTEgNS40MjY3NkMxMS4yNTEgNi4xMTc1OSAxMC42OTEgNi42NzY3NiAxMC4wMDEgNi42NzY3NkM5LjMxMDE0IDYuNjc2NzYgOC43NTA5OCA2LjExNzU5IDguNzUwOTggNS40MjY3NkM4Ljc1MDk4IDQuNzM2NzYgOS4zMTAxNCA0LjE3Njc2IDEwLjAwMSA0LjE3Njc2QzEwLjY5MSA0LjE3Njc2IDExLjI1MSA0LjczNjc2IDExLjI1MSA1LjQyNjc2Wk0xMC4wMDEgOC43NDk5M0M5LjMxMDE0IDguNzQ5OTMgOC43NTA5OCA5LjMwOTkzIDguNzUwOTggOS45OTk5M0M4Ljc1MDk4IDEwLjY5MDggOS4zMTAxNCAxMS4yNDk5IDEwLjAwMSAxMS4yNDk5QzEwLjY5MSAxMS4yNDk5IDExLjI1MSAxMC42OTA4IDExLjI1MSA5Ljk5OTkzQzExLjI1MSA5LjMwOTkzIDEwLjY5MSA4Ljc0OTkzIDEwLjAwMSA4Ljc0OTkzWk0xMC4wMDEgMTMuMzIzMUM5LjMxMDE0IDEzLjMyMzEgOC43NTA5OCAxMy44ODIzIDguNzUwOTggMTQuNTczMUM4Ljc1MDk4IDE1LjI2MzkgOS4zMTAxNCAxNS44MjMxIDEwLjAwMSAxNS44MjMxQzEwLjY5MSAxNS44MjMxIDExLjI1MSAxNS4yNjM5IDExLjI1MSAxNC41NzMxQzExLjI1MSAxMy44ODIzIDEwLjY5MSAxMy4zMjMxIDEwLjAwMSAxMy4zMjMxWiIgZmlsbD0iI0EwQTBBMCIvPgo8L3N2Zz4K' xmlns:xlink='http://www.w3.org/1999/xlink'>";
// 			str += "<path fill-rule='evenodd' clip-rule='evenodd' d='M11.251 5.42676C11.251 6.11759 10.691 6.67676 10.001 6.67676C9.31014 6.67676 8.75098 6.11759 8.75098 5.42676C8.75098 4.73676 9.31014 4.17676 10.001 4.17676C10.691 4.17676 11.251 4.73676 11.251 5.42676ZM10.001 8.74993C9.31014 8.74993 8.75098 9.30993 8.75098 9.99993C8.75098 10.6908 9.31014 11.2499 10.001 11.2499C10.691 11.2499 11.251 10.6908 11.251 9.99993C11.251 9.30993 10.691 8.74993 10.001 8.74993ZM10.001 13.3231C9.31014 13.3231 8.75098 13.8823 8.75098 14.5731C8.75098 15.2639 9.31014 15.8231 10.001 15.8231C10.691 15.8231 11.251 15.2639 11.251 14.5731C11.251 13.8823 10.691 13.3231 10.001 13.3231Z' fill='#A0A0A0'></path></svg></div>";				
// 			str += "<div class='replyDrop'><button id='modReplyBtn' class='replyBtn'>수정</button>";
// 			str += "<button id='remReplyBtn' class='replyBtn'>삭제</button></div></li>";
			
// 			console.log(str);
// 			replyUL.append(str);
			
// 			// 댓글 개수 갱신
// 			$("span.replyCnt").html("댓글 " + replyCnt);
// 				// 삭제 버튼 눌러 삭제 처리 후엔 삭제 버튼 사라지므로 $(this) 사용할 수 없게됨
// 				// -> 카운트 요소 직접 찾아야함
			
// 			$(".replyDrop").css('display', 'none'); // 수정/삭제 버튼 drop 요소 숨기기
// 		}); // replyService.getList
		
// 	} // appendReply(replyCd)
	
	
/* 댓글 수정/삭제 드롭 다운 */
 	replyUL.on("click", ".replyTrigger", function(event){
//		console.log(event.currentTarget); // <div class="replyTrigger">..</div>
//		console.log($(this)); // r.fn.init [div.replyTriger]
//		console.log(this); // <div class="replyTrigger">..</div>
			// tip) 즉 this = event.currentTarget
			
//		event.stopPropagation();
		$(this).siblings(".replyDrop").toggle(); // 숨겨진 요소 보여지게 - 보여진 요소 숨기기
			// tip) find() 사용하기 위해 event.currentTarget 대신 $(this) 사용 
			// https://velog.io/@thyoondev/this-event.currentTarget-event.target-%EC%B0%A8%EC%9D%B4%EC%A0%90
	}); 
 	
 	// 바깥 화면 클릭 시 drop 요소 숨기기
 		// https://gpresss.blogspot.com/2015/06/jquery-dropdown-toggle.html
 	/* $(document).on("click", function(){
 		
 		alert("a");
 	    $('.replyDrop').hide();
 	}); */
	
	
	/* 댓글 모달창 오픈 처리 */
	var replyModal = $("#replyModal"); 
	
	
	var replyModalTextareaComment = replyModal.find("textarea[name='reply']");
	console.log(replyModalTextareaComment);
	
	// 처리 버튼
	var replyModalRegisterBtn = $("#replyModalRegisterBtn");
	var replyModalModBtn = $("#replyModalModBtn");
//	var replyModalRemoveBtn = $("#replyModalRemoveBtn");
	
	

/* 댓글 등록/수정/삭제 버튼 클릭 -  모달창 오픈 */
	 $(".replyBtn").off("click").on("click", function(event){
		// tip) 동적요소 이벤트 바인딩 위해 doucment에 이번트 걸고 원하는 요소에 위임
		
		console.log(".replyBtn");
//		alert("reply : " + event.currentTarget);
 	
 		// 로그인 후 이용 가능
 		if(userid != null){
 			
 			var replyLi = $(this).closest(".reply");
			// closest(): 상위 요소 중 해당 선택자의 요소 선택 - find() 와 유사
		
			var replyCdValue = replyLi.data("replycd");
			
			
	//		alert("replyCd : " + replyCdValue);
			
			replyValue = replyLi.find("p").html();
				// tip) children()은 자식요소만 / find()는 자식 요소 포함 하위 요소 모두
	//		var commentCdValue = $(".commentInfo").data("commentcd");
				// - 는 카멜표기법으로 변환됨 참고 / 카멜표기법은 소문자로 변환됨 참고
	//		console.log(commentCdValue);
			var replyBtnId = event.currentTarget.getAttribute('id');
			var BtnClass = event.currentTarget.getAttribute('class');
				// tip) event.currentTarget : 이벤트 걸린 요소 / event.target  : 이벤트 위임된 요소
			
				//modal.find("input").val("");
	//		modalInputCommentDate.closest("div").hide();
			replyModal.find("button").hide(); // 종료버튼 제외 전체 숨김
			
			console.log(replyBtnId); 
			
		
			
			// 등록 처리
			if(replyBtnId == 'addReplyBtn'){
	
				replyModal.find("textarea").val("");
				
				replyModalRegisterBtn.show();
				
				// 모달창 오픈
					// 옵션은 등록 function 내에 모달창 오픈 시 지정 
				replyModal.modal({
					 escapeClose: true,      // Allows the user to close the modal by pressing `ESC`
					  clickClose: true,	// Allows the user to close the modal by clicking the overlay
					  showClose: true	// Shows a (X) icon/link in the top-right corner
				});
				
			// 수정 처리
			} else if (replyBtnId == 'modReplyBtn'){
	
				replyModalModBtn.show();
				
				
				replyModal.find("textarea").val(replyValue);
				
				// 모달창 오픈
					// 옵션은 등록 function 내에 모달창 오픈 시 지정 
				replyModal.modal({
					 escapeClose: true,      // Allows the user to close the modal by pressing `ESC`
					  clickClose: true,	// Allows the user to close the modal by clicking the overlay
					  showClose: true	// Shows a (X) icon/link in the top-right corner
				});
			// 삭제 처리
			} else if (replyBtnId == 'remReplyBtn'){
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
	
			
			
			/* 댓글 등록 처리 */
			replyModalRegisterBtn.off("click").on("click", function(e){
				
				var reply = {
					commentCd : commentCdValue,
					movieCd: movieCdValue,
					userid : userid,
					reply : replyModalTextareaComment.val()		
				};
				
				replyService.add(reply, function(replyCd){
					
					//alert(replyCd);
					console.log(replyCd);
					
		
					replyModal.find("textarea").val("");				
					
					$.modal.close();
								
					// 등록한 댓글 추가
					showCommentReplyList(0, reply.commentCd, replyCd);
					
					
				});
				
			});
			
			/* 댓글 수정 처리 */
	   		replyModalModBtn.off("click").on("click",  function(e){
	   			// tip) 클릭 이벤트 변경하기 위해 다시 정의하면 이벤트 대체되는것이 아니라 중복 되는 경우 발생
	   			// -> 누적된 이벤트가 다 실행되는 불상사 발생 => off() 전체이벤트 제거 또는 off("click") 특정 이벤트 제거
	   		//	modal.data("commentCd", commentCdValue); // modal에 data-commentCd 추가
			//	alert("m : " + modal.data('commentCd'));
	   			//var comment = {commentCd:modal.data("commentCd"), contents : modalTextareaComment.val()};
	   			var reply = {replyCd:replyCdValue, reply : replyModalTextareaComment.val()};
	   			
	   			replyService.update(reply, function(result){
		   				
	       			//alert(result);
	       			
	       			$.modal.close();
	       			

	       			// 댓글 리스트에서 해당 댓글 내용 수정
		  			$('.reply[data-replyCd='+replyCdValue+']').find('p').html(replyModalTextareaComment.val());
		  			

	   			}); 
	
	   		});
	
			
			/* 댓글 삭제 처리 */
		 	$("#modalRemoveBtn").off("click").on("click", function(e){
		 		
		
	 			//removeModal.data("commentCd", commentCdValue);
	 			//console.log("c2:"+removeModal.data("commentCd"));
				//commentService.remove(removeModal.data("commentCd"), function(result){
				replyService.remove(replyCdValue, function(result){
					
		  			//alert(result);
				
		  			$.modal.close(); // 이때 modal은 변수명 아님
				
		  			
					// 댓글 리스트에서 해당 댓글 삭제
		  			$('.reply[data-replyCd='+replyCdValue+']').remove();
		  			
		  			// 댓글 개수 갱신
					commentService.getReplyCnt(commentCdValue, function(replyCnt){
						console.log(replyCnt);
						commentReplyCntSpan.html("댓글 " + replyCnt);
					});
		  			
		  			
		  			
	  			});
	  		});
			
			/* 모달창 종료 버튼 클릭 처리 */
			$("#modalCloseBtn").off("click").on("click", function(e){	
				$.modal.close();
			});
 			
 		} else {
 			// 미로그인 사용자	
 		
 			var confirmResult = confirm("로그인 후 댓글 기능을 이용할 수 있습니다. \n로그인 페이지로 이동하시겠습니까?");
			if(confirmResult){
			  location.href= '/login';
			}
 		}
		
			
		
	}); 
	
//////////댓글 /////////////////////////////////////////



//////////좋아요 ///////////////////////////////////////// 

/* 메서드 - 유저 해당 코멘트 좋아요 유무 확인 */


	var likeCnt = $(".likeCnt");
	
	<sec:authorize access="isAuthenticated()">
	
	// 유저 해당 코멘트 좋아요 유무 확인
	likeService.getUser({userid: userid, commentCd:commentCdValue}, function(like){
		console.log("like: " + like);
		
		if(like == null){
			
		} else {
			$(".likeBtn").addClass('likeActive');
		}
	});
</sec:authorize>
		

// 	}


			
/* 이벤트 - 좋아요 버튼 클릭 시 좋아요 등록/제거 */
	$(".likeBtn").off("click").on("click", function(event){
		
		if(userid != null){
			
// 			var commentInfo = $(this).parent().siblings("div.commentInfo");
			var likeBtn = $(this);
		
			var commentCdValue = commentInfo.data("commentcd");
			
// 			console.log("commentCdValue: " + commentCdValue);
		
// 			var commentCntDiv = $(this).parent().siblings("div.commentCntNmodRemBtn").children("div.commentCnt");
// 				// 동적 생성된 요소라도 무관함.
				
// 			var commentLikeCntSpan = commentCntDiv.children("span.likeCnt");
			
// 			console.log("commentLikeCntSpan : " + commentLikeCntSpan.val());


			
			
			
			// 좋아요 활성화 클래스 포함 여부 확인
			// tip) jQuery 클래스 관련 메서드 hasClass, addClass, removeClass	
			if($(this).hasClass("likeActive") == true){
				
				
				// 유저의 특정 코멘트 좋아요 조회 메서드 -> 해당 좋아요 삭제
				likeService.getUser({userid:userid, commentCd:commentCdValue}, function(like){
					console.log("like: " + like);
					
					likeService.remove(like, function(likeCnt){
					
						//alert("likeCnt: " + likeCnt);
						
						// 좋아요 제거
						likeBtn.removeClass('likeActive');
						
// 						// 좋아요 개수 갱신
						$(".likeCnt").html("좋아요 " + likeCnt);
						
			
					});
					
				});
				
			} else {
				
				var like = {
						commentCd : commentCdValue,
						movieCd: movieCdValue,
						userid : userid
				}
	
				likeService.add(like, function(likeCnt){
					
					//alert("likeCnt: " + likeCnt);
					
					// 좋아요 등록
					likeBtn.addClass('likeActive');
					
					// 좋아요 개수 갱신
					$(".likeCnt").html("좋아요 " + likeCnt);
			
						
// 					});
					
				});	
				
			}
			
			console.log($(this));
			
		} else {
			// 미로그인 유저
			var confirmResult = confirm("로그인 후 좋아요 기능을 이용할 수 있습니다. \n로그인 페이지로 이동하시겠습니까?");
			if(confirmResult){
			  location.href= '/login';
			}
		}
		

	
	});
	



//////////좋아요 ///////////////////////////////////////// 
	















		
	
	
	
	
});
</script>

<!-- / Modal - Comment -->

<!-- Modal - Login -->
<!-- / Modal - Login -->

<script>
$(document).ready(function(){
	$(".replyUL").off("click").on("click", ".replyBtn", function(event){
		//alert('a');
	});
});

</script>
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
