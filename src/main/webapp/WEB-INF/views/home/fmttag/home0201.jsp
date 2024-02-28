<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>HOME0201</title>
</head>
<body>
	<p>coin : ${coin }</p>
	<!-- 기본적인  type 속성의 값은 number -->
	<fmt:parseNumber value="${coin }" var="coinNum"/>
	<p>coinNum : ${coinNum }</p>
</body>
</html>