<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<%-- <meta id="_csrf" name="_csrf" th:content="${_csrf.token}" />
<meta id="_csrf_header" name="_csrf_header" th:content="${_csrf.headerName}" /> --%>
	
	
	<link href="/resources/css/main.css" rel="stylesheet">
	<link href="/resources/css/reset.css" rel="stylesheet">
	<link href="/resources/css/style.css" rel="stylesheet">
	
<!-- Remember to include jQuery :) -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.0.0/jquery.min.js"></script>

<!-- jQuery Modal -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-modal/0.9.1/jquery.modal.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-modal/0.9.1/jquery.modal.min.css" />
 
 <script src="https://kit.fontawesome.com/1c6c1eba10.js" crossorigin="anonymous"></script>
 
	<script type="text/javascript" src="/resources/js/movie.js"></script>
	<script type="text/javascript" src="/resources/js/comment.js"></script>
	<script type="text/javascript" src="/resources/js/reply.js"></script>
	<script type="text/javascript" src="/resources/js/like.js"></script>
	<script type="text/javascript" src="/resources/js/starRating.js"></script>
	<script type="text/javascript" src="/resources/js/search.js"></script>
	<script type="text/javascript" src="/resources/js/manage.js"></script>
	<script type="text/javascript" src="/resources/js/member.js"></script>
	
	

	
</head>
<body>
	<!-- 별점 -->
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
	
	 <!-- HEADER -->
<!-- 4-1 헤더, 메인 등 한줄 한줄의 영역을 section이라함. -->
<!-- 4-1 하나의 섹션 안에서 특정 컨텐츠들을 감싸서 화면의 중앙에 몰아주는 것들을 container, wrapper, inner 등이라 함 -->
	<header class="header section">
        <div class="inner clearfix">
        
			<div class="header-group">
				<!-- 4-2. Header- 왼쪽 콘텐츠 구조 -->
	            <div class="menu-group float--left">
	                <div class="logo">
	                    <a href="/"> <img alt="MOVIEPEIDA logo" src="/resources/img/logo.png"></a>
	                </div>
	                
	            </div>
				<!-- 4-2. Header- 오른쪽 콘텐츠 구조 -->
	            <div class="sign-group float--right">
	            	<!-- 4-5 검색 양식 - form태그 이용-->
	                <!-- 4-5 양식은 사용자로부터 입력받는 것 > 고유해야한다. > id 사용 권장 -->
	                <!-- 4-5 method 속성: form태그로 데이터 전송 시 post방식 이용하겠다 -->
	                <div style="position: relative;">
		                <form id="search-form" method="get" action="/search">	               		
		                
		                    <input type="search" id="search" class="input--text" name="query" placeholder="영화, 인물, 유저를 검색해보세요." autocomplete="off">
		                   	<input hidden="hidden" name="category" value="movie">
		                    
		                    <!-- 4-5 submit 제출을 위해 필요는 하지만 화면에 나타나진 않았으면 함 
		                    	display: none; -->
		                    <input type="submit" value="submit"> 
		                </form>
	                <!-- 연관검색어 DropDown 창 -->  
		                <div style="position: absolute;">
		                	<ul id="searchDrop">
		                
		                	</ul>
		                </div>    
	                </div>
	         	<!-- 
	         		<div class="btn-group">
	         			<button id="loginBtn" type="button" class="btn sign-in">로그인</button>
	         			<button id="signUpBtn" type="button" class="btn btn--primary sign-up">회원가입</button>
	                </div> -->
	         	
	         		<%-- <div class="btn-group">
	         			<a href="index.jsp?center=review.jsp" class="btn">평가하기</a>
	         			<form action="index.jsp?center=userInfo.jsp" method="post">
	         				<input type="hidden" name="userCd" value=<%=userCd %>>
	         				<input type="submit" value=<%=udto.getName()  %> class="btn class="btn sign-in">
	         			</form>
	         			<a href="index.jsp?center=userInfo.jsp" class="btn sign-in"><%=udto.getName()  %></a>
	         			<a href="sign/signOut.jsp" class="btn sign-in">로그아웃</a>
	         		</div> --%>
	         		
	         		
	   
	         		
	         		
	         		<div class="btn-group">
	         		
		         		<sec:authorize access="isAnonymous()">
		         			<!-- <button id="loginBtn" type="button" class="btn sign-in">로그인</button> -->
		         			<a href="/login" class="btn sign-in">로그인</a>
		         			
		         		</sec:authorize>
		         		<sec:authorize access="isAuthenticated()">
		         			<a href="/review"><span class="margin--right10">평가하기</span></a>
		         			<a href="/users/${userInfo.randomString}" class="btn sign-in margin--right5">${userInfo.userName }</a>
		         			<!-- <button id="loginBtn" type="button" class="btn sign-in">로그인</button> -->
		         			<!-- <a href="/logout" class="btn sign-in">로그아웃</a> -->
		         			<a href="#" onclick="document.getElementById('logoutForm').submit();" class="btn sign-in">로그아웃</a>
							<form id="logoutForm" action="/logout" method="POST">
							   <input name="${_csrf.parameterName}" type="hidden" value="${_csrf.token}"/>
							</form>
		         			
		         		</sec:authorize>
	         		
	         		</div>
	         
	          		
	            </div>



			</div>
			
           
        </div>  
    </header>
	
