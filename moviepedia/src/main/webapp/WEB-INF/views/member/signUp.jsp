<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../includes/header.jsp"%>

<main>
	<div class="section">
		<div class="center">
			<div class="innerFix">
				
				
				<div style="margin-left: 50px; margin-top: 70px;">
					<h2>회원가입</h2>
					<form role="form" class="user padding--h5" method="post" action="signUp">
					
						<!-- 이름 -->
						<div class="margin--bottom10">
							<label value="false" > <!-- label value : false 기본(처음상태, 커서올려논상태) true: 정상입력, 잘못입력 - css위한 클래스만 다름 -->
								<div class="clearfix">
									<div class="requiredInputParent float--left" >				
										<input id="userNameValidation" class="validationInput" hidden="hidden" value="false">
										<input id="userName" class="requiredInput" autocomplete="off" placeholder="이름" type="text" name="userName" value="">
									</div>
									<div value="false" class="clearBtnParent float--left">
										<div class="clearBtn"><i class="fa-regular fa-circle-xmark fa-sm"></i></div>
									</div>
									<div value="false" class="stateCheckParent float--left">
										<div class="stateCheck"><i class="fa-solid fa-circle-exclamation fa-sm"></i></div>
			<!-- 							<i class="fa-regular fa-circle-check fa-sm"></i> -->
									</div>
								</div>
								
		<!-- 						<p class="warning">이름은 최소 2자리 이상이어야 합니다.</p> -->
							</label>
						</div>
						
						
						<!-- 이메일 -->
						<div class="margin--bottom10">
							<label value="false"> <!-- label value : false 기본(처음상태, 커서올려논상태) true: 정상입력, 잘못입력 - css위한 클래스만 다름 -->
							
								<div class="clearfix">
									<div class="requiredInputParent float--left" >				
