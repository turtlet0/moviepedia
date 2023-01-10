/**
 * 
 */
 
 console.log("Reply Module....");
 
 // 즉시 실행함수 및 {} 이용해 객체 구성
 	// 즉시실행함수 : () 안에 메서드 선언하고 바깥쪽에서 바로 e실행해버림
 	// -> 함수 실행결과가 바깥쪽에 선언된 변수에 할당]
 	// ex) 메서드 리턴 결과로 name이라는 속성에 'AAA'라는 속성값 가진 객체가 commentService 객체에 할당되는 것

 var replyService = (function(){
 	
 	/* 댓글 등록 메서드 */
 	function add(reply, callback){
 		console.log("reply.............");
 		
 		// add 메서드 내에 ajax 이용해 post방식으로 호출하는 코드
 		$.ajax({
 			type: 'post',
 			url: '/replies/new.json',
 			data: JSON.stringify(reply),
 			contentType: "application/json; charset=utf-8",
 			success: function(replyCd, status, xhr) {
 				if(callback) {
 					console.log('success');
 					callback(replyCd);
 				}	
 			},
 			error : function(xhr, status, err) {
 				if(err) {
 					error(err);
 					alert("e");
				}
 			} 		
 		})
 	} // add(reply, callback)
 	
 	
 	/* 특정 댓글 조회 */
 	function get(replyCd, callback, error) {
 
 		$.getJSON("/replies/" + replyCd +".json", 
 			function(ReplyListDTO){
	 			if(callback) {
 					// callback(ReplyListDTO);
 					callback(ReplyListDTO.replyList, ReplyListDTO.replyCnt); // replyListDTO 이용해 한번에 받기
 				}
	 	}).fail(function(xhr, status, err) {
	 		if(error) {
	 			error();
	 		}
	 	});
 	}
 	
 	
 	/*Ajax_특정 코멘트의 댓글 목록 호출 메서드 */
 	function ajax_getCommentReplyList(param, callback, error) {
		var commentCd = param.commentCd;
		var crudIdx = param.crudIdx;
		var replyCd = param.replyCd;
		
 		$.ajax({
			url: "/ajax/comments/replies",
			type: "post",
			data: {commentCd: commentCd, crudIdx: crudIdx, replyCd: replyCd},
			beforeSend: function () {
				// console.log("로딩 중");
				
			},
			success: function(result, status, xhr) {
			// console.log(result);
					 
				 $('.replyList').append(result);
				 
				 if(callback) {
 					callback(result);
 				
 				}
				
			},
			error : function(xhr, status, err) {
				console.log(err);
			} 		
			
		});	
 	}  // ajax_getCommentReplyList(commentCd, callback, error)
 	
 	/* 사용X) 특정 코멘트의 댓글 목록 호출 메서드*/
 	function getList(param, callback, error) {
 		// param이라는 이름의 객체를 통해 필요한 파라미터 전달받도록 설계
 			// movieCd 이외 추후 추가적인 파라미터 더 필요한 경우 위함
 			
 			
 		var commentCd = param.commentCd;
 		
 		$.getJSON("/comments/" + commentCd + "/replies" + ".json",
 			function(ReplyListDTO) {
 				if(callback) {
 					// callback(ReplyListDTO);
 					callback(ReplyListDTO.replyList, ReplyListDTO.replyCnt); // replyListDTO 이용해 한번에 받기
 				}
 			}
 		).fail(function(xhr, status, err) {
 			if(error) {
 				error();
 			}
 		});		
 	}  // getList(param, callback, error)
 	
 	
 	
	/* 댓글 삭제 */
		// DELETE 방식으로 데이터 전달하므로 type : delete 지정
	function remove(replyCd, callback, error) {
		$.ajax({
			type: 'delete', 
			url : '/replies/' + replyCd,
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
	} // remove(replyCd, callback, error)
 	
 	
 	/* 댓글 수정 */ 
 	function update(reply, callback, error) {
 		
 		console.log("replyCd: " + reply.replyCd);
 		
 		$.ajax({
 			type: 'put',
 			url: '/replies/' + reply.replyCd,
 			data: JSON.stringify(reply),
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
 	} // update(reply, callback, error)
 	
 	
 	

 	/* 댓글 개수 조회 메서드 */
 	function getCntByCommentCd(commentCd, callback, error) {
	 	
	 	$.getJSON("/replies/count/" + commentCd + ".json",
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
 			getList: getList,
 			remove: remove,
 			update: update,
 			get: get,
 			getCntByCommentCd : getCntByCommentCd,
 			
 			ajax_getCommentReplyList: ajax_getCommentReplyList
	}; // add라는 변수명에 add 메서드 결과가 들어가는 형태
 	
 	
 	
 	
 })();
 
 