<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>

 

</head>

<body class="bg-gradient-primary">

    
	<form role="form" class="user" method="post" action="/customLogout">
	                                      
		<a href="index.html" class="btn btn-primary btn-user btn-block btn-success">
			Logout
		</a>
		  
		<input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }">
		<!-- 이 EL의 이름은 실제 브라우저에서 _csrf라는 이름으로 처리됨
			value 값은 임의의값 지정됨
			브라우저 페이지 소스 보기에서 확인 가능 -->   
	</form>

    <!-- Bootstrap core JavaScript-->
    <script src="/resources/vendor/jquery/jquery.min.js"></script>


    <!-- Core plugin JavaScript-->
    <script src="/resources/vendor/jquery-easing/jquery.easing.min.js"></script>

 
    
    <script>
    $(".btn-success").on("click", function(e){
    	e.preventDefault();
    	$("form").submit();
    });
    </script>
    
    <c:if test="${param.logout != null }"></c:if>
	    <script type="text/javascript">
	   	$(document).ready(function(){
	   		alert("로그아웃하였습니다.");
	   		
	   	});
	    
	    </script>

</body>
</html>