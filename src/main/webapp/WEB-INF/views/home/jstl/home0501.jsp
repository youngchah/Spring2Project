<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>HOME0501</title>
</head>
<body>
	<c:if test="${member.hobbyArray == null }">
		<p>member.hobbyArray == null</p>
	</c:if>
	<c:if test="${member.hobbyArray eq null }">
		<p>member.hobbyArray eq null</p>
	</c:if>
	
	<p>test속성에 true나 false를 값으로 가지는 bool 타입의 변수가 올 수 있다.</p>
	<c:if test="${member.foreigner }">
		<p>member.foreigner == true</p>
	</c:if>
	
</body>
</html>