<script>
$(document).ready(function(){
	
	var csrfHeaderName = "${_csrf.headerName}";
	var csrfTokenValue = "${_csrf.token}"
	
	/* 38.5.3 Ajax로 CSRF 토큰 전송 방식
		: ajaxSend() 이용 시 모든 Ajax 전송 시 CSRF 토큰 같이 전송하도록 되어있음 */
	$(document).ajaxSend(function(e, xhr, options){
		xhr.setRequestHeader(csrfHeaderName, csrfTokenValue);
	});
	
	
	// 로그인 정보
	var userid = null;
	var loginUserRandomString = '${userInfo.randomString}';
	console.log("loginUserRandomString: " +  loginUserRandomString);
	<sec:authorize access="isAuthenticated()">
		userid = '<sec:authentication property="principal.username"/>';
		// 특수 문자 치환
			// @ . 과 같은 특수문자 Java -> JS 넘어오면서 변환되어 메서드 에러 발생시킴
		userid = userid.replace("&#64;", "@");
		userid = userid.replace(/\&\#46\;/gi, "."); // /gi: JS엔 replaceAll 없음 -> 정규식 gi 이용해 구현
		console.log("userid",userid);
	</sec:authorize>
	

	

	
	
	
/* -------------------- 검색 ---------------------------------------- */	
	
	const searchInput = $('#search');
	const searchDropUL = $('#searchDrop');
	searchInput.val(''); // 검색창 초기화
	searchDropUL.hide();  // 검색 드롭창 숨기기
		// tip) hide(): display none; / show() : display block
	
	
	/* 검색 드롭창 오픈 */
	searchInput.on({
		keyup: function(e){
			//e.stopPropagation(); // 부모요소로 이벤트 전파 방지(이후 드롭창 hide 위함)위함이나 작동안함..
			var query = $(this).val();
			console.log($(this).val());
			
// 			searchDropDownByRelatedQuery(query);
			searchDropDown(query);
		},
		focus: function(e){
			//e.stopPropagation();
			var query = $(this).val();
			console.log($(this).val());
			
// 			searchDropDownByRecentSearchQuery(query);
			searchDropDown(query);
		}
	});
		
	/* 연관 검색어/ 최근 검색어 출력 메서드 */
	function searchDropDown(query){
		
		// 연관 검색어 호출
		if(query != ''){
			
			searchService.getRelatedQuery({query:query}, function(relatedQueryList){
				console.log(relatedQueryList);
				searchDropUL.append(relatedQueryList);
				
				//searchDropUL.toggle();
				console.log('a');
					
				
				if(relatedQueryList.length != 0){
					
					let relatedQueryHtml = '';
					
					relatedQueryHtml += '<p style="color:red;">연관 검색어</p>'; 
					
					for(let i=0, len=relatedQueryList.length; i<len; i++){
// 						html += '<li><a href="/movieInfo/'+relatedQueryList[i].movieCd+'">'+relatedQueryList[i].movieNm+
// 								'</a></li>';
						relatedQueryHtml += '<li><a href="/search?query='+relatedQueryList[i].movieNm+'&category=movie">'+relatedQueryList[i].movieNm+'</a></li>';			
								/* tip) 공백 문자 인코딩 결과 : java:+, JS:%20 */
					}
			
					searchDropUL.html('');
					searchDropUL.html(relatedQueryHtml);
					
					searchDropUL.show();
				} 
				
			});	
		
		} 
		// 최근 검색어 출력
		else {
			if(userid != null) {
				
				
				if(recentSearchQueryList.length == 0){
					searchDropUL.hide();
				} else {
					
					console.log("recentSearchQueryList.length : " + recentSearchQueryList.length );
					
					let recentSearchQueryHtml = '';
					
					recentSearchQueryHtml += '<div class="clearfix">' +
												'<div class="float--left"><p style="color:red;">최근 검색어</p></div>' +
												'<div class="float--right searchQueryBtn" id="allRecentQueryDeleteBtn">모두 삭제</div>' +
											'</div>';
					
					for(let i=recentSearchQueryList.length -1; i>=0; i--){
// 						recentSearchQueryHtml += '<li><a href="/search?query='+recentSearchQueryList[i].query+'&category=movie">'+
// 													'<div class="clearfix">' + 
// 														'<div class="float--left">'+recentSearchQueryList[i].query+'</div>' +
// 														'<div class="recentQueryDeleteBtn float--right">❌</div>' +
// 												'</div></a></li>';
												
						recentSearchQueryHtml += '<li class="recentSearchQueryLi" id="recentSearchQuery_'+recentSearchQueryList[i].date+'"><div class="clearfix">'+
													'<div class="float--left">' +
															'<a style="display: block; width:210px;" href="/search?query='+recentSearchQueryList[i].query+'&category=movie"><div>' + recentSearchQueryList[i].query+'</div></a></div>' +
													'<div class="recentQueryDeleteBtn float--right searchQueryBtn">❌</div>' +
												'</div></li>';
					}
	//	 			recentSearchQueryList.reverse().forEach(function(recentSearchQuery) {
	//	 				recentSearchQueryHtml += '<li><a href="/search?query='+recentSearchQuery.query+'&category=movie">'+recentSearchQuery.query+'</a></li>';
	
	//	 			});
					
					searchDropUL.html('');
					searchDropUL.html(recentSearchQueryHtml);
					
					searchDropUL.show();
				}
				
			}

		}
	
	};
	


	// 바깥 화면 클릭 시 drop 요소 숨기기
	$("html").off("click").on("click", function(e){
		// ?) stopPropagation() 이벤트 전파 방지가 제대로 작동하지 않아 document 대신 html 태그 지정
	
// 		console.log(this);
// 		console.log($(this));
// 		console.log(e.target);
// 		console.log("e.target: " + e.target);
// 		console.log("e.target: ", e.target);
// 		console.log($(e.target));
// 		console.log("$(e.target): " + $(e.target));
// 		console.log("$(e.target): ", $(e.target));
// 		console.log(e.currentTarget);
// 		console.log($(e.currentTarget));

		
		if($(e.target).attr("id") != "search" && $(e.target).closest("#searchDrop").attr("id") != "searchDrop") {
			 searchDropUL.hide();
		};
	   
	});
	
	
/* ------ 최근 검색어 ------------------------- */

	// localStorage 저장 키 값
	const RECENTSEARCHQUERYLIST_KEY = 'recentSearchQueryList_' + loginUserRandomString;
	
	// 최근 검색어 담을 리스트
		// 검색어 저장/삭제 등 실제 처리 이루어질 리스트 객체
	let recentSearchQueryList = new Array();
	
	// localStorage에 저장되어있는 리스트 가져와 저장 
	const savedRecentSearchQueryList = JSON.parse(localStorage.getItem(RECENTSEARCHQUERYLIST_KEY));
	

	if(userid != null){
		console.log("userid not null");
		

		// 실제 사용할 최근 검색어 리스트에 담기
		if(savedRecentSearchQueryList !== null) {
			recentSearchQueryList = savedRecentSearchQueryList //전에 있던 items들을 계속 가지도 있다록 합니다. 
			console.log("savedRecentSearchQueryList: " + savedRecentSearchQueryList);
			console.log("savedRecentSearchQueryList.length: " + savedRecentSearchQueryList.length);
			recentSearchQueryList.forEach(recentSearchQuery => console.log("recentSearchQuery: " + recentSearchQuery.date+"/"+recentSearchQuery.query));
			

		}
	}
	
	
	
	
	
	// function - localStorage에 리스트 저장
	function saveRecentSearchQueryList() { //item을 localStorage에 저장합니다.
	    typeof(Storage) !== 'undefined' && localStorage.setItem(RECENTSEARCHQUERYLIST_KEY, JSON.stringify(recentSearchQueryList));
	};
	
	/* 최근 검색어 기능 구현 */
	
	// submit 조건
 	$('#search-form').on("submit", function(e){
 		e.preventDefault();

 		var searchQuery = searchInput.val();
 		
 	// input 값 없는 경우 submit X
 		if(searchQuery.length == 0) {
 			return false;
 		}	
 		
 		/* 최근 검색어 구현 */
 		if(userid != null){
 			
	 		// 최근 검색어 저장 
	 		const newRecentSearchQuery = searchQuery;
	 		
	 		const newRecentSearchObj = {
	 				date: Date.now(),
	 				query: newRecentSearchQuery
	 		};
	 		
			// 검색어가 최근 검색어 리스트 기존재 여부 체크 
	 		recentSearchQueryList.forEach(function(recentSearchObj, index){
				
	 			if(recentSearchObj.query == newRecentSearchObj.query) {
	 				
	 				// 기존 검색어 삭제
	 				recentSearchQueryList.splice(index, 1); 
	 				// array.splice(start[, deleteCount[, item1[, item2[, ...]]]]) : start 인덱스부터 deletecount만큼 요소 삭제 후 그 index에서 item 차례로 추가
	 				// index부터 한개의 요소 삭제 (삭제할 요소와 추가할 요소 개수 다르면 배열 길이 달라짐)
	
	 				return false;
	 			} 
	 		});
	 		
			// 배열에 추가
		 	recentSearchQueryList.push(newRecentSearchObj);
		
	 		
	 		
	 		// 총 5개의 최근 검색어만 저장
	 		if(recentSearchQueryList.length > 5){
	 			console.log("5 초과");
	 			recentSearchQueryList.shift(); // 배열 첫번째 요소 제거 및 반환(배열 크기 줄어듬)
	 			
	 		}
	 	
	 		// localStorage에 추가
	 		saveRecentSearchQueryList(); // localStorage에 저장(key값 동일하므로 value만 변경되는 것)
	 		
 		}
 		
 		
 		//$(e.target).attr('action', '/search/' + searchInput.val());
 		console.log($(e.target).attr('action'));

 		e.target.submit(); 	
 		
 		
	}); 
	
	/* 최근 검색어 모두 삭제 */
	$(document.body).off("click").on("click", "#allRecentQueryDeleteBtn", function(e){
	 	
		console.log("allRecentQueryDeleteBtn");
		
		localStorage.removeItem(RECENTSEARCHQUERYLIST_KEY);
		
		recentSearchQueryList = [];
	
		searchDropUL.hide();
		
	});
	
	/* 최근 검색어 모두 삭제 */
	$(document).off("click").on("click", ".recentQueryDeleteBtn", function(e){
	 	
		console.log("recentQueryDeleteBtn");
		
		const recentSearchQueryLi = $(e.target).closest(".recentSearchQueryLi");
		

		recentSearchQueryList = recentSearchQueryList.filter(recentSearchQuery => 'recentSearchQuery_' + recentSearchQuery.date != recentSearchQueryLi.attr("id"));
			// array.filter : 조건 true인 요소로만 새로운 배열 생성
		
		saveRecentSearchQueryList();
		
		recentSearchQueryLi.remove();
		
		if(recentSearchQueryList.length == 0){
			
			searchDropUL.html('');
			
			searchDropUL.hide();
		}
	});
	
/* ------ 최근 검색어 ------------------------- */

/* -------------------- 검색 ---------------------------------------- */

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
	
	
	
<!-- Modal - Login -->
 	<div id="loginModal" class="modal">
		<div class="modal_content">
		    <header>로고</header>
		    <h2>로그인</h2>
			<section>
				<form id="loginForm" method="post" action="/login">
					<div>
				   		<!-- <input name="email" placeholder="이메일"> -->
				   		<input type="text" name="username" aria-describedby="emailHelp"
			        	placeholder="아이디" autofocus="autofocus">
				   	</div>
					<div>
				   		<!-- <input name="password" placeholder="비밀번호"> -->
				   		<input type="password" name="password" placeholder="비밀번호" value="">
				   	</div>
				   	<label>
			        	<input type="checkbox"  name="remember-me">
			        	Remember Me
			        </label>
			        
			        <input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }">
			<!-- 이 EL의 이름은 실제 브라우저에서 _csrf라는 이름으로 처리됨
				value 값은 임의의값 지정됨
				브라우저 페이지 소스 보기에서 확인 가능 -->   
				
				    <div>
				        <button id="modalLoginBtn" type="button">로그인</button>
				        <button id="modalSignupLinkBtn" type="button">회원가입 이동</button>
				    </div>
				</form>
			</section>
		</div>
	</div>
	<!-- /.modal -->
	
	
<!-- Modal - Login -->
<script type="text/javascript">
	$(document).ready(function(){
		
		/* 로그인 처리 */
		var modal = $("#loginModal"); 
		
		var modalInputEmail = modal.find("input[name='email']");
		var modalInputPassword = modal.find("input[name='password']");
		
		var modalLoginBtn = $("#modalLoginBtn");
		var modalSignUpLinkBtn = $("#modalSignUpLinkBtn"); // 회원가입 모달창 이동 버튼
		
		
		/* 로그인 모달창 오픈 처리 */
		$("#loginBtn, #modalLoginLinkBtn").click(function(e){
			// 선택자 OR -> , 로 구분해 작성
			
			//alert("b");
			
			modal.find("input").val(""); // 모달창 오픈시 내용 초기화
			
			// 모달창 오픈
				// 옵션은 등록 function 내에 모달창 오픈 시 지정 
			modal.modal({
				clickClose: true,	// Allows the user to close the modal by clicking the overlay	
				 escapeClose: false,      // Allows the user to close the modal by pressing `ESC`
				  showClose: false	// Shows a (X) icon/link in the top-right corner
			});
			
		});
		
		
	
		/* 로그인 이벤트 처리 */
		modalLoginBtn.on("click", function(e){
	    	e.preventDefault();
	    	$("#loginForm").submit();
   		 });
		
		/* modalLoginBtn.on("click", function(e){
			
			var comment = {
				contents : modalInputComment.val(),
				userCd : modalInputUserCd.val(),
				movieCd: movieCdValue
			};
			
			commentService.add(comment, function(result){
				

				alert(result);
				
	
				modal.find("textarea").val("");
				modal.find("input").val("");
				
				
				$.modal.close();
				
				showList();							
			});			
		}); */
	});
	</script>
	<!-- / Modal - Login -->
	
	
	<!-- Modal - SignUp -->
 	<div id="signUpModal" class="modal">
		<div class="modal_content">
		    <header>로고</header>
		    <h2>회원가입</h2>
			<section>
				<div>
			   		<input name="name" placeholder="이름">
			   	</div>
				<div>
			   		<input name="email" placeholder="이메일">
			   	</div>
				<div>
			   		<input name="password" placeholder="비밀번호">
			   	</div>
			    <div>
			        <button id="modalsignUpBtn" type="button">회원가입</button>
			        <button id="modalLoginLinkBtn" type="button">로그인 이동</button>
			    </div>
			</section>
		</div>
	</div>
	<!-- /.modal -->
	
	<!-- Modal - SignUp -->
	<script type="text/javascript">
	$(document).ready(function(){
		
		/* 회원가입 처리 */
		var modal = $("#signUpModal"); 
		
		var modalInputName = modal.find("input[name='name']");
		var modalInputEmail = modal.find("input[name='email']");
		var modalInputPassword = modal.find("input[name='password']");
		
		var modalSignUpBtn = $("#modalSignUpBtn");
		var modalLoginLinkBtn = $("#modalLoginLinkBtn");
		
		
		/* 회원가입 모달창 오픈 처리 */
		$("#signUpBtn, #modalSignupLinkBtn").click(function(e){
			//alert("b");
			
			modal.find("input").val(""); // 모달창 오픈시 내용 초기화
			
			// 모달창 오픈
				// 옵션은 등록 function 내에 모달창 오픈 시 지정 
			modal.modal({
				clickClose: true,	// Allows the user to close the modal by clicking the overlay	
				 escapeClose: false,      // Allows the user to close the modal by pressing `ESC`
				  showClose: false	// Shows a (X) icon/link in the top-right corner
			
			});
			
		});
	
		/* 회원가입 이벤트 처리 */
		/* modalLoginBtn.on("click", function(e){
			
			var comment = {
				contents : modalInputComment.val(),
				userCd : modalInputUserCd.val(),
				movieCd: movieCdValue
			};
			
			commentService.add(comment, function(result){
				
				alert(result);
				
	
				modal.find("textarea").val("");
				modal.find("input").val("");
				
				
				$.modal.close();
				
				showList();							
			});			
		}); */
	});
	</script>
	<!-- / Modal - SignUp -->
	
	
	
	
	
</body>
</html>