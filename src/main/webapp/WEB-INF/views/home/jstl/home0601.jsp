<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>HOME0601</title>
</head>
<body>
	<!-- 
		c:choose / c:when / c:otherwise는
		if와  else의 조건문과 같다.
		
		이때, if와 else if와 else if와 else의 조건문이 필요하다면
		c:when을 if와 else if의 조건문으로 사용 가능하다.
	 -->

	<c:choose>
		<c:when test="${member.gender == 'M' }">
			<p>남자</p>
		</c:when>
		<c:otherwise>
			<p>여자</p>
		</c:otherwise>
	</c:choose>
</body>
</html>