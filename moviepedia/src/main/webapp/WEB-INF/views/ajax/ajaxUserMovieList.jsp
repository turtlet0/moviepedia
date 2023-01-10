<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<c:forEach var="userMovie" items="${userMovieList }">
<!-- 	<hr> -->
<%-- 	${userMovie } --%>
<!-- 	<hr> -->
<%-- 	<c:set var="movieVO" value="${userMovie.movieVO }"/> --%>
	
	<li class="movieLi">
		<a title="${userMovie.movieNm } 포스터" href="/movieInfo/${userMovie.movieCd }">
			<div class="margin--bottom5">
				<div class="img-border">
<%-- 											<img alt="${userMovie.movieNm }" src="${userMovie.posterUrl }"> --%>
					<img  alt="<c:out value="${userMovie.movieNm }"/> 포스터" src="<c:out value="${userMovie.posterUrl eq null || userMovie.posterUrl eq ' ' ? '/resources/img/poster-not-available.jpg' : userMovie.posterUrl}" />">
				</div>
			</div>
			
			<div>
				<div class="movie-Name font--bold margin--bottom5">${userMovie.movieNm }</div>
			</div>
			<div style="color:orange">평가함★ ${userMovie.userStarRating}</div>			
		
		</a>
	</li>
</c:forEach>
