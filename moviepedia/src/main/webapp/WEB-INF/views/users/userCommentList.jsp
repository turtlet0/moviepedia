<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ include file="../includes/header.jsp" %>

<main>
	<div class = "section" >
		<div class = "center">
			<div class = "innerFix">
			
				<header>
					<div>
						<button  style="font-size:30px; color:red;"  class="backBtn padding--v5" onclick="history.back(-1)">←</button>
					</div>
					<h2>작성한 코멘트</h2>
					<div class="padding--bottom10 padding--h5">
						<select id="orderBy">
							<option value="0">작성 순</option>
							<option value="1">좋아요 순</option>
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
							
							<option value="4">평균 별점 순</option>
							<option value="5">신작 순</option>
						</select>
					</div>
				</header>
				<!-- 전체 코멘트 리스트 -->
				<section class="padding--h5">
					<ul class="commentsList">
					
					</ul>
				</section> 
				

			<div id="sentinel">************</div>		
						
				
			
			</div>			
		</div>
	</div>
</main>	

<!-- Modal - Comment -->
 	<div id="commentModal" class="modal" >

		<div class="modal_content" title="클릭하면 창이 닫힙니다.">
	
<%--             <header><c:out value="${movie.movieNm }" /></header> --%>

            <div>
            	<div>
            		<textarea rows="50" cols="50" name="comment" maxlength="1000" placeholder="이 영화에 대한 생각을 자유롭게 표현해주세요."></textarea>
  
            	</div>
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
	
	
	
	
	
//////////////// 코멘트 ////////////////////////////////////////////////


/* 이벤트 처리 - 영화 코멘트 목록 */
		// tip) for문은 비동기 함수의 결과를 기다려주지않음
			// -> 즉시 실행함수로 감싸거나 async 설정 false로 변경
	var randomString = '<c:out value="${randomString}" />';
	
	var commentUL = $(".commentsList");
	
	let orderBy = 0;
	let currentSqnc = 0;
// 	var currentSqnc = $(".commentLi").length;
	
	var additionalCntInitValue = 20;
	var additionalCntAddValue = 10;
	let additionalCnt = additionalCntInitValue;
		// 무한 스크롤 사용 시 최초 출력 코멘트 리스트 높이가 화면보다 커야함
// 	var totalCntValue = 4; // 최초 조회 시 출력할 총 개수
	let _returnListCnt = 0; // 추가된 코멘트 리스트의 개수
	
	// 코멘트 리스트 조회 최초 실행
	showUserCommentList(randomString, orderBy, currentSqnc, additionalCnt); 
	
	
	/* 페이지 접근 시 코멘트 개수 0개인 경우 유저 정보 페이지로 이동 */
	/* if($(".commentLi").length == 0){
		alert("작성한 코멘트가 없습니다. 유저 페이지로 이동합니다.");
		
// 		history.back(-1);
		location.href = "/users/"+ userRandomString;
// 		location.replace("/users/"+ userRandomString);
			// tip) href는 그대로 페이지 이동을 의미, replace는 현재 페이지에 덮어씌우기 때문에 replace를 사용한 다음에는 이전 페이지로 돌아갈 수 없음.		
	} */
	
	
	
	// 코멘트 목록 조회 순서 변경 시 동작
	$("#orderBy").off("change").on("change", function(){
		
		$('#sentinel').show(); // 무한스크롤 감시 요소 show
		
		$('.commentsList').html("");
		
		orderBy = $(this).val();
		
		console.log("change orderBy: " + orderBy);

		currentSqnc = 0; // 값 초기화 ROWNUM 1부터 출력되도록
		additionalCnt = additionalCntInitValue;
		
		showUserCommentList(randomString, orderBy, currentSqnc, additionalCnt);
	});
	

/* 코멘트 리스트 추가 메서드 */
	function showUserCommentList(randomStringValue, orderByValue, currentSqncValue, additionalCntValue){
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
		
		commentService.ajax_getUserCommentList({randomString: randomStringValue, orderBy: orderByValue, currentSqnc: currentSqncValue, additionalCnt: additionalCntValue}, 
				function(result) {
			
		});
// 		commentService.ajax_csrf_getUserCommentList({randomString: randomString, orderBy: orderByValue, currentSqnc: currentSqnc, additionalCnt: additionalCnt,
// 			csrfHeaderName: csrfHeaderName,
// 			csrfTokenValue: csrfTokenValue}, function(result) {
	
// 		});

		// 변수 값 갱신
		_returnListCnt = $(".commentLi").length - currentSqnc;
		currentSqnc = $(".commentLi").length;	
		
		
	}
