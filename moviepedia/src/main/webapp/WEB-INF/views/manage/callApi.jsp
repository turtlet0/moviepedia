<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ include file="../includes/header.jsp" %>

<main>
	<div class = "section" >
		<div class = "center">
			<div class = "inner">
			
				<!-- currentPage <= 20
					for(int i=1; i<=1; i++) > 1
					0 -> 정상실행되나 반복문 돌지않음
					2 -> 1, 2
					
				// 선택 요청 정보 지정
				String curPage = "1"; // 현재 페이지를 지정 default : “1”
				String itemPerPage = "100"; // 출력 결과 Row의 개수 / default : 10 / Max : 100
				String movieNm = null; // 영화명 조회 (UTF-8 인코딩)
				String directorNm = null; // 감독명 조희 (UTF-8 인코딩)
				String openStartDt = "2022"; // YYYY형식의 조회시작 개봉연도
				String openEndDt = "2022"; // YYYY형식의 조회종료 개봉연도
				String prdtStartYear = null; // YYYY형식의 조회시작 제작연도
				String prdtEndYear = null; // YYYY형식의 조회종료 제작연도
				String repNationCd = null; // 영화 국적. 국적코드는 공통코드 조회 서비스에서 “2204” 로서 조회된 국적코드입니다. (default : 전체)
				String movieTypeCd = null; // 영화 유형 코드. 공통코드 조회 서비스에서 “2201”로서 조회된 영화유형코드입니다.(default: 전체) -->
				<h2>영화&인물 정보 API 호출</h2>
				<section id="callApiMoviePeople"  class="padding--h5 border-box margin--bottom20">
					
					
					<label for="curPageStart">시작 페이지</label>
					<select id="curPageStart" name="curPageStart">
						<c:forEach begin="1" end="30" var="num">
							<option value="${num }">${num }</option>
						</c:forEach>
					</select>
					
					<label for="curPageFinish">종료 페이지</label>
					<select id="curPageFinish" name="curPageFinish">
						<c:forEach begin="1" end="30" var="num">
							<option value="${num }">${num }</option>
						</c:forEach>
					</select>
					
					<label for="itemPerPage">출력 행 수/페이지</label>
					<input name="itemPerPage" type="number" min="1" max="100" value="100">
					
					<label for="openStartDt">조회 시작 개봉연도</label>
					<select id="openStartDt" name="openStartDt">
						<c:forEach begin="0" end="35" var="num">
							<option value="${2022 - num }">${2022 - num }</option>
						</c:forEach>
					</select>
					
					<label for="openEndDt">조회 종료 개봉연도</label>
					<select id="openEndDt" name="openEndDt">
						<c:forEach begin="0" end="35" var="num">
							<option value="${2022 - num }">${2022 - num }</option>
						</c:forEach>
					</select>
									
					<button style="background-color: rgb(242,242,242)" id="callApiMoviePeopleBtn" class="border-box">호출</button>
				
				</section>
		
				<section>
					<table id="resultListTable">
						<thead style="height: 24px; font-weight: bold">
							<tr>
								<td>호출정보명</td>
								<td>조회 페이지</td>
								<td>출력 행 수</td>
								<td>시작 개봉연도</td>
								<td>종료 개봉연도</td>
								<td>영화 저장 개수</td>
								<td>인물 저장 개수</td>
							</tr>	
						</thead>
						<tbody>
							
						</tbody>
					</table>
				</section>
	
			
			</div>
		</div>
	</div>
</main>

<script type="text/javascript">
$(document).ready(function(){
	
	// 조회 페이지 select 시
	$("#curPageStart").off("change").on("change", function(){

		var curPageStartValue = $(this).val();
		
		$('#curPageFinish').val(curPageStartValue).prop("selected", true);
	});
	
	// 조회 시작 개봉연도 select 시
	$("#openStartDt").off("change").on("change", function(){

		var openStartDtValue = $(this).val();
		
		$('#openEndDt').val(openStartDtValue).prop("selected", true);
	});
	
	// 호출 버튼 클릭
	$("#callApiMoviePeopleBtn").off("click").on("click", function(){
		
		$(this).attr("disabled", true);
		$(this).css("color","red !important");
		
		console.log("click");
		
		var curPageStart = $("#callApiMoviePeople > select[name='curPageStart']").val();
		var curPageFinish = $("#callApiMoviePeople > select[name='curPageFinish']").val();
		var itemPerPage = $("#callApiMoviePeople > input[name='itemPerPage']").val();
		var openStartDt = $("#callApiMoviePeople > select[name='openStartDt']").val();
		var openEndDt = $("#callApiMoviePeople > select[name='openEndDt']").val();
		
		console.log(curPageStart +"/"+curPageFinish+"/"+itemPerPage+"/"+openStartDt+"/"+openEndDt);
		
		manageService.callApiMoviePeople({
			curPageStart: curPageStart,
			curPageFinish: curPageFinish,
			itemPerPage: itemPerPage,
			openStartDt: openStartDt,
			openEndDt: openEndDt
			
		}, function(resultList){
			console.log(resultList);
			
			$("#callApiMoviePeopleBtn").attr("disabled", false); // $(this) 불가
			$("#callApiMoviePeopleBtn").css("color","green !important");
			
			var html = "";
			for(let i=0, len=resultList.length||0; i<len; i++) {
				html += "<tr><td>"+resultList[i].name+"</td>" +
					"<td>"+resultList[i].curPage+"</td>"+
					"<td>"+resultList[i].itemPerPage+"</td>"+
					"<td>"+resultList[i].openStartDt+"</td>"+
					"<td>"+resultList[i].openEndDt+"</td>"+
					"<td>"+resultList[i].movieTotalRegisterCnt+"</td>"+
					"<td>"+resultList[i].peopleTotalRegisterCnt+"</td><tr>";
				
			}
			$("#resultListTable").children("tbody").append(html);
			
			alert("호출 완료");
			
		});
	});
	
	
	
	
});
</script>



