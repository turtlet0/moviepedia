<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<h1>Access Denied Page</h1>


<h2><c:out value="${SPRING_SECURITY_403_EXCEPTION.getMessage() }" /></h2>
	<!-- tip) Access Denied의 경우 403 에러 메세지가 발생. 
		-> jsp에선 HttpServlet Request 안에 SPRING_SECURITY_403_EXCEPTION 라는 이름으로
		AccessDeniedException 객체가 전달됨  -->
		
<h2><c:out value="${msg }" /></h2>

</body>
</html>