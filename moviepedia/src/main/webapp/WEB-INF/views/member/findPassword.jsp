<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../includes/header.jsp"%>

<main>
	<div class="section">
		<div class="center">
			<div class="innerFix">
				
				

				<div style="margin-left: 50px; margin-top: 70px;">
					<h2>비밀번호 재설정</h2>				
					<div class="margin--bottom20 padding--h5">
						<p class="font--bold margin--bottom5">비밀번호를 잊으셨나요?</p>
						<p class="margin--bottom5">가입했던 이메일을 적어주세요.</p>
						<p>입력하신 이메일 주소로 비밀번호 변경 메일을 보낼게요</p>
					</div>
				
					<form role="form" class="findPassword  padding--h5" method="post" action="/user/findPassword">
			
						<!-- 이메일 -->
						<div class="margin--bottom10">
							<label value="false"> <!-- label value : false 기본(처음상태, 커서올려논상태) true: 정상입력, 잘못입력 - css위한 클래스만 다름 -->
								<div class="clearfix">
									<div class="requiredInputParent float--left" >				
										<input id="emailValidation" class="validationInput" hidden="hidden" value="false">
										<input id="email" class="requiredInput" autocomplete="off" placeholder="이메일" type="text" name="userid" value="">
									</div>
									<div value="false" class="clearBtnParent float--left">
										<div class="clearBtn"><i class="fa-regular fa-circle-xmark fa-sm"></i></div>
									</div>
									<div value="false" class="stateCheckParent float--left">
										<div class="stateCheck"><i class="fa-solid fa-circle-exclamation fa-sm"></i></div>
			<!-- 							<i class="fa-regular fa-circle-check fa-sm"></i> -->
									</div>
								</div>
			<!-- 					<p class="warning">정확하지 않은 -->
			<!-- 							이메일 형식입니다. 이미 가입된 이메일입니다.</p> -->
								
							</label>
						</div>
						
	<!-- 					<button type="button">회원가입</button> -->
	<!-- 					<input type="button" class="btn btn-primary btn-user btn-block btn-success" value="회원가입" onclick="a();"/> -->
						<a href="#" style="width:358px;" class="btn  btn-success margin--bottom10">
							<p style="margin:0 auto;" class="font--bold">재설정 이메일 전송</p></a> 
						<input type="hidden" name="${_csrf.parameterName }"
							value="${_csrf.token }">
						<!-- 이 EL의 이름은 실제 브라우저에서 _csrf라는 이름으로 처리됨
						value 값은 임의의값 지정됨
						브라우저 페이지 소스 보기에서 확인 가능 -->
					</form>
				</div>

			</div>
		</div>
	</div>
</main>


<script type="text/javascript">
$(document).ready(function(){
// 	function a(){
// 		return false;
// 	}
	// 아이콘 이미지
	
	var warningIcon = '<i class="fa-solid fa-circle-exclamation fa-sm"></i>';
	var checkIcon = '<i class="fa-regular fa-circle-check fa-sm"></i>';
	

	
/* clear Btn 제어 */
$('.clearBtn').on('click', function(){
	$(this).parent('div').siblings('div.requiredInputParent').children('input.requiredInput').val('');
	$(this).closest('label').attr('value', 'false'); // label true 	
	$(this).closest('label').find('input.validationInput').attr('value', 'false'); // validation false
	$(this).closest('label').children('p').remove(); // p remove
	$(this).parent('div.clearBtnParent').attr('value', 'false'); // clearBtnt false
	$(this).parent('div.clearBtnParent').siblings('div.showPwBtnParent').attr('value', 'false'); // showPwBtn false
	$(this).parent('div.clearBtnParent').siblings('div.stateCheckParent').attr('value', 'false'); // stateCheck false
	
	
})

	

	
/* 이메일 유효성 검사 */
	$('#email').on('keyup', function(e){
// 		e.stopPropagation();
		var email = $(this).val();
		console.log(email);

		var emailReg = /[\S]+@[\S]+\.[\S]+/g;
		var spaceReg = /\s/g;
		
		if(email != ''){
			$(this).closest('label').attr('value', 'true'); // label true
			$(this).closest('label').find('.clearBtn').parent('div').attr('value', 'true'); // clearBtnt true
			$(this).closest('label').find('.stateCheck').parent('div').attr('value', 'true'); // stateCheck true
			
			if(emailReg.test(email) && ! email.match(/\s/g)){
				/* 최종 true */
				
				$(this).closest('label').children('p').remove();
				$('#emailValidation').attr('value', 'true'); // validation true						
				$(this).closest('label').find('.stateCheck').html(checkIcon); // stateCheck icon 교체
				
				
			} else {
				$('#emailValidation').attr('value', 'false'); // validation false
				$(this).closest('label').find('.stateCheck').html(warningIcon); // stateCheck icon 교체
				
				$(this).closest('label').children('p').remove(); // p remove
				if($(this).closest('label').children('p').length < 1){
 					var warningHtml = '<p class="warning-text">정확하지 않은 이메일 형식입니다.</p>';
 					$(this).closest('label').append(warningHtml); // p append
 					console.log($(this).closest('label'));
				} 
			}
			
		} else {
			$(this).closest('label').attr('value', 'false'); // label false
			$('#emailValidation').attr('value', 'false'); // validation false		
			$(this).closest('label').children('p').remove(); // p remove
			$(this).closest('label').find('.clearBtn').parent('div').attr('value', 'false'); // clearBtnt false
			$(this).closest('label').find('.stateCheck').parent('div').attr('value', 'false'); // stateCheck false
		}
		
		
	});
	
	
	

	
	
	
	$(".btn-success").on("click", function(e) {
		e.preventDefault();
		
		var email = $('#email').val();
		
		if($('#emailValidation').val() == 'false'){
			alert('이메일을 입력해 주세요.');
			$('#email').focus();
			return false;
		} 
		
		/* 입력한 이메일 존재 여부 확인 */
		$.ajax({
			url : '/rest/member/signUp/emailDupliCheck',
			data: {email: email},
			type: 'post',
			cache: false,
			success: function(result){
				if(result == 1) { /* 최종 true */
					console.log("최종 true");
					// tip) ajax 내에서 this 제대로 동작하지 않는 경우 발생
					
					/* JWT 토큰 생성 */
					
					
					/* 비밀번호 변경 메일 발송 */			
					var emailJObj = {email: email};
					
					$.ajax({
						url : '/rest/member/findPassword/passwordReset',
						data: JSON.stringify(emailJObj), // JSON.stringify: JavaScript 값이나 객체를 JSON 문자열로 변환
						type: 'post',
						cache: false,
						contentType: "application/json; charset=utf-8", // json 형식으로 data 전송 -> Contoller에서 consumes 속성을 json 형식으로 일치시켜줘야함
							// error) SyntaxError: Unexpected number in JSON at position 1 에러 대응을 위해 json 형식으로 data 전송함
						success: function(result){				
							console.log('result: '+result[0]);
							
							alert('비밀번호 재설정 이메일을 보냈습니다.\n이메일을 확인해주세요.');
							
							$("form").submit();
							
						
						},
						error: function(request, status, error){
							
							console.log("code: " + request.status+"\n"+"message: " + request.responseText+"\n"+"error: "+error);
							alert('비밀번호 재설정 이메일 전송에 실패했습니다. 다시 시도해 주세요.');
						}
					
					});
					
		
					
				} else {
					
					alert(email+"은 가입되지 않은 이메일입니다.");
				}
			}
		
		});

		
		
	});
});
</script>





