/**
 * 
 */
 
 console.log("Comment Module....");
 
 // 즉시 실행함수 및 {} 이용해 객체 구성
 	// 즉시실행함수 : () 안에 메서드 선언하고 바깥쪽에서 바로 e실행해버림
 	// -> 함수 실행결과가 바깥쪽에 선언된 변수에 할당]
 	// ex) 메서드 리턴 결과로 name이라는 속성에 'AAA'라는 속성값 가진 객체가 commentService 객체에 할당되는 것

 var commentService = (function(){
 	
 	/*코멘트 등록 메서드 */
 	function add(comment, callback){
 		console.log("comment.............");
 		
 		// add 메서드 내에 ajax 이용해 post방식으로 호출하는 코드
 		$.ajax({
 			type: 'post',
 			url: '/comments/new',
 			data: JSON.stringify(comment),
 			contentType: "application/json; charset=utf-8",
 			success: function(result, status, xhr) {
 				if(callback) {
 					callback(result);
 
 				}	
 			},
 			error : function(xhr, status, err) {
 				if(err) {
 					error(err);
 					alert("e");
				}
 			} 		
 		})
 	} // add(comment, callback)
 	
 	
 	/*코멘트 목록 호출 메서드*/
 	function getList(param, callback, error) {
 		// param이라는 이름의 객체를 통해 필요한 파라미터 전달받도록 설계
 			// movieCd 이외 추후 추가적인 파라미터 더 필요한 경우 위함
 			
 			
 		var movieCd = param.movieCd;
 		var orderBy = param.orderBy;
 		var totalCnt = param.totalCnt;
 		
 		// tip) url 맨 앞에 / 빼면 http://localhost:8088/movieInfo/contents/20000006/comments.json 404 와 같이 현재 view url 에 이어져버림	
 		$.getJSON("/contents/" + movieCd + "/comments/" + orderBy +"/" + totalCnt + ".json",
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
 	
 	/*코멘트 추가적인 목록 호출 메서드*/
 	function getAdditionalList(param, callback, error) {
 		// param이라는 이름의 객체를 통해 필요한 파라미터 전달받도록 설계
 			// movieCd 이외 추후 추가적인 파라미터 더 필요한 경우 위함
 			
 			
 		var movieCd = param.movieCd;
 		var orderBy = param.orderBy;
 		var currentCnt = param.currentCnt;
 		var additionalCnt = param.additionalCnt;
 		
 		console.log(currentCnt);
 		console.log(additionalCnt);
 		
 		// tip) url 맨 앞에 / 빼면 http://localhost:8088/movieInfo/contents/20000006/comments.json 404 와 같이 현재 view url 에 이어져버림	
 		$.getJSON("/contents/" + movieCd + "/" + orderBy + "/" + currentCnt +"/" + additionalCnt + "/comments" + ".json",
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
 	}  // getAdditionalList(param, callback, error)
 	
 	$.ajaxSetup({ async: false }); // movieInfo.jsp 내 for문에서 비동기함수 동기식 처리 위해 설정
 	
 	
 	/*Ajax_특정 영화의 코멘트 리스트 조회 메서드 */
 	function ajax_getMovieCommentList(param, callback, error) {
		var movieCd = param.movieCd;
 		var orderBy = param.orderBy;
 		var currentCnt = param.currentCnt;
 		var additionalCnt = param.additionalCnt;
 		console.log(movieCd+  "/" + orderBy + "/" + currentCnt + "/" + additionalCnt);
 		$.ajax({
			url: "/ajax/movies/comments",
			type: "post",
			data: {movieCd: movieCd, orderBy: orderBy, currentCnt: currentCnt, additionalCnt: additionalCnt},
			beforeSend: function () {
				// console.log("로딩 중");
			},
			success: function(result, status, xhr) {
				 
				 $('.commentsList').append(result);
			
				if(callback) {
 				//	console.log(result);
 				} 
				
			},
			error : function(xhr, status, err) {
				console.log("err: " + err);
			} 		
			
		});	
 	}  // ajax_getMovieCommentList(param, callback, error)
 	
 	
 	
 	/* 특정 유저 코멘트 호출 메서드 */
 	function getUser(param, callback, error) {

 	var userid = param.userid;
 	var movieCd = param.movieCd;
 	
 	$.getJSON("/rest/comments/" + userid + "/" + movieCd + ".json",
 			function(commentUserDTO) {
 				if(callback) {
 					console.log('commentUserDTOa: ' + commentUserDTO);
 					callback(commentUserDTO);
 				} 
 			}
 		).fail(function(xhr, status, err) {
 		
			callback(); // DB에서 해당 comment 없는경우 null return
			
 			if(error) {
 				error();
 			}
 		});	
 	
 	
 	} // getUser(param, callback, error)
 	
//	$.ajaxSetup({ async: true }); // 여기서 true로 재 설정해봐야 의미없음. 각 메서드 내에서 설정할거면 ajax함수 이용할 것
 	
	/* 코멘트 삭제 */
		// DELETE 방식으로 데이터 전달하므로 type : delete 지정
	function remove(commentCd, callback, error) {
		$.ajax({
			type: 'delete', 
			url : '/rest/comments/' + commentCd,
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
	} // remove(commentCd, callback, error)
 	
 	
 	/* 코멘트 수정 */ 
 	function update(comment, callback, error) {
 		
 		console.log("commentCd: " + comment.commentCd);
 		
 		
 		$.ajax({
 			type: 'put',
 			url: '/rest/comments/' + comment.commentCd + ".json",
 			data: JSON.stringify(comment),
 			contentType: "application/json; charset=utf-8",
 			success : function(commentVO, status, xhr){
 				console.log("s commentVO: " + commentVO);
 				if(callback) {
	 				console.log("c commentVO: " + commentVO);
 					callback(commentVO);
 				}
			},
			error : function(xhr, status, err) {
				console.log("js. error: " + err);
				if(error) {
					error();
				}
			}
 		});
 	} // update(comment, callback, error)
 	
 	
 	/* 사용x) 특정 코멘트 조회 */
 	function get(commentCd, callback, error) {
 		
 		$.get("/rest/comments/" + commentCd +".json", function(result){
 			if(callback) {
 				callback(result);
 			}
	 	}).fail(function(xhr, status, err) {
	 		callback(); // DB에서 해당 comment 없는경우 null return
	 		
	 		if(error) {
	 			error();
	 		}
	 	});
 	}
 	
 	/*Ajax_특정 코멘트 조회 메서드 */
 	function ajax_get(commentCd, callback, error) {
	

 		$.ajax({
			url: "/ajax/comments/commentInfo",
			type: "post",
			data: {commentCd: commentCd},
			beforeSend: function () {
				// console.log("로딩 중");
			},
			success: function(result, status, xhr) {
				 
				 $('.commentInfoMainDiv').html(result);
			
				
			},
			error : function(xhr, status, err) {
				console.log(err);
			} 		
			
		});	
 	}  // ajax_get(commentCd, callback, error)
 	
 	
 	/* 코멘트 좋아요 개수 갱신 */
 	function getLikeCnt(commentCd, callback, error) {
 	
 		$.getJSON("/rest/comments/getLikeCnt/" + commentCd + ".json",
 			function(likeCnt) {
 				if(callback) {
 					console.log('likeCnt: ' + likeCnt);
 					callback(likeCnt);
 				} 
 			}
 		).fail(function(xhr, status, err) {
 		
			callback(); // DB에서 해당 comment 없는경우 null return
			
 			if(error) {
 				error();
 			}
 		});	
 	
 	
 	} // getLikeCnt(commentCd, callback, error)
 	
 	/* 코멘트 댓글 개수 갱신 */
 	function getReplyCnt(commentCd, callback, error) {
 	
 		$.getJSON("/rest/comments/getReplyCnt/" + commentCd + ".json",
 			function(replyCnt) {
 				if(callback) {
 					console.log('replyCnt: ' + replyCnt);
 					callback(replyCnt);
 				} 
 			}
 		).fail(function(xhr, status, err) {
 		
			callback(); // DB에서 해당 comment 없는경우 null return
			
 			if(error) {
 				error();
 			}
 		});	
 	
 	
 	} // getReplyCnt(commentCd, callback, error)
 	
 	
 	
/* --------------유저 페이지---------------- */
/*Ajax_유저 작성한 코멘트 리스트 조회 메서드 */
 	function ajax_getUserCommentList(param, callback, error) {
	
 		var randomString = param.randomString;
 		var orderBy = param.orderBy;
 		var currentSqnc = param.currentSqnc;
 		var additionalCnt = param.additionalCnt;

 		$.ajax({
			url: "/ajax/users/comments",
			type: "post",
			data: {randomString: randomString, orderBy: orderBy, currentSqnc:currentSqnc, additionalCnt:additionalCnt},
			beforeSend: function () {
				// console.log("로딩 중");
				_scrollchk = true; // 변수 값 변경. 제대로 작동하지 않는 것 같음..
			},
			success: function(result, status, xhr) {
			// console.log(result);
				_returnListCnt = result.split('commentLi').length -1;
			 console.log(".js _returnListCnt: " + _returnListCnt);
			 
				_scrollchk = false;
				 
				 $('.commentsList').append(result);
			
				
			},
			error : function(xhr, status, err) {
				console.log(err);
			} 		
			
		});	
 	}  // ajax_getUserCommentList(param, callback, error)
 	
 	/*Ajax_csrf_유저 작성한 코멘트 리스트 조회 메서드 */
 	function ajax_csrf_getUserCommentList(param, callback, error) {
	
 		var randomString = param.randomString;
 		var orderBy = param.orderBy;
 		var currentSqnc = param.currentSqnc;
 		var additionalCnt = param.additionalCnt;
 		var csrfHeaderName = param.csrfHeaderName;
		var csrfTokenValue = param.csrfTokenValue;
		console.log("A");
 		$.ajax({
			url: "/ajax/users/comments",
			type: "post",
			data: {randomString: randomString, orderBy: orderBy, currentSqnc:currentSqnc, additionalCnt:additionalCnt},
			beforeSend : function(xhr){
				xhr.setRequestHeader(csrfHeaderName, csrfTokenValue);
			},
			success: function(result, status, xhr) {
			// console.log(result);
				
				if(additionalCnt == 10) {
					console.log("additionalCnt: " + additionalCnt);
					$('.commentsList').append(result);
				} else {
					console.log("additionalCnt: " + additionalCnt);
					$('.commentsList').append(result);
				}
			},
			error : function(xhr, status, err) {
				console.log(err);
			} 		
			
		});	
 	}  // ajax_getUserCommentList(param, callback, error)
/* --------------유저 페이지---------------- */



 	
 	// return {name:"AAA"};
 	return {add: add,
 			getList: getList,
 			getAdditionalList: getAdditionalList,
 			getUser: getUser,
 			remove: remove,
 			update: update,
 			get: get,
 			getLikeCnt: getLikeCnt,
 			getReplyCnt: getReplyCnt,
 			
 			ajax_getMovieCommentList: ajax_getMovieCommentList,
 			ajax_getUserCommentList: ajax_getUserCommentList,
 			ajax_csrf_getUserCommentList: ajax_csrf_getUserCommentList
 			
	}; // add라는 변수명에 add 메서드 결과가 들어가는 형태
 	
 	
 	
 	
 })();
 
 