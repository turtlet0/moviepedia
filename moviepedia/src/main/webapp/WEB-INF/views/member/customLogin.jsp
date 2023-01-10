<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%@ include file="../includes/header.jsp"%>

<main>
	<div class="section">
		<div class="center">
			<div class="innerFix">
				
				<div style="margin-left: 50px; margin-top: 70px;">
					<h2>로그인</h2>
					<!-- spring security Login Form : post 방식, /login URI, username/password 속성 이용, csrf값 전송 -->
					<form  role="form" class="user padding--h5" method="post" action="/login">
						<!-- 실제 로그인 처리는 /login 통해 처리됨 (POST방식으로 전달해야함) -->
						<!-- input 태그 name 속성 username, password 기본적으로 사용 -->
						<!-- csrf 태그값 hidden 으로 전달 -->
						
						
	<!-- 					<div class="form-group"> -->
	<!-- 						<input type="text" class="form-control form-control-user" -->
	<%-- 							name="username" placeholder="Email" value="${username }"> --%>
	<!-- 					</div> -->
	<!-- 					<div class="form-group"> -->
	<!-- 						<input type="password" class="form-control form-control-user" -->
	<%-- 							name="password" placeholder="Password" value="${password }"> --%>
	<!-- 					</div> -->
						
						<!-- 이메일 -->
						<div style="position: relative;" class="margin--bottom10">
							<label value="false"> <!-- label value : false 기본(처음상태, 커서올려논상태) true: 정상입력, 잘못입력 - css위한 클래스만 다름 -->
								<div class="clearfix">
									<div class="requiredInputParent float--left" >				
										<input id="emailValidation" class="validationInput" hidden="hidden" value="false">
										<input id="email" class="requiredInput" autocomplete="off" placeholder="이메일" type="text" name="username" value="">
									</div>
									<div  value="false" class="clearBtnParent float--left">
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
						<!-- 비밀번호 -->
						<div style="position: relative;" class="margin--bottom10">
							<label value="false"> <!-- label value : false 기본(처음상태, 커서올려논상태) true: 정상입력, 잘못입력 - css위한 클래스만 다름 -->			
								<div class="clearfix">
									<div class="requiredInputParent float--left" >				
										<input id="passwordValidation" class="validationInput" hidden="hidden" value="false">
										<input id="password" class="requiredInput" autocomplete="off" placeholder="비밀번호" type="password" name="password" value="">
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
						
						<div class="form-group  margin--bottom10">
							<div class="custom-control custom-checkbox small">
								<div>
									<input type="checkbox" id="remember-me" name="remember-me">
									<label for="remember-me">로그인 상태 유지</label>
								</div>
							</div>
						</div>
						

						<c:if test="${not empty errorMsg }">
							<font color="red">
								<p class="margin--bottom10">로그인에 실패하였습니다. <br/>
								${errorMsg }</p>
							</font>
							
							<c:set var="errorMsg" value="" />

						</c:if>

						<input   type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }">
						<a href="#" style="width:358px;" class="btn btn-success  margin--bottom10" >
							<p style="margin:0 auto;" class="font--bold">로그인 </p></a> 
							
						<!-- 이 EL의 이름은 실제 브라우저에서 _csrf라는 이름으로 처리됨
							value 값은 임의의값 지정됨
							브라우저 페이지 소스 보기에서 확인 가능 -->
					</form>
	
					<ul class="find_wrap padding--h5">
						<li class="margin--bottom10 ">비밀번호를
								잊어버리셨나요? <a href="user/findPassword" class="find_text font--bold">비밀번호 찾기</a></li>
						<li>계정이 없으신가요? <a href="/signUp" class="find_text font--bold"
							target="_blank">회원가입</a></li>
					</ul>
				</div>

				<h2>
					<c:out value="${error }"></c:out>
				</h2>
				<h2>
					<c:out value="${logout }"></c:out>
				</h2>
				<!-- 왜 Session에 값이 저장되지 않는지 이유 아직 못찾음 -->
