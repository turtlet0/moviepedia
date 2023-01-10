<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<script src="https://code.jquery.com/jquery-3.4.1.min.js" integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo=" crossorigin="anonymous"></script>
<script src="https://www.google.com/recaptcha/api.js?render=6Le52NoiAAAAAFYuLeEMMZT_eO4qfnlK4AVEtC3P"></script>
    
</head>
<body>

<form  method="get">
    <input type="text" name="name" />
    <input type="text" name="g-recaptcha-response" id="g-recaptcha-response" />
    <input type="submit" value="submit" />
</form>

<script>
$(document).ready(function(){
	alert("a");
    grecaptcha.ready(function() {
      grecaptcha.execute('6Le52NoiAAAAAFYuLeEMMZT_eO4qfnlK4AVEtC3P', {action: 'login'}).then(function(token) {
         console.log(token)
         $.ajax({
            url: '${pageContext.servletContext.contextPath}/robot/token',
            type : 'POST',
            dataType: 'json',
            data : {'token': token},
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

</body>
</html>