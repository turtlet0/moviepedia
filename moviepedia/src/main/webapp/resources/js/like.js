/**
 * 
 */
 
 console.log("Like Module....");
 
 // 즉시 실행함수 및 {} 이용해 객체 구성
 	// 즉시실행함수 : () 안에 메서드 선언하고 바깥쪽에서 바로 e실행해버림
 	// -> 함수 실행결과가 바깥쪽에 선언된 변수에 할당]
 	// ex) 메서드 리턴 결과로 name이라는 속성에 'AAA'라는 속성값 가진 객체가 commentService 객체에 할당되는 것

	   	
 var likeService = (function(){
 	
 	/* csrf_좋아요 등록 메서드 */
 	function add(like, callback){
 		console.log("like.............");
 		
 		// add 메서드 내에 ajax 이용해 post방식으로 호출하는 코드
 		$.ajax({
 			type: 'post',
 			url: '/rest/likes/new',
 			data: JSON.stringify(like),
 			contentType: "application/json; charset=utf-8",
 			success: function(result, status, xhr) {
 				console.log(result);
 				if(result){
 					console.log("성공");
				} else {
					console.log("전송값 없음");
				}
 				if(callback) {
 					
 					callback(result);
 				}	
 			},
 			error : function(xhr, status, err) {
 				console.log("error: " + err);
 				if(err) {
 					error(err);
 					
				}
 			} 		
 		})
 	} // add(reply, callback)
 	
 	/* csrf_좋아요 등록 메서드 */
 	function csrf_add(like, callback){
 		console.log("like.............");
 		
 		// add 메서드 내에 ajax 이용해 post방식으로 호출하는 코드
 		$.ajax({
 			type: 'post',
 			url: '/rest/likes/new',
 			data: JSON.stringify(like),
 			contentType: "application/json; charset=utf-8",
		 	beforeSend : function(xhr){
				xhr.setRequestHeader(like.csrfHeaderName, like.csrfTokenValue);
			},
 			success: function(result, status, xhr) {
 				if(result){
 					console.log("성공");
				} else {
					console.log("전송값 없음");
				}
 				if(callback) {
 					
 					callback(result);
 				}	
 			},
 			error : function(xhr, status, err) {
 				if(err) {
 					error(err);
 					console.log("e");
				}
 			} 		
 		})
 	} // csrf_add(reply, callback)
 	
 	
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
 	
 	
 	/* 유저의 특정 코멘트 좋아요 호출 메서드 */
 	
 	function getUser(param, callback, error) {
 	
	 	var userid = param.userid;
	 	var commentCd = param.commentCd;
	 	
	 	$.getJSON("/likes/" + userid + "/" + commentCd + ".json",
 			function(likeVO) {
 				if(callback) {
 					callback(likeVO);
 				} 
 			}
 		).fail(function(xhr, status, err) {
 		
 				callback(); // DB에서 해당 like 없는경우 null return
 				
 			if(error) {
 				error();
 			}
 		});	
 	
 	
 	} // getUser(param, callback, error)
 	
 	
	/* 좋아요 삭제 */
		// DELETE 방식으로 데이터 전달하므로 type : delete 지정
	function remove(like, callback, error) {

		
		$.ajax({
			type: 'delete', 
			url : '/rest/likes/delete',
			data: JSON.stringify(like),
 			contentType: "application/json; charset=utf-8",
			success : function(deleteResult, status, xhr) {
				if(callback) {
					callback(deleteResult);
				}
			},
			error: function(xhr, status, err) {
				if(error) {
					console.log("error: " + error);
					error();
				}
			}
		});
	} // remove(likeCd, callback, error)
	
	/* csrf_좋아요 삭제 */
		// DELETE 방식으로 데이터 전달하므로 type : delete 지정
	function csrf_remove(param, callback, error) {
		var likeCd = param.likeCd;
		var csrfHeaderName = param.csrfHeaderName;
		var csrfTokenValue = param.csrfTokenValue;
		
		$.ajax({
			type: 'delete', 
			url : '/likes/' + likeCd,
			beforeSend : function(xhr){
				xhr.setRequestHeader(csrfHeaderName, csrfTokenValue);
			},
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
	} // csrf_remove(likeCd, callback, error)
 	
 	
 	
 	
 	/* 특정 좋아요 조회 - likeCd */
 	function get(likeCd, callback, error) {
 	
 		$.get("/likes/" + likeCd +".json", function(result){
 			if(callback) {
 				callback(result);
 			}
	 	}).fail(function(xhr, status, err) {
	 		if(error) {
	 			error();
	 		}
	 	});
 	}

 	/* 좋아요 개수 조회 메서드 */
 	function getCntByCommentCd(commentCd, callback, error) {
	 	
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
 	
 	// return {name:"AAA"};
 	return {add: add,
 			remove: remove,
 			get: get,
 			getUser: getUser,
 			getCntByCommentCd : getCntByCommentCd
 			
	}; // add라는 변수명에 add 메서드 결과가 들어가는 형태
 	
 	
 	
 	
 })();
 
 