<!-- 										<input id="emailValidation" class="validationInput" hidden="hidden" value="false"> -->
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
						
						<!-- 메일 본인 인증 -->
						<div class="margin--bottom10">					
							<label>
								<div class="clearfix">
									<div class="float--left border-box margin--right5">
										<button id="emailSendBtn" type="button" disabled="disabled" >본인인증<br>메일 전송</button>
									</div>
									<div class="float--left">
										<input id="emailCode" class="requiredInput requiredInput emailCodeInput" type="text" placeholder="인증번호 6자리를 입력해주세요." disabled="disabled" maxlength="6">
										<input id="emailVerification" class="validationInput " hidden="hidden" value="false">
											<!-- 유효성 검사는 Validation / 본인 인증은 Verification을 사용하나 최종 상태 hidden input class는 validationInput으로 통일 -->
									</div>
									<div class="float--left  border-box margin--right5">
										<button id="emailVerificationBtn" type="button">본인인증<br>확인</button>
									</div>					
								</div>
							</label>
						</div>
						
						<!-- 비밀번호 -->
						<div style="position: relative;" class="margin--bottom10">
							<label value="false"> <!-- label value : false 기본(처음상태, 커서올려논상태) true: 정상입력, 잘못입력 - css위한 클래스만 다름 -->			
								<div class="clearfix">
									<div class="requiredInputParent float--left" >				
										<input id="passwordValidation" class="validationInput" hidden="hidden" value="false">
										<input id="password" class="requiredInput" autocomplete="off" placeholder="비밀번호" type="password" name="userpw" value="">
									</div>
		<!-- 							<div class="btnParent"> -->
									<div value="false" class="showPwBtnParent float--left">
										<div class="showPwBtn"><i class="fa-regular fa-eye fa-sm"></i></div>
										<!-- <i class="fa-regular fa-eye-slash fa-sm"></i> -->
									</div>
									<div value="false" class="clearBtnParent float--left">
										<div class="clearBtn with-showPwBtn"><i class="fa-regular fa-circle-xmark fa-sm"></i></div>
									</div>
									<div value="false" class="stateCheckParent float--left">
										<div class="stateCheck with-showPwBtn"><i class="fa-solid fa-circle-exclamation fa-sm"></i></div>
			<!-- 							<i class="fa-regular fa-circle-check fa-sm"></i> -->
									</div>
		<!-- 							</div> -->
								</div>
			<!-- 					<p class="warning">비밀번호는 영문, -->
			<!-- 							숫자, 특수문자 중 2개 이상을 조합한 10~16자여야 합니다. </p> -->
							</label>
						</div>
						
						<!-- 비밀번호 재확인 -->
						<div style="position: relative;" class="margin--bottom10">
							<label value="false"> <!-- label value : false 기본(처음상태, 커서올려논상태) true: 정상입력, 잘못입력 - css위한 클래스만 다름 -->
								<div class="clearfix">
									<div class="requiredInputParent float--left" >				
										<input id="passwordCheckValidation" class="validationInput" hidden="hidden" value="false">
										<input id="passwordCheck" class="requiredInput" autocomplete="off" placeholder="비밀번호 재확인" type="password" name="userpwCheck" value="">
									</div>
									<div value="false" class="showPwBtnParent float--left">
										<div class="showPwBtn"><i class="fa-regular fa-eye fa-sm"></i></div>
										<!-- <i class="fa-regular fa-eye-slash fa-sm"></i> -->
									</div>
									<div value="false" class="clearBtnParent float--left">
										<div class="clearBtn with-showPwBtn"><i class="fa-regular fa-circle-xmark fa-sm"></i></div>
									</div>
									<div value="false" class="stateCheckParent float--left">
										<div class="stateCheck with-showPwBtn"><i class="fa-solid fa-circle-exclamation fa-sm"></i></div>
			<!-- 							<i class="fa-regular fa-circle-check fa-sm"></i> -->
									</div>
								</div>
			
			<!-- 					<p class="warning">비밀번호가 일치하지 않습니다.</p> -->
							</label>
						</div>
						
						
	<!-- 					<button type="button">회원가입</button> -->
	<!-- 					<input type="button" class="btn btn-primary btn-user btn-block btn-success" value="회원가입" onclick="a();"/> -->
						<a href="#" style="width:358px;" class="btn btn-primary btn-user btn-block btn-success  margin--bottom10">
							<p style="margin:0 auto;" class="font--bold">회원가입</p></a> 
						<input type="hidden" name="${_csrf.parameterName }"
							value="${_csrf.token }">
						<!-- 이 EL의 이름은 실제 브라우저에서 _csrf라는 이름으로 처리됨
						value 값은 임의의값 지정됨
						브라우저 페이지 소스 보기에서 확인 가능 -->
					</form>
				
					
					<ul class="find_wrap padding--h5"">
						<li>이미 가입 하셨나요? <a href="/login" class="find_text font--bold" target="_blank">로그인</a></li>
					</ul>
				</div>



			</div>
		</div>
	</div>
</main>

<!-- Bootstrap core JavaScript-->
<script src="/resources/vendor/jquery/jquery.min.js"></script>


<!-- Core plugin JavaScript-->
<script src="/resources/vendor/jquery-easing/jquery.easing.min.js"></script>

<script type="text/javascript">
function a(){
	console.log('a');
	return true;
}
</script>