<%-- 				<c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION}">     --%>
<!-- 					<font color="red">         -->
<!-- 						<p>Your login attempt was not successful due to <br /> -->
<%-- 						   ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message} --%>
<!-- 						</p>         -->
<%-- 						<c:remove var="SPRING_SECURITY_LAST_EXCEPTION" scope="session" />     --%>
<!-- 					</font> -->
<%-- 				</c:if> --%>
				


			</div>
		</div>
	</div>
</main>




<!-- Bootstrap core JavaScript-->
<script src="/resources/vendor/jquery/jquery.min.js"></script>


<!-- Core plugin JavaScript-->
<script src="/resources/vendor/jquery-easing/jquery.easing.min.js"></script>



<script>
$(document).ready(function(){
	
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
				
				$('#emailValidation').attr('value', 'true'); // validation true						
				$('#email').closest('label').children('p').remove(); // p remove
				$('#email').closest('label').find('.stateCheck').html(checkIcon); // stateCheck icon 교체
				console.log($('#email').closest('label').find('.stateCheck').val());
				
// 				$(this).closest('label').children('p').remove();
// 				$.ajax({
// 					url : '/rest/member/signUp/emailDupliCheck',
// 					data: {email: email},
// 					type: 'post',
// 					cache: false,
// 					success: function(result){
// 						if(result == 0) { /* 최종 true */
// 							console.log("최종 true");
// 							// tip) ajax 내에서 this 제대로 동작하지 않는 경우 발생
							
// 							$('#emailValidation').attr('value', 'true'); // validation true						
// 							$('#email').closest('label').children('p').remove(); // p remove
// 							$('#email').closest('label').find('.stateCheck').html(checkIcon); // stateCheck icon 교체
// 							console.log($('#email').closest('label').find('.stateCheck').val());
							
						

							
// 						} else {
							
// 							$('#emailValidation').attr('value', 'false'); // validation false
// 							$('#email').closest('label').find('.stateCheck').html(warningIcon); // stateCheck icon 교체
							
// 							$('#email').closest('label').children('p').remove(); // p remove
// 							if($('#email').closest('label').children('p').length < 1){ 
// 								var warningHtml = '<p>이미 가입된 이메일입니다.</p>';
// 								$('#email').closest('label').append(warningHtml); // p append
							
// 							} 

// 						}
// 					}
				
// 				});
						
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

/* 비밀번호 유효성 검사 */
$('#password').on('keyup', function(e){
//		e.stopPropagation();
	var password = $(this).val();
	
	console.log(password);
	
	if(password != ''){
		$(this).closest('label').attr('value', 'true'); // label true
		$(this).closest('label').find('.showPwBtn').parent('div').attr('value', 'true'); // showPwBtn true
		$(this).closest('label').find('.clearBtn').parent('div').attr('value', 'true'); // clearBtnt true
		$(this).closest('label').find('.stateCheck').parent('div').attr('value', 'true'); // stateCheck true
		
		$('#passwordValidation').attr('value', 'true'); // validation true												
		$(this).closest('label').children('p').remove(); // p remove
		$(this).closest('label').find('.stateCheck').html(checkIcon); // stateCheck icon 교체

	} else {
		$(this).closest('label').attr('value', 'false'); // label false
		$('#passwordValidation').attr('value', 'false'); // validation false
		$(this).closest('label').children('p').remove(); // p remove
		$(this).closest('label').find('.showPwBtn').parent('div').attr('value', 'false'); // showPwBtn true
		$(this).closest('label').find('.clearBtn').parent('div').attr('value', 'false'); // clearBtnt false
		$(this).closest('label').find('.stateCheck').parent('div').attr('value', 'false'); // stateCheck false
	}
	
});
	
	
	
	// submit
	$(".btn-success").on("click", function(e) {
		e.preventDefault();
		
 		/* if($('#emailValidation').val() == 'false'){
 			alert('이메일을 입력해 주세요.');
 			$('#email').focus();
 			return false;
 		}  */
		if($('#passwordValidation').val() == 'false'){
			alert('비밀번호를 입력해 주세요.');
			$('#password').focus();
			return false;
		} 
		
		
		
		
		$("form").submit();
	});
});
</script>
