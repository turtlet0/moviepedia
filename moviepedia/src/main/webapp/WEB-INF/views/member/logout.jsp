<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그아웃</title>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.0.0/jquery.min.js"></script>
</head>
<body>
<!-- 비밀번호변경/회원탈퇴 등의 과정 후 로그아웃 위한 GET 페이지 -->
<form id="logoutForm" action="/logout" method="POST">
   <input name="${_csrf.parameterName}" type="hidden" value="${_csrf.token}"/><!-- logout도 csrf 필수 -->
</form>
<script type="text/javascript">
$(document).ready(function(){
	$("#logoutForm").submit(); // 즉시 전송
});
</script>
</body>
</html>