<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<c:choose>
	<c:when test="${fn:length(peopleList) == 0}"> 
		<div class="noResultDiv">		
			<span  id="peopleNoResultSpan">검색 결과가 없습니다. 다른 검색어를 입력해보세요.</span>
		</div>
	</c:when>
	<c:otherwise>
			<c:forEach var="people" items="${peopleList }">
				<li class='peopleLi clearfix margin--bottom10 padding--bottom5 border--bottom'>
					<a title='${people.peopleNm}' href='/people/${people.peopleCd}'>
<!-- 						<div class='float--left'>  -->
<!-- 							<div class='reviewPoster'> -->
<%-- 								<img  alt='${people.peopleNm} 포스터' src='#'/> --%>
<!-- 							</div> -->
<!-- 						</div> -->
	
						<div class='float--left'>
							<div>
								<p class="font--bold margin--bottom5">${people.peopleNm}</p>
								<div class="margin--bottom3">${people.repRoleNm}</div>	
								<div class="clearfix">
								<c:set var="repMovieList" value="${fn:split(people.repMovieList, ',') }"/>
								<c:forEach var="repMovie" items="${repMovieList }" varStatus="status">	
									<c:set var="repMovieNm" value="${fn:split(repMovie, '|')[1] }"/>
									<div class="float--left repNm margin--right10">${repMovieNm }</div>				
								</c:forEach>
								</div>
							</div>
						</div>
					</a>
				</li>
			</c:forEach>
	</c:otherwise>
</c:choose>