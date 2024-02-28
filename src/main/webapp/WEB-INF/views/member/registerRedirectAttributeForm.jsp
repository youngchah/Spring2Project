<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h3>RegisterRedirectAttributeForm</h3>
	<form action="/redirect/register" method="post">
		userId: <input type="text" name="userId"/> <br/>
		password: <input type="text" name="password"/> <br/>
		<input type="submit" value="전송"/> <br/>
	</form>

</body>
</html>