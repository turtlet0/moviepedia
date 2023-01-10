<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../includes/header.jsp"%>

<main>
	<div class="section">
		<div class="center">
			<div class="innerFix">

				<div style="margin-left: 50px; margin-top: 70px;">
					<h2>비밀번호 초기화</h2>
					<div class="margin--bottom20 padding--h5">
						<p class="margin--bottom5">${userid } 회원님의 비밀번호를 다시 설정해주세요.<p>
						<p>앞으로 이 비밀번호로 로그인하시면 됩니다.</p>
					</div>
				

			

<!-- 				<form action="#"> -->
<!-- 						<div> -->
							
<!-- 						</div> -->
<!-- 						<button type="submit" class="css-13tbdpx-StylelessButton" disabled="disabled">확인</button> -->
<!-- 					</form> -->
					<form role="form" class="findPassword" padding--h5 method="post" action="/user/resetPassword">
	
						<!-- 비밀번호 변경 -->
						<div style="position: relative;" class="margin--bottom10">
							<label value="false"> <!-- label value : false 기본(처음상태, 커서올려논상태) true: 정상입력, 잘못입력 - css위한 클래스만 다름 -->			
								<div class="clearfix">
									<div class="requiredInputParent float--left" >				
										<input id="passwordValidation" class="validationInput" hidden="hidden" value="false">
										<input id="password" class="requiredInput" autocomplete="off" placeholder="새로운 비밀번호" type="password" name="userpw" value="">
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
						
						
						<input type="hidden" name="userid" value="${userid }">
						<input type="hidden" name="jwtToken" value="${jwtToken }">
						<input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }">
					
						<a href="#" style="width:358px;" class="btn  btn-success margin--bottom10">
							<p style="margin:0 auto;" class="font--bold">초기화</p></a> 
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
$(document).ready(function() {
	// 비밀번호 변경 Post 처리 결과 리턴
	var result = '<c:out value="${result}"/>'; // rttr.addFlashAttribute 통해 넘긴 파라미터
	if(result != ''){
		alert(result);
	}
	
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
	$('.clearBtn').on('click', function() {
		$(this).parent('div').siblings(
				'div.requiredInputParent')
				.children('input.requiredInput')
				.val('');
		$(this).closest('label').attr('value',
				'false'); // label true 	
		$(this).closest('label').find(
				'input.validationInput').attr(
				'value', 'false'); // validation false
		$(this).closest('label').children('p')
				.remove(); // p remove
		$(this).parent('div.clearBtnParent').attr(
				'value', 'false'); // clearBtnt false
		$(this).parent('div.clearBtnParent')
				.siblings('div.showPwBtnParent')
				.attr('value', 'false'); // showPwBtn false
		$(this).parent('div.clearBtnParent')
				.siblings('div.stateCheckParent')
				.attr('value', 'false'); // stateCheck false

	})
	
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
		
		
	});

	// Submit 버튼
	$(".btn-success").on("click", function(e) {
		e.preventDefault();

		if($('#passwordValidation').val() == 'false'){
			alert('비밀번호를 입력해 주세요.');
			$('#password').focus();
			return false;
		} 
	
		$("form").submit();
	});
	
});
</script>