/**
 * 
 */
 
 console.log("Member Module....");
 
 // 즉시 실행함수 및 {} 이용해 객체 구성
 	// 즉시실행함수 : () 안에 메서드 선언하고 바깥쪽에서 바로 e실행해버림
 	// -> 함수 실행결과가 바깥쪽에 선언된 변수에 할당]
 	// ex) 메서드 리턴 결과로 name이라는 속성에 'AAA'라는 속성값 가진 객체가 commentService 객체에 할당되는 것

 var memberService = (function(){
 	
 	
 	
 	/* 코멘트 수정 */ 
 	function update(memberVO, callback, error) {
 		
 		console.log("memberVO",memberVO);
 		
 		
 		$.ajax({
 			type: 'put',
 			url: '/rest/member/update/profile.json',
 			data: JSON.stringify(memberVO),
 			contentType: "application/json; charset=utf-8",
 			success : function(memberVO, status, xhr){
 				console.log("s memberVO: " + memberVO);
 				if(callback) {
	 				console.log("c memberVO: " + memberVO);
 					callback(memberVO);
 				}
			},
			error : function(xhr, status, err) {
				console.log("js. error: " + err);
				if(error) {
					error();
				}
			}
 		});
 	} // update(memberVO, callback, error)
 	
 	
 	



 	
 	// return {name:"AAA"};
 	return {
 			update: update
 			
	}; // add라는 변수명에 add 메서드 결과가 들어가는 형태
 	
 	
 	
 	
 })();
 
 