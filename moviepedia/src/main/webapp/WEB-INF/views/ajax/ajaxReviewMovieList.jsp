<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<c:choose>
	<c:when test="${fn:length(reviewMovieList) == 0}"> 
		<div>
			<span id="movieNoResultSpan" >조회 결과가 없습니다.</span>
		</div>
	</c:when>
	<c:otherwise>
		<c:forEach var="movie" items="${reviewMovieList }">
			<li class='movieLi clearfix margin--bottom5 padding--h5 padding--v5 border--bottom'>	
				<div class='float--left margin--right10'>
					<div class='reviewPoster img-border'>		
						<a title='${movie.movieNm}' href='/movieInfo/${movie.movieCd}' target='_blank'>
							<img  alt="<c:out value="${movie.movieNm }"/> 포스터" src="<c:out value="${movie.posterUrl eq null || movie.posterUrl eq ' ' ? '/resources/img/poster-not-available.jpg' : movie.posterUrl}" />">
						</a>
					</div>
				</div>
			
				<div class='float--left margin--top5'>
					<div>
						<p class="font--bold margin--bottom10">${movie.movieNm}</p>
						<div class="margin--bottom5">${movie.prdtYear} • 
						
						<!-- 장르 조회는 genreAlt 조회해 출력. 검색은 repGenreNm 이용 -->
						<c:choose>
							<c:when test="${movie.genreAlt ne null}">${movie.genreAlt}</c:when>
							<c:otherwise>${movie.repGenreNm}</c:otherwise>
						</c:choose>
						• ${movie.repNationNm}</div>
						
						<!-- 평점 -->
						<div class="margin--bottom5">평균★${movie.avgStarRating}</div>	
					</div>
			
					<section class='starRating' data-value='0' data-movieCd='${movie.movieCd}'>
						<div class='stars'>
						<c:forEach var='score' begin='0' step='1' end='4' >
							<input class='stars-input' type='radio' name='${movie.movieCd}-rating' value='${5.0 - score}' id='${movie.movieCd}-rating-${5.0 - score}' >
							<label class='stars-view' for='${movie.movieCd}-rating-${5.0 - score}'><svg class='icon icon-star'><use xlink:href='#icon-star'></use></svg></label>
							<input class='stars-input' type='radio' name='${movie.movieCd}-rating' value='${4.5 - score}' id='${movie.movieCd}-rating-${4.5 - score}' >
							<label class='stars-view is-half' for='${movie.movieCd}-rating-${4.5 - score}'><svg class='icon icon-star-half'><use xlink:href='#icon-star-half'></use></svg></label>
						</c:forEach>
						</div>
					</section>
				</div>
			</li>
		</c:forEach>
	</c:otherwise>
</c:choose>

