<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<c:if test="${replyListDTO.replyList ne null }">
	<input hidden="hidden" name="replyCnt" value="${replyListDTO.replyCnt }"/>
	
	<c:forEach var="replyInfoDTO" items="${replyListDTO.replyList }">
		<li class="reply margin--bottom10 border--bottom"  data-replyCd="${replyInfoDTO.replyCd }">
			<div>
				<a title="${replyInfoDTO.userName}" href="/users/${replyInfoDTO.randomString}">
					<Strong class="margin--right10 font--bold">${replyInfoDTO.userName}</Strong>
					<small>			
						<jsp:useBean id="now" class="java.util.Date"/>		
						<fmt:parseNumber value="${now.time / (1000*60)}" var="nowfmtTime"/><!-- .time 필수 -->
						<fmt:parseNumber value="${replyInfoDTO.replyDate.time / (1000*60)}" var="replyDatefmtTime"/><!-- .time 필수 -->
						<fmt:parseNumber value="${nowfmtTime - replyDatefmtTime}" var="timeDefference"/>
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
					</small>
				</a>
				<!-- 댓글 내용 -->
				<p  class="text padding--h5 padding--v5">${replyInfoDTO.reply}</p>
			</div>
			
			<c:if test="${userInfo.userid eq  replyInfoDTO.userid}">
				<div class="clearfix">
					<div class="replyTrigger float--left">
						<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 20 20" fill="none" class="injected-svg" data-src="data:image/svg+xml +base64,PHN2ZyB3aWR0aD0iMjAiIGhlaWdodD0iMjAiIHZpZXdCb3g9IjAgMCAyMCAyMCIgZmlsbD0ibm9uZSIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPHBhdGggZmlsbC1ydWxlPSJldmVub2RkIiBjbGlwLXJ1bGU9ImV2ZW5vZGQiIGQ9Ik0xMS4yNTEgNS40MjY3NkMxMS4yNTEgNi4xMTc1OSAxMC42OTEgNi42NzY3NiAxMC4wMDEgNi42NzY3NkM5LjMxMDE0IDYuNjc2NzYgOC43NTA5OCA2LjExNzU5IDguNzUwOTggNS40MjY3NkM4Ljc1MDk4IDQuNzM2NzYgOS4zMTAxNCA0LjE3Njc2IDEwLjAwMSA0LjE3Njc2QzEwLjY5MSA0LjE3Njc2IDExLjI1MSA0LjczNjc2IDExLjI1MSA1LjQyNjc2Wk0xMC4wMDEgOC43NDk5M0M5LjMxMDE0IDguNzQ5OTMgOC43NTA5OCA5LjMwOTkzIDguNzUwOTggOS45OTk5M0M4Ljc1MDk4IDEwLjY5MDggOS4zMTAxNCAxMS4yNDk5IDEwLjAwMSAxMS4yNDk5QzEwLjY5MSAxMS4yNDk5IDExLjI1MSAxMC42OTA4IDExLjI1MSA5Ljk5OTkzQzExLjI1MSA5LjMwOTkzIDEwLjY5MSA4Ljc0OTkzIDEwLjAwMSA4Ljc0OTkzWk0xMC4wMDEgMTMuMzIzMUM5LjMxMDE0IDEzLjMyMzEgOC43NTA5OCAxMy44ODIzIDguNzUwOTggMTQuNTczMUM4Ljc1MDk4IDE1LjI2MzkgOS4zMTAxNCAxNS44MjMxIDEwLjAwMSAxNS44MjMxQzEwLjY5MSAxNS44MjMxIDExLjI1MSAxNS4yNjM5IDExLjI1MSAxNC41NzMxQzExLjI1MSAxMy44ODIzIDEwLjY5MSAxMy4zMjMxIDEwLjAwMSAxMy4zMjMxWiIgZmlsbD0iI0EwQTBBMCIvPgo8L3N2Zz4K" xmlns:xlink="http://www.w3.org/1999/xlink">
						<path fill-rule="evenodd" clip-rule="evenodd" d="M11.251 5.42676C11.251 6.11759 10.691 6.67676 10.001 6.67676C9.31014 6.67676 8.75098 6.11759 8.75098 5.42676C8.75098 4.73676 9.31014 4.17676 10.001 4.17676C10.691 4.17676 11.251 4.73676 11.251 5.42676ZM10.001 8.74993C9.31014 8.74993 8.75098 9.30993 8.75098 9.99993C8.75098 10.6908 9.31014 11.2499 10.001 11.2499C10.691 11.2499 11.251 10.6908 11.251 9.99993C11.251 9.30993 10.691 8.74993 10.001 8.74993ZM10.001 13.3231C9.31014 13.3231 8.75098 13.8823 8.75098 14.5731C8.75098 15.2639 9.31014 15.8231 10.001 15.8231C10.691 15.8231 11.251 15.2639 11.251 14.5731C11.251 13.8823 10.691 13.3231 10.001 13.3231Z" fill="#A0A0A0"></path></svg>
					</div>				
					<div class="replyDrop float--left" style="display: none;">
						<button id="modReplyBtn" class="replyBtn">수정</button>
						<button id="remReplyBtn" class="replyBtn">삭제</button>
					</div>
				</div>
			</c:if>
	
		</li>
	</c:forEach>
</c:if>