<script type="text/javascript">
$(document).ready(function(){
// 	function a(){
// 		return false;
// 	}
	// 아이콘 이미지
	var showPwIcon = '<i class="fa-regular fa-eye fa-sm"></i>';
	var showPwSlashIcon = '<i class="fa-regular fa-eye-slash fa-sm"></i>';
	var warningIcon = '<i class="fa-solid fa-circle-exclamation fa-sm"></i>';
	var checkIcon = '<i class="fa-regular fa-circle-check fa-sm"></i>';
	
/* showPwBtn 제어 */
$('.showPwBtn').on('click', function(){
	var requiredInput = $(this).parent('div').siblings('div.requiredInputParent').children('input.requiredInput');
	
	requiredInput.toggleClass('active');
	if(requiredInput.hasClass('active')){
		requiredInput.attr('type', 'text');
		$(this).html(showPwSlashIcon);
		
	} else {
		requiredInput.attr('type', 'password');
		$(this).html(showPwIcon);
	}
	
})
	
/* clear Btn 제어 */
$('.clearBtn').on('click', function(){
	$(this).parent('div').siblings('div.requiredInputParent').children('input.requiredInput').val('');
	$(this).closest('label').attr('value', 'false'); // label true 	
	$(this).closest('label').find('input.validationInput').attr('value', 'false'); // validation false
	$(this).closest('label').children('p').remove(); // p remove
	$(this).parent('div.clearBtnParent').attr('value', 'false'); // clearBtnt false
	$(this).parent('div.clearBtnParent').siblings('div.showPwBtnParent').attr('value', 'false'); // showPwBtn false
	$(this).parent('div.clearBtnParent').siblings('div.stateCheckParent').attr('value', 'false'); // stateCheck false
	
	// 비밀번호일 경우
	if($(this).parent('div').siblings('div.requiredInputParent').children('input.requiredInput').attr('id') == 'password'){
	
		// 비밀번호 재확인도 함께 false
		$('#passwordCheck').val(''); // input 값 초기화
		$('#passwordCheckValidation').closest('label').attr('value', 'false'); // label false
		$('#passwordCheckValidation').attr('value', 'false'); // validation false
		$('#passwordCheckValidation').closest('label').children('p').remove(); // p remove
		$('#passwordCheckValidation').parent('div.requiredInputParent').siblings('div.showPwBtnParent').attr('value', 'false'); // showPwBtn false
		$('#passwordCheckValidation').parent('div.requiredInputParent').siblings('div.clearBtnParent').attr('value', 'false'); // clearBtnt false
		$('#passwordCheckValidation').parent('div.requiredInputParent').siblings('div.stateCheckParent').attr('value', 'false'); // stateCheck false
	}
})

	
/* 이름 유효성 검사 */
	$('#userName').on('keyup', function(e){
// 		e.stopPropagation();
		var userName = $(this).val();
		console.log(userName);
		if(userName != ''){
			$(this).closest('label').attr('value', 'true'); // label true
			$(this).closest('label').find('.clearBtn').parent('div').attr('value', 'true'); // clearBtnt true
			$(this).closest('label').find('.stateCheck').parent('div').attr('value', 'true'); // stateCheck true
			
			
			
			if(userName.length < 2 || userName.length > 20){
				$('#userNameValidation').attr('value', 'false'); // validation false	
				
				$(this).closest('label').find('.stateCheck').html(warningIcon); // stateCheck icon 교체
				if($(this).closest('label').children('p').length < 1){ 
					var warningHtml = '<p class="warning-text">이름은 2~20자 사이여야 합니다.</p>';
					$(this).closest('label').append(warningHtml); // p append
				} 
				
			} else { /* 최종 true */	
				$('#userNameValidation').attr('value', 'true');  //a validation true	
				$(this).closest('label').children('p').remove(); // p remove
				$(this).closest('label').find('.stateCheck').html(checkIcon); // stateCheck icon 교체
			}
			
		} else {
			$(this).closest('label').attr('value', 'false'); // label true 			
			$('#userNameValidation').attr('value', 'false'); // validation false		
			$(this).closest('label').children('p').remove(); // p remove
			$(this).closest('label').find('.clearBtn').parent('div').attr('value', 'false'); // clearBtnt false
			$(this).closest('label').find('.stateCheck').parent('div').attr('value', 'false'); // stateCheck false
		}
		
		
	});
	
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
				
				$(this).closest('label').children('p').remove();
				$.ajax({
					url : '/rest/member/signUp/emailDupliCheck',
					data: {email: email},
					type: 'post',
					cache: false,
					success: function(result){
						if(result == 0) { /* 최종 true */
							console.log("최종 true");
							// tip) ajax 내에서 this 제대로 동작하지 않는 경우 발생
							
							$('#emailValidation').attr('value', 'true'); // validation true						
							$('#email').closest('label').children('p').remove(); // p remove
							$('#email').closest('label').find('.stateCheck').html(checkIcon); // stateCheck icon 교체
							console.log($('#email').closest('label').find('.stateCheck').val());
							
							// 본인 인증 메일 전송 버튼 열기
							$('#emailSendBtn').removeAttr('disabled');

							
						} else {
							
							$('#emailValidation').attr('value', 'false'); // validation false
							$('#email').closest('label').find('.stateCheck').html(warningIcon); // stateCheck icon 교체
							
							$('#email').closest('label').children('p').remove(); // p remove
							if($('#email').closest('label').children('p').length < 1){ 
								var warningHtml = '<p class="warning-text">이미 가입된 이메일입니다.</p>';
								$('#email').closest('label').append(warningHtml); // p append
							
							} 

						}
					}
				
				});
						
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
		
		// 이메일 본인 인증 완료 후 이메일 값 변경 시
		if($('#emailVerification').attr('value') == 'true'){
			
			$('#emailVerification').attr('value', 'false'); // validation false
			
			sendVerificationEmail = '';
			emailVerificationCode = '';
			
			$('#emailSendBtn').removeAttr('disabled'); // emailSendBtn 열기
			$('#emailCode').val(''); // emailCode input 초기화

			
			alert('입력하신 이메일 주소가 변경되었습니다. 본인인증 절차를 다시 진행해 주세요.');
		}
		
	});
	
