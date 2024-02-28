<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
		String[] hobbyArray = {"Music","Movie"};
	%>
	<!-- 
		EL에서 발생한 에러가 아니라, 자바식에서 발생한 에러 정보는 ex과 같은 변수에 담아서
		에러 정보를 출력할 수 있다.
		EL자체에서 발생하는 에러는 우리가 컨트롤 하기 어려우므로, 버깅이 쉽지 않다
		오타에서 발생하는 에러가 많으니 꼼꼼하게 체크!
	 -->
	<c:catch var="ex">
		<%=hobbyArray[3] %>
	</c:catch>
	<p>
		<c:if test="${ex != null }">
			${ex }
		</c:if>
	</p>
</body>
</html>