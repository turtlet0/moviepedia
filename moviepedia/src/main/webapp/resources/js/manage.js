/**
 * 
 */
 
  console.log("Manage Module....");
  
  var manageService = (function(){
 	
 	
 	/* 영화&인물 정보 API 호출 메서드 */
 	function callApiMoviePeople(param, callback, error) {
 		// param이라는 이름의 객체를 통해 필요한 파라미터 전달받도록 설계
 			// movieCd 이외 추후 추가적인 파라미터 더 필요한 경우 위함
 			
 			
 		var curPageStart = param.curPageStart;
 		var curPageFinish = param.curPageFinish;
 		var itemPerPage = param.itemPerPage;
 		var openStartDt = param.openStartDt;
 		var openEndDt = param.openEndDt;
 		
 		// tip) url 맨 앞에 / 빼면 http://localhost:8088/movieInfo/contents/20000006/comments.json 404 와 같이 현재 view url 에 이어져버림	
 		$.getJSON("/rest/manage/callApi/moviePeople/" + curPageStart + "/" + curPageFinish + "/" + itemPerPage + "/" + openStartDt + "/" + openEndDt + ".json",
 			function(data) {
 				if(callback) {
 					callback(data);
 				}
 			}
 		).fail(function(xhr, status, err) {
 			if(error) {
 				error();
 			}
 		});		
 	}  // callApiMoviePeople(param, callback, error)
 	
 	
 	
 	
 	
 	
 	// return {name:"AAA"};
 	return {
 			callApiMoviePeople: callApiMoviePeople
 
	}; // add라는 변수명에 add 메서드 결과가 들어가는 형태
 	
 	
 	
 	
 })();