/* 이메일 본인 인증 */

// <div class="float--left">
// 	<button id="emailSendBtn" type="button">본인인증 메일 전송</button>
// </div>
// <div class="float--left">
// 	<input id="emailCode" class="requiredInput" type="text" placeholder="인증번호 6자리를 입력해주세요." disabled="disabled" maxlength="6">
// 	<input id="emailVerification" class="validationInput" hidden="hidden" value="false">
// 		<!-- 유효성 검사는 Validation / 본인 인증은 Verification을 사용하나 최종 상태 hidden input class는 validationInput으로 통일 -->
// </div>
// <div class="float--left">
// 	<button id="emailVerificationBtn" type="button">본인인증 확인</button>
// </div>	
	
	var sendVerificationEmail = '';
	var emailVerificationCode = '';
	
	// 본인 인증 메일 전송 버튼 클릭 시
	$('#emailSendBtn').on('click', function(){
		
		
		if($('#emailValidation').attr('value') == 'true'){
			
			sendVerificationEmail = $('#email').val();
			
			alert('입력하신 이메일 주소로 본인 인증 메일을 전송 중입니다.\n전송에는 1분가량의 시간이 소요될 수 있습니다.');
			
			// 이메일 Input 잠구기
				// 잠그지 않고 인증 메일 전송한 email 값도 받아 본인 인증 확인 버튼 클릭 시 함께 체크
// 			$('#email').attr('disabled', 'disabled');
// 			$('#email').closest('label').find('.clearBtnParent').attr('value', 'false'); // clearBtnt false
// 			$('#email').closest('label').find('.stateCheckParent').attr('value', 'false'); // stateCheck false
			
			// 이메일 본인인증 Input 열기
			$('#emailCode').removeAttr('disabled');
			
			
// 			$('#emailCode').removeAttr('disabled'); // 본인인증 input 열기
// 			$('#emailVerificationBtn').show(); // 본인인증 확인 버튼 열기

			// 본인 인증 이메일 전송
			var emailJObj = {email: sendVerificationEmail};
			
			$.ajax({
				url : '/rest/member/signUp/emailVerification',
				data: JSON.stringify(emailJObj), // JSON.stringify: JavaScript 값이나 객체를 JSON 문자열로 변환
				type: 'post',
				cache: false,
				contentType: "application/json; charset=utf-8", // json 형식으로 data 전송 -> Contoller에서 consumes 속성을 json 형식으로 일치시켜줘야함
					// error) SyntaxError: Unexpected number in JSON at position 1 에러 대응을 위해 json 형식으로 data 전송함
				success: function(result){				
					console.log('result: '+result);
					
					emailVerificationCode = result;
					
					alert('인증 번호가 전송되었습니다.\n이메일 확인 후 인증 번호를 입력해 주세요.');
					console.log('emailVerificationCode: ' + emailVerificationCode);
					
					$('#emailCode').removeAttr('disabled'); // 본인인증 input 열기
					
					// 인증 메일 전송 완료 시
					if(emailVerificationCode != ''){
// 						$('#emailVerificationBtn').removeAttr('disabled'); // 본인인증 확인 버튼 열기
						$('#emailVerificationBtn').show(); // 본인인증 확인 버튼 열기
					}
				
				},
				error: function(request, status, error){
					
					$('#emailVerificationBtn').hide(); // emailVerificationBtn 숨기기
					
					console.log("code: " + request.status+"\n"+"message: " + request.responseText+"\n"+"error: "+error);
					alert('인증 이메일 전송에 실패했습니다. 다시 시도해 주세요.');
				}
			
			});
			
			
		} else {
			alert('입력하신 이메일이 올바르지 않습니다. 다시 입력해 주세요.');
		}
	});
	
	// 본인인증 확인 버튼 클릭
	$('#emailVerificationBtn').on('click', function(e) {

		// 본인인증 확인 완료 여부 체크
		console.log($('#emailVerification').attr('value'));
		if( $('#emailVerification').attr('value') == 'false'){

			// 입력된 이메일과 전송한 이메일 주소 일치 여부 체크
			if(sendVerificationEmail == $('#email').val()){

				// 인증 번호 일치 체크
				if($('#emailCode').val() == emailVerificationCode){
					/* 최종 true */
					
					$('#emailVerification').attr('value', 'true'); // validation true
					
					$('#emailCode').attr('disabled', 'disabled'); // emailCode input 잠그기
					$('#emailSendBtn').attr('disabled', 'disabled'); // emailSendBtn 잠그기
					$('#emailVerificationBtn').hide(); // emailVerificationBtn 숨기기
					
					alert('이메일 본인 인증이 완료되었습니다.');
			
				} else {
					alert('입력하신 인증 번호가 틀립니다. 다시 정확히 입력해 주세요.');
				}
				
			} else {

				alert('입력하신 이메일과 본인 인증 메일을 전송한 이메일의 주소가 다릅니다. 다시 확인해 주세요.');
				console.log('inputEmail: '+ $('#email').val());
				console.log('sendVerificationEmail: '+ sendVerificationEmail);
			
			}
			
		}
		
	});
	
