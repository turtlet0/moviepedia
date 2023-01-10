/**
 * 
 */
 
 console.log("StarRating Module....");
 
 // 즉시 실행함수 및 {} 이용해 객체 구성
 	// 즉시실행함수 : () 안에 메서드 선언하고 바깥쪽에서 바로 e실행해버림
 	// -> 함수 실행결과가 바깥쪽에 선언된 변수에 할당]
 	// ex) 메서드 리턴 결과로 name이라는 속성에 'AAA'라는 속성값 가진 객체가 commentService 객체에 할당되는 것

 var starRatingService = (function(){
 	
 	/* 좋아요 등록 메서드 */
 	function add(starRating, callback){
 		console.log("starRating.............");
 		console.log(starRating);
 		// add 메서드 내에 ajax 이용해 post방식으로 호출하는 코드
 		$.ajax({
 			type: 'post',
 			url: '/starRating/new',
 			data: JSON.stringify(starRating),
 			contentType: "application/json; charset=utf-8",
 			success: function(result, status, xhr) {
 				if(callback) {
 					callback(result);
 				}	
 			},
 			error : function(xhr, status, err) {
 				if(err) {
 					error(err);
 					console.log(err);
 					console.log(xhr);
 					alert("e");
				}
 			} 		
 		})
 	} // add(starRating, callback)
 	
 	
 	/* 특정 코멘트의 댓글 목록 호출 메서드*/
 	/* function getList(param, callback, error) {
 		// param이라는 이름의 객체를 통해 필요한 파라미터 전달받도록 설계
 			// movieCd 이외 추후 추가적인 파라미터 더 필요한 경우 위함
 			
 			
 		var commentCd = param.commentCd;
 		
 		$.getJSON("/comments/" + commentCd + "/replies" + ".json",
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
 	}  // getList(param, callback, error)
 	*/
 	
	/* 별점 조회 - userid, movieCd */
 	function get(param, callback, error) {
 	
 		var userid = param.userid;
 		var movieCd = param.movieCd;
 		
 		$.get("/starRating/" + userid +"/"+ movieCd +".json", function(result){
 			if(callback) {
 				callback(result);
 			}
	 	}).fail(function(xhr, status, err) {
	 		if(error) {
	 			error();
	 		}
	 	});
 	}
 	
 	
	/* 별점 삭제 */
		// DELETE 방식으로 데이터 전달하므로 type : delete 지정
	function remove(param, callback, error) {
		var userid = param.userid;
 		var movieCd = param.movieCd;
 		
		$.ajax({
			type: 'delete', 
			url : '/starRating/' + userid +'/'+ movieCd,
			success : function(deleteResult, status, xhr) {
				if(callback) {
					callback(deleteResult);
				}
			},
			error: function(xhr, status, err) {
				if(error) {
					error();
				}
			}
		});
	} // remove(param, callback, error)
 	
 	/* 별점 수정 */ 
 	function update(starRating, callback, error) {

 		console.log("starRating: " + starRating);
 		
 		
 		$.ajax({
 			type: 'put',
 			url: '/starRating/' + starRating.userid +'/'+ starRating.movieCd,
 			data: JSON.stringify(starRating),
 			contentType: "application/json; charset=utf-8",
 			success : function(result, status, xhr){
 				if(callback) {
 					callback(result);
 				}
			},
			error : function(xhr, status, err) {
				if(error) {
					error();
				}
			}
 		});
 	} // update(starRating, callback, error)
 	
 	
 
	
 	/* 좋아요 개수 조회 메서드 */
 	/* function getCntByCommentCd(commentCd, callback, error) {
 		 	
 		 	$.getJSON("/likes/count/" + commentCd + ".json",
 			function(data) {
 				if(callback) {
 					callback(data);
 				} 
 			}
 		).fail(function(xhr, status, err) {
 		
 				callback(); 
 				
 			if(error) {
 				error();
 			}
 		});	
 	
 	
 	} // getCntByCommentCd(commentCd, callback, error)
 	*/
 	
 	// return {name:"AAA"};
 	return {add: add,
 			remove: remove,
 			get: get,
 			update: update

 			
	}; // add라는 변수명에 add 메서드 결과가 들어가는 형태
 	
 	
 	
 	
 })();
 
 