<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ include file="../includes/header.jsp" %>

<main>
	<div class = "section" >
		<div class = "center">
			<div class = "innerFix">
			
				<header>
					<div>
						<button style="font-size:30px; color:red;"  class="backBtn padding--v5" onclick="history.back(-1)">←</button>
					</div>
					<h2>코멘트</h2>
					<div class="padding--bottom10 padding--h5">
						<select id="orderBy">
							<option value="0">좋아요 순</option>
							<option value="1">작성 순</option>
						</select>
					</div>
				</header>
				
				<!-- 전체 코멘트 리스트 -->
				<section class="padding--h5">
					<ul class="commentsList infinite">		
					</ul>
			
				</section> 
				
				
				
				
				<div id="sentinel"></div>
									
	
			
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
		
	var csrfHeaderName = "${_csrf.headerName}";
	var csrfTokenValue = "${_csrf.token}"
	
	/* 38.5.3 Ajax로 CSRF 토큰 전송 방식
 		: ajaxSend() 이용 시 모든 Ajax 전송 시 CSRF 토큰 같이 전송하도록 되어있음 */
   	$(document).ajaxSend(function(e, xhr, options){
   		xhr.setRequestHeader(csrfHeaderName, csrfTokenValue);
   	});

	
	
//////////////// 코멘트 ////////////////////////////////////////////////


/* 이벤트 처리 - 영화 코멘트 목록 */
		// tip) for문은 비동기 함수의 결과를 기다려주지않음
			// -> 즉시 실행함수로 감싸거나 async 설정 false로 변경
	var movieCdValue = '<c:out value="${movieCd}" />';
	var commentUL = $(".commentsList");
	
	var orderByValue = 0;
	let currentCnt = $(".commentLi").length;
	
	var additionalCntInitValue = 5;
	var additionalCntAddValue = 3;
	let additionalCnt = additionalCntAddValue;

	let _returnListCnt = 0; // 추가된 코멘트 리스트의 개수
	
// 	showList(movieCdValue, orderByValue, totalCntValue); // 페이지 갱신 시 최초 실행
// 	showAdditionalList(movieCdValue, orderByValue, currentCnt, additionalCnt);  // 페이지 갱신 시 최초 실행
	showMovieCommentList(movieCdValue, orderByValue, currentCnt);
	
	console.log("1 additionalCnt:" + additionalCnt);
	
	// ================================================================//
	function showMovieCommentList(param_movieCd, param_orderBy, param_currentCnt, param_additionalCnt){
		
	
		// 코멘트 최초 조회 시엔 additionalCnt 파라미터 전달안함
		if(param_additionalCnt == null){
			param_additionalCnt = additionalCntInitValue; // 메서드 파라미터 변수.
		}
		
		
		console.log(param_movieCd+  "/" + param_orderBy + "/" + param_currentCnt + "/" + param_additionalCnt);
		
		commentService.ajax_getMovieCommentList({movieCd: param_movieCd, orderBy: param_orderBy, currentCnt: param_currentCnt, additionalCnt: param_additionalCnt}, 
				function(result){
			
		
			console.log("result: " + result);
		});
		
		// 변수 값 갱신
		console.log("commentLi.length: " + $(".commentLi").length);
		_returnListCnt = $(".commentLi").length - param_currentCnt;
		currentCnt = $(".commentLi").length;
		console.log("param_currentCnt: " + param_currentCnt +"/currentCnt: " + currentCnt);
		
	}
	
	
	
	
	// ================================================================//
	
	
	
	// 코멘트 목록 조회 순서 변경 시 동작
	$("#orderBy").off("change").on("change", function(){

		orderByValue = $(this).val();

		$('.commentsList').html("");
		$('#sentinel').show(); // 무한스크롤 감시 요소 show
		
		currentCnt = 0; // 값 초기화 ROWNUM 1부터 출력되도록
		additionalCnt = additionalCntInitValue;
		
		
		
// 		showList(movieCdValue, orderByValue, totalCntValue);
// 		showAdditionalList(movieCdValue, orderByValue, currentCnt, additionalCnt); 
		console.log("function before \n"+ movieCdValue+  "/" + orderByValue + "/" + currentCnt);
		showMovieCommentList(movieCdValue, orderByValue, currentCnt);
	});
	
	
	/*
	 * ===================== 무한 스크롤 ==================================
	 * JS IntersectionObserver API 이용
	 * https://velog.io/@eunoia/%EB%AC%B4%ED%95%9C-%EC%8A%A4%ED%81%AC%EB%A1%A4Infinite-scroll-%EA%B5%AC%ED%98%84%ED%95%98%EA%B8%B0
	 */