/* 비밀번호 유효성 검사 */
	$('#password').on('keyup', function(e){
// 		e.stopPropagation();
		var password = $(this).val();
		console.log(password);

		var eng = password.match(/[a-z]/ig);
		var num = password.match(/[0-9]/g);
		var spe = password.match(/[`~!@@#$%^&*|₩₩₩'₩";:₩/?]/gi);
		var passCombiTrueCnt = (eng?1:0) + (num?1:0) + (spe?1:0);
		
		console.log(eng+"/"+num+"/"+spe);
		console.log(passCombiTrueCnt);
		
		if(password != ''){
			$(this).closest('label').attr('value', 'true'); // label true
			$(this).closest('label').find('.showPwBtn').parent('div').attr('value', 'true'); // showPwBtn true
			$(this).closest('label').find('.clearBtn').parent('div').attr('value', 'true'); // clearBtnt true
			$(this).closest('label').find('.stateCheck').parent('div').attr('value', 'true'); // stateCheck true
			
			if((password.length >= 10 && password.length <=16) 
					&& (passCombiTrueCnt >= 2 && ! password.match(/\s/g))){
				/* 최종 true */
				
				$('#passwordValidation').attr('value', 'true'); // validation true												
				$(this).closest('label').children('p').remove(); // p remove
				$(this).closest('label').find('.stateCheck').html(checkIcon); // stateCheck icon 교체
				
						
			} else {
				$('#passwordValidation').attr('value', 'false'); // validation false
				$(this).closest('label').find('.stateCheck').html(warningIcon); // stateCheck icon 교체
				
				if($(this).closest('label').children('p').length < 1){
					var warningHtml = '<p class="warning-text">비밀번호는 영문, 숫자, 특수문자 중 2개 이상을 조합한 10~16자여야 합니다.</p>';
 					$(this).closest('label').append(warningHtml); // p append
				} 
			}
			
		} else {
			$(this).closest('label').attr('value', 'false'); // label false
			$('#passwordValidation').attr('value', 'false'); // validation false
			$(this).closest('label').children('p').remove(); // p remove
			$(this).closest('label').find('.showPwBtn').parent('div').attr('value', 'false'); // showPwBtn false
			$(this).closest('label').find('.clearBtn').parent('div').attr('value', 'false'); // clearBtnt false
			$(this).closest('label').find('.stateCheck').parent('div').attr('value', 'false'); // stateCheck false
		}
		
		// 비밀번호 재확인 크로스 체크 - 비밀번호 변경 시 
		var passwordCheck = $('#passwordCheck').val();
		if(passwordCheck != ''){
			
			if(password != passwordCheck){
				
				$('#passwordCheckValidation').attr('value', 'false'); // validation false
				$('#passwordCheckValidation').closest('label').find('.clearBtn').parent('div').attr('value', 'true'); // clearBtnt true
				$('#passwordCheckValidation').closest('label').find('.stateCheck').parent('div').attr('value', 'true'); // stateCheck true
				$('#passwordCheckValidation').closest('label').find('.stateCheck').html(warningIcon); // stateCheck icon 교체
				
				if($('#passwordCheck').closest('label').children('p').length < 1){
					
					var warningHtml = '<p class="warning-text">비밀번호가 일치하지 않습니다.</p>';
						$('#passwordCheck').closest('label').append(warningHtml); // p append
				} 
			} else {
				/* 최종 true */
					$('#passwordCheckValidation').attr('value', 'true'); // validation true	
					$('#passwordCheckValidation').closest('label').find('.stateCheck').html(checkIcon); // stateCheck icon 교체
					$('#passwordCheckValidation').closest('label').children('p').remove(); // p remove
			}
			
		}
		
	});
	
/* 비밀번호 재확인 유효성 검사 */
	$('#passwordCheck').on('keyup', function(e){
// 		e.stopPropagation();
		var password = $('#password').val();
		var passwordCheck = $(this).val();
		
		console.log(passwordCheck);
		
		if(password != ''){
			$(this).closest('label').attr('value', 'true'); // label true
			$(this).closest('label').find('.showPwBtn').parent('div').attr('value', 'true'); // showPwBtn true
			$(this).closest('label').find('.clearBtn').parent('div').attr('value', 'true'); // clearBtnt true
			$(this).closest('label').find('.stateCheck').parent('div').attr('value', 'true'); // stateCheck true
			
			if(password == passwordCheck){
				/* 최종 true */
				
				$('#passwordCheckValidation').attr('value', 'true'); // validation true												
				$(this).closest('label').children('p').remove(); // p remove
				$(this).closest('label').find('.stateCheck').html(checkIcon); // stateCheck icon 교체
						
			} else {
				$('#passwordCheckValidation').attr('value', 'false'); // validation false
				$(this).closest('label').find('.stateCheck').html(warningIcon); // stateCheck icon 교체
				if($(this).closest('label').children('p').length < 1){
					var warningHtml = '<p class="warning-text">비밀번호가 일치하지 않습니다.</p>';
 					$(this).closest('label').append(warningHtml); // p append
				} 
			}
			
		} else {
			$(this).closest('label').attr('value', 'false'); // label false
			$('#passwordCheckValidation').attr('value', 'false'); // validation false
			$(this).closest('label').children('p').remove(); // p remove
			$(this).closest('label').find('.showPwBtn').parent('div').attr('value', 'false'); // showPwBtn true
			$(this).closest('label').find('.clearBtn').parent('div').attr('value', 'false'); // clearBtnt false
			$(this).closest('label').find('.stateCheck').parent('div').attr('value', 'false'); // stateCheck false
		}
		
	});
	
	
	function validationCheck(){

// 		console.log($('#userNameValidation').val());
// 		if($('#userNameValidation').val() == 'false'){
// 			alter('이름을 입력해 주세요.');
			
// 			return false;
// 		} 
		return false;
	}
	
	
	
	$(".btn-success").on("click", function(e) {
		e.preventDefault();

		if($('#userNameValidation').val() == 'false'){
			alert('이름을 입력해 주세요.');
			$('#userName').focus();
			return false;
		}
 		if($('#emailValidation').val() == 'false'){
 			alert('이메일을 입력해 주세요.');
 			$('#email').focus();
 			return false;
 		} 
 		if($('#emailVerification').val() == 'false'){
 			alert('이메일 본인 인증을 완료해 주세요.');
 			return false;
 		} 
		if($('#passwordValidation').val() == 'false'){
			alert('비밀번호를 입력해 주세요.');
			$('#password').focus();
			return false;
		} 
 		if($('#passwordCheckValidation').val() == 'false'){
 			alert('비밀번호 재확인을 완료해 주세요.');
 			$('#passwordCheck').focus();
 			return false;
 		} 
		
		$("form").submit();
	});
});
</script>
