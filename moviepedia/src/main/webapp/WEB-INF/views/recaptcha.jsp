<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

 <meta id="_csrf" name="_csrf" th:content="${_csrf.token}"/>
 <meta id="_csrf_header" name="_csrf_header" th:content="${_csrf.headerName}"/>
 
 
<title>Insert title here</title>

<script src="https://code.jquery.com/jquery-3.4.1.min.js" integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo=" crossorigin="anonymous"></script>
    <script src="https://www.google.com/recaptcha/api.js?render=6Le52NoiAAAAAFYuLeEMMZT_eO4qfnlK4AVEtC3P"></script>
    
</head>
<body>

<form action="/robot" method="get">
    <input type="text" name="name" />
    <input type="text" name="g-recaptcha-response" id="g-recaptcha-response" />
    <input type="submit" value="submit" />
</form>

<script>
$(document).ready(function(){
	
	 var token = $("meta[name='_csrf']").attr("content");
	 var header = $("meta[name='_csrf_header']").attr("content");
	 
	 
    grecaptcha.ready(function() {
      grecaptcha.execute('6Le52NoiAAAAAFYuLeEMMZT_eO4qfnlK4AVEtC3P', {action: 'submit'}).then(function(token) {
         console.log(token)
         $.ajax({
            url: '${pageContext.servletContext.contextPath}/robot/token',
            type : 'post',
            dataType: 'json',
            data : {'token': token},
            ,beforeSend : function(xhr){
        		xhr.setRequestHeader(header, token);
        	},
            success : function(result){
                console.log(result);
            },
            fail: function(e){
                console.log("fail")
            }
          });// end ajax
      });
    });
});
</script>

<!-- 	<script src="https://www.google.com/recaptcha/api.js?render=공개키"></script> -->
<!-- 	grecaptcha.ready(function() {  -->
<!-- 		grecaptcha.execute('공개키', {action:'submit'}) -->
<!-- 		.then(function(token) {  -->
<!-- 			$.ajax({ type : "POST" -->
<!-- 				, url :"reCAPTCHA_URL" -->
<!-- 				, data : {"token" : token} -->
<!-- 				, success : function(data) {// 성공 처리 },  -->
<!-- 				error : function(request, status, msg) { // 실패 처리 }  -->
<!-- 			});  -->
<!-- 		}); -->
<!-- 	}); -->



</body>
</html>