//	 	var intersectionObserver = new IntersectionObserver(function(entries){
//	 		// 만약 intersectionRatio 0이면 target이 view 밖에 있는 것
//	 		// -> 즉, 이땐 아무 것도 할 필요가 없는 것
//	 		if(entries[0].intersectionRatio <= 0) return ;
			
//	 		console.log("새 리스트 추가");
//	 	});	
		
//	 	// oveserving 감시 시작
//	 	intersectionObserver.observe(document.querySelector('.scrollerFooter'));
		
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
//	 			page._page += 1; // 불러올 페이지 추가
				
				// 코멘트 추가 메서드 호출
				console.log("1 _returnListCnt: " + _returnListCnt);
// 				showUserCommentList(randomString, orderBy, currentSqnc);

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
	
	
	
// 	// 코멘트 목록 조회 메서드 
// 	function showList(totalCntValue, orderByValue, totalCntValue){
		
// 		commentService.getList(
// 		{movieCd:movieCdValue, orderBy:orderByValue, totalCnt:totalCntValue}, function(list){
		
// 			var str = "";
			
// 			if(list == null || list.length == 0){
// 				commentUL.html("");
				
// 				return;
// 			}
			

			
// 			for(let i=0, len=list.length||0; i<len; i++) {

// 				str += "<li class='commentLi list' data-commentCd='"+list[i].commentCd+"'>";
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
		
// 	} // showList(totalCntValue, orderByValue, totalCntValue)
	
	
// 	/* 이벤트 처리 - 추가적인 코멘트 리스트 가져오기 */
	
// 	function showAdditionalList(movieCd, orderByValue, currentCnt){

// 		commentService.getAdditionalList(
// 		{movieCd:movieCd, orderBy:orderByValue, currentCnt:currentCnt}, function(list){
		
// 			var str = "";
			
// 			for(let i=0, len=list.length||0; i<len; i++) {

// 				str += "<li class='commentLi list' data-commentCd='"+list[i].commentCd+"'>";
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
			

// 			commentUL.html(commentUL.html() + str);
// 		}); // commentService.getAddtionalList
		
// 	} // showAdditionalList(movieCd, orderByValue, currentCnt)
	
// /* 메서드 - 코멘트 리스트 가져오기 (최초, 추가 시 메서드 통일) */
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
		
// 	} // showAdditionalList(movieCd, orderByValue, currentCnt, additionalCnt)
	
	
// /* 이벤트-  무한 스크롤 시 코멘트 목록 추가 */

// 	var currentCnt = $(".commentLi").length;
	
// 	var commentCnt = '<c:out value="${movie.commentCnt}" />';
	
// 	// https://swimfm.tistory.com/entry/무한-스크롤-Infinite-Scroll-페이징-구현해보기-예제#close
// 	function YesScroll() {
// 		const pagination = document.querySelector('.paginaiton'); // 페이지네이션 정보획득
// 		const fullContent = document.querySelector('.infinite'); // 전체를 둘러싼 컨텐츠 정보획득
// 		const screenHeight = screen.height; // 화면 크기
		
// 		let oneTime = false; // 일회용 글로벌 변수
// 			// 우선 oneTime 변수를 쓰는 이유는 바닥에 닿고 나서 madeBox를 딱 한번만 호출하기 위해서입니다.
// 			// 처음에 거짓으로 설정한 후, 바닥에 닿으면 oneTime을 true로 바꿔주면 똑같은 것들을 여러번 호출하는 일을 방지해줍니다.
// 		document.addEventListener('scroll',OnScroll,{passive:true}) // 스크롤 이벤트함수정의
 		
