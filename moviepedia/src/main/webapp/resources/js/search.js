/**
 * 
 */
 
 console.log("Search Module....");
 
 // 즉시 실행함수 및 {} 이용해 객체 구성
 	// 즉시실행함수 : () 안에 메서드 선언하고 바깥쪽에서 바로 e실행해버림
 	// -> 함수 실행결과가 바깥쪽에 선언된 변수에 할당]
 	// ex) 메서드 리턴 결과로 name이라는 속성에 'AAA'라는 속성값 가진 객체가 commentService 객체에 할당되는 것

 var searchService = (function(){
 	

 	/* 검색창 연관 검색어 출력 */
 	function getRelatedQuery(param, callback, error) {
 		// param이라는 이름의 객체를 통해 필요한 파라미터 전달받도록 설계
		
 		var query = param.query;

 		// tip) url 맨 앞에 / 빼면 http://localhost:8088/movieInfo/contents/20000006/comments.json 404 와 같이 현재 view url 에 이어져버림	
 		$.getJSON("/rest/search/related/" + query + ".json",
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
 	}  // getRelatedQuery(param, callback, error)
 	
 	
 	/* 검색_영화 리스트 */
 	function getMovieList(param, callback, error) {
 		// param이라는 이름의 객체를 통해 필요한 파라미터 전달받도록 설계
		
 		var query = param.query;

 		// tip) url 맨 앞에 / 빼면 http://localhost:8088/movieInfo/contents/20000006/comments.json 404 와 같이 현재 view url 에 이어져버림	
 		$.getJSON("/rest/search/movie/" + query + ".json",
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
 	}  // getMovieList(param, callback, error)
 	
 	/* 검색_영화인 리스트 */
 	function getPeopleList(param, callback, error) {
 		// param이라는 이름의 객체를 통해 필요한 파라미터 전달받도록 설계

 		var query = param.query;

 		// tip) url 맨 앞에 / 빼면 http://localhost:8088/movieInfo/contents/20000006/comments.json 404 와 같이 현재 view url 에 이어져버림	
 		$.getJSON("/rest/search/people/" + query + ".json",
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
 	}  // getPeopleList(param, callback, error)
 	
/* ------------ Ajax -------------- */

	/* 사용x-mapper쿼리개선) Ajax_검색_영화 리스트 */
	/*
	function ajax_getMovieList(param){
		var query = param.query;
		var currentActorCnt = param.currentActorCnt;
		var currentMovieNmDirectorCnt = param.currentMovieNmDirectorCnt;
		
	 	$.ajax({
			url: "/ajax/search/movie",
			type: "post",
			data: {query: query,
					currentActorCnt: currentActorCnt,
					currentMovieNmDirectorCnt: currentMovieNmDirectorCnt},
		
				success: function(result, status, xhr) {
					// console.log(result);
			
					$('#movieList').children('section').children('ul').append(result);
					
				
				},
				error : function(xhr, status, err) {
					console.log(err);
				} 		
			
		});
	}
	*/
	
	/* Ajax_검색_영화 리스트 */
	function ajax_getMovieList(param, callback, error){
		var query = param.query;
		var currentSqnc = param.currentSqnc;
		var additionalCnt = param.additionalCnt;
		
	 	$.ajax({
			url: "/ajax/search/movie",
			type: "post",
			data: {query: query, currentSqnc: currentSqnc, additionalCnt: additionalCnt},
		
				success: function(result, status, xhr) {
					// console.log(result);
					
					callback(result);
					
					$('#movieList').children('section').children('ul').append(result);
				},
				error : function(xhr, status, err) {
					console.log(err);
				} 		
			
		});
		
	}
	
	/* Ajax_검색_인물 리스트 */
	function ajax_getPeopleList(param, callback, error){
		var query = param.query;
		var currentSqnc = param.currentSqnc;
		var additionalCnt = param.additionalCnt;
		
	 	$.ajax({
			url: "/ajax/search/people",
			type: "post",
			data: {query: query, currentSqnc: currentSqnc, additionalCnt: additionalCnt},
		
				success: function(result, status, xhr) {
					// console.log(result);
					
					callback(result);
					
					$('#peopleList').children('section').children('ul').append(result);
				},
				error : function(xhr, status, err) {
					console.log(err);
				} 		
			
		});
		
	}
	
	
	/* Ajax_검색_유저 리스트 */
	function ajax_getUserList(param, callback, error){
		var query = param.query;
		var currentSqnc = param.currentSqnc;
		var additionalCnt = param.additionalCnt;
		
	 	$.ajax({
			url: "/ajax/search/user",
			type: "post",
			data: {query: query, currentSqnc: currentSqnc, additionalCnt: additionalCnt},
		
				success: function(result, status, xhr) {
					// console.log(result);
					
					callback(result);
					
					$('#userList').children('section').children('ul').append(result);
				},
				error : function(xhr, status, err) {
					console.log(err);
				} 		
			
		});
		
	}
	
	
/* ------------ Ajax -------------- */
 	
 	// return {name:"AAA"};
 	return {getRelatedQuery: getRelatedQuery,
 			getMovieList: getMovieList,
 			getPeopleList: getPeopleList,
 			ajax_getMovieList: ajax_getMovieList,
 			ajax_getPeopleList: ajax_getPeopleList,
 			ajax_getUserList: ajax_getUserList
	}; // add라는 변수명에 add 메서드 결과가 들어가는 형태
 	
 	
 	
 	
 })();
 
 