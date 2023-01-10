/**
 * 
 */
 
  console.log("Movie Module....");
  
  var movieService = (function(){
 	
 	
 	/** 사용x) 평가하기 추가적인 목록 호출 메서드*/
 	function getReviewList(param, callback, error) {
 		// param이라는 이름의 객체를 통해 필요한 파라미터 전달받도록 설계
 			// movieCd 이외 추후 추가적인 파라미터 더 필요한 경우 위함
 			
 			
 		var userid = param.userid;
 		var orderBy = param.orderBy;
 		
 		// tip) url 맨 앞에 / 빼면 http://localhost:8088/movieInfo/contents/20000006/comments.json 404 와 같이 현재 view url 에 이어져버림	
 		$.getJSON("/rest/review/" + userid + "/" + orderBy + ".json",
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
 	}  // getReviewList(param, callback, error)
 	
 	
/* --------------Ajax ---------------- */
 	/** Ajax_평가하기_영화 리스트 */
	function ajax_getReviewList(param, callback, error){
		var orderBy = param.orderBy;
		var orderBy1 = param.orderBy1;
		var orderBy2 = param.orderBy2;
		var currentSqnc = param.currentSqnc;
		var additionalCnt = param.additionalCnt;
		
	 	$.ajax({
			url: "/ajax/review/movies",
			type: "post",
			data: {orderBy: orderBy, orderBy1: orderBy1, orderBy2: orderBy2, currentSqnc: currentSqnc, additionalCnt: additionalCnt},
		
				success: function(result, status, xhr) {
					// console.log(result);
					
					callback(result);
					
					$('#reviewList').append(result);
				},
				error : function(xhr, status, err) {
					console.log(err);
				} 		
			
		});
		
	}
 	
/*Ajax_유저 평가한 영화 리스트 조회 메서드 */
 	function ajax_getUserMovieList(param, callback, error) {
	
 		var randomString = param.randomString;
 		var orderBy = param.orderBy;
 		var currentSqnc = param.currentSqnc;
 		var additionalCnt = param.additionalCnt;

 		$.ajax({
			url: "/ajax/users/movies",
			type: "post",
			data: {randomString: randomString, orderBy: orderBy, currentSqnc:currentSqnc, additionalCnt:additionalCnt},
			beforeSend: function () {
				// console.log("로딩 중");
				_scrollchk = true; // 변수 값 변경. 제대로 작동하지 않는 것 같음..
			},
			success: function(result, status, xhr) {
			// console.log(result);
				_returnListCnt = result.split('movieLi').length -1;
			 console.log(".js _returnListCnt: " + _returnListCnt);
			 
				_scrollchk = false;
				 
				 $('.movieList').append(result);
			
				
			},
			error : function(xhr, status, err) {
				console.log(err);
			} 		
			
		});	
 	}  // ajax_getUserMovieList(param, callback, error)
 	
 	
/* --------------Ajax---------------- */
 	
 	
 	
 	// return {name:"AAA"};
 	return {
 			getReviewList: getReviewList,
 			
 			ajax_getReviewList: ajax_getReviewList,
 			ajax_getUserMovieList: ajax_getUserMovieList
 
	}; // add라는 변수명에 add 메서드 결과가 들어가는 형태
 	
 	
 	
 	
 })();