/* 코멘트 리스트 추가 메서드 */		
	
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
			showUserCommentList(randomString, orderBy, currentSqnc);
			
			if(_returnListCnt == 0){
				console.log("0 _returnListCnt: " + _returnListCnt);
			
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
	
/* ------------------------ 무한 스크롤 --------------------------- */	
 

/* 코멘트 모달창 오픈 처리 */
	var modal = $("#commentModal"); 
	var removeModal = $("#removeModal");
	//console.log(modal);
	//console.log($(".modal"));
	//console.log($(".modal[id=commentModal]"));
	
	var modalTextareaComment = modal.find("textarea[name='comment']");
	console.log(modalTextareaComment);
	
// 	var modalInputUserid = modal.find("input[name='userid']");
// 	var modalInputCommentDate = modal.find("input[name='commentDate']");
	
	var modalRegisterBtn = $("#modalRegisterBtn");
	var modalModBtn = $("#modalModBtn");
	var modalRemoveBtn = $("#modalRemoveBtn");
	
	

	/* 코멘트 수정/삭제 버튼 클릭 */
	$(".commentBtn").off("click").on("click", function(event){
		// tip) 동적요소 이벤트 바인딩 위해 document에 이번트 걸고 원하는 요소에 위임
		console.log(".commentBtn");
	
		var commentBtn = $(this);
		
		var commentContentsDiv = commentBtn.closest("div.commentMainGroup").find("div.commentContents");

		var commentContentsValue = commentContentsDiv.html();
		
		var commentLi = commentBtn.closest(".commentLi");
		
		var commentCdValue = commentLi.data("commentcd");
		var movieCdValue = commentLi.data("moviecd");
			// - 는 카멜표기법으로 변환됨 참고 / 카멜표기법은 소문자로 변환됨 참고
		console.log(commentCdValue);
			
		var commentBtnId = commentBtn.attr("id");
		var BtnClass = commentBtn.attr("class")
			
// 		modalInputCommentDate.closest("div").hide();
		
		modal.find("button").hide(); // 종료버튼 제외 전체 숨김
		
		
		// 수정 처리
		if (commentBtnId == 'modCommentBtn'){
			console.log("수정 처리 commentBtnId: " + commentBtnId);

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
			console.log("삭제 처리 commentBtnId: " + commentBtnId);
		
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
       			commentContentsDiv.html(commentVO.contents);
  				
   			}); 

   		});

		
		/* 코멘트 삭제 처리 */
	 	$("#modalRemoveBtn").off("click").on("click", function(e){
	 		
	
 			//removeModal.data("commentCd", commentCdValue);
 			//console.log("c2:"+removeModal.data("commentCd"));
			//commentService.remove(removeModal.data("commentCd"), function(result){
			commentService.remove(commentCdValue, function(result){
				
	  			alert(result);
			
	  			$.modal.close(); // 이때 modal은 변수명 아님
	  			
	  			// 코멘트 삭제
	  			commentLi.remove();
	  			
	  			// 코멘트 0개인 경우 유저 페이지로 이동
	  			if($(".commentLi").length == 0){
		  			location.replace("/users/"+ userRandomString);
		  				// tip) href는 그대로 페이지 이동을 의미, replace는 현재 페이지에 덮어씌우기 때문에 replace를 사용한 다음에는 이전 페이지로 돌아갈 수 없음.		
	  			}
  			});
  		});
		
		/* 모달창 종료 버튼 클릭 처리 */
		$("#modalCloseBtn").off("click").on("click", function(e){	
			$.modal.close();
		});
			
		
	}); // $(document).on("click", ".commentBtn", function(event)
/*/ 코멘트 모달창 오픈 버튼 클릭 */


//////////////// 코멘트 ////////////////////////////////////////////////


//////////////// 좋아요 ////////////////////////////////////////////////

/* 이벤트 - 좋아요 버튼 클릭 시 좋아요 등록/제거 */

	$(document).off("click").on("click", ".likeBtn", function(event){
		
		// 로그인한 사용자만 이용 가능
		if(userid != null){

			var likeBtn = $(this);
			
			var commentLi = $(this).closest("li.commentLi");
			
			var commentCdValue = commentLi.data("commentcd");
			var movieCdValue = commentLi.data("moviecd");
			
			console.log("commentCdValue: " + commentCdValue);
		
			var commentCntDiv = $(this).closest(".commentBtnGroup").siblings("div.commentMainGroup").find(".commentCnt");
				// 동적 생성된 요소라도 무관함.
				
			var commentLikeCntSpan = commentCntDiv.children("span.likeCnt");
			
			console.log("commentLikeCntSpan : " + commentLikeCntSpan);
			console.log("commentLikeCntSpan : " + commentLikeCntSpan.html());
			// 좋아요 활성화 클래스 포함 여부 확인
				// tip) jQuery 클래스 관련 메서드 hasClass, addClass, removeClass
			if(likeBtn.hasClass("likeActive") == true){
				likeBtn.removeClass('likeActive');
				
				// 유저의 특정 코멘트 좋아요 조회 메서드 -> 해당 좋아요 삭제
				likeService.getUser({userid:userid, commentCd:commentCdValue}, function(likeVO){
					console.log("likeVO: " + likeVO);
			
					likeService.remove(likeVO, function(result){

					
						//alert(result);
						
						// 좋아요 개수 갱신
						commentService.getLikeCnt(commentCdValue, function(likeCnt){
							console.log(likeCnt);
							commentLikeCntSpan.html("좋아요 " + likeCnt);
						});
					});
					
				});
		
			} else {
				
				
				
				var like = {
					commentCd : commentCdValue,
					movieCd: movieCdValue,
					userid : userid,
					csrfHeaderName: csrfHeaderName,
					csrfTokenValue: csrfTokenValue
				}
				
				likeService.add(like, function(result){
					//alert(result);
					
					// 좋아요 개수 갱신
					commentService.getLikeCnt(commentCdValue, function(likeCnt){
						console.log(likeCnt);
						commentLikeCntSpan.html("좋아요 " + likeCnt);
					});

					// 좋아요 등록
					likeBtn.addClass('likeActive');
				});
				
			}
			
			console.log($(this));
			
			
		} else {
			var confirmResult = confirm("로그인 후 좋아요 기능을 이용할 수 있습니다. \n로그인 페이지로 이동하시겠습니까?");
			if(confirmResult){
			  location.href= '/login';
			}
		}
		

	
	});
	
	// 다른 유저(미로그인 포함)가 유저 코멘트 리스트 페이지 방문 시
		// 좋아요 likeActive 클래스 제거
	if(userid == null){
		$('.likeBtn').removeClass('likeActive');

	}
	

//////////////// 좋아요 ////////////////////////////////////////////////



	
});
</script>


