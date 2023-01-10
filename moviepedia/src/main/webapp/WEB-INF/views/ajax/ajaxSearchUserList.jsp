<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<c:choose>
	<c:when test="${fn:length(userList) == 0}"> 
		<div class="noResultDiv">		
			<span id="userNoResultSpan">검색 결과가 없습니다. 다른 검색어를 입력해보세요.</span>
		</div>
	</c:when>
	<c:otherwise>
			<c:forEach var="user" items="${userList }">
				<li style="margin-left: 10px; height: 65px; vertical-align: top;" class='userLi clearfix border--bottom margin--bottom10'>
					<a title='${user.userName}' href='/users/${user.randomString}'>
<!-- 						<div class='float--left'>  -->
<!-- 							<div class='reviewPoster'> -->
<%-- 								<img  alt='${people.peopleNm} 포스터' src='#'/> --%>
<!-- 							</div> -->
<!-- 						</div> -->
	
						<div class='float--left'>
							<p class="font--bold margin--bottom5">${user.userName}</p>
							<div>
								<c:if test="${user.cntStarRating != 0}">
									<c:choose>
										<c:when test="${user.cntComment != 0}">
											<div class="margin--bottom3">평가 ${user.cntStarRating} • 코멘트 ${user.cntComment}</div>
										</c:when>
										<c:otherwise>
											<div class="margin--bottom3">평가 ${user.cntStarRating}</div>
										</c:otherwise>
									</c:choose>
								</c:if>
							</div>	
							
							<c:if test="${user.userIntroduction ne null }">
								<div class="margin--bottom3">${user.userIntroduction }</div>
							</c:if>
						</div>
					</a>
				</li>
			</c:forEach>
	</c:otherwise>
</c:choose>