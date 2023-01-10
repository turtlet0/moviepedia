<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<c:forEach var="userComment" items="${userCommentList }">

	<c:set var="commentVO" value="${userComment.commentVO }"/>
	
	<li class="commentLi margin--bottom20 padding--h5 padding--v5" data-commentCd="${commentVO.commentCd}" data-movieCd="${commentVO.movieCd}">
		<div class="clearfix padding--top5">
			<div style="width:80%;" class="float--left">
				<!-- 유저 정보 / 작성 일자 -->
				<div style="height:25px; line-height: 25px;" class="clearfix border--bottom">
					<a title="${userComment.userName}" href="/users/${userComment.randomString}">
						<div class="float--left margin--right10 font--bold">${userComment.userName}</div>
					

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
					</a>	
					
				</div>
				
				<!-- 영화 정보 -->
				<div class="peopleSMovie clearfix  padding--h5"">				
					<div class="font--bold margin--bottom5">${userComment.movieNm }</div>
					<div><c:out value="${userComment.prdtYear }" /> • <c:out value="${userComment.repGenreNm }" /> • <c:out value="${userComment.repNationNm }" /></div>
				</div>
				
				<!-- 별점 정보 -->
				<c:if test="${userComment.userStarRating ne 0.0 }">
					<div class="commentStarRatingDiv  padding--h5">
						<svg class="icon icon-star" style="width:16px; height:16px;"><use xlink:href="#icon-star"></use></svg>
						<span>${userComment.userStarRating}</span>
					</div>
				</c:if>
			</div>
			
			<!-- 영화 포스터 -->			
			<div class="peopleSMoviePoster img-border float--right">
				<a href="/movieInfo/${commentVO.movieCd }">
					<img  alt="<c:out value="${userComment.movieNm }"/> 포스터" src="<c:out value="${userComment.posterUrl eq null || userComment.posterUrl eq ' ' ? '/resources/img/poster-not-available.jpg' : userComment.posterUrl}" />">
				</a>
			</div>
		</div>
		
		<div class="commentMainGroup margin--top7">
			<!-- 코멘트 내용 -->
			<div style="margin-bottom: 10px;" class="peopleSMovieInfo padding--h5">
				<a class="commentMove" href="/comments/${commentVO.commentCd}">
					<div  class="commentContents text">${commentVO.contents}</div>
				</a>
			</div>	
			
			<div class="clearfix">
				<div class="commentCnt float--left">
					<span class="likeCnt  margin--right10">좋아요 ${commentVO.likeCnt}</span>
					<span class="replyCnt">댓글 ${commentVO.replyCnt}</span>
				</div>
				
				<c:if test="${userInfo.userid eq commentVO.userid}">
					<div class="float--right commentModRemBtn">
						<button id="modCommentBtn" class="commentBtn border--right">수정</button>
						<button id="remCommentBtn" class="commentBtn">삭제</button>
					</div>
				</c:if>
			</div>
		</div>
		
		<div class="commentBtnGroup  border--top ">
			<c:choose>
				<c:when test="${userComment.likeCd eq null}">
					<button class="likeBtn likeBtn-alone">좋아요</button>
				</c:when>
				<c:when test="${userComment.likeCd ne null}">
					<button class="likeBtn likeBtn-alone likeActive">좋아요</button>
				</c:when>
			</c:choose>
		</div>
		
		
	</li>
</c:forEach>
