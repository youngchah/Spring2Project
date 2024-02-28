<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>HOME0301</title>
</head>
<body>
	<p>type 속성을 date로 지정하여 날짜 포맷팅을 한다</p>
	<p>now : ${now }</p>
	<p>date default : <fmt:formatDate value="${now }" type="date" dateStyle="default"/> </p>
	<p>date short : <fmt:formatDate value="${now }" type="date" dateStyle="short"/> </p>
	<p>date medium : <fmt:formatDate value="${now }" type="date" dateStyle="medium"/> </p>
	<p>date long : <fmt:formatDate value="${now }" type="date" dateStyle="long"/> </p>
	<p>date full : <fmt:formatDate value="${now }" type="date" dateStyle="full"/> </p>
	<br/>
	
	
	<p>type 속성을 time으로 지정하여 날짜 포맷팅을 한다</p>
	<p>now : ${now }</p>
	<p>time default : <fmt:formatDate value="${now }" type="time" timeStyle="default"/> </p>
	<p>time short : <fmt:formatDate value="${now }" type="time" timeStyle="short"/> </p>
	<p>time medium : <fmt:formatDate value="${now }" type="time" timeStyle="medium"/> </p>
	<p>time long : <fmt:formatDate value="${now }" type="time" timeStyle="long"/> </p>
	<p>time full : <fmt:formatDate value="${now }" type="time" timeStyle="full"/> </p>
	<br/>
	
	<p>type 속성을 both으로 지정하여 날짜 포맷팅을 한다</p>
	<p>now : ${now }</p>
	<p>both default : <fmt:formatDate value="${now }" type="both" dateStyle="default" timeStyle="default"/> </p>
	<p>both short : <fmt:formatDate value="${now }" type="both" dateStyle="short" timeStyle="short"/> </p>
	<p>both medium : <fmt:formatDate value="${now }" type="both" dateStyle="medium" timeStyle="medium"/> </p>
	<p>both long : <fmt:formatDate value="${now }" type="both" dateStyle="long"  timeStyle="long"/> </p>
	<p>both full : <fmt:formatDate value="${now }" type="both" dateStyle="full" timeStyle="full"/> </p>
	<br/>
	

</body>
</html>













