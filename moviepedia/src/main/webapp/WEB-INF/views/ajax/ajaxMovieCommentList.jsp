<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<c:forEach var="movieComment" items="${movieCommentList }">

	<c:set var="commentVO" value="${movieComment.commentVO }"/>
	
	<li class="commentLi margin--bottom20 padding--h5 padding--v5" data-commentCd="${commentVO.commentCd}" data-movieCd="${commentVO.movieCd}">
		<div style="height:25px; line-height: 25px;" class="clearfix border--bottom">
			<div class="float--left">
				<a title="${movieComment.userName}" href="/users/${movieComment.randomString}">
					<div  class="clearfix">
						<div class="float--left margin--right10 font--bold">${movieComment.userName}</div>	
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
			<!-- 별점 -->
		<c:if test="${movieComment.userStarRating ne 0.0 }">
			<div class="float--right commentStarRatingDiv">
				<svg class="icon icon-star" style="width:16px; height:16px;"><use xlink:href="#icon-star"></use></svg>
				<span>${movieComment.userStarRating}</span>
			</div>
		</c:if>
		
		</div>
		
		<div class="commentMainGroup">
			<div>
				<a class="commentMove" href="/comments/${commentVO.commentCd}">			
					<div  class="commentContents padding--v5">${commentVO.contents}</div>
				</a>
			</div>
			<div  class="commentCnt border--top border--bottom line--height">
				<span class="likeCnt">좋아요 ${commentVO.likeCnt}</span>
				<span class="replyCnt">댓글 ${commentVO.replyCnt}</span>
			</div>
		</div>
		<div class="commentBtnGroup clearfix">
			<div class="float--left ">
				<c:choose>
					
					<c:when test="${movieComment.likeCd eq null}">
						<button  class="likeBtn likeBtn-alone line--height">좋아요</button>
					</c:when>
					<c:when test="${movieComment.likeCd ne null}">
						<button class="likeBtn likeBtn-alone line--height likeActive">좋아요</button>
					</c:when>
				</c:choose>
			</div>
			<!-- 영화 페이지의 코멘트 리스트에선 수정/삭제 노출 x -->
<%-- 			<c:if test="${userInfo.userid eq commentVO.userid}"> --%>
<!-- 				<div class="float--right commentModRemBtn"> -->
<!-- 					<button id="modCommentBtn" class="commentBtn">수정</button> -->
<!-- 					<button id="remCommentBtn" class="commentBtn">삭제</button> -->
<!-- 				</div> -->
<%-- 			</c:if> --%>
			
		</div>
		
	</li>
</c:forEach>
