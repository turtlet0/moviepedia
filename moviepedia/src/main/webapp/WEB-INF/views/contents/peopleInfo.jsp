<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    

<%@ include file="../includes/header.jsp" %>


<main>
	<div class = "section" >
		<div class = "center">
			<div class = "innerFix">
			
			<div>
				<c:choose>
					<c:when test="${peopleInfoDirectorMovieList ne null}">
						<h2>${peopleInfoDirectorMovieList.peopleNm }</h2>
						<div class="margin--bottom5">${peopleInfoDirectorMovieList.peopleNmEn }</div>
						<div>${peopleInfoDirectorMovieList.repRoleNm }</div>
					</c:when>
					<c:when test="${peopleInfoDirectorMovieList eq null}">
						<h2>${peopleInfoActorMovieList.peopleNm }</h2>
						<div class="margin--bottom5">${peopleInfoActorMovieList.peopleNmEn }</div>
						<div>${peopleInfoActorMovieList.repRoleNm }</div>
					</c:when>
				</c:choose>
				
			</div>
			
			<hr>
			<c:if test="${peopleInfoActorMovieList ne null }">
			
				<div id="actorSMovieList">
					<h3>출연</h3>
					<ul>
						<c:forEach var="movieWithStarDTO" items="${peopleInfoActorMovieList.movieWithStarDTOList }">
	<%-- 						<c:set var="movieVO" value="${movieWithStarDTO.movieVO }"/> --%>
							<li>
								<a href="/movieInfo/${movieWithStarDTO.movieCd }">
									<div class="peopleSMovie clearfix">							
										<div class="peopleSMoviePoster img-border float--left margin--right10">
											<img  alt="<c:out value="${movieWithStarDTO.movieNm }"/> 포스터" src="<c:out value="${movieWithStarDTO.posterUrl eq null || movieWithStarDTO.posterUrl eq ' ' ? '/resources/img/poster-not-available.jpg' : movieWithStarDTO.posterUrl}" />">
										</div>
										<div class="peopleSMovieInfo float--left margin--top5">
											<div class="font--bold margin--bottom10">${movieWithStarDTO.movieNm }</div>
											<div class="margin--bottom5"><c:out value="${movieWithStarDTO.prdtYear }" /> • <c:out value="${movieWithStarDTO.repGenreNm }" /> • <c:out value="${movieWithStarDTO.repNationNm }" /></div>
											<!-- 평점 -->
											<c:choose>
												<c:when test="${movieWithStarDTO.userStarRating eq 0.0 }">	
														<div style="color:red">평균★${movieWithStarDTO.avgStarRating}</div>
												</c:when>
												<c:when test="${movieWithStarDTO.userStarRating ne 0.0 }">
													<div style="color:orange">평가함★	${movieWithStarDTO.userStarRating}</div>
												</c:when>
											</c:choose>
										</div>
									</div>
								</a>
							</li>		
						</c:forEach>
					</ul>
				</div>
			</c:if>
			
			<hr id="hrForMovieList">
			
			
			<c:if test="${peopleInfoDirectorMovieList ne null }">
			
				<div id="directorSMovieList">
					<h3>감독</h3>
					<ul>
						<c:forEach var="movieWithStarDTO" items="${peopleInfoDirectorMovieList.movieWithStarDTOList }">
	<%-- 						<c:set var="movieVO" value="${movieWithStarDTO.movieVO }"/> --%>
							<li>
								<a href="/movieInfo/${movieWithStarDTO.movieCd }">
									<div class="peopleSMovie clearfix">							
										<div class="peopleSMoviePoster img-border float--left margin--right10">
											<img  alt="<c:out value="${movieWithStarDTO.movieNm }"/> 포스터" src="<c:out value="${movieWithStarDTO.posterUrl eq null || movieWithStarDTO.posterUrl eq ' ' ? '/resources/img/poster-not-available.jpg' : movieWithStarDTO.posterUrl}" />">
										</div>
										<div  class="peopleSMovieInfo float--left margin--top5">
											<div  class="font--bold margin--bottom10">${movieWithStarDTO.movieNm }</div>
											<div  class="margin--bottom5"><c:out value="${movieWithStarDTO.prdtYear }" /> • <c:out value="${movieWithStarDTO.repGenreNm }" /> • <c:out value="${movieWithStarDTO.repNationNm }" /></div>
											<!-- 평점 -->
											<c:choose>
												<c:when test="${movieWithStarDTO.userStarRating eq 0.0 }">	
														<div style="color:red">평균★${movieWithStarDTO.avgStarRating}</div>
												</c:when>
												<c:when test="${movieWithStarDTO.userStarRating ne 0.0 }">
													<div style="color:orange">평가함★	${movieWithStarDTO.userStarRating}</div>
												</c:when>
											</c:choose>
										</div>
									</div>
								</a>
							</li>		
						</c:forEach>
					</ul>
				</div>
			</c:if>
			
			
	
				
			</div>			
		</div>
	</div>
</main>


<script type="text/javascript">
$(document).ready(function(){
	var result = '<c:out value="${result }" />';
	if(result != ''){
		alert(result);
	}
	
	/* 감독/ 출연 영화리스트 출력 순서 */
	var repRoleNm = '${peopleInfoDirectorMovieList.repRoleNm }';
	console.log(repRoleNm);
	if(repRoleNm == '감독'){
		$('#hrForMovieList').insertAfter('#directorSMovieList');
		$('#actorSMovieList').insertAfter('#hrForMovieList');
	}
	
	
});
</script>	
	
	

