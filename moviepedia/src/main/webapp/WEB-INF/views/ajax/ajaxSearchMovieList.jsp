<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<c:choose>
	<c:when test="${fn:length(movieList) == 0}"> 
		<div class="noResultDiv">	
			<span id="movieNoResultSpan" >검색 결과가 없습니다. 다른 검색어를 입력해보세요.</span>
		</div>
	</c:when>
	<c:otherwise>
			<c:forEach var="movie" items="${movieList }">
				<li style="height:130px;" class='clearfix movieLi margin--bottom10'>
					<a title='${movie.movieNm}' href='/movieInfo/${movie.movieCd}'>
						<div class='float--left margin--right10'> 
							<div class='reviewPoster img-border'>
								<img  alt="<c:out value="${movie.movieNm }"/> 포스터" src="<c:out value="${movie.posterUrl eq null || movie.posterUrl eq ' ' ? '/resources/img/poster-not-available.jpg' : movie.posterUrl}" />">
							</div>
						</div>
	
						<div style="height: 100%;  width:250px;" class='float--left margin--top5 border--bottom'>
							<div>
								<p class="font--bold margin--bottom5">${movie.movieNm}</p>
								<div class="margin--bottom3">${movie.prdtYear} • ${movie.repGenreNm} • ${movie.repNationNm}</div>	
								<div class="clearfix">
									<c:set var="directorList" value="${fn:split(movie.directorDetail, ',') }"/>
									<c:forEach var="director" items="${directorList }" varStatus="status">	
										<%-- <c:set var="directorNm" value="${fn:split(director, '|')[0] }"/> --%>
										<div class="float--left margin--right5 repNm">${fn:split(director, '|')[0] }</div>				
									</c:forEach>			
								</div>			
							</div>
						</div>
					</a>
				</li>
			</c:forEach>
	</c:otherwise>
</c:choose>

<%-- <c:set var="session" value="${returnActorCnt }" scope="session" /> --%>
<%-- <c:set var="requst" value="${returnActorCnt }" scope="request" /> --%>
<%-- <c:set var="page" value="${returnActorCnt }" scope="page" /> --%>