// 		function OnScroll () { //스크롤 이벤트 함수
// 		   const fullHeight = fullContent.clientHeight;    // infinite 클래스의 높이   
// 		   const scrollPosition = pageYOffset; // 스크롤 위치
		   
// 		   console.log(fullHeight + "/" + screenHeight + "/" + scrollPosition);
			
// 		   if (fullHeight-screenHeight/2 <= scrollPosition && !oneTime) { // 만약 전체높이-화면높이/2가 스크롤포지션보다 작아진다면, 그리고 oneTime 변수가 거짓이라면
// 			    alert(currentCnt +'/'+commentCnt+'/'+oneTime);
// 				oneTime = true; // oneTime 변수를 true로 변경해주고,
			     
// 			     madeAdditionalList();  // 컨텐츠를 추가하는 함수를 불러온다.
// 			}
// 		}
// 		// 메서드 - 무한 스크롤 시 컨텐츠 추가 함수
// 		function madeAdditionalList() {
// 			$('.loading').show(); // 로딩 애니메이션 보이기

// 			showAdditionalList(movieCdValue, orderByValue, currentCnt);
			    
// 			currentCnt = $(".commentLi").length;
// 			console.log(currentCnt);
				
// 		    oneTime = false;
			    
// 		 // 영화의 코멘트 총 개수 commentCnt = 현재 개수 currentCnt 인 경우 코멘트 추가 조회 버튼 숨김
// 			if(currentCnt >= commentCnt){
// //				alert(currentCnt + "a" + commentCnt);
// 				oneTime =true;
// 			}
		 
// 			$('.loading').hide();  // 로딩 애니메이션 숨기기
// 		}
// 	} // YesScroll()
	
// 	YesScroll()
		
		
// 	console.log(commentService);

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
		
// 		// 등록 처리
// 		if(commentBtnId == 'addCommentBtn'){

// 			modalRegisterBtn.show();
			
// 			// 모달창 오픈
// 				// 옵션은 등록 function 내에 모달창 오픈 시 지정 
// 			modal.modal({
// 				 escapeClose: true,      // Allows the user to close the modal by pressing `ESC`
// 				  clickClose: true,	// Allows the user to close the modal by clicking the overlay
// 				  showClose: true	// Shows a (X) icon/link in the top-right corner
// 			});
// 		// 수정 처리
// 		} else 
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
		
		
		
// 		/* 코멘트 등록 처리 */
// 		modalRegisterBtn.off("click").on("click", function(e){
// 			var comment = {
// 				contents : modalTextareaComment.val(),
// 				userid : userid,
// 				movieCd: movieCdValue
// 			};
			
// 			console.log(comment);
// 			commentService.add(comment, function(commentVO){
				
// 				alert(commentVO);
				
				
	
// 				modal.find("textarea").val("");				
				
// 				$.modal.close();
				
// 				showUserComment(); 
				
// 				/* 코멘트 등록/수정/삭제 시 리스트 갱신 X. 삭제 시에만 해당  */
// // 				additionalCnt = 2;
// // 				commentUL.html("");
// // 				showAdditionalList(movieCdValue, orderByValue, currentCnt, additionalCnt);
// // 				showList(movieCdValue, orderByValue, totalCntValue);
// 				// 수정필요) 왓챠피디아는 코멘트 삭제 시에만 해당 코멘트만 골라 삭제함
// 				// 등록/수정/삭제 모두 코멘트 리스트 갱신은 없음
// 				// 결정 필요
				
// 			});
			
// 		});
		
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
	   				
       		//	alert(commentVO);
       			
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
				
	  			alert(result);
				
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

//////////////// 코멘트 ////////////////////////////////////////////////


//////////////// 좋아요 ////////////////////////////////////////////////

/* 이벤트 - 좋아요 버튼 클릭 시 좋아요 등록/제거 */

	$(document).off("click").on("click", ".likeBtn", function(event){
		
		// 로그인한 사용자만 좋아요 가능
		if(userid != null){
			
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

